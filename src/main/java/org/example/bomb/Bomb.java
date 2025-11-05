package org.example.bomb;

import org.example.GameObject;

public abstract class Bomb extends GameObject {
    protected int x;
    protected int y;
    protected int speed;   // 낙하 속도
    protected int damage;  // 폭발 시 피해량
    protected char symbol; // 콘솔 표시 문자
    protected boolean exploded; // 폭발 여부
    protected String shaping; // 폭발 여부

    public Bomb(int x, int y, char symbol, int speed, int damage, String shaping) {
        this.x = x;
        this.y = y;
        this.symbol = symbol;
        this.speed = speed;
        this.damage = damage;
        this.exploded = false;
        this.shaping = shaping;
    }

    /** 폭탄이 떨어지는 기본 로직 */
    public void fall() {
        if (!exploded) {
            y += speed;

            // 화면 아래 도달하면 폭발
            if (y >= mapSiezY - 1) {
                y = mapSiezY - 1;
                explode();
            }
        }
    }

    /** 매 프레임마다 호출되는 업데이트 */
    public void update() {
        if (!exploded) {
            fall();
            // 속도에 따라 데미지가 커짐
            damage = (int)(damage + speed * 0.5);
        }
    }

    /** 콘솔에서 출력할 때 사용할 위치 */
    public char getSymbol() {
        return exploded ? 'X' : symbol;
    }

    /** 폭발 시 처리 (각 폭탄마다 다름) */
    public abstract void explode();

    public int getX() { return x; }
    public int getY() { return y; }
    public int getDamage() { return damage; }
    public boolean isExploded() { return exploded; }



}
