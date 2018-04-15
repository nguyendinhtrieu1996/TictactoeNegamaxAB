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
            int newA;
            int newB;

            if (alpha == Integer.MIN_VALUE) {
                newB = Integer.MAX_VALUE;
            } else if (alpha == Integer.MAX_VALUE) {
                newB = Integer.MIN_VALUE;
            } else {
                newB = -alpha;
            }

            if (beta == Integer.MIN_VALUE) {
                newA = Integer.MAX_VALUE;
            } else if (beta == Integer.MAX_VALUE) {
                newA = Integer.MIN_VALUE;
            } else {
                newA = -beta;
            }

            chessBoard.makeMove(move);
            Record record = negamaxAB(
                    currentDept + 1,
                    maxDept,
                    newA,
                    newB
            );


            int currentScore = -record.getScore();
            chessBoard.removeMove(move);
            chessBoard.resetWinner();

            if(currentScore > bestScore) {
                bestScore = currentScore;
                bestMove = move;
            }

            alpha = Math.max(bestScore, alpha);

            if (bestScore >= beta) {
                return new Record(bestMove, alpha);
            }

        }

        return new Record(bestMove, bestScore);
    }

}







