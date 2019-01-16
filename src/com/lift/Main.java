package com.lift;

public class Main {

    public static void main(String[] args)  {

        /**
         * Запуск 2х лифтов в одном доме
         * 10 этажей
         * 20 людей
         * 2 вип ключа
         */
        Building home = new Building(10);
        for(int i=0;i<12;i++)
        home.Stage(5).toLastStage();
        home.Stage(3).callLift(9).turnVipKey();
        for(int i=0;i<4;i++)
        home.Stage(4).callLift(1);
        home.Stage(7).callLift(2);
        home.Stage(3).callLift(5).turnVipKey();
        home.Stage(8).callLift(1);
        new Thread(new Lift(home,1)).start();
        new Thread(new Lift(home,2)).start();






    }
}
