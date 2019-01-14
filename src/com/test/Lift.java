package com.test;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class Lift {

    public enum Status {UP, DOWN, STOP}

    private final int V = 1;//скорость движения
    private final int H = 4;//высота лифта и этажа

    private int lastStage;

    private Integer currStage = 1;//текущий этаж. Начинаем всегда с первого
    private Status currStatus;//текущее состояние
    private Status prevStatus;//пред состояние
    private Integer maxNumOfPerson = 10;
    private int lastCall;

    private Set<Integer> call = new LinkedHashSet<>();//список выозовов лифта
    private Set<Integer> goUp = new HashSet<>();//список этажей , для выхода из лифта при подьеме
    private Set<Integer> goDown = new HashSet<>();//список этажей , для выхода из лифта при спуске

    public Lift(int lastStage) {
        this.lastStage = lastStage;


    }

    /**
     * Запуск лифта
     */
    public void go() throws InterruptedException {

        while (true) {

            if (currStage == 1) currStatus = Status.UP;
            if (currStage == lastStage) currStatus = Status.DOWN;
            System.out.println(currStatus);
            System.out.println("list up " + goUp);
            System.out.println("list down " + goDown);
//            System.out.println("cur stage "+currStage);
            if (currStatus == Status.UP && currStage != lastStage && !goUp.isEmpty()) {

                if (call.contains(currStage)) {
                    call.remove(currStage);
                    System.out.println("Этаж " + currStage + ". Подбираем человека");
                }

                System.out.println("Этаж " + currStage + ". Лифт едет вверх...");
                Thread.sleep(V * H * 100);
                currStage++;

                if (goUp.contains(currStage)) {
                    goUp.remove(currStage);
                    System.out.println("Этаж " + currStage + ". Высаживаю человека");
                }

                if (goUp.isEmpty()) currStatus = Status.DOWN;
            } else if (currStatus == Status.DOWN && currStage != 1 && !goDown.isEmpty()) {

                if (call.contains(currStage)) {
                    call.remove(currStage);
                    System.out.println("Этаж " + currStage + ". Подбираем человека");
                }

                System.out.println("Этаж " + currStage + ". Лифт едет вниз...");
                Thread.sleep(V * H * 100);
                currStage--;
                if (goDown.contains(currStage)) {
                    goDown.remove(currStage);
                    System.out.println("Этаж " + currStage + ". Высаживаю человека");
                }
                if (goDown.isEmpty()) currStatus = Status.UP;

            } else break;
        }
    }

    public void goToStage(Integer stage) {
        System.out.print("Ехать на "+ stage);
//        int maxStageToUp = !goUp.isEmpty() ? goUp.stream().max(Comparator.comparing(Integer::valueOf)).get() : 0;
//        if (maxStageToUp < stage)
//            goUp.add(stage);
//        else goDown.add(stage);
        if(stage>lastCall){
            goUp.add(stage);
        }else goDown.add(stage);
    }


    public int getLastStage() {
        System.out.println("Последний этаж " + lastStage);
        return lastStage;
    }

    /**
     * Вызов лифта
     *
     * @param from с какого этажа вызов
     */
    public Lift call(Integer from, Integer numOfPerson) {
        lastCall = from;
        call.add(from);
        System.out.println("Лифт вызвали на " + from + " этаже");
        return this;
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
