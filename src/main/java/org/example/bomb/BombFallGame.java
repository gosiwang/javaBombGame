package org.example.bomb;

import org.example.bomb.*;
import java.util.Scanner;

public class BombFallGame {
    public static void main(String[] args) throws InterruptedException {
        final int width = 10;
        final int height = 10;
        Scanner sc = new Scanner(System.in);

        System.out.println("🎯 폭탄 종류를 선택하세요!");
        System.out.println("1. 💣 일반 폭탄");
        System.out.println("2. 🔥 화염 폭탄");
        System.out.println("3. ☠️ 독 폭탄");
        System.out.print("번호 입력: ");
        int choice = sc.nextInt();

        Bomb bomb;
        switch (choice) {
            case 2 -> bomb = new FireBomb(4, 0);
            case 3 -> bomb = new PoisonBomb(4, 0);
            default -> bomb = new NormalBomb(4, 0);
        }

        // 폭탄 모양 변경 (글자 대신 이모지)
        String symbol = switch (bomb.getClass().getSimpleName()) {
            case "FireBomb" -> "🔥";
            case "PoisonBomb" -> "☠️";
            default -> "💣";
        };

        // 맵 초기화 및 시뮬레이션
        for (int tick = 0; tick < height; tick++) {
            String[][] map = new String[height][width];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    map[i][j] = "⬛"; // 빈칸
                }
            }

            bomb.update();

            // 폭탄이 범위 내에 있으면 표시
            if (!bomb.isExploded() && bomb.getY() < height) {
                map[bomb.getY()][bomb.getX()] = symbol;
            } else if (bomb.isExploded()) {
                map[height - 1][bomb.getX()] = "💥";
            }

            // 콘솔 클리어 효과 (줄바꿈으로 대체)
            System.out.print("\n\n=== Tick " + tick + " ===\n");

            // 맵 출력
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    System.out.print(map[i][j]);
                }
                System.out.println();
            }

            Thread.sleep(500);
        }

        System.out.println("\n💥 폭탄이 바닥에 도달했습니다!");
        sc.close();
    }
}
