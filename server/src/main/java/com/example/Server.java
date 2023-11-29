package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    public ArrayList <ThreadServer> listaThread = new ArrayList();

    public void main( String[] args ) {
        //Server
        try {

            ServerSocket serverSocket = new ServerSocket(3000);
            System.out.println( "Server in avvio!" );
            System.out.println("");
            
            do {

                Socket socket = serverSocket.accept();
                System.out.println( "Client connesso: " + socket);

                ThreadServer thread = new ThreadServer(socket, this);
                listaThread.add(thread);
                thread.start();

            }while(true);

        } 
        
        catch(Exception e) {

            System.out.println(e.getMessage());
            System.out.println( "Errore durante l'istanza del Server!" );
        }

    }




    public boolean isNickUsed(String nick) {

        for(int i = 0; i < listaThread.size(); i++) {
            if(listaThread.get(i).clientNickName.equals(nick))
                return true;

        }

        return false;

    }

    public void removeClient(ThreadServer thread){

        this.listaThread.remove(thread);
    }

    public boolean broadCastMessage(ThreadServer thread, String message) throws IOException {

        if(listaThread.size() == 1)
            return false;

        for (ThreadServer t : listaThread) {
            if (thread != t)
                thread.inviaMessaggio(message + "\n");
        }

        return true;

    }

    public boolean privateMessage(String nick, String message) throws IOException {

        for (ThreadServer t : listaThread) {
            if (t.clientNickName.equals(nick)) {

                t.inviaMessaggio(message + "\n");
                return true;
            }
        }

        return false;
    }

    public ArrayList<String> listMemebers() {

        ArrayList <String> lista = new ArrayList();
        for(ThreadServer t : listaThread) {

            lista.add(t.clientNickName);
        }
        
        return lista;

    }

}