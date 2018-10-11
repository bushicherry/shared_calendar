
package distSys;
import distSys.algorithm.wb.*;

import java.sql.Time;
import java.util.*;

public class test {
    public static void main(String[] args){
        LogAndDic c1 = new LogAndDic(5,1 );


        Vector<String> v = new Vector<>();
        v.add("aaaa1");
        v.add("bbbbb");
        v.add("ccccc");
        Date d = new Date();
        Time d1 = new Time(d.getTime());
        GregorianCalendar test = new GregorianCalendar(1998,11,12, 13,50);
        meetingInfo v1 = new meetingInfo("ssss", test, test, test, v, 1);


        c1.Insert(v1);
        c1.Insert(v1);
        c1.Insert(v1);
        c1.Insert(v1);
        System.out.println("check1");
        c1.View_dic();
        c1.View_log();

        c1.Delete(v1);

        System.out.println("check2");
        c1.View_dic();
        c1.View_log();


        System.out.println(test.get(Calendar.MONTH));


    }
}