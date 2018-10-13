package distSys;

import java.io.IOException;
import java.net.*;

public class udpSender implements Runnable{

    private DatagramSocket socket;
    private DatagramPacket packet;

    public udpSender(int myPort, int clientPort, String clientName, byte[] msg, int length) {
        try {
            socket = new DatagramSocket(myPort);
            packet = new DatagramPacket(
                    msg,
                    length,
                    InetAddress.getByName(clientName),
                    clientPort
            );
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        socket.close();
    }

}
