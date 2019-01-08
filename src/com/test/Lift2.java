package com.test;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;

public class Lift2 {
    enum Status {UP, DOWN, STOP}

    Integer currStage = 1;
    Status currStatus;
    Status prevStatus;

    Set<Integer> call = new LinkedHashSet<>();
    Set<Integer> goUp = new LinkedHashSet<>();
    Set<Integer> goDown = new LinkedHashSet<>();

    public void go() {
        if (currStage == 1) currStatus = Status.UP;
        if (currStage == 4) currStatus = Status.DOWN;

        while (currStatus == Status.UP && currStage != 4) {

            if (call.contains(currStage)) {
                call.remove(currStage);
                System.out.println("Этаж " + currStage + ". Подбираем человека");
            }
            currStage++;
            if (goUp.contains(currStage)) {
                goUp.remove(currStage);
                System.out.println("Этаж " + currStage + ". Высаживаю человека");
            }
            if (goUp.isEmpty()) currStatus = Status.DOWN;
            else System.out.println("Этаж " + currStage + ". Лифт едет вверх");

        }

        while (currStatus == Status.DOWN && currStage != 1) {
            if (call.contains(currStage)) {
                call.remove(currStage);
                System.out.println("Этаж " + currStage + ". Подбираем человека");
            }
            if (goDown.isEmpty()) currStatus = Status.UP;
            else System.out.println("Этаж " + currStage + ". Лифт едет вниз");
            currStage--;
            if (goDown.contains(currStage)) {
                goDown.remove(currStage);
                System.out.println("Этаж " + currStage + ". Высаживаю человека");
            }


        }
    }

    public void call(Integer from, Integer to) {
        call.add(from);
        if (!goUp.isEmpty() && goUp.stream().max(Comparator.comparing(Integer::valueOf)).get() > to)
            goDown.add(to);
        else goUp.add(to);
    }

    public void stop() {
        if (currStatus == Status.STOP) {
            System.out.println("Лифт поехал");
            currStatus = prevStatus;
        } else {
            System.out.println("Лифт остановился");
            prevStatus = currStatus;
            currStatus = Status.STOP;
        }
    }
}
