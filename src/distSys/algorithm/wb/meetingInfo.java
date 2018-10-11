package distSys.algorithm.wb;


import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

public class meetingInfo {
    // name of the meeting
    public String name;
    // day and time
    public GregorianCalendar day;
    public GregorianCalendar start;
    public GregorianCalendar end;
    // users
    public Vector<String> users;
    //indicator 1 if insert, 0 if delete
    public int ind;

    // constructor
    public meetingInfo(String n, GregorianCalendar d, GregorianCalendar s, GregorianCalendar e, Vector<String> u, int i){
        name = n;
        day = new GregorianCalendar(d.get(Calendar.YEAR), d.get(Calendar.MONTH), d.get(Calendar.DATE));
        start = new GregorianCalendar(d.get(Calendar.YEAR), d.get(Calendar.MONTH), d.get(Calendar.DATE),s.get(Calendar.HOUR), s.get(Calendar.MINUTE));
        end = new GregorianCalendar(d.get(Calendar.YEAR), d.get(Calendar.MONTH), d.get(Calendar.DATE),e.get(Calendar.HOUR), e.get(Calendar.MINUTE));
        users = new Vector<>(u);
        int ind = i;
    }

    public meetingInfo(meetingInfo MI){
        name = MI.name;
        day = new GregorianCalendar(MI.day.get(Calendar.YEAR), MI.day.get(Calendar.MONTH), MI.day.get(Calendar.DATE));
        start = new GregorianCalendar(MI.day.get(Calendar.YEAR), MI.day.get(Calendar.MONTH), MI.day.get(Calendar.DATE),MI.start.get(Calendar.HOUR), MI.start.get(Calendar.MINUTE));
        end = new GregorianCalendar(MI.day.get(Calendar.YEAR), MI.day.get(Calendar.MONTH), MI.day.get(Calendar.DATE), MI.end.get(Calendar.HOUR), MI.end.get(Calendar.MINUTE));
        users = new Vector<>(MI.users);
        int ind = MI.ind;
    }
}
