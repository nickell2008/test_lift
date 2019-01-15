package com.test;

import java.util.*;
import java.util.stream.Collectors;

public class Lift implements Runnable {

    public enum Status {UP, DOWN, STOP}

    private final int V = 1; //скорость движения
    private final int H = 4; //высота лифта и этажа


    private Integer currStage = 1; //текущий этаж. Начинаем всегда с первого
    private Status currStatus = Status.UP; //текущее состояние
    private Status prevStatus; //пред состояние
    private int maxNumOfPerson = 10;


    private int liftNum;

    private Building building;
    private List<Person> personListInLift = new LinkedList<>();
    private int curPerInLift;
    private int lastStage;

    public Lift(Building building, int liftNum) {
        this.liftNum = liftNum;
        this.building = building;
        this.lastStage = building.getNumOfStages() - 1;
    }

    private List<Person> getPersonInStage() {
        return building.getStage(currStage).getPersonList();
    }

    /**
     * Запуск лифта
     */
    public void run() {
        while (hasCall() || isGoDown() || isGoUp()) {
            while (currStatus == Status.UP) {
                getPeople();
                if (!isGoUp()) {
                    currStatus = Status.DOWN;
                    break;
                }
                System.out.println("Этаж " + currStage + ". Лифт " + liftNum + " едет вверх...");
                try {
                    Thread.sleep(V * H * 30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                currStage++;
                pushPerson();
            }
            if (!hasCall() && curPerInLift == 0) stop();
            while (currStatus == Status.DOWN) {
                getPeople();
                System.out.println("Этаж " + currStage + ". Лифт " + liftNum + " едет вниз...");
                try {
                    Thread.sleep(V * H * 30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                currStage--;
                pushPerson();
                if (!isGoDown()) currStatus = Status.UP;
            }
        }
    }

    private boolean isGoUp() {
        return personListInLift.stream().anyMatch(person -> person.getToStage() > currStage)
                || building.getStages().stream()
                .anyMatch(stage -> stage.getPersonList().stream().anyMatch(person -> person.getCurrStage() > currStage));
    }

    private boolean isGoDown() {
        return personListInLift.stream().anyMatch(person -> person.getToStage() < currStage)
                || building.getStages().stream()
                .anyMatch(stage -> stage.getPersonList().stream().anyMatch(person -> person.getToStage() < currStage));
    }

    private boolean hasCall() {
        return (building.getStages().stream().anyMatch(Stage::hasPeople));
    }

    private synchronized void getPeople() {
        if (!getPersonInStage().isEmpty() && !ifFull()) {
            System.out.println("Этаж " + currStage + ". Лифт " +liftNum+ ". Подбираем людей");
            Iterator<Person> personIterator = getPersonInStage().iterator();
            while (personIterator.hasNext() && !ifFull()) {
                personListInLift.add(personIterator.next());
                personIterator.remove();
                curPerInLift++;
            }
            System.out.println("В лифте "+liftNum+" " + curPerInLift + " людей. ");

        }
    }

    private void pushPerson() {
        if (personListInLift.stream().anyMatch(person -> person.getToStage() == currStage)) {
            personListInLift = personListInLift.stream().filter(person -> {
                if (person.getToStage() == currStage) {
                    curPerInLift--;
                    return false;
                }
                return true;
            })
                    .collect(Collectors.toList());

            System.out.println("Этаж " + currStage + ". Лифт "+liftNum+". Высаживаю людей");
            System.out.println("В лифте "+liftNum+" " + curPerInLift + " людей. ");
        }
    }

    private boolean ifFull() {
        if (curPerInLift == maxNumOfPerson) {
            System.out.println("Лифт "+liftNum+" переполнен");
            return true;
        }
        return false;
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
