package com.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    public static void main( String[] args ) {
        //Server

        ArrayList <ThreadServer> listaThread = new ArrayList();

        try {

            ServerSocket serverSocket = new ServerSocket(3000);
            System.out.println( "Server in avvio!" ); System.out.println("");
            
            do {

                Socket socket = serverSocket.accept();
                System.out.println( "Client connesso: " + socket);

                ThreadServer thread = new ThreadServer(socket, listaThread);
                listaThread.add(thread);
                thread.start();

            }while(true);

        } 
        
        catch(Exception e) {

            System.out.println(e.getMessage());
            System.out.println( "Errore durante l'istanza del Server!" );
        }

    }

}