package distSys;

import com.sun.corba.se.impl.protocol.POALocalCRDImpl;

import javax.swing.text.PlainDocument;
import java.util.*;

public class LogAndDic {

    // define variable
    private Log PLi;
    private Dic Vi;



    // initialization
    public LogAndDic(int n, int i){
        PLi = new Log(n,i);
        Vi = new Dic();
    }

    // insert an event
    public void Insert( meetingInfo m ){
        PLi.Insert_E(m);
        Vi.Insert_Dic(m);
    }

    public boolean check_user(String mtname, String username){
        // true means user in the meeting, no means not in the meeting
        for(meetingInfo m: Vi.Cld){
            if(m.name.equals(mtname)){
                if(m.users.contains(username)){
                    return true;
                }
            }
        }
        return false;
    }

    // Delete an event
    public void Delete( String s ){
        meetingInfo m = new meetingInfo(s);
        Vi.Delete_Dic(m);
        PLi.Insert_E(m);
    }

    // check collision. True means collision happens
    public boolean check_collision( meetingInfo m){
        //loop users in m
        for(String user: m.users){
            for(meetingInfo meeting: this.Vi.Cld){
                if(meeting.users.contains(user)){
                    if(!Algorithm.ifFine(m, meeting)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // for send and receive
    public boolean has_rec(int[][] T, eRecord eR, int k){
        return T[k][eR.P_ind] >= eR.tm;
    }

    public sendPac PacReady(int k){
        // k is the index of which Pi
        Vector<eRecord> my_NP = new Vector<>();
        for(eRecord m: PLi.log_info){
            if(!has_rec(PLi.Ti, m, k)){
                my_NP.add(m);
            }
        }
        return new sendPac("", PLi.Ti, my_NP, PLi.Index);
    }

    // deal with rec
    private boolean helper2(eRecord eR){
        for(int s = 1; s < PLi.Ti.length; s++){
            if (!has_rec(PLi.Ti, eR, s)){
                return true;
            }
        }
        return false;
    }


    public void dealWithReceive(sendPac pac){
        // prepare NE
        Vector<eRecord> NE = new Vector<>();
        for(eRecord fR: pac.NP){
            if(!has_rec(PLi.Ti, fR, PLi.Index)){
                // which means It's in NE
                NE.add(fR);
            }
        }
        // update Vi
        for(eRecord dR: NE){
            if(!Vi.Cld.contains(dR.op)){
                if(dR.op.users == null){
                    System.out.println("Error: can insert because it exists");
                }
                else {
                    Vi.Insert_Dic((dR.op));
                }
            }
        }
        // update Ti
        for (int r = 0; r < PLi.Ti.length; r++){
            PLi.Ti[PLi.Index][r] = Math.max(PLi.Ti[PLi.Index][r], pac.Ti[pac.index][r]);
            for(int s = 0; s < PLi.Ti.length; s++){
                PLi.Ti[r][s] = Math.max(PLi.Ti[r][s], pac.Ti[r][s]);
            }
        }
        //update Log
        for(eRecord eR: NE){
            if(!PLi.log_info.contains(eR) && helper2(eR)){
                PLi.log_info.add(eR);
            }
        }

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
    private boolean helper1(meetingInfo m, String name){
        if(m.users.contains(name))return true;
        return false;
    }

    public void myView(String name){
        int ind = 0;
        for(meetingInfo m: Vi.Cld){
            if(m.users.contains(name)){
                print_all(m);
                ind = 1;
            }
        }
        if ( ind == 0 ){
            System.out.println("You have no schedule");
        }
    }

    // define record
    public static class eRecord {
        meetingInfo op; // operation type
        int tm; // time stamp
        int P_ind; // Process index, Pi, that i

        eRecord(meetingInfo a, int b, int c){
            op = new meetingInfo(a);
            tm = b;
            P_ind = c;
        }
        @Override
        public boolean equals(Object o) {
            if (o instanceof eRecord) {
                int a = ((eRecord) o).tm;
                int b = ((eRecord) o).P_ind;
                return  a == tm && b == P_ind;
            }
            return false;
        }
    }

    public static class sendPac {
        String msg;
        int[][] Ti;
        Vector<eRecord> NP;
        int index;
        public sendPac(String m, int[][] t, Vector<LogAndDic.eRecord> N, int i){
            msg = m;
            Ti = t;
            NP = N;
            index = i;
        }
    }

    // print out stuff
    private String formatTransfer(int k){
        String s;
        if(k<10){
            s = "0" + String.valueOf(k);
        } else {
            s = String.valueOf(k);
        }
        return s;
    }

    private void print_date(GregorianCalendar g){
        System.out.print(formatTransfer(g.get(Calendar.MONTH)+1));
        System.out.print("/" + formatTransfer(g.get(Calendar.DATE)) + "/");
        System.out.print(g.get(Calendar.YEAR) + " ");
    }

    private void print_time(GregorianCalendar g){
        System.out.print(formatTransfer(g.get(Calendar.HOUR_OF_DAY)) + ":");
        System.out.print(formatTransfer(g.get(Calendar.MINUTE)) + " ") ;
    }

    private void print_all(meetingInfo m){
        if(m.users != null) {
            System.out.print(m.name + " ");
            print_date(m.day);
            print_time(m.start);
            print_time(m.end);
            for (int i = 0; i < m.users.size(); i++) {
                if (i < m.users.size() - 1) {
                    System.out.print(m.users.get(i) + ",");
                } else {
                    System.out.print(m.users.get(i));
                }

            }
            System.out.println();
        } else {
            System.out.println(m.name);
        }
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
            if(log_info.size()==0){
                System.out.println("No Log now");
                return;
            }
            for(eRecord item: log_info){
                if(item.op.users == null){
                    System.out.print("delete ");
                } else {
                    System.out.print("Create ");
                }
                print_all(item.op);
            }

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
            if(Cld.size()==0){
                System.out.println("No Dic now");
                return;
            }
            Cld.forEach(item-> print_all(item));
        }

    }
}

