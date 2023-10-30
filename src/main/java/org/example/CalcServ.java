package org.example;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

public class CalcServ {
    public static void main(String[] args) {
        //start server
        System.out.println("Launching server...");
        try {
            //reg server
            LocateRegistry.createRegistry(2023);
            CalcImpl srv = new CalcImpl();
            String url = "rmi://localhost:2023/MyCalc";
            Naming.rebind(url, srv);
            System.out.println("Sea Battle!");
            //wait client 60s
            System.out.println("Count of players:" + srv.counterMaxPlayers() + ". Wait 2 player");
            System.out.println("Server listening...");
            int s = 10;
            while (s > 0) {
                try {
                    if (srv.mes == 200) {
                        System.out.println("client:ok");
                        srv.pong();
                        break;
                    }
                    s--;
                    Thread.sleep(1000L);    // 1000L = 1000ms = 1 second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (srv.mes == 0) {
                System.out.println("Nobody came, please, restart");
                return;
            }
            //start game, print sea one player and wait ping client
            System.out.println("Game Started");
            srv.printSea(srv.seaPlayerOne);
            //set ships and print ready sea
            System.out.println("Please, set your ships");
            Scanner in = new Scanner(System.in);
            for (int i = 0; i < 1; i++) {
                System.out.println("Enter type(1-4),x,y,direction(0-3)");
                int type = in.nextInt();
                int x = in.nextInt();
                int y = in.nextInt();
                int direction = in.nextInt();
                srv.setShip(srv.seaPlayerOne, type, x, y, direction);
            }
            //wait and say that we are ready
            srv.pong();
            while (srv.mes == 400) {
                Thread.sleep(1000L);
            }
            ;
            srv.printSea(srv.seaPlayerOne);
            //server first started
            while (srv.checkWin(srv.seaPlayerTwo) & srv.checkWin(srv.seaPlayerOne)) {

                if (srv.mes == 200) {
                    System.out.println("Your step: x,y");
                    int x = in.nextInt();
                    int y = in.nextInt();
                    srv.hitShip(srv.seaPlayerTwo, 200, x, y);
                    if(!srv.isUpd) System.out.println("wait");
                    Thread.sleep(1000L);
                }
                if (srv.mes == 400 & srv.isUpdate()) {
                    srv.printSea(srv.seaPlayerOne);
                    System.out.println("wait");
                    Thread.sleep(1000L);
                }
                srv.isUpd = false;


            }
            if (srv.checkWin(srv.seaPlayerOne)) System.out.println("YOU WIN");
            else System.out.println("YOU LOSE");
            srv.mes=0;
            srv.pong();
            while (srv.mes == 400) {
                Thread.sleep(1000L);
            }
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
