package com.lift;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Логика работы лифта
 */
public class Lift implements Runnable {

    public enum Status {UP, DOWN, STOP}

    private final int V = 1; //скорость движения
    private final int H = 4; //высота лифта и этажа


    private Integer currStage = 1; //текущий этаж. Начинаем всегда с первого
    private Status currStatus = Status.UP; //текущее состояние
    private Status prevStatus; //пред состояние
    private final int maxNumOfPerson = 10; // максимальное кол-во людей в лифте


    private int liftNum; // номер лифта

    private Building building;
    private List<Person> personListInLift = new LinkedList<>(); // список людей в лифте
    private int curPerInLift; //кол-во людей в лифте

    public Lift(Building building, int liftNum) {
        this.liftNum = liftNum;
        this.building = building;
    }

    private List<Person> getPersonInStage() {
        return building.Stage(currStage).getPersonList();
    }

    /**
     * Запуск лифта
     */
    @Override
    public void run() {
        while (isGoDown() || isGoUp()) {
            while (currStatus == Status.UP) {
                getPeople();
                Optional<Person> turnVip= personListInLift.stream()
                        .filter(Person::isVip)
                        .findAny();
                if (turnVip.isPresent()) {
                    vipPerson(turnVip.get());
                    continue;
                }
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

            while (currStatus == Status.DOWN) {
                getPeople();
                Optional<Person> turnVip= personListInLift.stream()
                        .filter(Person::isVip)
                        .findAny();
                if (turnVip.isPresent()) {
                    vipPerson(turnVip.get());
                    continue;
                }
                if (!isGoDown()){
                    currStatus = Status.UP;
                    break;
                }
                System.out.println("Этаж " + currStage + ". Лифт " + liftNum + " едет вниз...");
                try {
                    Thread.sleep(V * H * 30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                currStage--;
                pushPerson();
            }
        }
    }

    /**
     * Проверка на вызова лифта на верхних этажах
     */
    private boolean isGoUp() {
        return personListInLift.stream()
                .anyMatch(person -> person.getToStage() > currStage)
                ||
                building.getStages().stream()
                .anyMatch(stage -> stage.getPersonList().stream()
                        .anyMatch(person -> person.getCurrStage() > currStage));
    }

    /**
     * Проверка на вызова лифта на нижних этажах
     */
    private boolean isGoDown() {
        return personListInLift.stream()
                .anyMatch(person -> person.getToStage() < currStage)
                ||
                building.getStages().stream()
                .anyMatch(stage -> stage.getPersonList().stream()
                        .anyMatch(person -> person.getToStage() < currStage));
    }

    /**
     * Перемещения лифта при повороте ВИП ключа
     */
    public void vipPerson(Person haveVipKey) {
        int numOfStage = haveVipKey.getToStage();
        System.out.println("Повернут VIP ключ в лифте " + liftNum + " Движемся без остановок на " + numOfStage + " этаж");
        while (currStage != numOfStage) {
            if (currStage > numOfStage) {
                System.out.println("Этаж " + currStage + ". Лифт " + liftNum + " едет вниз ...");
                currStatus = Status.DOWN;
                currStage--;
                try {
                    Thread.sleep(V * H * 30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (currStage < numOfStage) {
                System.out.println("Этаж " + currStage + ". Лифт " + liftNum + " едет вверх ...");
                currStatus = Status.UP;
                currStage++;
                try {
                    Thread.sleep(V * H * 30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        pushPerson();
    }

    /**
     * Подбор людей на этаже
     */
    private synchronized void getPeople() {
        if (!getPersonInStage().isEmpty() && !isFull()) {
            System.out.println("Этаж " + currStage + ". Лифт " + liftNum + ". Подбираем людей");
            Iterator<Person> personIterator = getPersonInStage().iterator();
            while (personIterator.hasNext() && !isFull()) {
                personListInLift.add(personIterator.next());
                personIterator.remove();
                curPerInLift++;
            }
            System.out.println("В лифте " + liftNum + " " + curPerInLift + " людей. ");
        }
    }

    /**
     * Высадка пасажирова на этаже
     */
    private void pushPerson() {
        if (personListInLift.stream()
                .anyMatch(person -> person.getToStage() == currStage)) {
            personListInLift = personListInLift.stream()
                    .filter(person -> {
                if (person.getToStage() == currStage) {
                    curPerInLift--;
                    return false;
                }
                return true;
            })
                    .collect(Collectors.toList());

            System.out.println("Этаж " + currStage + ". Лифт " + liftNum + ". Высаживаю людей");
            System.out.println("В лифте " + liftNum + " " + curPerInLift + " людей. ");
        }
    }

    /**
     * Проверка на забитость лифта
     */
    private boolean isFull() {
        if (curPerInLift == maxNumOfPerson) {
            System.out.println("Лифт " + liftNum + " переполнен");
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
