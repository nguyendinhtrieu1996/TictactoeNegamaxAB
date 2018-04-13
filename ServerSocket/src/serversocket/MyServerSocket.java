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
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 *
 * @author dinhtrieu
 */
public class MyServerSocket {

    static final int SocketServerPORT = 8081;

    String msgLog = "";

    List<PlayerClient> userList;

    ServerSocket serverSocket;

    public static void main(String[] args) {
        MyServerSocket ChatServer = new MyServerSocket();
    }

    MyServerSocket() {
        System.out.print(getIpAddress());
        userList = new ArrayList<>();
        ChatServerThread chatServerThread = new ChatServerThread();
        chatServerThread.start();

    }

    private class ChatServerThread extends Thread {

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
                    System.out.println("Connect");
                    PlayerClient client = new PlayerClient();
                    userList.add(client);
                    ConnectThread connectThread = new ConnectThread(client, socket);
                    connectThread.start();
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }

        }

    }

    private class ConnectThread extends Thread {

        Socket socket;
        PlayerClient connectClient;
        String msgToSend = "";

        ConnectThread(PlayerClient client, Socket socket) {
            connectClient = client;
            this.socket = socket;
            client.socket = socket;
            client.chatThread = this;
        }

        @Override
        public void run() {
            DataInputStream dataInputStream = null;
            DataOutputStream dataOutputStream = null;

            try {
                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());

                String n = dataInputStream.readUTF();

                connectClient.name = n;

                msgLog = connectClient.name + " connected@"
                    + connectClient.socket.getInetAddress()
                    + ":" + connectClient.socket.getPort() + "\n";

                System.out.println(msgLog);

                dataOutputStream.writeUTF("Welcome " + n + "\n");
                dataOutputStream.flush();

                broadcastMsg(n + " join our chat.\n");

                while (true) {
                    if (dataInputStream.available() > 0) {
                        String newMsg = dataInputStream.readUTF();

                        msgLog = n + ": " + newMsg;
                        System.out.print(msgLog);
                        broadcastMsg(n + ": " + newMsg);
                    }

                    if (!msgToSend.equals("")) {
                        dataOutputStream.writeUTF(msgToSend);
                        dataOutputStream.flush();
                        msgToSend = "";
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (dataInputStream != null) {
                    try {
                        dataInputStream.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                if (dataOutputStream != null) {
                    try {
                        dataOutputStream.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                userList.remove(connectClient);

                System.out.println(connectClient.name + " removed.");

                msgLog = "-- " + connectClient.name + " leaved\n";
                System.out.println(msgLog);

                broadcastMsg("-- " + connectClient.name + " leaved\n");

            }

        }

        private void sendMsg(String msg) {
            msgToSend = msg;
        }

    }

    private void broadcastMsg(String msg) {
        for (int i = 0; i < userList.size(); i++) {
            userList.get(i).chatThread.sendMsg(msg);
            msgLog = "- send to " + userList.get(i).name + "\n";
            System.out.print(msgLog);
        }
        System.out.println();
        
    }

    private String getIpAddress() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                .getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces
                    .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                    .getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress.nextElement();

                    if (inetAddress.isSiteLocalAddress()) {
                        ip += "SiteLocalAddress: "
                            + inetAddress.getHostAddress() + "\n";
                    }

                }

            }

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ip += "Something Wrong! " + e.toString() + "\n";
        }

        return ip;
    }

    class PlayerClient {

        String name;
        Socket socket;
        ConnectThread chatThread;

    }

}