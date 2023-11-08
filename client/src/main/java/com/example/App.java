package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class App {
    public static void main( String[] args ) {

        System.out.println("");

        // Client

        try { 
            
            System.out.println( "Il client è partito" );
            System.out.println("");
            Socket s = new Socket("localhost", 3000);

            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            Scanner input = new Scanner(System.in);

            String risposta = "0";

            do{

                System.out.println( "Inserisci un numero: " );

                String num = input.nextLine();
                out.writeBytes(num + '\n');

                System.out.println("");

                risposta = in.readLine();

                if(Integer.parseInt(risposta) == 1)
                    System.out.println( "Numero troppo piccolo" );

                else if(Integer.parseInt(risposta) == 2)
                    System.out.println( "Numero troppo grande" );

                else if(Integer.parseInt(risposta) == 3) {

                    System.out.println( "NUMERO INDOVINATO" );

                    String count = in.readLine();
                    System.out.println("Hai indovinato: " + count + " tentativi");
                }

                System.out.println("");

            } while(Integer.parseInt(risposta) != 3);

            s.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("errore durante l'istanza del server");
            System.exit(1);
        }

    }
}
