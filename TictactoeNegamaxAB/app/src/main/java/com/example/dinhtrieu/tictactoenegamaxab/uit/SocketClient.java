package com.example.dinhtrieu.tictactoenegamaxab.uit;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.dinhtrieu.tictactoenegamaxab.MainActivity;
import com.example.dinhtrieu.tictactoenegamaxab.dm.ServerMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by dinhtrieu on 4/13/18.
 */

public class SocketClient extends Thread {

    private String address;
    private int port;
    public static Socket socket;
    public SocketClientCallback delegate;
    public static Boolean isOut = false;

    public SocketClient(String address, int port) {
        this.address = address;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(address, port);
            while (!isOut) {
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                ServerMessage serverMessage = new ServerMessage(inputStream.readUTF());

                delegate.handlerMessage(serverMessage);
            }

        } catch (IOException ex) {
            Log.d("connect exception", ex.toString());
        }
    }

}


