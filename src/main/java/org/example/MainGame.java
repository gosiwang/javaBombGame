package org.example;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public class MainGame {
    public static void main(String[] args) {

        MainGame game = new MainGame();

        game.start();
    }

    int mapSizeY = 30;
    int mapSizeX = 10;
    private long lastTime = System.nanoTime();
    private int[][] map = new int[mapSizeX][mapSizeY];

    int playerX =mapSizeX-1;
    int playerY =mapSizeY/2;
    volatile boolean running = true;  // 입력 스레드 종료 제어용

    // ===== 시작 메서드 =====
    public void start() {
        // 입력 스레드를 별도로 실행
        Thread inputThread = new Thread(this::playerMove);
        inputThread.setDaemon(true);
        inputThread.start();

        // 렌더링 루프
        gameLoop();
    }




    public void gameLoop() {
        while (true) {

            long currentTime = System.nanoTime();
            double deltaTime = (currentTime - lastTime) / 1_000_000_000.0;

            if (deltaTime >= 1.0) { // 0.1초
                lastTime = currentTime;
                render();
            }
        }
    }

    public void update(){
        // 값 업데이트
    }

    public void render(){
        // 출력


        for(int i=0; i< map.length; i++){
            for(int j=0; j < map[i].length; j++){
                if(i == playerX && j == playerY) System.out.print("▼");
                else System.out.print("□");
            }
            System.out.println();

        }
        //System.out.println();
    }



    // ===== 입력 처리 =====
    public void playerMove() {
        try {
            Terminal terminal = TerminalBuilder.builder()
                    .system(true)
                    .jna(true)
                    .build();

            while (running) {
                int ch = terminal.reader().read(100);
                if (ch == -1) continue;

                char key = (char) ch;

                switch (key) {
                    case 'w' -> playerX = Math.max(0, playerX - 1);
                    case 's' -> playerX = Math.min(mapSizeX - 1, playerX + 1);
                    case 'a' -> playerY = Math.max(0, playerY - 1);
                    case 'd' -> playerY = Math.min(mapSizeY - 1, playerY + 1);
                    case 'q', 'Q' -> {
                        System.out.println("게임 종료!");
                        running = false;
                        terminal.close();
                        return;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
