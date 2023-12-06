package com.example;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ThreadScrittura extends Thread{

    private Socket socket;
    DataOutputStream outServer;
    Scanner input = new Scanner(System.in);

    public ThreadScrittura(Socket socket) throws IOException{

        this.socket = socket;
        outServer = new DataOutputStream(socket.getOutputStream());
    }

    public void run() {

        try{

            System.out.println("Inserisci il tuo NickName: ");
            String nick = input.nextLine();
            outServer.writeBytes(nick + '\n');

            String stringa;
            do {

                stringaMenu();
                stringa = input.nextLine();
                outServer.writeBytes(stringa + '\n');

            }while(true);

        } catch(Exception e) {

            System.out.println(e.getMessage());
            System.out.println("Errore durante l'istanza del Client");
        }

    }

    public void stringaMenu() {

        System.out.println("Index Comandi: (successivamente al comando inserire il messaggio)");
        System.out.println("    /all -> Messaggio a tutti gli altri utenti connessi");
        System.out.println("    /one 'nick' -> Messaggio al singolo utente con quel nick");
        System.out.println("    /list -> Per vedere la lista di tutti i nick degli utenti connessi");
        System.out.println("    /close -> Disconnessione");
        System.out.println("ATTENZIONE, separare ogni campo da uno spazio");
        System.out.println("");

    }
    
}
