package com.test;

import static org.junit.Assert.*;

public class LiftTest {

    Lift lift = new Lift();
    @org.junit.Before
    public void setUp() throws Exception {
        lift.call(1, 4); //находится на 1м этаже, хочет ехать на 4 этаж
        lift.call(3, 2); //на 3м этаже, хочет ехать на 2 этаж
        lift.call(4, 1); //на 4м этаже, хочет ехать на 1 этаж
    }

    @org.junit.Test
    public void go() throws InterruptedException {
        lift.go();
        assertEquals((int)lift.getCurrStage(), 1);
    }

    @org.junit.Test
    public void stop() {
        lift.stop();
        assertEquals(Lift.Status.STOP, lift.getCurrStatus());
        lift.stop();
        assertNotEquals(Lift.Status.STOP, lift.getCurrStatus());

    }
}
