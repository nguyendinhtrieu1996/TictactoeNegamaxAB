package com.example.dinhtrieu.tictactoenegamaxab.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.dinhtrieu.tictactoenegamaxab.R;
import com.example.dinhtrieu.tictactoenegamaxab.activities.GameActicity;
import com.example.dinhtrieu.tictactoenegamaxab.activities.SettingActivity;
import com.example.dinhtrieu.tictactoenegamaxab.dm.GameType;
import com.example.dinhtrieu.tictactoenegamaxab.fragments.SettingsFragment;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    private ImageView newButton, optionButton, exitButton;

    private SettingsFragment settingsFragment;

    //Life circle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigaToGameActivity(GameType.BOT);
            }
        });

        optionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsFragment.show(getSupportFragmentManager(), "settings");
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        newButton = findViewById(R.id.newButton);
        optionButton = findViewById(R.id.optionButton);
        exitButton = findViewById(R.id.exitButton);
        settingsFragment = new SettingsFragment();
    }

    private void navigaToGameActivity(GameType type) {
        Intent intent = new Intent(getApplicationContext(), GameActicity.class);
        intent.putExtra("gametype", (Serializable)type);
        startActivity(intent);
    }

}
