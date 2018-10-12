
package distSys;

import java.sql.Time;
import java.util.*;

public class test {
    public static void main(String[] args){
        LogAndDic c1 = new LogAndDic(5,1 );


        Vector<String> v = new Vector<>();
        v.add("George");
        v.add("Fisher");
        v.add("Maria");

        GregorianCalendar t1 = new GregorianCalendar(1998,11,12, 15,50);
        GregorianCalendar t2 = new GregorianCalendar(1998,11,12, 15,20);

        GregorianCalendar t3 = new GregorianCalendar(1998,11,12, 15,20);
        GregorianCalendar t4 = new GregorianCalendar(1998,11,12, 15,50);

        System.out.println(t1);

        meetingInfo v1 = new meetingInfo("meeting1", t1, t1, t2, v, 1);
        meetingInfo v2 = new meetingInfo("meeting2", t3, t3, t4, v, 1);

        // check if ok
        //System.out.println("collison should be true: " + Algorithm.ifFine(v1,v2));

        //check view
        c1.Insert(v1);
        c1.Insert(v2);

        c1.View_log();
        c1.View_dic();



        // check check collison


    }
}