/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serversocket;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;

/**
 *
 * @author dinhtrieu
 */
public class ServerSocketHelper {
    
    public static String getIpAddress() {
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
    
    public static List<PlayerClient> randomPlayer(List<PlayerClient> listPlayer) {
        int size = listPlayer.size();
        int numberOfMatch = listPlayer.size() / 2;
        Random random = new Random();
        
        for (int i = 0; i < size; ++i) {
            int randValue = random.nextInt(size); 
            
            PlayerClient temp = listPlayer.get(i);
            listPlayer.set(i, listPlayer.get(randValue));
            listPlayer.set(randValue, temp);
        }
        
        return listPlayer;
    }
    
}
