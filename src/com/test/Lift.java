package com.test;


import java.util.LinkedList;
import java.util.List;


public class Lift {

    private int V = 1;
    private int H = 4;
    public int MAX_STAGE = 4;
    public List<Integer> goUp  = new LinkedList<>();
    public List<Integer> goDown  = new LinkedList<>();
    private Integer stage = 1;

    private Status currStatus ;
    private Status prevStatus ;
    enum Status{UP, DOWN, STOP}

    public void moveUp(){
        System.out.println("Лифт на "+stage+" этаже");
        while (!goUp.isEmpty()&&currStatus!=Status.STOP) {
            currStatus = Status.UP;
            System.out.println("Лифт едет вверх...");
            stage++;
            if (goUp.contains(stage)){
                System.out.println("Этаж " + stage+ ".Лифт останавливается.Двери открываются.");
                goUp.remove(stage);
            }
            if(stage==MAX_STAGE)  break;
        }
    }

    public void moveDown(){
        System.out.println("Лифт на "+stage+" этаже");
        while (!goDown.isEmpty()&&currStatus!=Status.STOP) {
            currStatus = Status.DOWN;
            System.out.println("Лифт едет вниз...");
            stage--;
            if (goDown.contains(stage)){
                System.out.println("Этаж " + stage+ ".Лифт останавливается.Двери открываются");
                goDown.remove(stage);
            }
            if(stage==1) { break;}

        }
    }
public void stop(){
        if(currStatus == Status.STOP){
            System.out.println("Лифт поехал");
            currStatus = prevStatus;}
        else {
            System.out.println("Лифт остановился");
            currStatus = Status.STOP;
        }
}
    public void go(){

            if (stage < 4 && !goUp.isEmpty()) moveUp();
            if (stage > 1 && !goDown.isEmpty()) moveDown();

    }
}
