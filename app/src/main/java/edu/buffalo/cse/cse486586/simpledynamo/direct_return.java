package edu.buffalo.cse.cse486586.simpledynamo;

import android.content.ContentValues;
import android.content.SharedPreferences;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by user on 4/20/16.
 */
public class direct_return {
    public static final String PREFS_NAME = "PA4";
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    public static cache cachemap=new cache();


    public synchronized void find(String selection) {
        String[] key=selection.split("--");
        String real_key=key[0];
        String return_mess_ind=key[2];
        Integer return_address=Integer.valueOf(key[1]);
        Ring rs=new Ring();
        PortNum PN=new PortNum();
        int myself=PN.get_port_num();

        //sp=new Get_context().get_context().getSharedPreferences(PREFS_NAME, 0);
        //String out_avd=sp.getString(real_key, null);
        String output_key=real_key;
        int n=0;
        String value=null;


            value = cachemap.key_value.get(real_key);


            if (value == null) {
                value = cachemap.back_up1.get(output_key);
            }



        Integer key_pos=rs.return_part_ind(output_key);
        int[] pos_list={0,0,0};
        int[] port_num={0,0,0};

        pos_list[0]=key_pos;
        port_num[0]=rs.port_list[pos_list[0]];

        if (pos_list[0]+1>4){
            pos_list[1]=0;

        }
        else{
            pos_list[1]=pos_list[0]+1;
        }

        port_num[1]=rs.port_list[pos_list[1]];

        if (pos_list[1]+1>4){
            pos_list[2]=0;
        }
        else{
            pos_list[2]=pos_list[1]+1;
        }

        port_num[2]=rs.port_list[pos_list[2]];


             if (value!=null) {
                 String exc = "return_query--" + output_key + "--" + value + "--" + return_mess_ind;
                 new Send_message(exc,return_address).exc();

             }

    }

    public void query_all(String selection)  {

        String[] temp=selection.split("--");
        Integer t_portnum=Integer.valueOf(temp[1]);
        Integer t_mesnum=Integer.valueOf((temp[2]));

        cachemap.unit();
        Iterator iter = cachemap.key_value.entrySet().iterator();

        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object value = entry.getValue();

            String exc_1="return*--"+key.toString()+"--"+value.toString()+"--"+t_mesnum.toString();
            new Send_message(exc_1,t_portnum).exc();

//            Thread t = new Thread(new Client_Thread(exc_1, t_portnum));
//            t.start();
            System.out.println(exc_1);
        }

//        sp=new Get_context().get_context().getSharedPreferences(PREFS_NAME, 0);
//        Map<String,?> all_out = sp.getAll();
//
//        for(Map.Entry<String,?> entry : all_out.entrySet()) {
//            String key=entry.getKey().toString();
//            String value=entry.getValue().toString();
//            String exc_1="return*--"+key+"--"+value+"--"+t_mesnum.toString();
//            Thread t = new Thread(new Client_Thread(exc_1, t_portnum));
//            t.start();
//            System.out.println(exc_1);
//            try {
//                t.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        //}

        String fin_mes="done*--"+t_mesnum.toString();
        new Send_message(fin_mes,t_portnum).exc();
//        t_portnum
//        Thread t = new Thread(new Client_Thread(fin_mes, t_portnum));
//        t.start();
        System.out.println(fin_mes);

    }

    public void insert(ContentValues values){

        //sp=new Get_context().get_context().getSharedPreferences(PREFS_NAME, 0);
        //edit=sp.edit();
        String key=(String)values.get("key");
        String value=(String)values.get("value");

        System.out.println(key);
        System.out.println(value);


            Ring rs = new Ring();

            System.out.println(value);
            String[] realvalue=value.split("--");
            value=realvalue[0];

            String exc = "insert--" + key + "--" + value + "--rep"+"--"+realvalue[2]+"--"+realvalue[3];

            //edit.putString(key, value);
            //edit.commit();

        if (cachemap.unit_lock.tryLock()){
            cachemap.key_value.put(key, value);
            cachemap.unit_lock.unlock();}

        else{
            cachemap.unit_backlock.tryLock();
            cachemap.get_back_up().put(key, value);
            cachemap.unit_backlock.unlock();
        }

            int port_tail=rs.find_tail();
            int myself=new PortNum().get_port_num();

            //System.out.println("myself is "+myself);
            //System.out.println("tail is "+ port_tail);
            //System.out.println(myself==port_tail);
            int ini_port=Integer.valueOf(realvalue[2]);



            Integer key_pos=rs.return_part_ind(key);
            int[] pos_list={0,0,0};
            int[] port_num={0,0,0};

            pos_list[0]=key_pos;
            port_num[0]=rs.port_list[pos_list[0]];

            if (pos_list[0]+1>4){
                pos_list[1]=0;

            }
            else{
                pos_list[1]=pos_list[0]+1;
            }

            port_num[1]=rs.port_list[pos_list[1]];

            if (pos_list[1]+1>4){
                pos_list[2]=0;
            }
            else{
                pos_list[2]=pos_list[1]+1;
            }

            port_num[2]=rs.port_list[pos_list[2]];


//            if (myself== port_num[0] || myself==port_num[1] || myself==port_num[2]) {
//                String exc_1="insert_ok"+"--"+realvalue[3];
//
//                new Send_message(exc_1,Integer.valueOf(realvalue[2])).exc();
////                Thread t = new Thread(new Client_Thread(exc_1, Integer.valueOf(realvalue[2])));
////                t.start();
////
////                try {
////                    t.join();
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
//            }






//            test_alive ts=new test_alive(port_tail);
//            while(!ts.is_alive()){
//                port_tail=rs.find_tail();
//                ts=new test_alive(port_tail);
//            }
//
//
//            if (myself!=port_tail){
//                int next_p = rs.return_next(myself);
//
//                test_alive tp=new test_alive(next_p);
//                while(!tp.is_alive()){
//                    next_p=rs.return_next(myself);
//                    tp=new test_alive(next_p);
//                }
//                Thread t = new Thread(new Client_Thread(exc, next_p));
//                t.start();
//            }
//            else{
//                String exc_1="insert_ok"+"--"+realvalue[3];
//                Thread t = new Thread(new Client_Thread(exc_1, Integer.valueOf(realvalue[2])));
//                t.start();
//
//                try {
//                    t.join();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }





    }

    public  direct_return(){

    }
}
