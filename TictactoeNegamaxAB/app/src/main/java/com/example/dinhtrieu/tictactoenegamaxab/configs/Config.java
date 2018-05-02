package com.example.dinhtrieu.tictactoenegamaxab.configs;

import com.example.dinhtrieu.tictactoenegamaxab.models.GameMode;
import com.example.dinhtrieu.tictactoenegamaxab.models.SizeBoard;

/**
 * Created by silent on 5/1/2018.
 */
public class Config {

    public static final String NAME_SETTINGS = "com.example.settings";

    public static final String KEY_SIZE_BOARD = "size_board";
    public static final String KEY_GAME_MODE = "game_mode";

    public static SizeBoard SIZE_BOARD = SizeBoard.TEN;
    public static GameMode GAME_MODE = GameMode.EASY;

}
