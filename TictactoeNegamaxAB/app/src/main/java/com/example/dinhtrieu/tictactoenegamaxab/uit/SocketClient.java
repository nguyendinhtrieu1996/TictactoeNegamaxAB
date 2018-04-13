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

public class SocketClient extends AsyncTask<Void, Void, Void> {

    private String address;
    private int port;
    private Socket socket;

    public SocketClient(String address, int port) {
        this.address = address;
        this.port = port;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        this.connect();
        return null;
    }

    private void connect() {
        try {
            socket = new Socket(address, port);
        } catch (IOException ex) {
            Log.d("exception", ex.toString());
        }
    }
}


