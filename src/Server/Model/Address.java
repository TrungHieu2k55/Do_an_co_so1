package Server.Model;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Address {
    public static String ipv4Address ;
    public static int PORT = 3029;


    public Address() {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            ipv4Address = localHost.getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
    public String getIpv4Address() {
        return ipv4Address;
    }


    public  int getPORT() {
        return PORT;
    }
}
