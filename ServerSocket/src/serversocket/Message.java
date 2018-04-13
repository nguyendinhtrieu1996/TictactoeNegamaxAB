/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serversocket;

import com.sun.security.ntlm.Client;

/**
 *
 * @author dinhtrieu
 */
public class Message {
    private PlayerClient toPlayer;
    private StatusCode statusCode;
    private Move move;

    public Message() {
    }

    public Message(PlayerClient toPlayer, StatusCode statusCode, Move move) {
        this.toPlayer = toPlayer;
        this.statusCode = statusCode;
        this.move = move;
    }
    
    public String getMessageContent() {
        String result = "";
        
        result += statusCode.getRawValue() + "";
        result += "-" + toPlayer.getRolePlayer().getRawValue();
        result += "-" + move.getRow();
        result += "-" + move.getCol();
        
        return result;
    }

    public PlayerClient getToPlayer() {
        return toPlayer;
    }

    public void setToPlayer(PlayerClient toPlayer) {
        this.toPlayer = toPlayer;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }
    
    
    
}
