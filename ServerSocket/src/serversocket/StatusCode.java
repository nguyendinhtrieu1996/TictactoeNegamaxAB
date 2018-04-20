/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serversocket;

/**
 *
 * @author dinhtrieu
 */
public enum StatusCode {
    CREATED(0),
    PLAYERAMOVE(1),
    PLAYERBMOVE(2),
    WIN(3),
    LOOSE(4),
    DRAW(5),
    CANTMOVE(6);
    
    private int value;
    
    StatusCode(int value) {
        this.value = value;
    }
    
    public int getRawValue() {
        return value;
    }
    
}









