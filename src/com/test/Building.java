package com.test;

import java.util.ArrayList;
import java.util.List;

public class Building {


    private List<Stage> stages = new ArrayList<>();

    public Building(Integer numOfStages) {
        for (int i = 0; i <= numOfStages; i++)
            stages.add(new Stage(i, numOfStages));
    }

    public Stage getStage(int stage) {
        try {
            return this.stages.get(stage);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("В доме нет такого этажа! Максимум " + (stages.size() - 1) + " этажей");
            return stages.get(stages.size() - 1);
        }
    }

    public int getNumOfStages() {
        return stages.size();
    }

    public List<Stage> getStages() {
        return stages;
    }

    @Override
    public String toString() {
        return "Building{" +
                "stages=" + stages +
                '}';
    }
}
