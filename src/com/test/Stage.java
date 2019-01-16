package com.test;

import java.util.LinkedList;
import java.util.List;

public class Stage {


    Integer numOfStage;
    List<Person> personList = new LinkedList<>();
    private int lastStage;


    public Stage(Integer currentStage, int lastStage) {
        this.lastStage = lastStage;
        this.numOfStage = currentStage;
    }

    public void callLift(int goToStage) {
        personList.add(new Person(numOfStage, goToStage));

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

    public void turnVipKey() {

    }

   public void toLastStage(){
       personList.add(new Person(numOfStage, lastStage));
   }
    public void toFirstStage(){
        personList.add(new Person(numOfStage, 1));
    }
}
