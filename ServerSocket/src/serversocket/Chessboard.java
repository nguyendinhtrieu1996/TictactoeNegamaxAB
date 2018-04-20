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
                    RolePlayer opponent;
                    int checkCountLeft = 0;
                    int checkCountRight = 0;

                    if (winner == RolePlayer.PLAYERA) {
                       opponent = RolePlayer.PLAYERB;
                    } else {
                        opponent = RolePlayer.PLAYERA;
                    }

                    for (int j = i; j < colQty; ++j) {
                        if (chessboard[row][j] == opponent) {
                            checkCountLeft++;
                            break;
                        }
                    }

                    for (int j = i - 5; j >= 0; --j) {
                        if (chessboard[row][j] == opponent) {
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
            if (chessboard[i][column] == chessboard[i-1][column] && chessboard[i][column] != RolePlayer.NULL)  {
                count++;

                if (count == winQty) {
                    winner = chessboard[i][column];
                    int checkCountTop = 0;
                    int checkCountBottom = 0;
                    RolePlayer opponent;
                    if (winner == RolePlayer.PLAYERA) {
                        opponent = RolePlayer.PLAYERB;
                    } else {
                        opponent = RolePlayer.PLAYERA;
                    }

                    for (int j = i - 5; j >= 0; --j) {
                        if (chessboard[j][column] == opponent) {
                            checkCountTop++;
                            break;
                        }
                    }

                    for (int j = i; j < rowQty; ++j) {
                        if (chessboard[j][column] == opponent) {
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
            if (chessboard[rowStart + i][colStart - i] == chessboard[rowStart + i + 1][colStart - i - 1] && chessboard[rowStart + i][colStart - i] != RolePlayer.NULL) {
                count++;

                if (count == winQty) {
                    winner = chessboard[rowStart + i][colStart - i];
                    int checkCountTop = 0;
                    int checkCountBottom = 0;
                    RolePlayer opponent;

                    if (winner == RolePlayer.PLAYERA) {
                        opponent = RolePlayer.PLAYERB;
                    } else {
                        opponent = RolePlayer.PLAYERA;
                    }
                    int x = rowStart + i + 1;
                    int y = colStart - i - 1;
                    int t = 0;

                    while (x + t < rowQty && y - t >= 0) {
                        if (chessboard[x + t][y - t] == opponent) {
                            checkCountBottom++;
                        }
                        t++;
                    }

                    t = 0;
                    x -= 5;
                    y += 5;

                    while (x - t >= 0 && y + t < colQty) {
                        if (chessboard[x - t][y + t] == opponent) {
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
            if (chessboard[rowStart + i][colStart + i] == chessboard[rowStart + i + 1][colStart + i + 1] && chessboard[rowStart + i][colStart + i] != RolePlayer.NULL) {
                count++;

                if (count == winQty) {
                    winner = chessboard[rowStart + i][colStart + i];
                    int checkCountTop = 0;
                    int checkCountBottom = 0;
                    RolePlayer opponent;

                    if (winner == RolePlayer.PLAYERA) {
                       opponent = RolePlayer.PLAYERB;
                    } else {
                        opponent = RolePlayer.PLAYERA;
                    }

                    int x = rowStart + i + 1;
                    int y = colStart + i + 1;
                    int t = 0;

                    while (x + t < colQty && y + t < rowQty ) {
                        if (chessboard[x + t][y + t] == opponent) {
                            checkCountBottom++;
                        }
                        t++;
                    }

                    t = 0;
                    x -= 5;
                    y -= 5;

                    while (x - t >= 0 && y - t >= 0) {
                        if (chessboard[x - t][y - t] == opponent) {
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
