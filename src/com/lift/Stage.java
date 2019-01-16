package com.lift;

import java.util.LinkedList;
import java.util.List;

/**
 * Этаж в здании
 */
public class Stage {


    Integer numOfStage; // номер этажа
    List<Person> personList = new LinkedList<>(); // список людей, ожидающих лифт
    private int lastStage;  // последний этаж здания


    public Stage(Integer currentStage, int lastStage) {
        this.lastStage = lastStage;
        this.numOfStage = currentStage;
    }

    /**
     * Вызов лифта
     *
     * @param goToStage на какой этаж ехать
     */
    public Person callLift(int goToStage) {
        Person newPerson = new Person(numOfStage, goToStage);
        personList.add(newPerson);
        return newPerson;
    }

    public int numOfPerson() {
        return personList.size();
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public boolean hasPeople() {
        return personList.size() != 0;
    }

    /**
     * Ехать на последний этаж
     */
    public Person toLastStage() {
        return callLift(lastStage);
    }

    /**
     * Ехать на первый этаж
     */
    public Person toFirstStage() {
        return callLift(1);
    }
}
