package org.example.player;

import org.example.player.Player;

public class Tank extends Player{

    public Tank(int playerX, int playerY) {
        super(playerX, playerY);
        this.hp = 60;
        this.speed = 1;
        this.shape = '＠';
    }


}
