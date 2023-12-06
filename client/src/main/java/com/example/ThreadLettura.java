package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ThreadLettura extends Thread{

    private Socket socket;
    BufferedReader inServer;

    String carattere;

    public ThreadLettura(Socket socket) throws IOException{

        this.socket = socket;
        inServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void run() {

        try{

            do {

                carattere = inServer.readLine();

                if(carattere.equals("X")) {

                    System.out.println( "ERRORE: client non connesso, attendi...");

                }else if(carattere.equals("!")) {

                    System.out.println( "NickName già in uso...");
                    System.out.println( "Ti sei disconnesso");
                    carattere = "Q";

                }else if(carattere.equals("Q")) {

                    System.out.println( "Ti sei disconnesso");

                }else {

                    System.out.println(carattere);
                }


            }while(carattere != "Q");

            socket.close();

        } catch(Exception e) {

            System.out.println(e.getMessage());
            System.out.println("Errore durante l'istanza del Client");
        }

    }
    
}
