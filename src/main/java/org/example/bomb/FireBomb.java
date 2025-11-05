package org.example.bomb;

public class FireBomb extends Bomb {
    public FireBomb(int x, int y) {
        super(x, y, 'F', 2, 15,"🔥");
    }

    @Override
    public void explode() {
        exploded = true;
        System.out.println("🔥 화염 폭탄 폭발! 피해: " + damage + " (추가 화상 데미지!)");
    }
}
