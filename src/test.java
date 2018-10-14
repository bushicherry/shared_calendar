

import java.util.*;
import distSys.*;
public class test {
    public static void main(String[] args){
//
//        Vector<String> u1 = new Vector<>();
//        u1.add("user1");
//        u1.add("user2");
//        LogAndDic.eRecord er1 = new LogAndDic.eRecord(
//                new meetingInfo( "event1",
//                        new GregorianCalendar(1998,10,20),
//                        new GregorianCalendar(1998,10,20,10,30),
//                        new GregorianCalendar(1998,10,20,11,0),
//                        u1),
//                1001,
//                20
//        );
//
//        LogAndDic.eRecord er2 = new LogAndDic.eRecord(
//                new meetingInfo("event2"),
//                98,
//                3);
//
//        Vector<String> u3 = new Vector<>();
//        u3.add("user3");
//        LogAndDic.eRecord er3 = new LogAndDic.eRecord(
//                new meetingInfo("event3",
//                        new GregorianCalendar(2018,7,7),
//                        new GregorianCalendar(2018,7,7,19,0),
//                        new GregorianCalendar(2018,7,7,21,30),
//                        u3),
//                10000,
//                7
//        );
//
//        Vector<LogAndDic.eRecord> np = new Vector<>();
//        np.add(er1);
//        np.add(er2);
//        np.add(er3);
//
//        int[][] ti = new int[21][21];
//        for (int i = 0; i < 21; i++) {
//            for (int j = 0; j < 21; j++) {
//                ti[i][j] = j;
//            }
//        }
//
//        LogAndDic.sendPac pac = new LogAndDic.sendPac("I'm fine!", ti, np, 20);
//
//
//
//        int numOfPorts = 21;
//        // pac to byte[]
//        byte[] buffer = new byte[65507];
//        byte[] msg_byte = pac.msg.getBytes(); //msg size <= 64
//        int offset = 0;
//        for (byte t : msg_byte) {
//            buffer[offset] = t;
//            offset++;
//        }
//
//        buffer[64] = (byte)pac.index;
//
//        for (int m=0; m<numOfPorts; m++) {
//            for (int n=0; n<numOfPorts; n++) {
//                buffer[65+m*numOfPorts+n] = (byte)pac.Ti[m][n];
//            }
//        }
//        offset = 65+numOfPorts*numOfPorts;
//
//        for (LogAndDic.eRecord eR : pac.NP) {
//            buffer[offset] = ',';
//            buffer[offset+1] = (byte)(eR.tm/128);
//            buffer[offset+2] = (byte)(eR.tm%128);
//            buffer[offset+3] = (byte)eR.P_ind;
//
//            byte[] name_byte = eR.op.name.getBytes(); // name size <= 20
//            offset += 4;
//            int i = 0;
//            for (byte t : name_byte) {
//                buffer[offset+i] = t;
//                i++;
//            }
//            offset+= 20;
//
//            if (eR.op.day!=null) {
//                buffer[offset] = (byte)(eR.op.day.get(Calendar.MONTH));
//                buffer[offset+1] = (byte)(eR.op.day.get(Calendar.DATE));
//                buffer[offset+2] = (byte)(eR.op.day.get(Calendar.YEAR)/128);
//                buffer[offset+3] = (byte)(eR.op.day.get(Calendar.YEAR)%128);
//                buffer[offset+4] = (byte)(eR.op.start.get(Calendar.HOUR_OF_DAY));
//                buffer[offset+5] = (byte)(eR.op.start.get(Calendar.MINUTE));
//                buffer[offset+6] = (byte)(eR.op.end.get(Calendar.HOUR_OF_DAY));
//                buffer[offset+7] = (byte)(eR.op.end.get(Calendar.MINUTE));
//
//                offset += 8;
//                for (String s : eR.op.users) {
//                    buffer[offset] = ',';
//                    byte[] user_byte = s.getBytes();
//                    int j = 0;
//                    for (byte t : user_byte) {
//                        buffer[offset+j+1] = t;
//                        j++;
//                    }
//                    offset += 26;
//                }
//            }
//
//            offset++;
//
//        }
//
//
//        int numOfHosts = 21;
//        // byte to sendPac
//        boolean isCreate = false;
//        byte[] tmp = Arrays.copyOfRange(buffer,0,64);
//        String msg_recv = new String(tmp).trim();
//        int index_recv = buffer[64];
//        int[][] Ti_recv = new int[numOfHosts][numOfHosts];
//        for (int i=0; i<numOfHosts; i++) {
//            for (int j=0; j<numOfHosts; j++) {
//                Ti_recv[i][j] = buffer[65+i*numOfHosts+j];
//            }
//        }
//        Vector<LogAndDic.eRecord> NP_recv = new Vector<>();
//
//        offset = 65+numOfHosts*numOfHosts;
//        byte comma = ',';
//        while (buffer[offset]==comma) {
//            int ts_recv = buffer[offset+1];
//            ts_recv = ts_recv*128+buffer[offset+2];
//
//            int Pi_recv = buffer[offset+3];
//
//            tmp = Arrays.copyOfRange(buffer,offset+4,offset+24);
//            String name_recv = new String(tmp).trim();
//
//            meetingInfo op_recv;
//
//            if (buffer[offset+24]!=(byte)0) {
//                isCreate = true;
//                int month_recv = buffer[offset+24];
//                int day_recv = buffer[offset+25];
//                int year_recv = buffer[offset+26];
//                year_recv = year_recv*128+buffer[offset+27];
//                int shour_recv = buffer[offset+28];
//                int smin_recv = buffer[offset+29];
//                int ehour_recv = buffer[offset+30];
//                int emin_recv = buffer[offset+31];
//
//                offset = offset+32;
//                Vector<String> users_recv = new Vector<>();
//                while (buffer[offset]==comma) {
//                    tmp = Arrays.copyOfRange(buffer,offset+1,offset+26);
//                    users_recv.add(new String(tmp).trim());
//                    offset += 26;
//                }
//
//                op_recv = new meetingInfo(name_recv,
//                        new GregorianCalendar(year_recv, month_recv, day_recv),
//                        new GregorianCalendar(year_recv, month_recv, day_recv, shour_recv, smin_recv),
//                        new GregorianCalendar(year_recv, month_recv, day_recv, ehour_recv, emin_recv),
//                        users_recv);
//                offset++;
//            } else {
//                op_recv = new meetingInfo(name_recv);
//                offset += 25;
//            }
//
//            NP_recv.add(new LogAndDic.eRecord(op_recv, ts_recv, Pi_recv));
//        }
//        LogAndDic.sendPac pac2 = new LogAndDic.sendPac(msg_recv,Ti_recv,NP_recv,index_recv);
//
//        offset = offset;
    }
}