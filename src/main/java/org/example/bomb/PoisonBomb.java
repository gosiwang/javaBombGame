package org.example.bomb;

public class PoisonBomb extends Bomb {
    public PoisonBomb(int x, int y) {
        super(x, y, 'P', 1, 12);
    }

    @Override
    public void explode() {
        exploded = true;
        System.out.println("☠️ 독 폭탄 폭발! 피해: " + damage + " (지속 피해 발생!)");
    }
}
