package com.example.dinhtrieu.tictactoenegamaxab.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;

import com.example.dinhtrieu.tictactoenegamaxab.R;

public class EndGameDiaglog extends android.support.v4.app.DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.end_game_dialog, null);

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

}
