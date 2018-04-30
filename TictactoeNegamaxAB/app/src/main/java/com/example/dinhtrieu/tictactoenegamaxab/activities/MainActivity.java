package com.example.dinhtrieu.tictactoenegamaxab.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dinhtrieu.tictactoenegamaxab.R;
import com.example.dinhtrieu.tictactoenegamaxab.activities.GameActicity;
import com.example.dinhtrieu.tictactoenegamaxab.dm.GameType;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    //UI Element
    private Button btnPlayWBot;
    private Button btn2Player;

    //Life circle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        btnPlayWBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigaToGameActivity(GameType.BOT);
            }
        });

        btn2Player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigaToGameActivity(GameType.PLAYER);
            }
        });
    }

    private void init() {
        btnPlayWBot = findViewById(R.id.btnPlayWithBot);
        btn2Player = findViewById(R.id.btn2Player);
    }

    private void navigaToGameActivity(GameType type) {
        Intent intent = new Intent(getApplicationContext(), GameActicity.class);
        intent.putExtra("gametype", (Serializable)type);
        startActivity(intent);
    }

}
