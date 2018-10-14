package distSys;

import com.sun.tools.corba.se.idl.StringGen;
import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;

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

        final String myName = args[0];
        final int myPort = hostsPorts.get(myName).getKey();
        final int myIndex = hostsPorts.get(myName).getValue();
        final int numOfHosts = numHosts;
        // set up log and dictionary
        final LogAndDic logAndDic = new LogAndDic(numHosts,myIndex);


        // set up udp socket on a new thread to listen for msgs from other sites
        Runnable udpListener = new Runnable() {

            @Override
            public void run() {
                System.out.println(myName + ": start listening for msgs.");
                try {
                    DatagramSocket socket = new DatagramSocket(myPort);

                    while (true) {
                        byte[] buffer = new byte[65507];
                        DatagramPacket packet = new DatagramPacket(buffer,0,buffer.length);
                        try {
                            socket.receive(packet);
                        } catch (IOException e) {
                            e.printStackTrace();
                            return;
                        }

                        Runnable recvMsg = new Runnable() {
                            @Override
                            public synchronized void run() {

                                // byte to sendPac
                                boolean isCreate = false;
                                byte[] tmp = Arrays.copyOfRange(buffer,0,64);
                                String msg_recv = new String(tmp).trim();
                                int index_recv = buffer[64];
                                int[][] Ti_recv = new int[numOfHosts][numOfHosts];
                                for (int i=0; i<numOfHosts; i++) {
                                    for (int j=0; j<numOfHosts; j++) {
                                        Ti_recv[i][j] = buffer[65+i*numOfHosts+j];
                                    }
                                }
                                Vector<LogAndDic.eRecord> NP_recv = new Vector<>();

                                int offset = 65+numOfHosts*numOfHosts;
                                byte comma = ',';
                                while (buffer[offset]==comma) {
                                    int ts_recv = buffer[offset+1];
                                    ts_recv = ts_recv*128+buffer[offset+2];

                                    int Pi_recv = buffer[offset+3];

                                    tmp = Arrays.copyOfRange(buffer,offset+4,offset+24);
                                    String name_recv = new String(tmp).trim();

                                    meetingInfo op_recv;

                                    if (buffer[offset+24]!=(byte)0) {
                                        isCreate = true;
                                        int month_recv = buffer[offset+24];
                                        int day_recv = buffer[offset+25];
                                        int year_recv = buffer[offset+26];
                                        year_recv = year_recv*128+buffer[offset+27];
                                        int shour_recv = buffer[offset+28];
                                        int smin_recv = buffer[offset+29];
                                        int ehour_recv = buffer[offset+30];
                                        int emin_recv = buffer[offset+31];

                                        offset = offset+32;
                                        Vector<String> users_recv = new Vector<>();
                                        while (buffer[offset]==comma) {
                                            tmp = Arrays.copyOfRange(buffer,offset+1,offset+26);
                                            users_recv.add(new String(tmp).trim());
                                            offset += 26;
                                        }

                                        op_recv = new meetingInfo(name_recv,
                                                new GregorianCalendar(year_recv, month_recv, day_recv),
                                                new GregorianCalendar(year_recv, month_recv, day_recv, shour_recv, smin_recv),
                                                new GregorianCalendar(year_recv, month_recv, day_recv, ehour_recv, emin_recv),
                                                users_recv);
                                        offset++;
                                    } else {
                                        op_recv = new meetingInfo(name_recv);
                                        offset += 25;
                                    }

                                    NP_recv.add(new LogAndDic.eRecord(op_recv, ts_recv, Pi_recv));
                                }
                                LogAndDic.sendPac pac = new LogAndDic.sendPac(msg_recv,Ti_recv,NP_recv,index_recv);

                                Algorithm.Onrec(logAndDic, pac, myName, hostsPorts);
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

                String dateStr = commandS.next();
                String pattern = "MM/dd/yyyy";
                Calendar date = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
                try {
                    date.setTime(sdf.parse(dateStr));// all done
                } catch (ParseException e) {
                    e.printStackTrace();
                    continue;
                }

                String startStr =  commandS.next();
                String endStr = commandS.next();
                LocalTime startTime = LocalTime.parse(startStr);
                LocalTime endTime = LocalTime.parse(endStr);
                // check if the times are in 30 minutes increments
                if (startTime.getMinute()%30 != 0 || endTime.getMinute()%30 != 0) {
                    System.out.println("Unable to schedule meeting " + name +".");
                    continue;
                } else if( startTime.isAfter(endTime) ){
                    System.out.println("Unable to schedule meeting <Start time after end time>" + name +".");
                    continue;
                }

                Vector<String> participants = new Vector<>();
                boolean selfIncluded = false;
                String users = commandS.next();
                String[] userArray = users.split(",");
                for (String u : userArray) {
                    if (u.equals(myName)) selfIncluded = true;
                    participants.add(u);
                }
                if (!selfIncluded) {
                    System.out.println("Unable to schedule meeting " + name +".");
                    continue;
                }

                GregorianCalendar gDate = new GregorianCalendar(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DATE));
                GregorianCalendar gStartTime = new GregorianCalendar(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DATE), startTime.getHour(), startTime.getMinute());
                GregorianCalendar gEndTime = new GregorianCalendar(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DATE), endTime.getHour(), endTime.getMinute());
                meetingInfo m = new meetingInfo(name, gDate, gStartTime, gEndTime, participants);

                Algorithm.Insert(logAndDic, m, hostsPorts, myName);



            }
            else if (command.equals("cancel")) {
                String name = commandS.next();
                Algorithm.Cancel(logAndDic,name,myName,hostsPorts);
            }
            else if (command.equals("view")) {
                logAndDic.View_dic();
            }
            else if (command.equals("myview")) {
                logAndDic.myView(myName);
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
