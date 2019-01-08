package com.test;

public class Main {

    public static void main(String[] args) {

//        Lift lift = new Lift();
//        lift.goUp.add(3);
//        lift.goUp.add(2);
//        lift.go();
//        lift.stop();
//        lift.goUp.add(4);
//
//        lift.go();
//        lift.stop();
//        lift.goDown.add(3);
//        lift.go();

        Lift2 lift2 = new Lift2();

        lift2.call(1, 4);
        lift2.call(3, 1);
        lift2.call(4, 3);
        lift2.go();

    }
}
