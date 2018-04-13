package com.example.dinhtrieu.tictactoenegamaxab.uit;

import com.example.dinhtrieu.tictactoenegamaxab.dm.ServerMessage;

/**
 * Created by dinhtrieu on 4/13/18.
 */

public interface SocketClientCallback {
    public void handlerMessage(ServerMessage serverMessage);
    public void hanlderFlushComplete();
}
