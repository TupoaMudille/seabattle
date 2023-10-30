package org.example;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Calc extends Remote {
    int[][] seaPlayerTwo = new int[10][10];

    int counterMaxPlayers() throws RemoteException;

    int ping() throws RemoteException;

    int getMes() throws RemoteException;

    int[][] getSea() throws RemoteException;

    int[][] getSea2() throws RemoteException;

    void setSea(int[][] arr) throws RemoteException;

    void setSea2(int[][] arr) throws RemoteException;

    void printSea(int[][] arr) throws RemoteException;

    int[][] setShip(int[][] arr, int type, int x, int y, int direction) throws RemoteException;

    int[][] hitShip(int[][] arr, int mes, int x, int y) throws RemoteException;

    boolean checkWin(int[][] arr) throws RemoteException;

    boolean isUpdate() throws RemoteException;

    void changeUpd(boolean upd) throws RemoteException;

}
