package com.test;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Building home = new Building(10);
        for(int i=0;i<12;i++)
        home.getStage(5).toLastStage();
        home.getStage(3).callLift(9);
        home.getStage(4).callLift(1);
        home.getStage(7).callLift(2);
        home.getStage(3).callLift(5);
        home.getStage(8).callLift(1);
        Lift lift = new Lift(home);
        lift.go();





    }
}
