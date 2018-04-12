package com.example.dinhtrieu.tictactoenegamaxab.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.dinhtrieu.tictactoenegamaxab.R;
import com.example.dinhtrieu.tictactoenegamaxab.bm.ChessBoard;
import com.example.dinhtrieu.tictactoenegamaxab.dm.GameType;

public class GameActicity extends AppCompatActivity {

    //Variable
    private int colQty = 5;
    private int rowQty = 5;
    private GameType gameType;

    //UI Elements
    private ImageView img;
    private ChessBoard chessBoard;

    //Life Circle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_acticity);

        init();
        setupPlayWithBot();
    }

    //Feature
    private void init() {
        img = findViewById(R.id.img);
        img.setImageBitmap(chessBoard.drawBoard());
    }

    private void setupPlayWithBot() {
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

    private void setUpTwoPlayer() {
        
    }


}
