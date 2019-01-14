package com.test;

public class Person {
    private int currStage;
    private int toStage;

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
}
