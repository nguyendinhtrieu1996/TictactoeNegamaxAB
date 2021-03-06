package com.example.dinhtrieu.tictactoenegamaxab.bm;

import android.util.Log;

import com.example.dinhtrieu.tictactoenegamaxab.dm.Move;
import com.example.dinhtrieu.tictactoenegamaxab.dm.Record;
import com.example.dinhtrieu.tictactoenegamaxab.uit.Constant;

/**
 * Created by dinhtrieu on 4/12/18.
 */


public class Negamax {
    private ChessBoard chessBoard;

    public Negamax(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }

    public Record negamaxAB(int currentDept, int maxDept, int alpha, int beta) {
        Move bestMove=null;//
        int bestScore;

        if(currentDept >= maxDept || chessBoard.isGameOver()) {
            int val = chessBoard.evaluate();
            return new Record(null, val);
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

            chessBoard.chessBoardHelper.makeMove(move, chessBoard.getPlayer());
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












