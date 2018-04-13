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
   private ChessboardState[][] chessboard;
   private RolePlayer winner = null;
   private int checkedCount;
   private Boolean isGameOver;
   private RolePlayer currentPlayer;

    public Chessboard() {
        this.init();
    }
   
   private void init() {
       checkedCount = 0;
       isGameOver = false;
       chessboard = new ChessboardState[rowQty][colQty];
       
       for (int i = 0; i < rowQty; ++i) {
           for (int j = 0; j < colQty; ++j) {
               chessboard[i][j] = ChessboardState.NULL;
           }
       }
   }
   
   //Public method
   public Boolean makeMove(Move move) {
       if (!canMove(move)) {
           return false;
       }
       
       return null;
   }
   
   public Boolean checkWin(Move move) {
       return null;
   }
   
   public Boolean canMove(Move move) {
       return null;
   }
   
   public boolean isGameOver(RolePlayer player){
        

        return false;
    }
   
}
