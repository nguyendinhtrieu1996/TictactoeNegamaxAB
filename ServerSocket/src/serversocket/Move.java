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
public class Move {
    private int col;
    private int row;

    public Move(int col, int row) {
        this.col = col;
        this.row = row;
    }
    
    public Move(String value) {
        String[] parts = value.split("-");
        this.row = Integer.parseInt(parts[0]);
        this.col = Integer.parseInt(parts[1]);
    }

    Move() {}

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
    
}
