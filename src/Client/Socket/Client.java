package Client.Socket;

import Client.Model.Address;

import java.io.IOException;
import java.net.Socket;

public class Client {
    public Socket socket;


    public Client() {
        try {
            socket = new Socket(Address.ipv4Address,Address.PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public Client setSocket(Socket socket) {
        this.socket = socket;
        return this;
    }
}
