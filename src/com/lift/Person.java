package com.lift;

/**
 * Класс, описывающий людей, который ездят на лифте
 */
public class Person {
    private int currStage; // на каком этаже находится человек
    private int toStage; // едет на этаж
    private boolean isVip = false; //имеет ВИП ключ

    public Person(int currStage, int toStage) {
        this.currStage = currStage;
        this.toStage = toStage;
    }

    public int getCurrStage() {
        return currStage;
    }

    public int getToStage() {
        return toStage;
    }

    public boolean isVip() {
        return isVip;
    }

    /**
     * Поворот ВИП ключа
     */
    public void turnVipKey() {
        this.isVip = true;
    }


}
