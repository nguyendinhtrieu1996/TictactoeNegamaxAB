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
            this.sendMessageToPlayer(playerA, StatusCode.CREATED, new Move(1000, 1000));
            this.sendMessageToPlayer(playerB, StatusCode.CREATED, new Move(1000, 1000));
            Move move = new Move();
            
            while (true) {
                dataInputStream = new DataInputStream(currentPlayer.getSocket().getInputStream());
               
                if (dataInputStream.available() > 0) {
                    move = new Move(dataInputStream.readUTF());
                    
                    if (currentPlayer.equals(playerA)) {    
                        if (chessboard.canMove(move)) {
                            this.sendMessageToPlayer(playerA, StatusCode.PLAYERAMOVE, move);
                            this.sendMessageToPlayer(playerB, StatusCode.PLAYERAMOVE, move);
                        } else {
                            this.sendMessageToPlayer(playerA, StatusCode.CANTMOVE, move);
                            continue;
                        }
                            
                    } else if (currentPlayer.equals(playerB)) {
                        if (chessboard.canMove(move)) {
                            this.sendMessageToPlayer(playerB, StatusCode.PLAYERBMOVE, move);
                            this.sendMessageToPlayer(playerA, StatusCode.PLAYERBMOVE, move);
                        } else {
                            this.sendMessageToPlayer(playerB, StatusCode.CANTMOVE, move);
                            continue;
                        }
                            
                    }
                    
                    chessboard.makeMove(move, currentPlayer.getRolePlayer());
                    
                    //Game over
                    if (chessboard.getIsGameOver()) {
                        DAO.getInstance().saveBoard(playerA.getSocket(), playerB.getSocket(), chessboard, chessboard.getWinner());
                        
                        if (chessboard.getWinner() == RolePlayer.PLAYERA) {
                            this.sendMessageToPlayer(playerA, StatusCode.WIN, new Move(1000, 1000));
                            this.sendMessageToPlayer(playerB, StatusCode.LOOSE, move);
                        } else {
                            this.sendMessageToPlayer(playerB, StatusCode.WIN, new Move(1000, 1000));
                            this.sendMessageToPlayer(playerA, StatusCode.LOOSE, move);
                        }
                    } else {
                        if (currentPlayer.getRolePlayer() == RolePlayer.PLAYERA) {            
                            this.sendMessageToPlayer(playerB, StatusCode.PLAYERAMOVE, move);
                        } else {
                            this.sendMessageToPlayer(playerA, StatusCode.PLAYERBMOVE, move);
                        }
                    }
                }
                
                swapCurrentPlayer();
            }
            
        } catch (IOException ex) {
            System.err.println("Match Thread " + ex.toString());
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

    public PlayerClient getPlayerA() {
        return playerA;
    }

    public PlayerClient getPlayerB() {
        return playerB;
    }

    public Chessboard getChessboard() {
        return chessboard;
    }
   
}
