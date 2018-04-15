package com.example.dinhtrieu.tictactoenegamaxab.bm;

import android.util.Log;

import com.example.dinhtrieu.tictactoenegamaxab.dm.Move;
import com.example.dinhtrieu.tictactoenegamaxab.dm.Record;

/**
 * Created by dinhtrieu on 4/12/18.
 */


public class Negamax {
    private ChessBoard chessBoard;
    private int index;

    public Negamax(ChessBoard chessBoard) {
        index = 0;
        this.chessBoard = chessBoard;
    }

    public Record negamaxAB(int currentDept, int maxDept, int alpha, int beta) {
        Move bestMove=null;//
        int bestScore;

        index++;
        Log.d("=== index = ", index + "");

        if(chessBoard.isGameOver() || currentDept >= maxDept) {
            int score = chessBoard.evaluate();
            if (score > 0) {
                score -= currentDept;
            } else if (score < 0) {
                score += currentDept;
            } else {
                score = currentDept;
            }
            return new Record(null, score);
        }

        bestScore = Integer.MIN_VALUE;

        for(Move move:chessBoard.getMove()){
            chessBoard.makeMove(move);
            Record record = negamaxAB(
                    currentDept + 1,
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

        }

        return new Record(bestMove, bestScore);
    }

}







