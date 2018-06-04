package com.example.dinhtrieu.tictactoenegamaxab.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.dinhtrieu.tictactoenegamaxab.R;
import com.example.dinhtrieu.tictactoenegamaxab.bm.ChessBoard;
import com.example.dinhtrieu.tictactoenegamaxab.dialog.EndGameDiaglog;
import com.example.dinhtrieu.tictactoenegamaxab.configs.Config;
import com.example.dinhtrieu.tictactoenegamaxab.dm.GameType;
import com.example.dinhtrieu.tictactoenegamaxab.models.SizeBoard;
import com.example.dinhtrieu.tictactoenegamaxab.uit.ChessBoardDelegate;
import com.example.dinhtrieu.tictactoenegamaxab.models.GameMode;
import com.example.dinhtrieu.tictactoenegamaxab.uit.Constant;
import com.example.dinhtrieu.tictactoenegamaxab.uit.EndGameDialogDelegate;

public class GameActicity extends AppCompatActivity implements EndGameDialogDelegate {

    //Variable
    public static GameType gameType;
    private ChessBoard chessBoard;

    //UI Elements
    private ImageView img;
    private EndGameDiaglog endGameDiaglog;

    //Life Circle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_acticity);
        init();

        setupPlayWithBot();

    }

    //Override

    @Override
    public void didSelectedContinue() {
        setupPlayWithBot();
    }

    @Override
    public void didSelectedQuit() {
        finish();
    }

    //Feature
    private void init() {
        setTitle("Cờ Ca rô");
        View decorView = getWindow().getDecorView();
        img = findViewById(R.id.img);
        Intent intent = getIntent();
        gameType = (GameType) intent.getSerializableExtra("gametype");
        endGameDiaglog = new EndGameDiaglog().newInstance();
        endGameDiaglog.delgate = this;
        getConfigSettings();
    }

    private void setupPlayWithBot() {
        chessBoard = new ChessBoard(GameActicity.this, Constant.bitmapWidth, Constant.bitmapheight,
                Config.SIZE_BOARD.value(),
                Config.SIZE_BOARD.value(), Config.GAME_MODE);
        chessBoard.init();
        img.setImageBitmap(chessBoard.drawBoard());

        chessBoard.delgate = new ChessBoardDelegate() {
            @Override
            public void gameOver(int winner) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        endGameDiaglog.winner = chessBoard.getWinner();
                        endGameDiaglog.show(getSupportFragmentManager(), "EndGameDialog");
                    }
                }, 2000);
            }
        };

        img.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (!ChessBoard.isGameOver) {
                        return chessBoard.onTouch(view, motionEvent);
                    }
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (!ChessBoard.isGameOver) {
                        return chessBoard.negaABMove(view, motionEvent);
                    }
                }

                return true;
            }
        });
    }

    public void getConfigSettings() {
        SharedPreferences sharedPreferences = getSharedPreferences(Config.NAME_SETTINGS, MODE_PRIVATE);
        int sizeChosen = sharedPreferences.getInt(Config.KEY_SIZE_BOARD, 1);
        int modeChosen = sharedPreferences.getInt(Config.KEY_GAME_MODE, 1);
        Config.SIZE_BOARD = SizeBoard.values()[sizeChosen];
        Config.GAME_MODE = GameMode.values()[modeChosen];
    }

}












