package com.example.dinhtrieu.tictactoenegamaxab.bm;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.dinhtrieu.tictactoenegamaxab.R;
import com.example.dinhtrieu.tictactoenegamaxab.dm.Line;
import com.example.dinhtrieu.tictactoenegamaxab.dm.Move;
import com.example.dinhtrieu.tictactoenegamaxab.dm.Record;
import com.example.dinhtrieu.tictactoenegamaxab.uit.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dinhtrieu on 4/12/18.
 */


public class ChessBoard {
    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint;
    private int[][] board;
    private int player;
    private Context context;
    private int bitmapWidth, bitmapHeight, colQty,rowQty;
    private List<Line> lines;
    private Negamax negamax;
    private int winQty;
    private Move previousMove;
    private int winner;
    public int checkedCount;
    public static boolean isGameOver = false;
    public ChessBoardHelper chessBoardHelper;

    private Bitmap playerA, playerB;

    public ChessBoard(Context context, int bitmapWidth, int bitmapHeight, int colQty, int rowQty) {
        this.context = context;
        this.bitmapWidth = bitmapWidth;
        this.bitmapHeight = bitmapHeight;
        this.colQty = colQty;
        this.rowQty = rowQty;
    }

    public void init() {
        player = Constant.playerValue;
        isGameOver = false;
        checkedCount = 0;
        winner = Constant.noneValue;
        previousMove = null;
        lines = new ArrayList<>();
        bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint = new Paint();
        board = new int[rowQty][colQty];

        playerA = BitmapFactory.decodeResource(context.getResources(), R.mipmap.player);
        playerB = BitmapFactory.decodeResource(context.getResources(),R.mipmap.opponent);

        if (colQty > 5) {
            winQty = 4;
        } else {
            winQty = 2;
        }

        for(int i = 0; i<rowQty; i++){
            for(int j = 0; j < colQty;j++){
                board[i][j] = Constant.noneValue;
            }
        }

        chessBoardHelper = new ChessBoardHelper(board, rowQty);
        paint.setStrokeWidth(2);
        int celWidth = bitmapWidth/colQty;
        int celHeight = bitmapHeight/rowQty;

        for(int i = 0; i <= colQty; i++){
            lines.add(new Line(celWidth*i, 0, celWidth*i, bitmapHeight));
        }
        for(int i = 0; i <= rowQty; i++){
            lines.add(new Line(0, i*celHeight, bitmapWidth, i*celHeight));
        }
    }

    public Bitmap drawBoard(){
        for(int i = 0; i < lines.size(); i++) {
            canvas.drawLine(
                    lines.get(i).getX1(),
                    lines.get(i).getY1(),
                    lines.get(i).getX2(),
                    lines.get(i).getY2(),
                    paint
            );
        }

        return bitmap;
    }

    public boolean onTouch(final View view, MotionEvent motionEvent){

        final int cellWidth = bitmapWidth / colQty;
        final int cellHeight = bitmapHeight / rowQty;
        final int colIndex = (int) (motionEvent.getX() / (view.getWidth() / colQty));
        final int rowIndex = (int) (motionEvent.getY() / (view.getHeight() / rowQty));

        if(board[rowIndex][colIndex] != Constant.noneValue) {
            Toast.makeText(context, "Da chon roi", Toast.LENGTH_LONG).show();
            return true;
        }

        onDrawBoard(rowIndex, colIndex, cellWidth, cellHeight);
        view.invalidate();

        board[rowIndex][colIndex] = player;
        previousMove = new Move(rowIndex, colIndex);
        checkedCount++;
        convertPlayer();

        if(isGameOver()){
            isGameOver = true;
            if (winner == Constant.computerValue) {
                Toast.makeText(context, "Ban thua roi", Toast.LENGTH_LONG).show();
            } else if (winner == Constant.playerValue) {
                Toast.makeText(context, "Ban thang roi", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Ban hoa", Toast.LENGTH_LONG).show();
            }

            return true;
        }

        return true;
    }

    public boolean negaABMove(final View view, MotionEvent motionEvent) {
        final int cellWidth = bitmapWidth / colQty;
        final int cellHeight = bitmapHeight / rowQty;

        int count = getCurrentDept();
        final int currentDetp = rowQty*colQty - count;

        negamax = new Negamax(this);
        chessBoardHelper.resetValue();

        Record record = negamax.negamaxAB(
                0,
                Constant.MAX_DEPTH,
                Integer.MIN_VALUE,
                Integer.MAX_VALUE
        );

        onDrawBoard(record.getMove().getRowIndex(), record.getMove().getColIndex() , cellWidth, cellHeight);
        board[record.getMove().getRowIndex()][ record.getMove().getColIndex()] = player;
        player = (player + 1) % 2;
        checkedCount++;
        previousMove = record.getMove();
        view.invalidate();

        if (isGameOver()) {
            isGameOver = true;
            if (winner == Constant.computerValue) {
                Toast.makeText(context, "Ban thua roi", Toast.LENGTH_LONG).show();
            } else if (winner == Constant.playerValue) {
                Toast.makeText(context, "Ban thang roi", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Ban hoa", Toast.LENGTH_LONG).show();
            }
        }

        return true;
    }

    public Move onTouchMove(final View view, MotionEvent motionEvent) {
        int cellWidth = bitmapWidth / colQty;
        int cellHeight = bitmapHeight / rowQty;
        int colIndex = (int) (motionEvent.getX() / (view.getWidth() / colQty));
        int rowIndex = (int) (motionEvent.getY() / (view.getHeight() / rowQty));

        if (board[rowIndex][colIndex] != Constant.noneValue) {
            return null;
        }

        onDrawBoard(rowIndex, colIndex, cellWidth, cellHeight);
        makeMove(new Move(rowIndex, colIndex));
        view.invalidate();

        return new Move(rowIndex, colIndex);
    }

    public void opponentDraw(Move move, View view) {
        int cellWidth = bitmapWidth / colQty;
        int cellHeight = bitmapHeight / rowQty;
        onDrawBoard(move.getRowIndex(), move.getColIndex(), cellWidth, cellHeight);
        makeMove(move);
        view.invalidate();
    }

    public void onDrawBoard(int rowIndex, int colIndex, int cellWidth, int cellHeight){
        int padding = 5;

        if(player == Constant.playerValue){
            canvas.drawBitmap(
                    playerA,
                    new Rect(0,0,playerA.getWidth(), playerA.getHeight()),
                    new Rect(colIndex*cellWidth+padding,rowIndex*cellHeight+padding,(colIndex+1)*cellWidth -padding, (rowIndex+1)*cellHeight -padding),
                    paint);
        } else {
            canvas.drawBitmap(
                    playerB,
                    new Rect(0, 0, playerB.getWidth(), playerB.getHeight()),
                    new Rect(colIndex * cellWidth, rowIndex * cellHeight, (colIndex + 1) * cellWidth, (rowIndex + 1) * cellHeight),
                    paint);
        }
    }

    public boolean isGameOver(){
        if (checkWin()) {
            return true;
        }

        if (checkedCount == rowQty * colQty) {
            return true;
        }

        return false;
    }

    private boolean checkWin() {
        if (previousMove == null) return false;
        if (checkRow(previousMove.getRowIndex())
                || checkColumn(previousMove.getColIndex())
                || checkDiagonalFromTopLeft(previousMove.getRowIndex(), previousMove.getColIndex())
                || checkDiagonalFromTopRight(previousMove.getRowIndex(), previousMove.getColIndex())) {
            return true;
        }

        return false;
    }

    private Boolean checkRow (int row) {
        int count = 0;

        for (int i = 1; i < rowQty; i++) {
            if (board[row][i] == board[row][i-1] && board[row][i] != Constant.noneValue) {
                count++;

                if (count == winQty) {
                    winner = board[row][i];

                    int opponent;
                    int checkCountLeft = 0;
                    int checkCountRight = 0;

                    if (winner == Constant.playerValue) {
                       opponent = Constant.computerValue;
                    } else {
                        opponent = Constant.playerValue;
                    }

                    for (int j = i; j < Constant.colQty; ++j) {
                        if (board[row][j] == opponent) {
                            checkCountLeft++;
                            break;
                        }
                    }

                    for (int j = i - 5; j >= 0; --j) {
                        if (board[row][j] == opponent) {
                            checkCountRight++;
                            break;
                        }
                    }

                    if (checkCountLeft > 0 && checkCountRight > 0) {
                        return false;
                    }


                    return true;
                }
            } else {
                count = 0;
            }
        }

        return false;
    }

    private boolean checkColumn (int column) {
        int count = 0;

        for (int i = 1; i < colQty; i++) {
            if (board[i][column] == board[i-1][column] && board[i][column] != Constant.noneValue)  {
                count++;

                if (count == winQty) {
                    winner = board[i][column];

                    int checkCountTop = 0;
                    int checkCountBottom = 0;
                    int opponent;
                    if (winner == Constant.playerValue) {
                        opponent = Constant.computerValue;
                    } else {
                        opponent = Constant.playerValue;
                    }

                    for (int j = i - 5; j >= 0; --j) {
                        if (board[j][column] == opponent) {
                            checkCountTop++;
                            break;
                        }
                    }

                    for (int j = i; j < Constant.rowQty; ++j) {
                        if (board[j][column] == opponent) {
                            checkCountBottom++;
                            break;
                        }
                    }

                    if (checkCountBottom > 0 && checkCountTop > 0) {
                        return false;
                    }

                    return true;
                }
            } else {
                count = 0;
            }
        }

        return false;
    }

    private Boolean checkDiagonalFromTopRight (int row, int col) {
        int rowStart, colStart;
        int i = 0;
        int count = 0;

        if (row + col < colQty - 1) {
            colStart = row + col;
            rowStart = 0;
        } else {
            colStart = colQty - 1;
            rowStart = col + row - (colQty - 1);
        }

        while (colStart - i - 1 >= 0 && rowStart + i + 1 < colQty) {
            if (board[rowStart + i][colStart - i] == board[rowStart + i + 1][colStart - i - 1] && board[rowStart + i][colStart - i] != Constant.noneValue) {
                count++;

                if (count == winQty) {
                    winner = board[rowStart + i][colStart - i];

                    int checkCountTop = 0;
                    int checkCountBottom = 0;
                    int opponent;

                    if (winner == Constant.playerValue) {
                        opponent = Constant.computerValue;
                    } else {
                        opponent = Constant.playerValue;
                    }
                    int x = rowStart + i + 1;
                    int y = colStart - i - 1;
                    int t = 0;

                    while (x + t < rowQty && y - t >= 0) {
                        if (board[x + t][y - t] == opponent) {
                            checkCountBottom++;
                        }
                        t++;
                    }

                    t = 0;
                    x -= 5;
                    y += 5;

                    while (x - t >= 0 && y + t < colQty) {
                        if (board[x - t][y + t] == opponent) {
                            checkCountTop++;
                        }
                        t++;
                    }

                    if (checkCountBottom > 0 && checkCountTop > 0) {
                        return false;
                    }

                    return true;
                }
            } else {
                count = 0;
            }

            i++;
        }

        return false;
    }

    private Boolean checkDiagonalFromTopLeft (int row, int col) {
        int rowStart, colStart;
        int i = 0;
        int count = 0;

        if (row > col) {
            rowStart = row - col;
            colStart = 0;
        } else {
            rowStart = 0;
            colStart = col - row;
        }

        while (rowStart + i + 1 < colQty && colStart + i + 1 < rowQty) {
            if (board[rowStart + i][colStart + i] == board[rowStart + i + 1][colStart + i + 1] && board[rowStart + i][colStart + i] != Constant.noneValue) {
                count++;

                if (count == winQty) {
                    winner = board[rowStart + i][colStart + i];

                    int checkCountTop = 0;
                    int checkCountBottom = 0;
                    int opponent;

                    if (winner == Constant.playerValue) {
                        opponent = Constant.computerValue;
                    } else {
                        opponent = Constant.playerValue;
                    }

                    int x = rowStart + i + 1;
                    int y = colStart + i + 1;
                    int t = 0;

                    while (x + t < colQty && y + t < rowQty ) {
                        if (board[x + t][y + t] == opponent) {
                            checkCountBottom++;
                        }
                        t++;
                    }

                    t = 0;
                    x -= 5;
                    y -= 5;

                    while (x - t >= 0 && y - t >= 0) {
                        if (board[x - t][y - t] == opponent) {
                            checkCountTop++;
                        }
                        t++;
                    }


                    if (checkCountTop > 0 && checkCountBottom > 0) {
                        return false;
                    }

                    return true;
                }
            } else {
                count = 0;
            }
            i++;
        }

        return false;
    }

    public List<Move> getMove() {
       return chessBoardHelper.getMoves(player);
    }

    public void makeMove(Move move) {
        checkedCount++;
        previousMove = move;
        board[move.getRowIndex()][move.getColIndex()] = player;
        convertPlayer();
    }

    public int evaluate() {
        return chessBoardHelper.evaluationBoard(player);
    }


    public int[][] getNewBoard(){
        int[][] newBoard = new int[rowQty][colQty];
        for (int i = 0; i < rowQty; i++) {
            for (int j = 0; j < colQty; j++) {
                newBoard[i][j] = board[i][j];
            }
        }
        return newBoard;
    }

    public void resetWinner() {
        winner = Constant.noneValue;
    }

    public void removeMove(Move move) {
        chessBoardHelper.resetMove(move);
        board[move.getRowIndex()][move.getColIndex()] = Constant.noneValue;
        checkedCount--;
        convertPlayer();
    }


    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getBitmapWidth() {
        return bitmapWidth;
    }

    public void setBitmapWidth(int bitmapWidth) {
        this.bitmapWidth = bitmapWidth;
    }

    public int getBitmapHeight() {
        return bitmapHeight;
    }

    public void setBitmapHeight(int bitmapHeight) {
        this.bitmapHeight = bitmapHeight;
    }

    public int getColQty() {
        return colQty;
    }

    public void setColQty(int colQty) {
        this.colQty = colQty;
    }

    public int getRowQty() {
        return rowQty;
    }

    public void setRowQty(int rowQty) {
        this.rowQty = rowQty;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int getCurrentDept(){
        int count = 0;
        for (int i = 0; i < rowQty; i++) {
            for (int j = 0; j < colQty; j++) {
                if (board[i][j] == Constant.noneValue) count++;
            }
        }
        return count;
    }

    public void convertPlayer() {
        if (player == Constant.playerValue) {
            player = Constant.computerValue;
        } else {
            player = Constant.playerValue;
        }
    }

    public int getWinner() {
        return winner;
    }

}









