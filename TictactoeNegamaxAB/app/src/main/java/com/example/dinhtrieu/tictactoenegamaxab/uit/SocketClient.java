package com.example.dinhtrieu.tictactoenegamaxab.uit;

import android.content.Context;
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
    private Socket socket;

    public SocketClient(String address, int port) {
        this.address = address;
        this.port = port;
        this.connect();
    }

    private void connect() {
        try {
            socket = new Socket(address, port);
            Log.d("Connected", "success");
        } catch (IOException ex) {
            ex.toString();
            Log.d("====Connect", "errr");
        }

    }


}


