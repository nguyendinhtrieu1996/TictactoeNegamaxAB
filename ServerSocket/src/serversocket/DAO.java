/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serversocket;

import java.net.Socket;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dinhtrieu
 */
public class DAO {
    private Connection connection;
    private Statement statement;
    
    private static DAO instanceDAO = null;
    private final String url = "jdbc:mysql://127.0.0.1:3306/Tictactoe?autoReconnect=true&useSSL=false";
    private final String user = "root";
    private final String password = "dinhtrieu1251996";
    
    private DAO() {
        createConnection();
    }
    
    
    private void createConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection =  DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
        } catch (ClassNotFoundException ex) {
            
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static DAO getInstance() {
        if (instanceDAO != null) {
            return instanceDAO;
        } else {
            instanceDAO = new DAO();
            return instanceDAO;
        }
    }
    
    public void saveBoard(Socket playerA, Socket PlayerB, Chessboard chessboard, RolePlayer rolePlayer) {
        String playerA_IP = playerA.getInetAddress().toString();
        String playerB_IP = PlayerB.getInetAddress().toString();
        String boardString = chessboard.getBoardString();
        int winner;
        
        if (rolePlayer == RolePlayer.PLAYERA) {
            winner = 1;
        } else if (rolePlayer == RolePlayer.PLAYERB) {
            winner = 2;
        } else {
            winner = 0;
        }
        
        String query = "INSERT INTO TictactoeDB (Player_A_IP, Player_B_IP, winner, board) VALUES ('" + playerA_IP + "', '" + playerB_IP + "', " + winner + ", '" + boardString + "')";
        try {
            statement.executeUpdate(query);
        } catch(SQLException ex) {
            System.out.println("Exeption" + ex.toString());
        }
        
    }
    
}
