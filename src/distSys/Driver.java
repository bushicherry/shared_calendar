package distSys;

import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        Scanner hostsInput;
        int numHosts=0;
        HashMap<String, Pair<Integer,Integer> > hostsPorts = new HashMap<>();

        // attempts to create scanner for 'knownhosts_udp.txt'
        try
        {
            hostsInput = new Scanner(new File("knownhosts_udp.txt"));
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File 'knownhosts_udp.txt' doesn't exist.");
            return;
        }

        // reads the file line by line
        while(hostsInput.hasNextLine()) {
            numHosts = numHosts+1;

            hostsPorts.put(hostsInput.next(),new Pair<Integer,Integer>(Integer.parseInt(hostsInput.next()), numHosts));
        }

        // test output the map
        /*for (HashMap.Entry<String,Integer> entry : hostsPorts.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue() + "\n");
        }*/

        int myPort = hostsPorts.get(args[0]).getKey();
        int myIndex = hostsPorts.get(args[0]).getValue();
        // set up log and dictionary
        final LogAndDic logAndDic = new LogAndDic(numHosts,myIndex);

        // set up udp socket on a new thread to listen for msgs from other sites
        Runnable udpListener = new Runnable() {

            @Override
            public void run() {
                System.out.println(args[0] + ": start listening for msgs.");
                try {
                    DatagramSocket socket = new DatagramSocket(myPort);

                    while (true) {
                        Runnable recvMsg = new Runnable() {
                            @Override
                            public synchronized void run() {
                                byte[] buffer = new byte[65507];
                                DatagramPacket packet = new DatagramPacket(buffer,0,buffer.length);
                                socket.receive(packet);
                                logAndDic.receive(buffer);
                            }
                        };

                        new Thread(recvMsg).start();
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        new Thread(udpListener).start();


        // loop to deal with commands
        while (true) {
            Scanner commandS = new Scanner(System.in);
            String command;

            command = commandS.next();
            if (command.equals("schedule")) {
                String name = commandS.next();
                Date date = new Date(commandS.next());
                LocalTime startTime = LocalTime.parse(commandS.next());
                LocalTime endTime = LocalTime.parse(commandS.next());
                // check if the times are in 30 minutes increments
                if (startTime.getMinute()%30 != 0 || endTime.getMinute()%30 != 0) {
                    System.out.println("Unable to schedule meeting " + name +".");
                    continue;
                }

                ArrayList<String> participants = new ArrayList<>();
                boolean selfIncluded = false;
                while (commandS.hasNext()) {
                    if (commandS.next().equals(args[0])) selfIncluded = true;
                    participants.add(commandS.next());
                }
                if (!selfIncluded) {
                    System.out.println("Unable to schedule meeting " + name +".");
                    continue;
                }

                boolean conflict = false;
                for (String p : participants) {
                    blurblur;
                }
                if (conflict) {
                    System.out.println("Unable to schedule meeting " + name +".");
                    continue;
                } else {
                    blurblur;
                }


            }
            else if (command.equals("cancel")) {

            }
            else if (command.equals("view")) {
                logAndDic.View_dic();
            }
            else if (command.equals("myview")) {
                logAndDic.myView(args[0]);
            }
            else if (command.equals("log")) {
                logAndDic.View_log();
            }
            else {
                System.out.println("The command is not recognizable. Please follow the following formats:\n" +
                        "schedule <name> <day> <start_time> <end_time> <participants>\n" +
                        "cancel <name>\nview\nmyview\nlog");
            }
        }
    }

}
