package com.example.dinhtrieu.tictactoenegamaxab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dinhtrieu.tictactoenegamaxab.activities.GameActicity;
import com.example.dinhtrieu.tictactoenegamaxab.activities.SettingActivity;
import com.example.dinhtrieu.tictactoenegamaxab.dm.GameType;
import com.example.dinhtrieu.tictactoenegamaxab.fragments.SettingsFragment;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    //UI Element
    private Button btnPlayWBot;
    private Button btn2Player;
    private Button btnSettings;

    private SettingsFragment settingsFragment;

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

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
            }
        });
    }

    private void init() {
        btnPlayWBot = findViewById(R.id.btnPlayWithBot);
        btn2Player = findViewById(R.id.btn2Player);
        btnSettings = findViewById(R.id.btnSettings);
        settingsFragment = new SettingsFragment();
    }

    private void navigaToGameActivity(GameType type) {
        Intent intent = new Intent(getApplicationContext(), GameActicity.class);
        intent.putExtra("gametype", (Serializable)type);
        startActivity(intent);
    }

}
