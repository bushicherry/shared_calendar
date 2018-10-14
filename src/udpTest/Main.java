package udpTest;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Main {

    public static void main(String[] args) throws SocketException {
        final DatagramSocket socket = new DatagramSocket(Integer.parseInt(args[0]));
        Runnable Listener = new Runnable() {
            @Override
            public void run() {
                System.out.println("Start listening for msgs.");
                while (true) {
                    byte[] buffer = new byte[65507];
                    DatagramPacket packet = new DatagramPacket(buffer,0,buffer.length);
                    try {
                        assert socket != null;
                        socket.receive(packet);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return;
                    }

                    Runnable recvMsg = new Runnable() {
                        @Override
                        public synchronized void run() {

                            String recv = new String(buffer).trim();
                            System.out.println("Receive: "+recv);
                        }
                    };
                    new Thread(recvMsg).start();
                }
            }
        };

        new Thread(Listener).start();

        Sender sender = new Sender(socket, Integer.parseInt(args[0]),Integer.parseInt(args[1]),args[2],"This is a test.");
        new Thread(sender).start();
        sender = new Sender(socket, Integer.parseInt(args[0]),Integer.parseInt(args[1]),args[2],"This is another test.");
        new Thread(sender).start();

    }
}
