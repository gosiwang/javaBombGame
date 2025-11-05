package org.example.player;

import org.example.player.Player;

public class Tank extends Player{

    public Tank(int playerX, int playerY) {
        super(playerX, playerY);
        this.hp = 5;
        this.speed = 1;
        this.shape = '＠';
    }


}
