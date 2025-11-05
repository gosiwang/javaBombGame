package org.example.player;

public class Runner extends Player{
    public Runner(int x, int y) {
        super(x, y);
        this.hp = 30;
        this.speed = 3;
        this.shape = '♣';
    }
}
