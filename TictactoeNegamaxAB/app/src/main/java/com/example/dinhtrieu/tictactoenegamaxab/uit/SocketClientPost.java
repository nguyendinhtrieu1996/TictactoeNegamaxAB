package com.example.dinhtrieu.tictactoenegamaxab.uit;

import com.example.dinhtrieu.tictactoenegamaxab.dm.Move;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by dinhtrieu on 4/13/18.
 */

public class SocketClientPost extends Thread {

    private Socket socket;
    private Move move;
    private DataOutputStream dataOutputStream;
    public SocketClientCallback delegate;

    public SocketClientPost() {

    }

    public void init(Move move) {
        try {
            this.move = move;
            this.socket = SocketClient.socket;
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (Exception ex) {

        }
    }

    @Override
    public void run() {
        try {
            String message = "" + this.move.getRowIndex() + "-" + this.move.getColIndex();
            this.dataOutputStream.writeUTF(message);
            this.dataOutputStream.flush();
        } catch (IOException ex) {

        }

    }

}
