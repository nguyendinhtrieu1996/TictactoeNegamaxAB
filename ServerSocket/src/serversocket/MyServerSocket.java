/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serversocket;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Scanner;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author dinhtrieu
 */
public class MyServerSocket {

    static final int SocketServerPORT = 8081;
    static final int TimeDelay = 0;
    static final int TimeRandom = 10000;
    
    private List<PlayerClient> userList;
    private List<MatchThread> listThread;

    ServerSocket serverSocket;

    public static void main(String[] args) {
        MyServerSocket myServerSocket = new MyServerSocket();
    }

    public MyServerSocket() {
        System.out.print(ServerSocketHelper.getIpAddress());
        userList = new ArrayList<>();
        listThread = new ArrayList<>();
        ServerThread serverThread = new ServerThread();
        serverThread.start();
    }
    
    public void addListPlayer(PlayerClient player) {
        this.userList.add(player);
    }

    /**
     * This class to wait and accept connect from client
     * then will random player and create match
    */
    private class ServerThread extends Thread {
        private Timer timer;

        public ServerThread() {
            timer = new Timer();
            timer.schedule(excuteRandom, TimeDelay, TimeRandom);
        }

        @Override
        public void run() {
            Socket socket = null;
            
            try {
                serverSocket = new ServerSocket(SocketServerPORT);
                System.out.println("I'm waiting here: "
                    + serverSocket.getLocalPort());
                    System.out.println("CTRL + C to quit");

                Boolean flag = false;
                PlayerClient client;
                while (true) {
                    socket = serverSocket.accept();                                
                                      
                    for (int i = 0; i < userList.size(); ++i) {
                        if (userList.get(i).getSocket().getInetAddress().equals(socket.getInetAddress())) {
                            flag = true;
                        }
                    }
                    
                    if (!flag) {
                        System.out.println(socket.getInetAddress());
                        client = new PlayerClient(socket);
                        userList.add(client);
                    }                  
                }

            } catch (IOException e) {
                e.printStackTrace();
            } 
        }
        
        TimerTask excuteRandom = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Execute random");
                
                if (userList.size() > 1) {
                    userList = ServerSocketHelper.randomPlayer(userList);
                    
                    int numberMatches = userList.size() / 2;
                    
                    for (int i = 0; i < numberMatches; i++) {
                        PlayerClient playerA = userList.get(i);
                        playerA.setRolePlayer(RolePlayer.PLAYERA);
                        
                        PlayerClient playerB = userList.get(i + numberMatches);
                        playerB.setRolePlayer(RolePlayer.PLAYERB);
                        MatchThread matchThread = new MatchThread(playerA, playerB);
                        listThread.add(matchThread);
                        matchThread.start();
                    }
                    
                    for (int i = 0; i < numberMatches; ++i) {
                        userList.remove(i);
                    }
                    
                }
            }
        };

    }

}