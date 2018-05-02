package com.example.dinhtrieu.tictactoenegamaxab.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dinhtrieu.tictactoenegamaxab.R;
import com.example.dinhtrieu.tictactoenegamaxab.configs.Config;
import com.example.dinhtrieu.tictactoenegamaxab.fragments.SettingsFragment;
import com.example.dinhtrieu.tictactoenegamaxab.models.GameMode;
import com.example.dinhtrieu.tictactoenegamaxab.models.SizeBoard;

/**
 * Created by silent on 5/1/2018.
 */
public class SettingActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageView minusModeButton, plusModeButton, minusSizeButton, plusSizeButton, backButton, applyButton;
    private TextView modeText, sizeText;

    private int sizeChosen;
    private int modeChosen;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        init();
    }

    private void init(){
        minusModeButton = findViewById(R.id.minusModeBtn);
        plusModeButton = findViewById(R.id.plusModeBtn);
        minusSizeButton = findViewById(R.id.minusSizeBtn);
        plusModeButton = findViewById(R.id.plusModeBtn);
        backButton = findViewById(R.id.backImg);
        applyButton = findViewById(R.id.applyImg);
        modeText = findViewById(R.id.modeText);
        sizeText = findViewById(R.id.sizeText);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.minusModeBtn:
                modeChosen--;
                if(modeChosen < 0){
                    modeChosen = GameMode.values().length - 1;
                }
                modeText.setText(GameMode.values()[modeChosen].key());
                break;
            case R.id.plusModeBtn:
                modeChosen++;
                if(modeChosen == GameMode.values().length){
                    modeChosen = 0;
                }
                modeText.setText(GameMode.values()[modeChosen].key());
                break;
            case R.id.minusSizeBtn:
                sizeChosen--;
                if(sizeChosen < 0){
                    sizeChosen = GameMode.values().length - 1;
                }
                modeText.setText(GameMode.values()[sizeChosen].key());
                break;
            case R.id.plusSizeBtn:
                sizeChosen++;
                if(sizeChosen == GameMode.values().length){
                    sizeChosen = 0;
                }
                modeText.setText(GameMode.values()[sizeChosen].key());
                break;
            case R.id.backImg:

                break;
            case R.id.applyImg:
                break;
        }
    }
}
