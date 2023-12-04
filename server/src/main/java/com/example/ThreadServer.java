package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class ThreadServer extends Thread{

    Socket socket;
    BufferedReader inClient;
    DataOutputStream outClient;

    Server server;
    String clientNickName = ""; 
    String sendTo;

    public ThreadServer(Socket socket, Server server) throws IOException{

        this.socket = socket;
        this.server = server;
        inClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outClient = new DataOutputStream(socket.getOutputStream());
    }

    public void run() {


        try {

            do{

                clientNickName = inClient.readLine();
                System.out.println("Connessione: " + clientNickName);  System.out.println("");

                if(server.isNickUsed(clientNickName)){

                    outClient.writeBytes("!" + '\n');
                    server.removeClient(this);
                    socket.close();
                    return;
                }

                sendTo = inClient.readLine();

                if(sendTo.startsWith("/all ")) {

                    String message = sendTo.replace("/all ", clientNickName + ": ");
                    boolean flag = server.broadCastMessage(this, message);

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

                    boolean flag = server.privateMessage(nick, message);

                    if(!flag) {

                        outClient.writeBytes("X" + '\n');
                    }
                    
                } else if(sendTo.equals("/list")){

                    ArrayList lista = server.listMemebers();


                    outClient.writeBytes(String.join("; ", lista) + '\n');


                } else if(sendTo.equals("/close")) {

                    server.removeClient(this);
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


    
}
