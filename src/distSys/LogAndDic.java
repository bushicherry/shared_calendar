package distSys;

import java.util.GregorianCalendar;
import java.util.Vector;
import java.util.Calendar;

public class LogAndDic {

    /////////////////////////////////////////////////////////
    // define variable
    private Log PLi;
    private Dic Vi;



    // initialization
    public LogAndDic(int n, int i){
        PLi = new Log(n,i);
        Vi = new Dic();
    }

    private static String helper1( Vector e){
        String temp = "";
        if (e.size() == 0)return temp;
        for(int i = 0; i < e.size(); i++){
            temp = temp + e.get(i) + " ";
        }
        return temp;
    }

    // insert an event
    public void Insert( meetingInfo m ){
       PLi.Insert_E(m);
       Vi.Insert_Dic(m);
    }

    // Delete an event
    public void Delete( meetingInfo e ){
        PLi.Insert_E(e);
        Vi.Delete_Dic(e);
    }


    // view dictionary
    public void View_dic(){
        Vi.printDic();
    }

    // view log
    public void View_log(){
        PLi.printLog();

    }

    // my view: view event about me
    public void myView(String ID){

    }

    // define record
    public class eRecord {
        meetingInfo op; // operation type
        int tm; // time stamp
        int P_ind; // Process index, Pi, that i

        private eRecord(meetingInfo a, int b , int c){
            op = new meetingInfo(a);
            tm = b;
            P_ind = c;
        }
    }

    // print out stuff
    private void print_date(GregorianCalendar g){
        System.out.print(g.get(Calendar.MONTH));
        System.out.print("/" + g.get(Calendar.DATE) + "/");
        System.out.print(g.get(Calendar.YEAR) + " ");
    }

    private void print_time(GregorianCalendar g){
        System.out.print(g.get(Calendar.HOUR) + ":");
        System.out.print(g.get(Calendar.MINUTE) + " ");
    }

    private void print_all(meetingInfo m){
        System.out.print(m.name + " ");
        print_date(m.day);
        print_time(m.start);
        print_time(m.end);
        m.users.forEach(item-> System.out.print(item + " "));
    }


    public class Log {
        // log information and current time stamp
        private Vector< eRecord > log_info;
        private int CurTmstmp;
        // Index indicates the index in all users
        private int Index;
        // matrix in algorithm
        int[][] Ti;

        // initialization n is how many users, and
        //  i is the index for itself
        private Log(int n, int i) {
            log_info = new Vector<>();
            CurTmstmp = 0;
            Index = i;
            Ti = new int[n][n];
        }

        // When insert event
        private void Insert_E( meetingInfo e ) {
            CurTmstmp++;
            Ti[Index][Index] = CurTmstmp;
            // create event record
            log_info.add(new eRecord(e, CurTmstmp, Index));
        }


        private void printLog(){
            System.out.println("View Log:");
            log_info.forEach(item-> print_all(item.op));
        }
        // send the log to all the users.
        public void Send_log(){


        }

        // receive log
        public void Rec_log(){

        }

    }

    private class Dic {
        // calender, a vector of vector
        // each vector consists of < <Event name>, <Date>, <Start>, <End>, <User1>...<User n> >
        private Vector< meetingInfo > Cld;

        // insert x to dictionary

        private Dic(){
            Cld = new Vector<>();
        }

        private void Insert_Dic(meetingInfo e){
            Cld.add(e);
        }

        // delete x
        private void Delete_Dic(meetingInfo e){
            Cld.remove(e);
        }

        private void printDic(){
            System.out.println("View Dic:");
            Cld.forEach(item-> print_all(item));
        }

    }
}

