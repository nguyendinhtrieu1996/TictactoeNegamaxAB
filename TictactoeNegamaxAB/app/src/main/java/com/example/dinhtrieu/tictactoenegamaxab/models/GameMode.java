package com.example.dinhtrieu.tictactoenegamaxab.models;

/**
 * Created by silent on 5/1/2018.
 */
public enum GameMode {

    EASY("Dễ", 1), MEDIUM("Thường", 3), HARD("Khó", 7);

    private String key;
    private int value;

    private GameMode(String key, int value){
        this.key = key;
        this.value = value;
    }

    public int value(){
        return value;
    }
    public String key(){
        return key;
    }

}
