package com.example.dinhtrieu.tictactoenegamaxab.dm;

/**
 * Created by dinhtrieu on 4/12/18.
 */

public class Move {
    private int rowIndex;
    private int colIndex;

    public Move() {
    }

    public Move(int rowIndex, int colIndex) {
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getColIndex() {
        return colIndex;
    }

    public void setColIndex(int colIndex) {
        this.colIndex = colIndex;
    }
}