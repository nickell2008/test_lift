package com.test;

public class Stage {

    Lift lift ;
    Integer numOfStage = 0;
    Integer numOfPerson;

    public Stage(Integer currentStage, Lift lift) {
        this.lift = lift;
        this.numOfStage = currentStage;
    }

    public Lift callLift(int numOfPerson) {
//        this.numOfPerson = this.numOfPerson+numOfPerson;
       return lift.call(numOfStage, numOfPerson);
    }

    public void goUpOnLift() {
    }

    public void goDownOnLift() {
    }

}
