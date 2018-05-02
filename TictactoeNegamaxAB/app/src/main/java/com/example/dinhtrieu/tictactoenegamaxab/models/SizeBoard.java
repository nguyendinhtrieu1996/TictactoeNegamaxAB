package com.example.dinhtrieu.tictactoenegamaxab.models;

import android.util.Size;

/**
 * Created by silent on 5/1/2018.
 */
public enum SizeBoard {

    EIGHT("8x8", 8), TEN("10x10", 10), TWELVE("12x12", 12), FOURTEEN("14x14", 14), FIFTEEN("15x15", 15);

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
