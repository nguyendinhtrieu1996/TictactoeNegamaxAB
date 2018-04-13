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
public enum RolePlayer {
    PLAYERA("playera"),
    PLAYERB("playerb");
    
    String value;

    private RolePlayer(String value) {
        this.value = value;
    }
    
    public String getRawValue() {
        return value;
    }
    
}
