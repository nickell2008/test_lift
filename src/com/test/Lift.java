package com.test;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class Lift {

    public enum Status {UP, DOWN, STOP}

    private final int V = 1;//скорость движения
    private final int H = 4;//высота лифта и этажа

    private Integer currStage = 1;//текущий этаж. Начинаем всегда с первого
    private Status currStatus;//текущее состояние
    private Status prevStatus;//пред состояние

    private Set<Integer> call = new HashSet<>();//список выозовов лифта
    private Set<Integer> goUp = new HashSet<>();//список этажей , для выхода из лифта при подьеме
    private Set<Integer> goDown = new HashSet<>();//список этажей , для выхода из лифта при спуске


    /**
     * Запуск лифта
     */
    public void go() throws InterruptedException {
        if (currStage == 1) currStatus = Status.UP;
        if (currStage == 4) currStatus = Status.DOWN;

        while (currStatus == Status.UP && currStage != 4 && !goUp.isEmpty()) {

            if (call.contains(currStage)) {
                call.remove(currStage);
                System.out.println("Этаж " + currStage + ". Подбираем человека");
            }

            System.out.println("Этаж " + currStage + ". Лифт едет вверх...");
            Thread.sleep(V * H * 1000);
            currStage++;

            if (goUp.contains(currStage)) {
                goUp.remove(currStage);
                System.out.println("Этаж " + currStage + ". Высаживаю человека");
            }

            if (goUp.isEmpty()) currStatus = Status.DOWN;
        }

        while (currStatus == Status.DOWN && currStage != 1 &&!goDown.isEmpty()) {

            if (call.contains(currStage)) {
                call.remove(currStage);
                System.out.println("Этаж " + currStage + ". Подбираем человека");
            }

            System.out.println("Этаж " + currStage + ". Лифт едет вниз...");
            Thread.sleep(V * H * 1000);
            currStage--;
            if (goDown.contains(currStage)) {
                goDown.remove(currStage);
                System.out.println("Этаж " + currStage + ". Высаживаю человека");
            }
            if (goDown.isEmpty()) currStatus = Status.UP;

        }
    }

    /**
     * Вызов лифта
     *
     * @param from с какого этажа вызов
     * @param to   куда ехать
     */
    public void call(Integer from, Integer to) {
        call.add(from);
        if (!goUp.isEmpty() && goUp.stream().max(Comparator.comparing(Integer::valueOf)).get() > to)
            goDown.add(to);
        else goUp.add(to);
    }

    /**
     * Остановить лифт
     */
    public void stop() {
        if (currStatus == Status.STOP) {
            System.out.println("Этаж " + currStage + ". Лифт поехал");
            currStatus = prevStatus;
        } else {
            System.out.println("Этаж " + currStage + ". Лифт остановился");
            prevStatus = currStatus;
            currStatus = Status.STOP;
        }
    }

    /**
     * Получить статус лифта
     */
    public Status getCurrStatus() {
        if (goUp.isEmpty() && goDown.isEmpty()) currStatus = Status.STOP;
        String status = currStatus == Status.DOWN ? "едет вниз" : currStatus == Status.UP ? "едет вверх" : "стоит";
        System.out.println("Лифт " + status);
        return currStatus;
    }

    /**
     * Получить значение текущего этажа
     */
    public Integer getCurrStage() {
        System.out.println("Лифт на " + currStage + " этаже");
        return currStage;
    }
}
