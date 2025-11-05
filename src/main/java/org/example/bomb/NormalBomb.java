package org.example.bomb;

public class NormalBomb extends Bomb {
    public NormalBomb(int x, int y) {
        super(x, y, 'B', 1, 10,"💣");
    }

    @Override
    public void explode() {
        exploded = true;
        System.out.println("💣 일반 폭탄이 터졌다! 피해: " + damage);
    }
}
