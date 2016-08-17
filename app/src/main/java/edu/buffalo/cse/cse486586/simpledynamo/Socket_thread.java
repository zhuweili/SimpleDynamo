package edu.buffalo.cse.cse486586.simpledynamo;

/**
 * Created by user on 4/19/16.
 */

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Socket_thread implements Runnable {
    static final String TAG = SimpleDynamoActivity.class.getSimpleName();
    //private BufferedReader Bu_read;
    public static final String PREFS_NAME = "PA4";
    private final Uri mUri;
    private boolean is_runing;
    private Integer my_port_num;
    private boolean first_time=true;
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    public static cache cachemap;
    public static ConcurrentHashMap<String, String> insert_ret_map= new ConcurrentHashMap<String, String>();
    public static ConcurrentHashMap<String, Integer> return_insertok= new ConcurrentHashMap<String, Integer>();
    public static boolean is_isrecing1=false;
    public static boolean is_isrecing2=false;
    public static boolean is_isrecing3=false;
    public static boolean is_isrecing4=false;
    public static boolean is_isrecing5=false;
    public static Lock insertok_lock=new ReentrantLock();
    public static int contact_people=0;
    public static Lock not_one_dead=new ReentrantLock();
    public long last_timestamp=0;
    private Socket socket = null;
    public Socket_thread(Socket socket) {
        this.socket = socket;
        mUri = Uri.parse("content://edu.buffalo.cse.cse486586.simpledynamo.provider");
        try {
            this.socket.setKeepAlive(true);
            //sp=new Get_context().get_context().getSharedPreferences(PREFS_NAME, 0);
            //edit=sp.edit();
            cachemap=new cache();
            //this.socket.setSoTimeout(3000);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        last_timestamp=System.currentTimeMillis();
        is_runing=true;
        my_port_num=0;
        first_time=true;

    }
    public Socket getSocket() {
        return socket;
    }
    public void setSocket(Socket socket) {
        this.socket = socket;
        try {
            this.socket.setKeepAlive(true);
            //this.socket.setSoTimeout(1000);
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }

    public synchronized ConcurrentHashMap<String, Integer>get_return_insertok() {
        return return_insertok;
    }

    private String genHash(String input) throws NoSuchAlgorithmException {
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        byte[] sha1Hash = sha1.digest(input.getBytes());
        Formatter formatter = new Formatter();
        for (byte b : sha1Hash) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }

    @Override
    public void run() {

        System.out.println("new thread!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        String Rec_msg="";
        boolean is_run=true;

        String bb="";
        ArrayList<String> pre_back_up=new ArrayList<String>();
        ArrayList<String> suc_back_up=new ArrayList<String>();
//        try {
//            Bu_read=new BufferedReader(new InputStreamReader(this.socket.getInputStream(), "utf8"));
//            Rec_msg = Bu_read.readLine();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        while(true && is_run){
            //new RunHandlerQue().update_screen("vfb");


            long now=System.currentTimeMillis();
            if (now-last_timestamp>2500){
                is_run=false;

            }

            try {

                InputStream ips = socket.getInputStream();
                byte[] b = new byte[150];

                if(ips.read(b)!=-1) {

                    String srt = new String(b, "utf-8");
//                    if (srt.length()!=150){
//                        break;
//                    }

                    Rec_msg = srt.trim();
                    //System.out.println(Rec_msg);
                    last_timestamp=System.currentTimeMillis();


//                    if (!Rec_msg.contains("--")){
//                        if (Rec_msg.length()>64){
//                        String key1=Rec_msg.substring(0,32);
//                        back_up.add(key1);
//                        String val1=Rec_msg.substring(32,64);
//                        back_up.add(val1);
//
//                        String key2=Rec_msg.substring(64,96);
//                            back_up.add(key2);
//                        String val2=Rec_msg.substring(96,128);
//                            back_up.add(val2);
//                        }else{
//                            String key1=Rec_msg.substring(0,32);
//                            back_up.add(key1);
//                            String val1=Rec_msg.substring(32,64);
//                            back_up.add(val1);
//                        }
//                        continue;
//                    }



                    // new RunHandlerQue().update_screen(Rec_msg);

                    String[] exc=Rec_msg.split("--");

//                    if (exc[0].equals("pre") && contact_people<2){
//                        new recover().set_needing();
//                        String key1=exc[1];
//
//                        String val1=exc[2];
//                        cachemap.back_up.put(key1, val1);
//
//                        String key2=exc[3];
//                        if (!key2.equals("fakekey")) {
//
//                            String val2 = exc[4];
//                            cachemap.back_up.put(key2,val2);
//                        }
//
//                    }
//
//
//                    if (exc[0].equals("suc") &&  contact_people<2){
//                        new recover().set_needing();
//                        String key1=exc[1];
//
//                        String val1=exc[2];
//                        cachemap.back_up.put(key1, val1);
//
//                        String key2=exc[3];
//                        if (!key2.equals("fakekey")) {
//
//                            String val2 = exc[4];
//                            cachemap.back_up.put(key2, val2);
//                        }
//
//                    }


                    if (exc[0].equals("hello")){
//                        Message msg = new Message();
//                        msg.obj = Rec_msg;
//                        Tv_Handler th = new Tv_Handler();
//                        th.get_handler().sendMessage(msg);



                        continue;
                    }


                    if (exc[0].equals("alive")){
//                        Message msg = new Message();
//                        msg.obj = Rec_msg;
//                        Tv_Handler th = new Tv_Handler();
//                        th.get_handler().sendMessage(msg);

                        Mes_center mes=new Mes_center();
                        mes.set_return(Integer.valueOf(exc[1]), "alive");
                        continue;

                    }

                    if (exc[0].equals("dead")){
                        Ring rs =new Ring();
                        rs.kill_node(Integer.valueOf(exc[1]));
                        continue;
                    }



                    if (exc[0].equals("join")) {


                        Ring rs=new Ring();
                        //rs.set_alive(Integer.valueOf(exc[1]));
                        Socket_Map socket_map=new Socket_Map();
                        PortNum PN=new PortNum();



                        socket_map.delete(Integer.valueOf(exc[1]));
                        //socket_map.socket_map.put(Integer.valueOf(exc[1]), socket);

                        SocketMap_link socket_map1=new SocketMap_link();
                        socket_map1.delete(Integer.valueOf(exc[1]));
                        Thread.sleep(500);



                        Message msg = new Message();
                        msg.obj = Rec_msg;
                        Tv_Handler th = new Tv_Handler();
                        th.get_handler().sendMessage(msg);




                        if (my_port_num==0) {
                            if(exc[1].length()==4) {
                                my_port_num = Integer.valueOf(exc[1]);
                            }
                        }

                        Integer next=rs.return_next(my_port_num);
                        Integer prev=rs.return_prev(my_port_num);

                        if(!cachemap.is_doing_sending) {
                            cachemap.set_is_sending();
                            if (my_port_num!=0) {

                                    not_one_dead.lock();
                                    Thread t = new Thread(new Send_all_message(my_port_num));

                                    //t.setPriority(-19);
                                    t.start();

                                    not_one_dead.unlock();

                            }
                        }


                        continue;
                    }


                    if (exc[0].equals("5554")){
                        cachemap.back_up1.put(exc[1],exc[2]);
                        if (!exc[3].equals("fakekey")){
                            cachemap.back_up1.put(exc[3],exc[4]);
                        }
                        is_isrecing1=true;
                        continue;
                    }


                    if (exc[0].equals("5556")){
                        cachemap.back_up2.put(exc[1],exc[2]);
                        if (!exc[3].equals("fakekey")){
                            cachemap.back_up2.put(exc[3],exc[4]);
                        }
                        is_isrecing2=true;
                        continue;
                    }


                    if (exc[0].equals("5558")){
                        cachemap.back_up3.put(exc[1],exc[2]);
                        if (!exc[3].equals("fakekey")){
                            cachemap.back_up3.put(exc[3],exc[4]);
                        }
                        is_isrecing3=true;
                        continue;
                    }


                    if (exc[0].equals("5560")){
                        cachemap.back_up4.put(exc[1],exc[2]);
                        if (!exc[3].equals("fakekey")){
                            cachemap.back_up4.put(exc[3],exc[4]);
                        }
                        is_isrecing4=true;
                        continue;
                    }


                    if (exc[0].equals("5562")){
                        cachemap.back_up5.put(exc[1],exc[2]);
                        if (!exc[3].equals("fakekey")){
                            cachemap.back_up5.put(exc[3],exc[4]);
                        }
                        is_isrecing5=true;
                        continue;
                    }




                    if (exc[0].equals("d_p") ){
                        //recover_into rec= new recover_into();

                            //new recover().set_proc_back();
                            if (exc[1].equals("5554")) {
                                is_isrecing1 = false;

                            }
                            if (exc[1].equals("5556")) {
                                is_isrecing2 = false;
                            }
                            if (exc[1].equals("5558")) {
                                is_isrecing3 = false;
                            }
                            if (exc[1].equals("5560")) {
                                is_isrecing4 = false;
                            }
                            if (exc[1].equals("5562")) {
                                is_isrecing5 = false;
                            }

                            Thread.sleep(100);

//                            Message msg = new Message();
//                            msg.obj = "recover is done for "+ exc[1]+"\n"+"length is "+cachemap.get_back_up().size();
//                            Tv_Handler th = new Tv_Handler();
//                            th.get_handler().sendMessage(msg);

//                            Thread t = new Thread(new Thread_recover(insert_ret_map));
//                            t.start();
                            continue;

                    }


                    if (exc[0].equals("d_s") && (new recover().is_rec_suc()) && contact_people<2){
                        //recover_into rec= new recover_into();

                        contact_people++;
                        if (contact_people>1) {
                            //new recover().set_proc_back();
                            new recover().need_recovering();
                            new recover().finish_recocering_suc();

                            new recover().finsih_processing();
                            Message msg = new Message();
                            msg.obj = "recover is done !!!!!!!";
                            Tv_Handler th = new Tv_Handler();
                            th.get_handler().sendMessage(msg);

//                            Thread t = new Thread(new Thread_recover(insert_ret_map));
//                            t.start();
                            //rec.deal_suc();
                        }

                    }





                    if (exc[0].equals("insert")){


                        //if (cachemap.unit_lock.tryLock()){
                            cachemap.key_value.put(exc[1], exc[2]);
//                        Message msg = new Message();
//                        msg.obj = "insert_"+exc[5];
//                        Tv_Handler th = new Tv_Handler();
//                        th.get_handler().sendMessage(msg);

//                        Message msg = new Message();
//                        msg.obj = "key_value length is "+cachemap.key_value.size();
//                        Tv_Handler th = new Tv_Handler();
//                        th.get_handler().sendMessage(msg);
                            //cachemap.unit_lock.unlock();}

                        //else{
                           // cachemap.unit_backlock.tryLock();
                            //cachemap.back_up.put(exc[1], exc[2]);
                           // cachemap.unit_backlock.unlock();
                       // }

//                        Thread t = new Thread(new Thread_cp(Rec_msg));
//                        t.start();

                        continue;
                    }



                    if (exc[0].equals("insert_ok")){

                        Integer ind=Integer.valueOf(exc[1]);
                        Mes_center mes=new Mes_center();

                        if ( !return_insertok.containsKey(Rec_msg) ) {

                            return_insertok.put(Rec_msg, 1);
                            mes.set_return(ind, "insert is successsful");
                            Message msg = new Message();
                            msg.obj = Rec_msg;
                            Tv_Handler th = new Tv_Handler();
                            th.get_handler().sendMessage(msg);


                        }




                        continue;
                    }




                    if (exc[0].equals("query")){
//                        Message msg = new Message();
//                        msg.obj = Rec_msg;
//                        Tv_Handler th = new Tv_Handler();
//                        th.get_handler().sendMessage(msg);
                        //Thread.sleep(50);
                        String value=null;
                        int i=1;
                        boolean back=true;

//                        if(is_isrecing1 || is_isrecing2 || is_isrecing3 || is_isrecing4 || is_isrecing5)
//                            continue;

                        while(i<=20 && back ) {
                            value = cachemap.key_value.get(exc[1]);
                            if (value!=null && i>2){
                                back=false;
                            }

                            if (value == null && !is_isrecing1) {
                                value = cachemap.back_up1.get(exc[1]);
                            }

                            if (value == null && !is_isrecing2) {
                                value = cachemap.back_up2.get(exc[1]);
                            }

                            if (value == null && !is_isrecing3) {
                                value = cachemap.back_up3.get(exc[1]);
                            }

                            if (value == null && !is_isrecing4) {
                                value = cachemap.back_up4.get(exc[1]);
                            }

                            if (value == null && !is_isrecing5) {
                                value = cachemap.back_up5.get(exc[1]);
                            }


                            i++;
                        }

                        if (value != null) {
                            String exc1 = "return_query--" + exc[1] + "--" + value + "--" + exc[3];
                            //new T(exc1, Integer.valueOf(exc[2])).exc();
                            Thread t = new Thread(new Client_Thread(exc1,Integer.valueOf(exc[2]) ));
                            t.start();

                        }


//                        Thread t = new Thread(new Thread_cp(Rec_msg));
//                        t.start();
                        //last_timestamp=System.currentTimeMillis();
                        continue;
                    }


                    if (exc[0].equals("return_query")){



                        Integer ind=Integer.valueOf(exc[3]);
                        Mes_center mes=new Mes_center();

                        //cachemap.key_value.put(exc[1],exc[2]);
                        mes.set_return(ind, exc[2]);
                        continue;
                    }

                    if (exc[0].equals("query*")){

                        Message msg = new Message();
                        msg.obj = Rec_msg;
                        Tv_Handler th = new Tv_Handler();
                        th.get_handler().sendMessage(msg);

                        Thread t = new Thread(new Thread_cp(Rec_msg));
                        t.start();
                        continue;
                    }

                    if (exc[0].equals("return*")){

                        Message msg = new Message();
                        msg.obj = Rec_msg;
                        Tv_Handler th = new Tv_Handler();
                        th.get_handler().sendMessage(msg);

                        String key=exc[1];
                        String value=exc[2];
                        Integer mes_ind=Integer.valueOf(exc[3]);
                        cachemap.insert(key, value);
                        //mes.add_mes(mes_ind, add_mes);
                        continue;
                    }

                    if (exc[0].equals("done*")){
                        Message msg = new Message();
                        msg.obj = Rec_msg;
                        Tv_Handler th = new Tv_Handler();
                        th.get_handler().sendMessage(msg);

                        Integer mes_ind=Integer.valueOf(exc[1]);
                        Mes_center mes=new Mes_center();

                        mes.set_unlock(mes_ind);

                        continue;
                    }


                    if (exc[0].equals("delete")){
//                        Message msg = new Message();
//                        msg.obj = Rec_msg;
//                        Tv_Handler th = new Tv_Handler();
//                        th.get_handler().sendMessage(msg);

                        Thread t = new Thread(new Thread_cp(Rec_msg));
                        t.start();
                        continue;
                    }

                    if (exc[0].equals("delete*")){
                        Message msg = new Message();
                        msg.obj = Rec_msg;
                        Tv_Handler th = new Tv_Handler();
                        th.get_handler().sendMessage(msg);

                        Thread t = new Thread(new Thread_cp(Rec_msg));
                        t.start();
                         continue;
                    }




                }
                else{

//                    System.out.println("dead!!!!!!!!");
//
//                    Message msg = new Message();
//                    msg.obj = my_port_num.toString()+" is dead";
//                    Tv_Handler th = new Tv_Handler();
//                    th.get_handler().sendMessage(msg);
//                    new Socket_Map().delete(my_port_num);
//                    is_run=false;
                    //System.out.println("dead__________________dead");


                }


                //new RunHandlerQue().update_screen(Rec_msg);




            }catch(SocketTimeoutException e){
                //Log.e(TAG, " connect failed " + socket.getPort());
//                        Message msg=new Message();
//                        msg.obj=portname+" is dead";
//                        handler.sendMessage(msg);
//
//                        socket.close();
//                        alive=false;
//                System.out.println("dead__________________dead");
//                Message msg = new Message();
//                msg.obj = my_port_num.toString()+" is dead";
//                Tv_Handler th = new Tv_Handler();
//                th.get_handler().sendMessage(msg);
//                is_runing=false;
//                Ring rs=new Ring();
//                rs.kill_node(my_port_num);
                //socket_map.delete(my_port_num);
                Log.e(TAG, " connect failed 1111");
                e.printStackTrace();

            }catch(SocketException e){
                //System.out.println("dead__________________dead");

                Log.e(TAG, " connect failed 2222");
                //onProgressUpdate("dead");
                e.printStackTrace();
            } catch (Exception e) {
                //System.out.println("dead__________________dead");

                Log.e(TAG, " connect failed  3333");
                //onProgressUpdate("dead");
                e.printStackTrace();
            }



        }
    }
}
