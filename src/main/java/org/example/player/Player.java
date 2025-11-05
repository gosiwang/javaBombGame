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

    // ===== Getter/Setter 추가 =====
    public int getPlayerX() { return playerX; }
    public int getPlayerY() { return playerY; }
    public int getSpeed() { return speed; }
    public int getHp() { return hp; }
    public char getShape() { return shape; }

    public void setPlayerX(int x) { this.playerX = x; }
    public void setPlayerY(int y) { this.playerY = y; }
    public void setHp(int hp) { this.hp = hp; }
}