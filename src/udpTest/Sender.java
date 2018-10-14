package udpTest;

import java.io.IOException;
import java.net.*;

public class Sender implements Runnable{

    private DatagramSocket socket;
    private DatagramPacket packet;
    private Object lock = new Object();

    public Sender(DatagramSocket socket, int myPort, int clientPort, String clientName, String msg) {
        try {
            byte[] buffer = msg.getBytes();
            this.socket = socket;
            packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(clientName), clientPort);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            synchronized (lock) {
                socket.send(packet);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
