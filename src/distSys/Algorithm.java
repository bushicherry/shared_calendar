package distSys;

public class Algorithm {

    // True means it's fine to make the schedule
    public static boolean ifFine(meetingInfo a, meetingInfo b){
        return ( !(a.end.after(b.start)) ) || ( !(b.end.after(a.start)) );
    }

    public static boolean Insert(LogAndDic m, meetingInfo e){
        // First check the collision
        if(m.check_collision(e)){
            System.out.println("Unable to schedule meeting " + e.name);
            return false;
        } else {
            m.Insert(e);
            System.out.println("Meeting " + e.name + " scheduled");
            return true;
        }
    }
    public static boolean Cancel(LogAndDic m, String mt, String s){
        // check if self in the meeting
        if(m.check_user(mt,s)){
            m.Delete(mt);
            System.out.println("Meeting " + mt + " cancelled");
            return true;
        } else {
            System.out.println("User is either not in the meeting or the meeting doesn't exist");
            return false;
        }
    }

    public static boolean Onrec(LogAndDic m, LogAndDic.sendPac pac){
        //check pac's avalable:
        m.dealWithReceive(pac);
        return true;
    }



}

