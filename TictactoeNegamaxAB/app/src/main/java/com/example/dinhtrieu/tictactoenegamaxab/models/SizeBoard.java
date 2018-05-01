package com.example.dinhtrieu.tictactoenegamaxab.models;

import android.util.Size;

/**
 * Created by silent on 5/1/2018.
 */
public enum SizeBoard {

    EIGHT("1", 8), TEN("2", 10), TWELVE("3", 12), FOURTEEN("4", 14), FIFTEEN("5", 15);

    private String key;
    private int value;

    public int value() {
        return value;
    }

    public String key(){
        return key;
    }

    SizeBoard(String key, int value) {
        this.key = key;
        this.value = value;
    }

}
