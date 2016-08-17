package edu.buffalo.cse.cse486586.simpledynamo;

import android.os.Message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by user on 4/24/16.
 */
public class Thread_recover implements Runnable {
    public String type;
    public ConcurrentHashMap<String, String> back_up;
    @Override
    public void run() {

//        if (type.equals("d_s")){
//            recover_into rec= new recover_into();
//            rec.deal_suc();
//            Message msg = new Message();
//            msg.obj = "recover is done !!!!!!!";
//            Tv_Handler th = new Tv_Handler();
//            th.get_handler().sendMessage(msg);
//        }
//        else {
//            recover_into rec= new recover_into();
//            rec.deal_pre();
//            Message msg = new Message();
//            msg.obj = "recover is done !!!!!!!";
//            Tv_Handler th = new Tv_Handler();
//            th.get_handler().sendMessage(msg);
//        }

           cache cachemap =new cache();
           cachemap.Send_lock.lock();

           Iterator iter = back_up.entrySet().iterator();

        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            cachemap.Back_up_insert(key.toString(),value.toString());
        }
           cachemap.Send_lock.unlock();

            new recover().finsih_processing();
            Message msg = new Message();
            msg.obj = "recover is done !!!!!!!";
            Tv_Handler th = new Tv_Handler();
            th.get_handler().sendMessage(msg);

    }

    public Thread_recover(ConcurrentHashMap < String, String > back_up){
        this.back_up=back_up;
    }
}
