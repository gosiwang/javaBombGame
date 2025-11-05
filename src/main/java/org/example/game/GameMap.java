package org.example.game;

import org.example.GameObject;

public class GameMap {

    protected static int mapSizeX;
    protected static int mapSizeY;

    private long lastTime = System.nanoTime();

    public GameMap(int mapSizeX, int mapSizeY) {
        GameMap.mapSizeX = mapSizeX;
        GameMap.mapSizeY = mapSizeY;
    }

    public static int getMapSizeX() {
        return mapSizeX;
    }

    public static int getMapSizeY() {
        return mapSizeY;
    }

    public void gameLoop() {
        while (true) {
            long currentTime = System.nanoTime();
            double deltaTime = (currentTime - lastTime) / 1_000_000_000.0;

            if (deltaTime >= 1) { // 1 초마다 게임 실행
                lastTime = currentTime;
                update();
                render();
            }
        }
    }

    public void update() {
        // 값 업데이트 (추후 플레이어/폭탄 이동 들어올 부분)
    }

    public void render() {

        // 화면 클리어
        System.out.println("=== GAME MAP ===");

        for (int y = 0; y < mapSizeY; y++) {
            for (int x = 0; x < mapSizeX; x++) {
                System.out.print("□"); // 빈 공간 출력
            }
            System.out.println();
        }
        System.out.println();
    }
}
