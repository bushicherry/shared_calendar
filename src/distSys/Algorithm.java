package distSys;

import javafx.util.Pair;

import javax.xml.crypto.Data;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Vector;

public class Algorithm {

    // True means it's fine to make the schedule
    public static boolean ifFine(meetingInfo a, meetingInfo b){
        return ( !(a.end.after(b.start)) ) || ( !(b.end.after(a.start)) );
    }

    public static boolean Insert(LogAndDic m, meetingInfo e, HashMap<String, Pair<Integer,Integer> > myhash, String myname, DatagramSocket socket){
        // First check the collision
        if(m.check_collision(e) != null){
            System.out.println("Unable to schedule meeting " + e.name);
            return false;
        } else {
            m.Insert(e);
            System.out.println("Meeting " + e.name + " scheduled");
            System.out.println(e.users);
            System.out.println(myhash);
            //send
            udpsend(e.users,myhash,m,myname, socket);
            return true;
        }
    }
    public static boolean Cancel(LogAndDic m, String mt, String s, HashMap<String, Pair<Integer,Integer> > myhash, DatagramSocket socket){
        // check if self in the meeting
        if(m.check_user(mt,s)){
            // get users
            Vector<String> meetingusers = m.get_user(mt);
            m.Delete(mt);
            System.out.println("Meeting " + mt + " cancelled");

            //send
            udpsend(meetingusers, myhash, m, s, socket);
            return true;
        } else {
            System.out.println("User is either not in the meeting or the meeting doesn't exist");
            return false;
        }
    }

    public static void Onrec(LogAndDic m, LogAndDic.sendPac pac, String myname, HashMap<String, Pair<Integer,Integer> > myhash, DatagramSocket socket){
        //check pac's avalable:
        m.dealWithReceive(pac, myname, myhash,socket);
    }

    private static void udpsend(Vector<String> meetingusers, HashMap<String, Pair<Integer,Integer> > myhash, LogAndDic m, String s, DatagramSocket socket){
        if(meetingusers.size() > 0) {
            for (String uname : meetingusers) {
                int proIndex = myhash.get(uname).getValue();
                if (proIndex != m.get_process()) {
                    LogAndDic.sendPac my_pac = m.PacReady(proIndex);
                    //send
                    int client_port = myhash.get(uname).getKey();
                    UdpSender udpSender = new UdpSender(socket, client_port, uname, myhash.size(), my_pac);
                    new Thread(udpSender).start();
                }
            }
        }
    }




}

