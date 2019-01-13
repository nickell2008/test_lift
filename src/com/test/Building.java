package com.test;

import java.util.ArrayList;
import java.util.List;

public class Building {

    Lift lift;
    private List<Stage> stages = new ArrayList<>();

    public Building(Integer numOfStages) {
        lift = new Lift(numOfStages);
        for (int i = 0; i <= numOfStages; i++)
            stages.add(new Stage(i, lift));
    }

    public Stage getStage(int stage) {
        try {
            return this.stages.get(stage);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("В доме нет такого этажа! Максимум " + (stages.size() - 1) + " этажей");
            return stages.get(stages.size() - 1);
        }
    }
    public void startLift(){
        try {
            lift.go();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
