package distSys.algorithm.wb;

import java.util.Vector;
import java.sql.Time;

public class LogAndDic {

    // define variable
    private Log PLi;
    private Dic Vi;



    // initialization
    public LogAndDic(Integer n, Integer i){
        PLi = new Log(n,i);
        Vi = new Dic();
    }

    private static String helper1( Vector e){
        String temp = "";
        if (e.capacity() == 0)return temp;
        for(int i = 0; i < e.capacity(); i++){
            temp = temp + e.get(i) + " ";
        }
        if (temp.charAt(temp.length()-1)==' '){
            temp = temp.replace(temp.substring(temp.length()-1), "");
        }
        return temp;
    }

    // insert an event
    public void Insert( Vector e ){
       String temp = helper1(e);
       PLi.Insert_E(temp);
       Vi.Insert_Dic(e);
    }

    // Delete an event
    public void Delete( Vector e ){
        String temp = helper1(e);
        PLi.Insert_E(temp);
        Vi.Delete_Dic(e);
    }


    // view dictionary
    public void View_dic(){

    }

    // view log
    public void View_log(){


    }

    // my view: view event about me
    public void myView(String ID){

    }

    // define record
    public class eRecord {
        String op; // operation type
        String tm; // operation time
        String eID; // Event ID (user ID)

        public eRecord(String a){
            op = a;
            tm = "";
            eID = "";
        }

        public eRecord(String a, String b , String c){
            op = a;
            tm = b;
            eID = c;
        }
    }

    public class Log {
        // log information and current time stamp
        private Vector< eRecord > log_info;
        private Integer CurTmstmp;
        // Index indicates the order in all users
        private Integer Index;
        // matrix in algorithm
        Integer[][] Ti;

        // initialization n is how many users, and
        //  i is the index for itself
        private Log(Integer n, Integer i) {
            log_info = new Vector< eRecord >();
            CurTmstmp = 0;
            Index = i;
            Ti = new Integer[n][n];
        }

        // When insert event
        private void Insert_E( String e ) {
            CurTmstmp++;
            Ti[Index][Index] = CurTmstmp;
            // create event record
            log_info.add(new eRecord(e));
        }
        // send the log to all the users.
        public void Send_log(){


        }

        // receive log
        public void Rec_log(){

        }

    }

    public class Dic {
        // calender, a vector of vector
        // each vector consists of < <Event name>, <Date>, <Start>, <End>, <User1>...<User n> >
        Vector Cld;

        // insert x to dictionary

        public Dic(){
            Cld = new Vector< Vector >(0);
        }

        private void Insert_Dic(Vector e){
            Cld.add(e);
        }

        // delete x
        private void Delete_Dic(Vector e){


        }

    }

}

