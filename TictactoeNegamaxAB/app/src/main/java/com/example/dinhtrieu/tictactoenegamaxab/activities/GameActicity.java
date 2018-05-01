package com.example.dinhtrieu.tictactoenegamaxab.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dinhtrieu.tictactoenegamaxab.R;
import com.example.dinhtrieu.tictactoenegamaxab.bm.ChessBoard;
import com.example.dinhtrieu.tictactoenegamaxab.dialog.EndGameDiaglog;
import com.example.dinhtrieu.tictactoenegamaxab.configs.Config;
import com.example.dinhtrieu.tictactoenegamaxab.dm.GameStatus;
import com.example.dinhtrieu.tictactoenegamaxab.dm.GameType;
import com.example.dinhtrieu.tictactoenegamaxab.dm.Move;
import com.example.dinhtrieu.tictactoenegamaxab.dm.RolePlayer;
import com.example.dinhtrieu.tictactoenegamaxab.dm.ServerMessage;
import com.example.dinhtrieu.tictactoenegamaxab.uit.ChessBoardDelegate;
import com.example.dinhtrieu.tictactoenegamaxab.models.GameMode;
import com.example.dinhtrieu.tictactoenegamaxab.uit.Constant;
import com.example.dinhtrieu.tictactoenegamaxab.uit.EndGameDialogDelegate;
import com.example.dinhtrieu.tictactoenegamaxab.uit.GameConstant;
import com.example.dinhtrieu.tictactoenegamaxab.uit.SocketClient;
import com.example.dinhtrieu.tictactoenegamaxab.uit.SocketClientCallback;
import com.example.dinhtrieu.tictactoenegamaxab.uit.SocketClientPost;

public class GameActicity extends AppCompatActivity implements SocketClientCallback, EndGameDialogDelegate {

    //Variable
    public static GameType gameType;
    private SocketClient socketClient;
    public static Boolean isAllowMove;
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

        if (gameType == GameType.BOT) {
            setupPlayWithBot();
        } else {
            initTwoPlayer();
            setupTwoPlayer();

            socketClient.delegate = GameActicity.this;
        }
    }

    //Override
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
                            Toast.makeText(getApplicationContext(), "Bạn chơi lượt thứ 2", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case PLAYERAMOVE:
                        if (message.getRolePlayer().equals(RolePlayer.PLAYERB)) {
                            isAllowMove = true;
                        }

                        chessBoard.drawMove(message.getMove(), img, RolePlayer.PLAYERA);
                        break;
                    case PLAYERBMOVE:
                        if (message.getRolePlayer().equals(RolePlayer.PLAYERA)) {
                            isAllowMove = true;
                        }

                        chessBoard.drawMove(message.getMove(), img, RolePlayer.PLAYERB);
                        break;
                    case WIN:
                        isAllowMove = false;
                        Toast.makeText(getApplicationContext(), "Ban thắng rồi", Toast.LENGTH_LONG).show();
                        SocketClient.isOut = true;
                        break;
                    case LOOSE:
                        isAllowMove = false;
                        Toast.makeText(getApplicationContext(), "Ban thua rồi", Toast.LENGTH_LONG).show();
                        SocketClient.isOut = true;
                        break;
                    case DRAW:
                        isAllowMove = false;
                        Toast.makeText(getApplicationContext(), "Ban hoà rồi", Toast.LENGTH_LONG).show();
                        break;
                    case CANTMOVE:
                        isAllowMove = true;
                        break;
                }
            }
        });
    }

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
        isAllowMove = false;
        img = findViewById(R.id.img);
//        chessBoard = new ChessBoard(GameActicity.this, Constant.bitmapWidth, Constant.bitmapheight,
//                Config.SIZE_BOARD, Config.SIZE_BOARD, Config.GAME_MODE);
//        chessBoard.init();
//        img.setImageBitmap(chessBoard.drawBoard());
        Intent intent = getIntent();
        gameType = (GameType) intent.getSerializableExtra("gametype");
        endGameDiaglog = new EndGameDiaglog().newInstance();
        endGameDiaglog.delgate = this;
        getConfigSettings();
    }

    private void initTwoPlayer() {
        socketClient = new SocketClient(
                GameConstant.ServerIP,
                GameConstant.SocketServerPORT
                );

        socketClient.start();
    }

    private void setupPlayWithBot() {
        chessBoard = new ChessBoard(GameActicity.this,Constant.bitmapWidth, Constant.bitmapheight,
                Config.SIZE_BOARD,
                Config.SIZE_BOARD, Config.GAME_MODE);
        chessBoard.init();
        img.setImageBitmap(chessBoard.drawBoard());

        chessBoard.delgate = new ChessBoardDelegate() {
            @Override
            public void gameOver(int winner) {
                endGameDiaglog.winner = chessBoard.getWinner();
                endGameDiaglog.show(getSupportFragmentManager(), "EndGameDialog");
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

    private void setupTwoPlayer() {
        chessBoard = new ChessBoard(GameActicity.this, Constant.bitmapWidth, Constant.bitmapheight, Constant.colQty, Constant.rowQty);
        chessBoard.init();
        img.setImageBitmap(chessBoard.drawBoard());

        img.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (isAllowMove) {
                        Move move = chessBoard.getMoveFromTouch(view, motionEvent);
                        if (move != null) {
                            isAllowMove = false;
                            SocketClientPost socketClientPost = new SocketClientPost();
                            socketClientPost.init(move);
                            socketClientPost.start();
                        }
                    }
                }

                return true;
            }
        });
    }

    public void getConfigSettings(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String sizeString = sharedPreferences.getString(Config.KEY_SIZE_BOARD, "");
        String gameModeString = sharedPreferences.getString(Config.KEY_GAME_MODE, "");
        try {
            Config.SIZE_BOARD = Integer.parseInt(sizeString);
            Config.GAME_MODE = GameMode.values()[Integer.parseInt(gameModeString)];
        } catch (Exception ex){
            Log.e("Error", "setConfigSetting: "+ex.getMessage());
        }
    }

}












