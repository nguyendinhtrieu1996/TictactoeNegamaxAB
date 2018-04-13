/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serversocket;

import java.net.Socket;

/**
 *
 * @author dinhtrieu
 */
public class PlayerClient {

    private Socket socket;
    private MatchThread matchThread;

    public PlayerClient() {
    }

    public PlayerClient(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public MatchThread getMatchThread() {
        return matchThread;
    }

    public void setMatchThread(MatchThread matchThread) {
        this.matchThread = matchThread;
    }
    
}
