package distSys.algorithm.wb;

import java.util.Vector;

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
        Integer tm; // operation time
        Integer P_ind; // Process index, Pi, that i

        private eRecord(String a, Integer b , Integer c){
            op = a;
            tm = b;
            P_ind = c;
        }
    }

    public class Log {
        // log information and current time stamp
        private Vector< eRecord > log_info;
        private Integer CurTmstmp;
        // Index indicates the index in all users
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
            log_info.add(new eRecord(e, CurTmstmp, Index));
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
        private Vector< Vector  > Cld;

        // insert x to dictionary

        private Dic(){
            Cld = new Vector<>(0);
        }

        private void Insert_Dic(Vector e){
            Cld.add(e);
        }

        // delete x
        private void Delete_Dic(Vector e){


        }

    }

}

