package com.example.dinhtrieu.tictactoenegamaxab.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.dinhtrieu.tictactoenegamaxab.R;
import com.example.dinhtrieu.tictactoenegamaxab.bm.ChessBoard;
import com.example.dinhtrieu.tictactoenegamaxab.dm.GameType;
import com.example.dinhtrieu.tictactoenegamaxab.uit.ClientSocketHelper;
import com.example.dinhtrieu.tictactoenegamaxab.uit.GameConstant;
import com.example.dinhtrieu.tictactoenegamaxab.uit.SocketClient;
import com.example.dinhtrieu.tictactoenegamaxab.uit.SocketClientCallback;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class GameActicity extends AppCompatActivity {

    //Variable
    private int colQty = 5;
    private int rowQty = 5;
    public static GameType gameType;
    private SocketClient socketClient;
    private Boolean isAllowMove;

    //UI Elements
    private ImageView img;
    private ChessBoard chessBoard;

    //Life Circle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_acticity);
        init();

        if (gameType == GameType.BOT) {
            setupPlayWithBot();
        } else {
            initTwoPlayer();
            setupTwoPlayer();

            socketClient.delegate = new SocketClientCallback() {
                @Override
                public void handlerMessage(String message) {
                    Log.d("========", message);
                }
            };
        }
    }

    //Feature
    private void init() {
        isAllowMove = false;
        img = findViewById(R.id.img);
        Intent intent = getIntent();
        gameType = (GameType) intent.getSerializableExtra("gametype");
    }

    private void initTwoPlayer() {
        socketClient = new SocketClient(
                GameConstant.ServerIP,
                GameConstant.SocketServerPORT
                );

        socketClient.start();
    }

    private void setupPlayWithBot() {
        chessBoard = new ChessBoard(GameActicity.this, 2000,2000, colQty, rowQty);
        chessBoard.init();
        img.setImageBitmap(chessBoard.drawBoard());

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

    private void setupTwoPlayer() {
        rowQty = 8;
        colQty = 8;
        chessBoard = new ChessBoard(GameActicity.this, 1000,1000, colQty, rowQty);
        chessBoard.init();
        img.setImageBitmap(chessBoard.drawBoard());

        img.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (!ChessBoard.isGameOver) {
                        return chessBoard.onTouch(view, motionEvent);
                    }
                }

                return true;
            }
        });
    }


}












