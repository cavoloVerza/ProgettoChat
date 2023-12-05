package com.example;

import java.net.Socket;

public class Client {
    public static void main( String[] args ) {
        // Client

        try { 
            
            Socket socket = new Socket("localhost", 3000);
            System.out.println( "Client in avvio!" ); System.out.println("");

            ThreadLettura thread1 = new ThreadLettura(socket);
            ThreadScrittura thread2 = new ThreadScrittura(socket);

            thread1.start();
            thread2.start();

        } catch (Exception e) {
            
            System.out.println(e.getMessage());
            System.out.println("errore durante l'istanza del server");
            System.exit(1);
        }

    }

}