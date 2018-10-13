package distSys;

import java.util.Vector;

public class Algorithm {

    // True means it's fine to make the schedule
    public static boolean ifFine(meetingInfo a, meetingInfo b){
        return ( !(a.end.after(b.start)) ) || ( !(b.end.after(a.start)) );
    }

    public class sendPac {
        String msg;
        int[][] Ti;
        Vector<LogAndDic.eRecord> NP;
        public sendPac(String m, int[][] t, Vector<LogAndDic.eRecord> N){
            msg = m;
            Ti = t;
            NP = N;
        }
    }
}

