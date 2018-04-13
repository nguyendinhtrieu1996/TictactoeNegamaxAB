package com.example.dinhtrieu.tictactoenegamaxab.uit;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.dinhtrieu.tictactoenegamaxab.MainActivity;

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

    public SocketClient(String address, int port) {
        this.address = address;
        this.port = port;
    }

    @Override
    public void run() {
        this.connect();

        try {
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            String result = inputStream.readUTF();
            Log.d("====== Connect test ", result);
            delegate.handlerMessage(result);

        } catch (IOException ex) {

        }

    }

    private void connect() {
        try {
            socket = new Socket(address, port);
        } catch (IOException ex) {
            Log.d("exception", ex.toString());
        }
    }

}


