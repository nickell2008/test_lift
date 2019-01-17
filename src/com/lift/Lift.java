package com.lift;


import java.util.*;
import java.util.stream.Collectors;

/**
 * Логика работы лифта
 */
public class Lift implements Runnable {

    private final int V = 1; //скорость движения
    private final int H = 4; //высота лифта и этажа
    private final int time = 1000 * V * H;


    private Integer currStage = 1; //текущий этаж. Начинаем всегда с первого
    private final int maxNumOfPerson = 10; // максимальное кол-во людей в лифте
    private boolean isRun = true; //лифт работает


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

    private void setPersonInStage(List<Person> lastPerson) {
        building.Stage(currStage).setPersonList(lastPerson);
    }

    /**
     * Запуск лифта
     */
    @Override
    public void run() {
        while ((isGoDown() || isGoUp()) && isRun) {
            while (isGoUp()) {
                getPeople();
                if (checkVip()) {
                    continue;
                }
                goUp();
                pushPerson();
            }
            while (isGoDown()) {
                getPeople();
                if (checkVip()) {
                    continue;
                }
                goDown();
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
                                .anyMatch(person -> person.getCurrStage() < currStage));
    }

    /**
     * Проверка на вип персон
     *
     * @return
     */
    public boolean checkVip() {
        Optional<Person> turnVip = personListInLift.stream()
                .filter(Person::isVip)
                .findAny();
        if (turnVip.isPresent()) {
            vipPerson(turnVip.get());
            return true;
        }
        return false;
    }

    /**
     * Перемещения лифта при повороте ВИП ключа
     */
    public void vipPerson(Person haveVipKey) {
        int numOfStage = haveVipKey.getToStage();
        System.out.println("Повернут VIP ключ в лифте " + liftNum + " Движемся без остановок на " + numOfStage + " этаж");
        while (currStage != numOfStage) {
            if (currStage > numOfStage) {
                goDown();
            }
            if (currStage < numOfStage) {
                goUp();
            }
        }
        pushPerson();
    }

    /**
     * Работа лифта вниз
     */
    private void goDown() {
        System.out.println("Этаж " + currStage + ". Лифт " + liftNum + " едет вниз ...");
        currStage--;
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Работа лифта вверх
     */
    private void goUp() {
        System.out.println("Этаж " + currStage + ". Лифт " + liftNum + " едет вверх ...");
        currStage++;
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Подбор людей на этаже
     */
    private synchronized void getPeople() {
        if (!getPersonInStage().isEmpty() && isFull()) {
            System.out.println("Этаж " + currStage + ". Лифт " + liftNum + ". Подбираем людей");
            Iterator<Person> personIterator = getPersonInStage().iterator();
            while (personIterator.hasNext() && isFull()) {
                personListInLift.add(personIterator.next());
                personIterator.remove();
                curPerInLift++;
            }
            List<Person> lastPerson = new ArrayList<>();
            personIterator.forEachRemaining(lastPerson::add);
            System.out.println("Осталось людей  " + lastPerson.size() + " на " + currStage + " этаже");
            setPersonInStage(lastPerson);
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
            return false;
        }
        return true;
    }

    /**
     * Остановить лифт
     */
    public void stop() {
        if (isRun) isRun = false;
        else isRun = true;
    }


    /**
     * Получить значение текущего этажа
     */
    public Integer getCurrStage() {
        System.out.println("Лифт на " + currStage + " этаже");
        return currStage;
    }


}
