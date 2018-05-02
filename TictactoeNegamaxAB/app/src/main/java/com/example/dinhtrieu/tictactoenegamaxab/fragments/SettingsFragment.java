package com.example.dinhtrieu.tictactoenegamaxab.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.preference.DialogPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceDialogFragmentCompat;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dinhtrieu.tictactoenegamaxab.R;
import com.example.dinhtrieu.tictactoenegamaxab.configs.Config;
import com.example.dinhtrieu.tictactoenegamaxab.models.GameMode;
import com.example.dinhtrieu.tictactoenegamaxab.models.SizeBoard;

/**
 * Created by silent on 4/30/2018.
 */
public class SettingsFragment extends android.support.v4.app.DialogFragment implements View.OnClickListener{

    private ImageView minusModeButton, plusModeButton, minusSizeButton, plusSizeButton, backButton, applyButton;
    private TextView modeText, sizeText;

    private int sizeChosen;
    private int modeChosen;

    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences(Config.NAME_SETTINGS, Context.MODE_PRIVATE);
        getConfigFromPreference();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getActivity().getLayoutInflater().inflate(R.layout.activity_settings, null);
        init(view);
        builder.setView(view);
        return builder.create();
    }

    private void init(View view){
        minusModeButton = view.findViewById(R.id.minusModeBtn);
        plusModeButton = view.findViewById(R.id.plusModeBtn);
        minusSizeButton = view.findViewById(R.id.minusSizeBtn);
        plusSizeButton = view.findViewById(R.id.plusSizeBtn);
        backButton = view.findViewById(R.id.backImg);
        applyButton = view.findViewById(R.id.applyImg);
        modeText = view.findViewById(R.id.modeText);
        sizeText = view.findViewById(R.id.sizeText);

        minusSizeButton.setOnClickListener(this);
        plusSizeButton.setOnClickListener(this);
        minusModeButton.setOnClickListener(this);
        plusModeButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        applyButton.setOnClickListener(this);

        modeText.setText(GameMode.values()[modeChosen].key());
        sizeText.setText(SizeBoard.values()[sizeChosen].key());
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
                sizeText.setText(SizeBoard.values()[sizeChosen].key());
                break;
            case R.id.plusSizeBtn:
                sizeChosen++;
                if(sizeChosen == SizeBoard.values().length){
                    sizeChosen = 0;
                }
                sizeText.setText(SizeBoard.values()[sizeChosen].key());
                break;
            case R.id.backImg:
                this.dismiss();
                break;
            case R.id.applyImg:
                saveSettingsToPreference(sizeChosen, modeChosen);
                this.dismiss();
                break;
        }
    }

    private void getConfigFromPreference(){
        sizeChosen = sharedPreferences.getInt(Config.KEY_SIZE_BOARD, 1);
        modeChosen = sharedPreferences.getInt(Config.KEY_GAME_MODE, 1);
        Config.SIZE_BOARD = SizeBoard.values()[sizeChosen];
        Config.GAME_MODE = GameMode.values()[modeChosen];
    }


    private boolean saveSettingsToPreference(int sizeChosen, int modeChosen){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Config.KEY_SIZE_BOARD, sizeChosen);
        editor.putInt(Config.KEY_GAME_MODE, modeChosen);
        return editor.commit();
    }
}
