package org.example;

import org.example.bomb.*;
import org.example.player.*;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GameManager {
    public static void main(String[] args) {
        GameManager game = new GameManager();
        game.start();
    }

    // 게임 설정
    private final int mapSizeX = GameObject.mapSizeX;
    private final int mapSizeY = GameObject.mapSizeY;
    private Player player;
    private List<Bomb> bombs = new ArrayList<>();
    private Random random = new Random();

    // 타이밍 관련
    private long lastTime = System.nanoTime();
    private long lastBombSpawn = System.nanoTime();
    private double bombSpawnInterval = 1.5; // 1.5초마다 폭탄 생성

    volatile boolean running = true;

    // ===== 게임 시작 =====
    public void start() {
        // GameObject의 mapSize 설정

        // 플레이어 선택
        selectPlayer();

        // 입력 스레드 시작
        Thread inputThread = new Thread(this::handleInput);
        inputThread.setDaemon(true);
        inputThread.start();

        // 게임 루프
        gameLoop();
    }

    // ===== 플레이어 선택 =====
    private void selectPlayer() {
        Scanner sc = new Scanner(System.in);
        System.out.println("┌──────────────────────────────┐");
        System.out.println("│   똥(폭탄) 피하기 게임!      │");
        System.out.println("└──────────────────────────────┘");
        System.out.println();
        System.out.println("직업을 선택하세요:");
        System.out.println("1. 탱커 (＠) - HP:60, Speed:1");
        System.out.println("2. 댄서 (＆) - HP:25, Speed:2");
        System.out.println("3. 러너 (♣) - HP:30, Speed:3");
        System.out.print("선택 (1-3): ");

        String choice = sc.nextLine();
        int startY = mapSizeY - 1;  // 맨 아래
        int startX = mapSizeX / 2;  // 가운데

        player = switch (choice) {
            case "2" -> new Dancer(startX, startY);
            case "3" -> new Runner(startX, startY);
            default -> new Tank(startX, startY);
        };

        System.out.println("\n" + player.getClass().getSimpleName() + " 선택! 게임 시작!\n");
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
    }

    // ===== 게임 루프 =====
    public void gameLoop() {
        while (running && player.getHp() > 0) {
            long currentTime = System.nanoTime();
            double deltaTime = (currentTime - lastTime) / 1_000_000_000.0;
            double bombDeltaTime = (currentTime - lastBombSpawn) / 1_000_000_000.0;

            // 0.1초마다 업데이트
            if (deltaTime >= 0.1) {
                lastTime = currentTime;
                update();
                render();
            }

            // 1.5초마다 폭탄 생성
            if (bombDeltaTime >= bombSpawnInterval) {
                lastBombSpawn = currentTime;
                spawnBomb();
            }
        }

        gameOver();
    }

    // ===== 폭탄 랜덤 생성 =====
    private void spawnBomb() {
        int x = random.nextInt(mapSizeX);  // 랜덤 위치
        int bombType = random.nextInt(3);   // 0, 1, 2 중 랜덤

        Bomb newBomb = switch (bombType) {
            case 0 -> new NormalBomb(x, 0);   // 💣 일반 폭탄
            case 1 -> new FireBomb(x, 0);     // 🔥 화염 폭탄
            case 2 -> new PoisonBomb(x, 0);   // ☠️ 독 폭탄
            default -> new NormalBomb(x, 0);
        };

        bombs.add(newBomb);
    }

    // ===== 업데이트 =====
    public void update() {
        // 폭탄 업데이트
        for (int i = bombs.size() - 1; i >= 0; i--) {
            Bomb bomb = bombs.get(i);
            bomb.update();

            // 충돌 체크
            if (bomb.getX() == player.getPlayerX() && bomb.getY() == player.getPlayerY()) {
                player.setHp(player.getHp() - bomb.getDamage());
                bomb.explode();
                bombs.remove(i);
                if(player.getHp() < 0) player.setHp(0);
                continue;
            }


            // 폭발했거나 화면 밖으로 나간 폭탄 제거
            if (bomb.isExploded() || bomb.getY() >= mapSizeY) {
                bombs.remove(i);
            }
        }
    }

    // ===== 렌더링 =====
    public void render() {
        // 화면 클리어
        for (int i = 0; i < 50; i++) System.out.println();

/*        System.out.println("┌────────────────────────────────────────────────────────────┐");
        System.out.println("│ HP: " + player.getHp() + " | Speed: " + player.getSpeed() + " | Bombs: " + bombs.size() + "                                │");
        System.out.println("└────────────────────────────────────────────────────────────┘");*/

        System.out.println("\n조작: A(왼쪽) D(오른쪽) Q(종료)");
        System.out.println("폭탄 종류: 💣(일반) 🔥(화염) ☠️(독)");

        // 맵 그리기
        for (int y = 0; y < mapSizeY; y++) {
            for (int x = 0; x < mapSizeX; x++) {
                boolean drawn = false;

                // 플레이어 그리기
                if (x == player.getPlayerX() && y == player.getPlayerY()) {
                    System.out.print(player.getShape());
                    drawn = true;
                }

                // 폭탄 그리기 (shaping 이모지 사용)
                if (!drawn) {
                    for (Bomb bomb : bombs) {
                        if (bomb.getX() == x && bomb.getY() == y) {
                            System.out.print(bomb.shaping);  // 💣, 🔥, ☠️ 이모지 출력
                            drawn = true;
                            break;
                        }
                    }
                }

                // 빈 공간
                if (!drawn) {
                    System.out.print("□");
                }
            }
            System.out.println();
        }

    }

    // ===== 입력 처리 =====
    public void handleInput() {
        try {
            Terminal terminal = TerminalBuilder.builder()
                    .system(true)
                    .jna(true)
                    .build();

            terminal.enterRawMode();

            while (running && player.getHp() > 0) {
                int ch = terminal.reader().read(10);
                if (ch == -1) continue;

                char key = Character.toLowerCase((char) ch);

                switch (key) {
                    case 'a' -> {
                        int newX = Math.max(0, player.getPlayerX() - player.getSpeed());
                        player.setPlayerX(newX);
                    }
                    case 'd' -> {
                        int newX = Math.min(mapSizeX - 1, player.getPlayerX() + player.getSpeed());
                        player.setPlayerX(newX);
                    }
                    case 'q' -> {
                        running = false;
                        terminal.close();
                        return;
                    }
                }
            }

            terminal.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== 게임 오버 =====
    private void gameOver() {
        System.out.println("\n\n");
        System.out.println("┌──────────────────────────────┐");
        System.out.println("│        GAME OVER!            │");
        System.out.println("│   최종 HP: " + player.getHp() + "               │");
        System.out.println("└──────────────────────────────┘");
        System.exit(0);
    }
}