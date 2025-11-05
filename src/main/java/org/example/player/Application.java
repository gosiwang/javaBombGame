package org.example.player;

import org.example.GameObject;

import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        Application app = new Application();

        app.map();

    }

    public void map() {
        int mapSizeX = GameObject.mapSizeX;
        int mapSizeY = GameObject.mapSizeY;
        Player playerObj;

        Scanner sc = new Scanner(System.in);
        System.out.println("직업을 선택 해주세요. 댄서,탱커,러너");
        String player = sc.nextLine();

        switch (player) {
            case "탱커":
                playerObj = new Tank(GameObject.mapSizeX / 2, GameObject.mapSizeY - 1);
                break;

            case "댄서":
                playerObj = new Dancer(GameObject.mapSizeX / 2, GameObject.mapSizeY - 1);
                break;

            case "러너":
                playerObj = new Runner(GameObject.mapSizeX / 2, GameObject.mapSizeY - 1);
                break;

            default:
                System.out.println("잘못 선택했습니다. 기본: 탱커 생성");
                playerObj = new Tank(GameObject.mapSizeX / 2, GameObject.mapSizeY - 1);
        }

        int boxX =GameObject.mapSizeX / 2;
        int boxY =0;

        for (int k = 0; k < 10; k++) {

            for (int i = 0; i < mapSizeY; i++) {
                for (int j = 0; j < mapSizeX; j++) {
                    if(boxX == playerObj.playerX && playerObj.playerY == boxY ) playerObj.hp -=1;

                    if (playerObj.playerX == j && playerObj.playerY == i) System.out.print(playerObj.shape);
                    if(boxX == j &&  boxY  == i) System.out.print("●");
                    else System.out.print("□");

                }
                System.out.println();
            }
            boxY++;
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();

        }

    }

}
