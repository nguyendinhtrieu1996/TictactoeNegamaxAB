package com.example.dinhtrieu.tictactoenegamaxab.bm;

import android.util.Log;

import com.example.dinhtrieu.tictactoenegamaxab.dm.Move;
import com.example.dinhtrieu.tictactoenegamaxab.dm.Record;

/**
 * Created by dinhtrieu on 4/12/18.
 */


public class Negamax {

    public static int index = 0;
    private ChessBoard chessBoard;

    public Negamax(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }

    public Record negamaxAB(int currentDept, int maxDept, int alpha, int beta) {
        Move bestMove=null;//
        int bestScore;

        if(chessBoard.isGameOver() || currentDept == maxDept) {

            Log.d(index++ + "====== evalue ", chessBoard.evaluate() + "");
            chessBoard.over = true;
            return new Record(null, chessBoard.evaluate());
        }

        bestScore = Integer.MIN_VALUE;

        for(Move move:chessBoard.getMove()){
            chessBoard.makeMove(move);
            Record record = negamaxAB(
                    currentDept++,
                    maxDept,
                    -beta,
                    -alpha
            );


            int currentScore = -record.getScore();
            chessBoard.removeMove(move);
            chessBoard.resetWinner();

            alpha = Math.max(alpha, currentScore);

            if(currentScore > bestScore) {
                bestScore = currentScore;
                bestMove = move;
            }

            if (currentScore >= beta || alpha >= beta) {
                return new Record(bestMove, bestScore);
            }

        }

        return new Record(bestMove, bestScore);
    }

}







