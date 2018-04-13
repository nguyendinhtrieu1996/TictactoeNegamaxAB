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
    static final int TimeRandom = 30;

    String msgLog = "";

    private List<PlayerClient> userList;

    ServerSocket serverSocket;

    public static void main(String[] args) {
        MyServerSocket myServerSocket = new MyServerSocket();
    }

    public MyServerSocket() {
        System.out.print(ServerSocketHelper.getIpAddress());
        userList = new ArrayList<>();
        ServerThread chatServerThread = new ServerThread();
        chatServerThread.start();
    }
    
    public void addListPlayer(PlayerClient player) {
        this.userList.add(player);
    }

    private class ServerThread extends Thread {
        
        private ScheduledExecutorService excutor = Executors.newSingleThreadScheduledExecutor();
        private Runnable task;

        public ServerThread() {
            task = excuteRnadom;
        }

        @Override
        public void run() {
            Socket socket = null;

            try {
                serverSocket = new ServerSocket(SocketServerPORT);
                System.out.println("I'm waiting here: "
                    + serverSocket.getLocalPort());
                    System.out.println("CTRL + C to quit");

                while (true) {
                    socket = serverSocket.accept();
                    System.out.println(socket.getInetAddress());
                    PlayerClient client = new PlayerClient(socket);
                    userList.add(client);
                    excutor.schedule(task, TimeRandom, TimeUnit.SECONDS);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } 
        }
        
        Runnable excuteRnadom = new Runnable() {
            @Override
            public void run() {
                System.out.println("Excute random");
            }
        };

    }

}