package distSys;

import java.util.Vector;

public class Algorithm {

    // True means it's fine to make the schedule
    public static boolean ifFine(meetingInfo a, meetingInfo b){
        return ( !(a.end.after(b.start)) ) || ( !(b.end.after(a.start)) );
    }
/*
    public static boolean Insert(meetingInfo e){

    }
    public static boolean delete(meetingInfo e){

    }
*/

}

