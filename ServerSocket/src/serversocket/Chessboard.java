/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serversocket;

/**
 *
 * @author dinhtrieu
 */
public class Chessboard {
   public static int colQty = 8;
   public static int rowQty = 8;
   private RolePlayer[][] chessboard;
   private RolePlayer winner = null;
   private int checkedCount;
   private Boolean isGameOver;
   private RolePlayer currentPlayer;
   private Move previousMove;

    public Chessboard() {
        this.init();
    }
   
   public void init() {
       previousMove = null;
       checkedCount = 0;
       isGameOver = false;
       chessboard = new RolePlayer[rowQty][colQty];
       
       for (int i = 0; i < rowQty; ++i) {
           for (int j = 0; j < colQty; ++j) {
               chessboard[i][j] = RolePlayer.NULL;
           }
       }
   }
   
   //Public method
   public Boolean makeMove(Move move, RolePlayer role) {
       if (!canMove(move)) {
           return false;
       }
       
       this.previousMove = move;
       this.currentPlayer = role;
       chessboard[move.getRow()][move.getCol()] = role;
       
       return null;
   }
   
   public Boolean checkWin(Move move) {
       return null;
   }
   
   public Boolean canMove(Move move) {
       if (chessboard[move.getRow()][move.getCol()] == RolePlayer.NULL) {
           return true;
       }
       return false;
   }
   
   public boolean isGameOver(RolePlayer player){
        return false;
    }

    public RolePlayer getWinner() {
        return winner;
    }

    public void setWinner(RolePlayer winner) {
        this.winner = winner;
    }

    public Boolean getIsGameOver() {
        return isGameOver;
    }

    public void setIsGameOver(Boolean isGameOver) {
        this.isGameOver = isGameOver;
    }

    public RolePlayer getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(RolePlayer currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
   
   
   
}
