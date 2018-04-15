package com.example.dinhtrieu.tictactoenegamaxab.bm;

import android.util.Log;

import com.example.dinhtrieu.tictactoenegamaxab.dm.Move;
import com.example.dinhtrieu.tictactoenegamaxab.dm.Record;

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

            if(currentScore > bestScore) {
                bestScore = currentScore;
                bestMove = move;
            }

            alpha = Math.max(alpha, currentScore);

//            if (currentScore >= beta) {
//                return new Record(bestMove, bestScore);
//            }

        }

        return new Record(bestMove, bestScore);
    }

}







