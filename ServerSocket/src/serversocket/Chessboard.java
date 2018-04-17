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
   private int winQty;

    public Chessboard() {
        this.init();
    }
   
   public void init() {
       winQty = 4;
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
       this.checkedCount++;
       chessboard[move.getRow()][move.getCol()] = role;
       isGameOver();
       
       return null;
   }
   
   public Boolean checkWin() {
       if (previousMove == null) return false;
        if (checkRow(previousMove.getRow())
                || checkColumn(previousMove.getCol())
                || checkDiagonalFromTopLeft(previousMove.getRow(), previousMove.getCol())
                || checkDiagonalFromTopRight(previousMove.getRow(), previousMove.getCol())) {
            return true;
        }

        return false;
   }
   
   private Boolean checkRow (int row) {
        int count = 0;
        for (int i = 1; i < rowQty; i++) {
            if (chessboard[row][i] == chessboard[row][i-1] && chessboard[row][i] != RolePlayer.NULL) {
                count++;

                if (count == winQty) {
                    winner = chessboard[row][i];
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
            if (chessboard[i][column] == chessboard[i-1][column] && chessboard[i][column] != RolePlayer.NULL)  {
                count++;

                if (count == winQty) {
                    winner = chessboard[i][column];
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
            if (chessboard[rowStart + i][colStart - i] == chessboard[rowStart + i + 1][colStart - i - 1] && chessboard[rowStart + i][colStart - i] != RolePlayer.NULL) {
                count++;

                if (count == winQty) {
                    winner = chessboard[rowStart + i][colStart - i];
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
            if (chessboard[rowStart + i][colStart + i] == chessboard[rowStart + i + 1][colStart + i + 1] && chessboard[rowStart + i][colStart + i] != RolePlayer.NULL) {
                count++;

                if (count == winQty) {
                    winner = chessboard[rowStart + i][colStart + i];
                    return true;
                }
            } else {
                count = 0;
            }
            i++;
        }

        return false;
    }
   
   public Boolean canMove(Move move) {
       if (chessboard[move.getRow()][move.getCol()] == RolePlayer.NULL) {
           return true;
       }
       return false;
   }
   
   public boolean isGameOver(){
       if (checkWin()) {
           isGameOver = true;
           return true;
       }
       
       if (checkedCount == rowQty * colQty) {
           isGameOver = true;
           return true;
       }
       
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

    public RolePlayer[][] getChessboard() {
        return chessboard;
    }
   
    public String getBoardString() {
        String result = "[";
        
        for (int i = 0; i < rowQty; ++i) {
            result += "[";
            for (int j = 0; j < colQty; ++j) {
                if (chessboard[i][j] == RolePlayer.PLAYERA) {
                    result += 1;
                } else if (chessboard[i][j] == RolePlayer.PLAYERB) {
                    result += 2;
                } else {
                    result += 0;
                }
                
                if (j != colQty - 1) {
                    result += ", ";
                }
            }
            result += "]";
        }
        
        return result + "]";
    }
   
}
