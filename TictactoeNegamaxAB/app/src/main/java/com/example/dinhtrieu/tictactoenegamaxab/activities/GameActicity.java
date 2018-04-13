package com.example.dinhtrieu.tictactoenegamaxab.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dinhtrieu.tictactoenegamaxab.R;
import com.example.dinhtrieu.tictactoenegamaxab.bm.ChessBoard;
import com.example.dinhtrieu.tictactoenegamaxab.dm.GameStatus;
import com.example.dinhtrieu.tictactoenegamaxab.dm.GameType;
import com.example.dinhtrieu.tictactoenegamaxab.dm.Move;
import com.example.dinhtrieu.tictactoenegamaxab.dm.RolePlayer;
import com.example.dinhtrieu.tictactoenegamaxab.dm.ServerMessage;
import com.example.dinhtrieu.tictactoenegamaxab.uit.GameConstant;
import com.example.dinhtrieu.tictactoenegamaxab.uit.SocketClient;
import com.example.dinhtrieu.tictactoenegamaxab.uit.SocketClientCallback;
import com.example.dinhtrieu.tictactoenegamaxab.uit.SocketClientPost;

public class GameActicity extends AppCompatActivity implements SocketClientCallback {

    //Variable
    private int bitmapWidth = 1000;
    private int bitmapheight = 1000;
    private int colQty = 5;
    private int rowQty = 5;
    public static GameType gameType;
    private SocketClient socketClient;
    public static Boolean isAllowMove;
    private ChessBoard chessBoard;
    private SocketClientPost socketClientPost;

    //UI Elements
    private ImageView img;

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

            socketClient.delegate = GameActicity.this;
        }
    }

    @Override
    public void hanlderFlushComplete() {
        socketClientPost.interrupt();
    }

    @Override
    public void handlerMessage(ServerMessage serverMessage) {
        final ServerMessage message = serverMessage;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                GameStatus gameStatus = message.getGameStatus();

                switch (gameStatus) {
                    case CREATED:
                        if (message.getRolePlayer() == RolePlayer.PLAYERA) {
                            isAllowMove = true;
                            Toast.makeText(getApplicationContext(), "Bạn chơi lượt đầu", Toast.LENGTH_LONG).show();
                        } else {
                            isAllowMove = false;
                            chessBoard.setPlayer(1);
                            Toast.makeText(getApplicationContext(), "Bạn chơi lượt thứ 2", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case DOING:
                        isAllowMove = true;
                        chessBoard.opponentDraw(message.getMove(), img);
                        break;
                    case WIN:
                        isAllowMove = false;
                        Toast.makeText(getApplicationContext(), "Ban thắng rồi", Toast.LENGTH_LONG).show();
                        break;
                    case LOOSE:
                        isAllowMove = false;
                        Toast.makeText(getApplicationContext(), "Ban thua rồi", Toast.LENGTH_LONG).show();
                        break;
                    case DRAW:
                        isAllowMove = false;
                        Toast.makeText(getApplicationContext(), "Ban hoà rồi", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }

    //Feature
    private void init() {
        isAllowMove = false;
        img = findViewById(R.id.img);
        chessBoard = new ChessBoard(GameActicity.this, bitmapWidth,bitmapheight, colQty, rowQty);
        chessBoard.init();
        img.setImageBitmap(chessBoard.drawBoard());
        Intent intent = getIntent();
        gameType = (GameType) intent.getSerializableExtra("gametype");
    }

    private void initTwoPlayer() {
        socketClientPost = new SocketClientPost();
        socketClient = new SocketClient(
                GameConstant.ServerIP,
                GameConstant.SocketServerPORT
                );

        socketClient.start();
    }

    private void setupPlayWithBot() {
        chessBoard = new ChessBoard(GameActicity.this, bitmapWidth,bitmapheight, colQty, rowQty);
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
        chessBoard = new ChessBoard(GameActicity.this, bitmapWidth, bitmapheight, colQty, rowQty);
        chessBoard.init();
        img.setImageBitmap(chessBoard.drawBoard());

        img.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (isAllowMove) {
                        Move move = chessBoard.onTouchMove(view, motionEvent);
                        if (move != null) {
                            isAllowMove = false;
                            socketClientPost.init(move);
                            socketClientPost.start();
                            socketClient.stop();
                        }
                    }
                }

                return true;
            }
        });
    }


}












