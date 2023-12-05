package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class ThreadServer extends Thread{

    Socket socket;
    BufferedReader inClient;
    DataOutputStream outClient;

    Server server;
    String clientNickName = ""; 
    String sendTo;

    public ArrayList <ThreadServer> listaThread = new ArrayList();

    public ThreadServer(Socket socket, ArrayList listaThread) throws IOException{

        this.socket = socket;
        this.listaThread = listaThread;
        inClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outClient = new DataOutputStream(socket.getOutputStream());
    }

    public void run() {

        try {

            clientNickName = inClient.readLine();
            System.out.println("Connessione: " + clientNickName);  System.out.println("");

            if(isNickUsed(clientNickName)){

                outClient.writeBytes("!" + '\n');
                System.out.println( "Un client si è disconnesso" );
                removeClient(this);
                socket.close();
                return;
            }

            do{

                sendTo = inClient.readLine();

                if(sendTo.startsWith("/all ")) {

                    String message = sendTo.replace("/all ", clientNickName + ": ");
                    boolean flag = broadCastMessage(this, message);

                    if(!flag) {

                        outClient.writeBytes("X" + '\n');
                    }

                } else if(sendTo.startsWith("/one ")) {

                    String arguments = sendTo.replace("/one ", clientNickName + ": ");
                    String[] array = arguments.split(" ");

                    String nick = array[1];
                    String message = clientNickName + ": ";

                    for(int i = 2; i < array.length; i++) {

                        message = message + array[i] + " ";
                    }

                    boolean flag = privateMessage(nick, message);

                    if(!flag) {

                        outClient.writeBytes("X" + '\n');
                    }
                    
                } else if(sendTo.equals("/list")){

                    ArrayList lista = listMemebers();

                    outClient.writeBytes(String.join("; ", lista) + '\n');


                } else if(sendTo.equals("/close")) {

                    outClient.writeBytes("Q" + '\n');
                    System.out.println( "Un client si è disconnesso" );
                    removeClient(this);
                    socket.close();
                    return;
                }


            }while(true);

        }

        catch(Exception e) {
            System.out.println(e.getMessage());
            System.out.println( "Errore durante l'istanza del Server!" );
        }
       
        
    }

    public void inviaMessaggio(String message) throws IOException {

        outClient.writeBytes(message + '\n');
    }

    public boolean isNickUsed(String nick) {

        if(listaThread.size() == 1){

            return false;

        }else {

            for(int i = 0; i < listaThread.size() - 1; i++) {
            if(listaThread.get(i).clientNickName.equals(nick))
                return true;
            }
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
            if (t.getNick() != thread.getNick())
                t.inviaMessaggio(message + "\n");
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

    public String getNick() {

        return this.clientNickName;
    }

}