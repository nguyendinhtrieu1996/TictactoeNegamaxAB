/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serversocket;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 *
 * @author dinhtrieu
 */
public class MatchThread extends Thread{
    
    private PlayerClient playerA;
    private PlayerClient playerB; 
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    
    private String message;

    public MatchThread(PlayerClient playerA, PlayerClient playerB) {
        this.playerA = playerA;
        this.playerB = playerB;
        dataInputStream = null;
        dataOutputStream = null;
    }

    //Override method
    @Override
    public void run() {
        System.out.println("Create match" + playerA.getSocket().getInetAddress() + " ==== " + playerB.getSocket().getInetAddress());
    }
    
    
    
}
