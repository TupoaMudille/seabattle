package org.example;

import java.rmi.Naming;
import java.util.Scanner;

public class CalcCli {
    public static void main(String[] args) {
        try {
            //connect
            String url = "rmi://localhost:2023/MyCalc";
            Calc remote = (Calc) Naming.lookup(url);
            //check if 3+ player
            if (remote.counterMaxPlayers() > 2) {
                System.out.println("2 player already, please, connect later");
                return;
            }
            //wait 5sec answer from server
            remote.ping();
            int s = 5;
            while (s > 0) {
                try {
                    if (remote.getMes() == 400) {
                        System.out.println("server:ok");
                        break;
                    }
                    s--;
                    Thread.sleep(1000L);    // 1000L = 1000ms = 1 second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (remote.getMes() != 400) {
                System.out.println("Something wrong with server");
                return;
            }
            //print empty sea
            System.out.println("Sea Battle!");
            System.out.println("Game Started");
            for (int n = 0; n < 10; n++) {
                for (int m = 0; m < 10; m++) {
                    System.out.print("[" + remote.seaPlayerTwo[m][n] + "]");
                }
                System.out.print("\n");
            }
            //set ships and print ready sea
            System.out.println("Please, set your ships");
            int[][] seaPlayerTwo = new int[10][10];
            int[][] seaPlayerOne = new int[10][10];
            Scanner in2 = new Scanner(System.in);
            for (int i = 0; i < 1; i++) {
                System.out.println("Enter type(1-4),x,y,direction(0-3)");
                int type = in2.nextInt();
                int x = in2.nextInt();
                int y = in2.nextInt();
                int direction = in2.nextInt();
                seaPlayerTwo = remote.setShip(seaPlayerTwo, type, x, y, direction);
            }
            remote.setSea(seaPlayerTwo);
            for (int n = 0; n < 10; n++) {
                for (int m = 0; m < 10; m++) {
                    System.out.print("[" + remote.getSea()[m][n] + "]");
                }
                System.out.print("\n");
            }
            while (remote.getMes() == 200) {
                Thread.sleep(1000L);
            }
            remote.ping();
            System.out.println("\n");
            for (int n = 0; n < 10; n++) {
                for (int m = 0; m < 10; m++) {
                    System.out.print("[" + remote.getSea()[m][n] + "]");
                }
                System.out.print("\n");
            }
            while (remote.checkWin(remote.getSea2()) & remote.checkWin(remote.getSea())) {

                if (remote.getMes() == 400) {
                    System.out.println("Your step: x,y");
                    int x = in2.nextInt();
                    int y = in2.nextInt();
                    seaPlayerOne = remote.getSea2();
                    seaPlayerOne = remote.hitShip(seaPlayerOne, 400, x, y);
                    remote.setSea2(seaPlayerOne);
                    System.out.println(remote.getMes());
                    if(!remote.isUpdate()) System.out.println("wait");
                }
                if (remote.getMes() == 200 & remote.isUpdate()) {
                    seaPlayerTwo = remote.getSea();
                    remote.printSea(seaPlayerTwo);
                    System.out.println("wait");
                    System.out.println(remote.getMes());
                }
                remote.changeUpd(false);

            }
            if (remote.checkWin(remote.getSea())) System.out.println("YOU WIN");
            else System.out.println("YOU LOSE");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}