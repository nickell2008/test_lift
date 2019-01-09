package com.test;

public class Main {

    public static void main(String[] args) throws InterruptedException {


        Lift lift2 = new Lift();

        lift2.call(1, 4);
        lift2.call(3, 1);
        lift2.call(4, 3);
        lift2.go();
        lift2.call(2,3);
        lift2.go();

    }
}
