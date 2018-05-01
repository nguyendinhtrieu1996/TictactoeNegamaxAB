package com.example.dinhtrieu.tictactoenegamaxab.models;

/**
 * Created by silent on 5/1/2018.
 */
public enum GameMode {

    EASY(1), MEDIUM(3), HARD(7);

    private int value;

    private GameMode(int value){
        this.value = value;
    }

    public int value(){
        return value;
    }

}
