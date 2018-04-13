package com.example.dinhtrieu.tictactoenegamaxab.bm;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import com.example.dinhtrieu.tictactoenegamaxab.dm.Move;

/**
 * Created by dinhtrieu on 4/13/18.
 */

public class ChessboardTwoPlayer {
    private int bitmapWidth, bitmapHeight, colQty,rowQty;

    public ChessboardTwoPlayer(int bitmapWidth, int bitmapHeight, int colQty, int rowQty) {
        this.bitmapWidth = bitmapWidth;
        this.bitmapHeight = bitmapHeight;
        this.colQty = colQty;
        this.rowQty = rowQty;
    }

    public Move onTouch(final View view, MotionEvent motionEvent) {

        final int cellWidth = bitmapWidth / colQty;
        final int cellHeight = bitmapHeight / rowQty;
        final int colIndex = (int) (motionEvent.getX() / (view.getWidth() / colQty));
        final int rowIndex = (int) (motionEvent.getY() / (view.getHeight() / rowQty));
        return new Move(rowIndex, colIndex);
    }


}
