/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serversocket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 * @author dinhtrieu
 */
public class MatchThread extends Thread{
    
    private PlayerClient playerA;
    private PlayerClient playerB; 
    private Chessboard chessboard;

    public MatchThread(PlayerClient playerA, PlayerClient playerB) {
        this.playerA = playerA;
        this.playerB = playerB;
        this.chessboard = new Chessboard();
    }

    //Override method
    @Override
    public void run() { 
        handlerPlayerA();
        handlerPlayerB();
    }
    
    private void handlerPlayerA() {
        String messageAString = new Message(playerA, StatusCode.CREATED, new Move(-1, -1)).getMessageContent();
        DataInputStream dataInputStream = null;
        DataOutputStream dataOutputStream = null;
        
        try {
            dataInputStream = new DataInputStream(playerA.getSocket().getInputStream());
            dataOutputStream = new DataOutputStream(playerA.getSocket().getOutputStream());
            
            dataOutputStream.writeUTF(messageAString);
            dataOutputStream.flush();
            
            while (true) {
                if (dataInputStream.available() > 0) {
                    String message = dataInputStream.readUTF();
                    
                    if (!message.endsWith("")) {
                        
                    }
                }
            }
            
        } catch (IOException ex) {
            
        }
    }
    
    private void handlerPlayerB() {
        String messageBString = new Message(playerB, StatusCode.CREATED, new Move(-1, -1)).getMessageContent();
        DataInputStream dataInputStream;
        DataOutputStream dataOutputStream;
    }
    
    private void postcastMessage() {
        
    }
    
}
