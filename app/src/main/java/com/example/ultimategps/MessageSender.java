package com.example.ultimategps;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.URL;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class MessageSender extends AsyncTask<String,Void,Void> {
    Socket socket;
    InetAddress inetAddress;
    DataOutputStream dos;
    PrintWriter pw;


    @Override
    protected Void doInBackground(String... voids) {

            String message = voids[0];
            String ipAddress1= voids[1] ;







            try{
               /* socket= new Socket(ipAddress1,portServer);





                //TCP

                pw= new PrintWriter(socket.getOutputStream());
                pw.write(message);
                pw.flush();
                pw.close();
                socket.close();*/


                //UDP
                DatagramSocket clientSocket = new DatagramSocket();

               InetAddress ipaddr = InetAddress.getByName(ipAddress1);

                byte[] sendData;


                sendData = message.getBytes();

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipaddr, 5000);

                clientSocket.send(sendPacket);






                clientSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;

    }
}
