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
    private PlayerClient currentPlayer;
    private DataInputStream dataInputStream = null;
    private DataOutputStream dataOutputStream = null;

    public MatchThread(PlayerClient playerA, PlayerClient playerB) {
        this.playerA = playerA;
        this.playerB = playerB;
        this.chessboard = new Chessboard();
        this.currentPlayer = playerA;
    }

    //Override method
    @Override
    public void run() { 
    
        try {
            this.sendMessageToPlayer(playerA, StatusCode.CREATED, new Move(-1, -1));
            this.sendMessageToPlayer(playerB, StatusCode.CREATED, new Move(-1, -1));
            Move move = new Move();
            
            while (true) {
                dataInputStream = new DataInputStream(currentPlayer.getSocket().getInputStream());
               
                if (dataInputStream.available() > 0) {
                    move = new Move(dataInputStream.readUTF());
                    chessboard.makeMove(move, currentPlayer.getRolePlayer());
                    
                    //Game over
                    if (chessboard.getIsGameOver()) {
                        if (chessboard.getWinner() == RolePlayer.PLAYERA) {
                            this.sendMessageToPlayer(playerA, StatusCode.WIN, new Move(-1, -1));
                        } else {
                            this.sendMessageToPlayer(playerB, StatusCode.LOOSE, new Move(-1, -1));
                        }
                    } else {
                        if (currentPlayer.getRolePlayer() == RolePlayer.PLAYERA) {
                            this.sendMessageToPlayer(playerB, StatusCode.DOING, move);
                        } else {
                            this.sendMessageToPlayer(playerA, StatusCode.DOING, move);
                        }
                    }
                }
                
                swapCurrentPlayer();
            }
            
        } catch (IOException ex) {
            
        }
    }
    
    private void sendMessageToPlayer(PlayerClient player, StatusCode statusCode, Move move) throws IOException {
        dataOutputStream = new DataOutputStream(player.getSocket().getOutputStream());
        Message message = new Message(player, statusCode, move);
        dataOutputStream.writeUTF(message.getMessageContent());
        dataOutputStream.flush();
    }
    
    private void swapCurrentPlayer() {
        if (currentPlayer.equals(playerA)) {
            currentPlayer = playerB;
        } else {
            currentPlayer = playerA;
        }
    }
    
}
