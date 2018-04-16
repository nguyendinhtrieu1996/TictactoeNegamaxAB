package com.example.dinhtrieu.tictactoenegamaxab.bm;

import com.example.dinhtrieu.tictactoenegamaxab.dm.Move;
import com.example.dinhtrieu.tictactoenegamaxab.dm.Record;
import com.example.dinhtrieu.tictactoenegamaxab.uit.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dinhtrieu on 4/16/18.
 */

public class ChessBoaardHelper {
    private String[] caseHuman = {"0110", "01112", "0110102", "21110", "010110",
            "011010", "01110", "011112", "211110", "2111010", "011110", "11111",
            "0111012", "10101011", "0101110", "0111010", "0111102", "110110",
            "011011", "0101112", "11110",";11110","01111;"};

    private String[] caseAI = {"0220", "02221", "0220201", "12220", "020220",
            "022020", "02220", "022221", "122220", "1222020", "022220", "22222",
            "0222021", "20202022", "0202220", "0222020", "0222201", "220220",
            "022022", "0202221", "22220",";22220","02222;"};

    private int[] point = {6, 4, 4, 4, 12, 12, 12, 1000, 1000, 1000, 3000, 10000,
            1000, 3000, 1000, 1000, 1000, 1000, 1000,1000, 1000,1000,1000};

    private int n;
    private Random rand;
    private int[] defenseScore = {0, 1, 9, 85, 769};
    private int[] attackScore = {0, 4, 28, 256, 2308};
    private int[][] evaluateSquare;
    private int maxSquare;
    private int[][] chessBoard;

    public ChessBoaardHelper(int[][] chessboard, int size) {
        n = size;
        this.chessBoard = chessboard;
        maxSquare = 4;
        rand = new Random();
        evaluateSquare = new int[n][n];
    }

    public void resetValue() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                evaluateSquare[i][j] = 0;
            }
        }
    }

    public void evaluateEachSquare(int[][] b, int Player) {
        resetValue();
        int row, colum, i;
        int countAI, countHuman;

        /**
         * Check in rows
         */
        for (row = 0; row < n; row++) {
            for (colum = 0; colum < n - 4; colum++) {
                countAI = 0;
                countHuman = 0;
                for (i = 0; i < 5; i++) {
                    if (b[row][colum + i] == 2) {
                        countAI++;
                    }
                    if (b[row][colum + i] == 1) {
                        countHuman++;
                    }
                }
                if (countAI * countHuman == 0 && countAI != countHuman) {
                    for (i = 0; i < 5; i++) {
                        if (b[row][colum + i] == 0) {
                            if (countAI == 0) {
                                if (Player == 2) { // AI turn => AI have to Defense
                                    evaluateSquare[row][colum + i] += defenseScore[countHuman];
                                } else { // Player Turn => Player wil Attack
                                    evaluateSquare[row][colum + i] += attackScore[countHuman];
                                }
                                if (CheckPoint(row, colum - 1) && CheckPoint(row, colum + 5) && b[row][colum - 1] == 2 && b[row][colum + 5] == 2) {
                                    evaluateSquare[row][colum + i] = 0;
                                }
                            }
                            if (countHuman == 0) {
                                if (Player == 1) {
                                    evaluateSquare[row][colum + i] += defenseScore[countAI];
                                } else {
                                    evaluateSquare[row][colum + i] += attackScore[countAI];
                                }
                                if (CheckPoint(row, colum - 1) && CheckPoint(row, colum + 5) && b[row][colum - 1] == 1 && b[row][colum + 5] == 1) {
                                    evaluateSquare[row][colum + i] = 0;
                                }
                            }
                            if ((countAI == 4 || countHuman == 4) && ((CheckPoint(row, colum + i - 1) && b[row][colum + i - 1] == 0) || (CheckPoint(row, colum + i + 1) && b[row][colum + i + 1] == 0))) {
                                evaluateSquare[row][colum + i] *= 2;
                            } else if (countAI == 4 || countHuman == 4) {
                                evaluateSquare[row][colum + i] *= 2;
                            }
                        }
                    }
                }
            }
        }
        /**
         * check in colums
         */
        for (row = 0; row < n - 4; row++) {
            for (colum = 0; colum < n; colum++) {
                countAI = 0;
                countHuman = 0;
                for (i = 0; i < 5; i++) {
                    if (b[row + i][colum] == 2) {
                        countAI++;
                    }
                    if (b[row + i][colum] == 1) {
                        countHuman++;
                    }
                }
                if (countAI * countHuman == 0 && countAI != countHuman) {
                    for (i = 0; i < 5; i++) {
                        if (b[row + i][colum] == 0) {
                            if (countAI == 0) {
                                if (Player == 2) {
                                    evaluateSquare[row + i][colum] += defenseScore[countHuman];
                                } else {
                                    evaluateSquare[row + i][colum] += attackScore[countHuman];
                                }
                                if (CheckPoint(row - 1, colum) && CheckPoint(row + 5, colum) && b[row - 1][colum] == 2 && b[row + 5][colum] == 2) {
                                    evaluateSquare[row + i][colum] = 0;
                                }
                            }
                            if (countHuman == 0) {
                                if (Player == 1) {
                                    evaluateSquare[row + i][colum] += defenseScore[countAI];
                                } else {
                                    evaluateSquare[row + i][colum] += attackScore[countAI];
                                }
                                if (CheckPoint(row - 1, colum) && CheckPoint(row + 5, colum) && b[row - 1][colum] == 1 && b[row + 5][colum] == 1) {
                                    evaluateSquare[row + i][colum] = 0;
                                }
                            }
                            if ((countAI == 4 || countHuman == 4) && ((CheckPoint(row + i - 1, colum) && b[row + i - 1][colum] == 0) || (CheckPoint(row + i + 1, colum) && b[row + i + 1][colum] == 0))) {
                                evaluateSquare[row + i][colum] *= 2;
                            } else if (countAI == 4 || countHuman == 4) {
                                evaluateSquare[row + i][colum] *= 2;
                            }
                        }
                    }
                }
            }
        }
        /**
         * Check in diagonal line
         */
        for (row = 0; row < n - 4; row++) {
            for (colum = 0; colum < n - 4; colum++) {
                countAI = 0;
                countHuman = 0;
                for (i = 0; i < 5; i++) {
                    if (b[row + i][colum + i] == 2) {
                        countAI++;
                    }
                    if (b[row + i][colum + i] == 1) {
                        countHuman++;
                    }
                }
                if (countAI * countHuman == 0 && countAI != countHuman) {
                    for (i = 0; i < 5; i++) {
                        if (b[row + i][colum + i] == 0) {
                            if (countAI == 0) {
                                if (Player == 2) {
                                    evaluateSquare[row + i][colum + i] += defenseScore[countHuman];
                                } else {
                                    evaluateSquare[row + i][colum + i] += attackScore[countHuman];
                                }
                                if (CheckPoint(row - 1, colum - 1) && CheckPoint(row + 5, colum + 5) && b[row - 1][colum - 1] == 2 && b[row + 5][colum + 5] == 2) {
                                    evaluateSquare[row + i][colum + i] = 0;
                                }
                            }
                            if (countHuman == 0) {
                                if (Player == 1) {
                                    evaluateSquare[row + i][colum + i] += defenseScore[countAI];
                                } else {
                                    evaluateSquare[row + i][colum + i] += attackScore[countAI];
                                }
                                if (CheckPoint(row - 1, colum - 1) && CheckPoint(row + 5, colum + 5) && b[row - 1][colum - 1] == 1 && b[row + 5][colum + 5] == 1) {
                                    evaluateSquare[row + i][colum + i] = 0;
                                }
                            }
                            if ((countAI == 4 || countHuman == 4) && ((CheckPoint(row + i - 1, colum + i - 1) && b[row + i - 1][colum + i - 1] == 0) || (CheckPoint(row + i + 1, colum + i + 1) && b[row + i + 1][colum + i + 1] == 0))) {
                                evaluateSquare[row + i][colum + i] *= 2;
                            } else if (countAI == 4 || countHuman == 4) {
                                evaluateSquare[row + i][colum + i] *= 2;
                            }
                        }
                    }
                }
            }
        }
        /**
         * Check in diagonal line
         */
        for (row = 4; row < n; row++) {
            for (colum = 0; colum < n - 4; colum++) {
                countAI = 0;
                countHuman = 0;
                for (i = 0; i < 5; i++) {
                    if (b[row - i][colum + i] == 2) {
                        countAI++;
                    }
                    if (b[row - i][colum + i] == 1) {
                        countHuman++;
                    }
                }
                if (countAI * countHuman == 0 && countAI != countHuman) {
                    for (i = 0; i < 5; i++) {
                        if (b[row - i][colum + i] == 0) {
                            if (countAI == 0) {
                                if (Player == 2) {
                                    evaluateSquare[row - i][colum + i] += defenseScore[countHuman];
                                } else {
                                    evaluateSquare[row - i][colum + i] += attackScore[countHuman];
                                }
                                if (CheckPoint(row + 1, colum - 1) && CheckPoint(row - 5, colum + 5) && b[row + 1][colum - 1] == 2 && b[row - 5][colum + 5] == 2) {
                                    evaluateSquare[row - i][colum + i] = 0;
                                }
                            }
                            if (countHuman == 0) {
                                if (Player == 1) {
                                    evaluateSquare[row - i][colum + i] += defenseScore[countAI];
                                } else {
                                    evaluateSquare[row - i][colum + i] += attackScore[countAI];
                                }
                                if (CheckPoint(row + 1, colum - 1) && CheckPoint(row - 5, colum + 5) && b[row + 1][colum - 1] == 1 && b[row - 5][colum + 5] == 1) {
                                    evaluateSquare[row + i][colum + i] = 0;
                                }
                            }
                            if ((countAI == 4 || countHuman == 4) && ((CheckPoint(row - i + 1, colum + i - 1) && b[row - i + 1][colum + i - 1] == 0) || (CheckPoint(row - i - 1, colum + i + 1) && b[row - i - 1][colum + i + 1] == 0))) {
                                evaluateSquare[row - i][colum + i] *= 2;
                            } else if (countAI == 4 || countHuman == 4) {
                                evaluateSquare[row - i][colum + i] *= 2;
                            }
                        }
                    }
                }
            }
        }
    }

    public Record getMaxSquare() {
        Move move = new Move(0, 0);
        List<Record> list = new ArrayList<>();
        int t = Integer.MIN_VALUE;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (t < evaluateSquare[i][j]) {
                    t = evaluateSquare[i][j];
                    move = new Move(i, j);
                    list.clear();
                    list.add(new Record(move, t));
                } else if (t == evaluateSquare[i][j]) {
                    move = new Move(i, j);
                    list.add(new Record(move, t));
                }
            }
        }

        int x = rand.nextInt(list.size());
        evaluateSquare[list.get(x).getMove().getRowIndex()][list.get(x).getMove().getColIndex()] = 0;
        return list.get(x);
    }

    private int evaluationBoard(int[][] b) {
        String s = ";";
        //check in row and colum (|,__)
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s += b[i][j];
            }
            s += ";";
            for (int j = 0; j < n; j++) {
                s += b[j][i];
            }
            s += ";";
        }
        // check on diagonally ( \ )
        for (int i = 0; i < n - 4; i++) {
            for (int j = 0; j < n - i; j++) {
                s += b[j][i + j];
            }
            s += ";";
        }
        // check bottom diagonally ( \ )
        for (int i = n - 5; i > 0; i--) {
            for (int j = 0; j < n - i; j++) {
                s += b[i + j][j];
            }
            s += ";";
        }
        // check on diagonally ( / )
        for (int i = 4; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                s += b[i - j][j];
            }
            s += ";";
        }
        // check bottom diagonally ( / )
        for (int i = n - 5; i > 0; i--) {
            for (int j = n - 1; j >= i; j--) {
                s += b[j][i + n - j - 1];
            }
            s += ";\n";
        }
        //X = 1 human, O = 2 AI;
        String find1, find2;
        int diem = 0;
        for (int i = 0; i < caseHuman.length; i++) {
            find1 = caseAI[i];
            find2 = caseHuman[i];
            diem += point[i] * count(s, find1);
            diem -= point[i] * count(s, find2);
        }
        return diem;
    }

    public int count(String s, String find) {
        Pattern pattern = Pattern.compile(find);
        Matcher matcher = pattern.matcher(s);
        int i = 0;
        while (matcher.find()) {
            i++;
        }
        return i;
    }

    public boolean CheckPoint(int x, int y) {
        return (x >= 0 && y >= 0 && x < 20 && y < 20);
    }

    public List<Move> getMoves(int player) {
        evaluateEachSquare(chessBoard, player);

        ArrayList<Record> list = new ArrayList();
        for (int i = 0; i < maxSquare; i++) {
            list.add(getMaxSquare());
        }

        return null;
    }

    public void resetMove(Move move) {
        chessBoard[move.getRowIndex()][move.getColIndex()] = Constant.noneValue;
    }

}
