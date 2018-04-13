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
    DOING(1),
    WIN(2),
    LOOSE(3);
    
    private int value;
    
    StatusCode(int value) {
        this.value = value;
    }
    
    public int getRawValue() {
        return value;
    }
    
}









