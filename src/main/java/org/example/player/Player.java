package org.example.player;

import org.example.GameObject;

public abstract class Player extends GameObject {
    protected int playerX;
    protected int playerY;
    protected int speed;
    protected int hp;
    protected char shape;

    public Player(int x, int y) {
        this.playerX = x;
        this.playerY = y;
    }

    public void move(int dx, int dy) {
        playerX += dx * speed;
        playerY += dy * speed;
    }

    public Player(){
    }


    public Player(int playerX, int playerY, int speed, int hp) {
        this.playerX = playerX;
        this.playerY = playerY;
        this.speed = speed;
        this.hp = hp;
    }
}
