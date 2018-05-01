package com.example.dinhtrieu.tictactoenegamaxab.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.dinhtrieu.tictactoenegamaxab.R;
import com.example.dinhtrieu.tictactoenegamaxab.uit.Constant;
import com.example.dinhtrieu.tictactoenegamaxab.uit.EndGameDialogDelegate;

public class EndGameDiaglog extends android.support.v4.app.DialogFragment implements View.OnClickListener {

    //Variable
    public EndGameDialogDelegate delgate;

    //UI Elements
    private ImageView imgWin;
    private ImageButton btnContinue;
    private ImageButton btnQuit;
    public int winner;

    //Life Circle
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = getActivity().getLayoutInflater().inflate(R.layout.end_game_dialog, null);
        imgWin = view.findViewById(R.id.imgWin);
        btnContinue = view.findViewById(R.id.btnContinue);
        btnQuit = view.findViewById(R.id.btnQuit);

        if (winner == Constant.playerValue) {
            imgWin.setImageResource(R.drawable.win);
        } else if (winner == Constant.computerValue) {
            imgWin.setImageResource(R.drawable.loose);
        } else {
            imgWin.setImageResource(R.drawable.draw);
        }

        btnContinue.setOnClickListener(this);
        btnQuit.setOnClickListener(this);

        builder.setView(view);

        return builder.create();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static EndGameDiaglog newInstance() {
        EndGameDiaglog endGameDiaglog = new EndGameDiaglog();
        return endGameDiaglog;
    }

    //Handler


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnContinue:
                delgate.didSelectedContinue();
                getDialog().cancel();
                break;
            case R.id.btnQuit:
                delgate.didSelectedQuit();
                getDialog().cancel();
                break;
        }
    }
}
