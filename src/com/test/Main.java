package com.test;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Building home = new Building(4);
        home.getStage(3).callLift(2).goToStage(4);
        home.getStage(2).callLift(1).goToStage(3);
        home.getStage(3).callLift(1).goToStage(1);
        home.getStage(1).callLift(1).goToStage(2);
        home.getStage(3).callLift(1).goToStage(2);
        home.startLift();




    }
}
