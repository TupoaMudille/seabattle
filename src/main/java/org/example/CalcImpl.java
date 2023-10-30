package org.example;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CalcImpl extends UnicastRemoteObject implements Calc {
    int countOfPlayer = 0;
    int mes = 0;
    boolean isUpd = false;
    int[][] seaPlayerOne = new int[10][10];
    int[][] seaPlayerTwo = new int[10][10];

    protected CalcImpl() throws RemoteException {
        super();
    }

    @Override
    public int counterMaxPlayers() throws RemoteException {
        countOfPlayer++;
        return countOfPlayer;
    }

    @Override
    public int ping() throws RemoteException {
        mes = 200;
        return mes;
    }

    public int pong() throws RemoteException {
        mes = 400;
        return mes;
    }

    public int getMes() throws RemoteException {
        return mes;
    }

    public void printSea(int[][] arr) throws RemoteException {
        for (int n = 0; n < 10; n++) {
            for (int m = 0; m < 10; m++) {
                System.out.print("[" + arr[m][n] + "]");
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    public boolean checkWin(int[][] arr) throws RemoteException {
        int sum = 0;
        for (int n = 0; n < 10; n++) {
            for (int m = 0; m < 10; m++) {
                sum += arr[m][n];
            }
        }
        if (sum == 0) return false;
        return true;
    }

    public boolean isUpdate() throws RemoteException {
        return isUpd;
    }

    public void changeUpd(boolean upd) throws RemoteException {
        isUpd = upd;
    }

    public int[][] hitShip(int[][] arr, int state, int x, int y) throws RemoteException {
        if (arr[x][y] == 1) {
            arr[x][y] = 0;
            if (state == 400) seaPlayerOne = arr;
            else seaPlayerTwo = arr;
            mes = state;
            isUpd = true;
        } else {
            if (state == 400) {
                mes = 200;
            } else {
                mes = 400;
            }
            isUpd = false;
        }
        return arr;
    }

    public int[][] getSea() throws RemoteException {
        return seaPlayerTwo;
    }

    public int[][] getSea2() throws RemoteException {
        return seaPlayerOne;
    }

    public void setSea(int[][] arr) throws RemoteException {
        seaPlayerTwo = arr;
    }

    public void setSea2(int[][] arr) throws RemoteException {
        seaPlayerOne = arr;
    }

    public int[][] setShip(int[][] arr, int type, int x, int y, int direction) throws RemoteException {
        switch (direction) {
            case 0:
                if (y >= type - 1) {
                    for (int i = 0; i < type; i++) {
                        arr[x][y - i] = 1;
                    }
                }

                break;
            case 2:
                if (y <= 10 - type) {
                    for (int i = 0; i < type; i++) {
                        arr[x][y + i] = 1;
                    }
                }
                break;
            case 1:
                if (x <= 10 - type) {
                    for (int i = 0; i < type; i++) {
                        arr[x + i][y] = 1;
                    }
                }
                break;
            case 3:
                if (x >= type - 1) {
                    for (int i = 0; i < type; i++) {
                        arr[x - i][y] = 1;
                    }
                }
                break;
        }
        return arr;
    }
}
