package com.test;

import java.util.*;

public class Lift {

    public enum Status {UP, DOWN, STOP}

    private final int V = 1; //скорость движения
    private final int H = 4; //высота лифта и этажа

    private int lastStage;

    private Integer currStage = 1; //текущий этаж. Начинаем всегда с первого
    private Status currStatus; //текущее состояние
    private Status prevStatus; //пред состояние
    private Integer maxNumOfPerson = 10;
    private int maxStageUp = 1;
    private int minStageDown = 4;

    private Set<Integer> call = new HashSet<>(); //список выозовов лифта
    private Set<Integer> stageList = new HashSet<>(); //список этажей , для выхода из лифта
    private List<Person> listPersons = new LinkedList<>();

    public Lift(int lastStage) {
        this.lastStage = lastStage;
    }

    /**
     * Запуск лифта
     */
    public void go() throws InterruptedException {


        if (currStage == 1) currStatus = Status.UP;
        if (currStage == lastStage) currStatus = Status.DOWN;
        System.out.println(currStatus);
        System.out.println("list stage " + stageList);
        System.out.println("list call " + call);

        while (currStatus == Status.UP) {
            if (call.contains(currStage)) {
                call.remove(currStage);
                System.out.println("Этаж " + currStage + ". Подбираем человека");
            }
            System.out.println("Этаж " + currStage + ". Лифт едет вверх...");
            Thread.sleep(V * H * 100);
            currStage++;
            if (stageList.contains(currStage)) {
                stageList.remove(currStage);
                System.out.println("Этаж " + currStage + ". Высаживаю человека");
            }

            if (maxStageUp == currStage) currStatus = Status.DOWN;
        }
        while (currStatus == Status.DOWN) {

            if (call.contains(currStage)) {
                call.remove(currStage);
                System.out.println("Этаж " + currStage + ". Подбираем человека");
            }

            System.out.println("Этаж " + currStage + ". Лифт едет вниз...");
            Thread.sleep(V * H * 100);
            currStage--;
            if (stageList.contains(currStage)) {
                stageList.remove(currStage);
                System.out.println("Этаж " + currStage + ". Высаживаю человека");
            }
            if(minStageDown==currStage) currStatus = Status.UP;
        }
    }

    public void goToStage(Integer stage) {
        System.out.print("Ехать на " + stage);

        stageList.add(stage);
    }

    /**
     * Вызов лифта
     *
     * @param from с какого этажа вызов
     */
    public Lift call(Integer from, Integer numOfPerson) {
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
        if (stageList.isEmpty()) currStatus = Status.STOP;
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
    public void addPassenger(Person person){
    listPersons.add(person);
    }
}
