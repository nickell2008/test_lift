package com.test;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return currStage == person.currStage &&
                toStage == person.toStage;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currStage, toStage);
    }

    @Override
    public String toString() {
        return "Person{" +
                "currStage=" + currStage +
                ", toStage=" + toStage +
                '}';
    }
}
