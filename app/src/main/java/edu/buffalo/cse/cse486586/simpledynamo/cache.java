package edu.buffalo.cse.cse486586.simpledynamo;

import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by user on 4/23/16.
 */
public class cache {
    public static ConcurrentSkipListMap<String, String> key_value = new ConcurrentSkipListMap<String, String>();
    public static ConcurrentSkipListMap<String, String> back_up1 = new ConcurrentSkipListMap<String, String>();
    public static ConcurrentSkipListMap<String, String> back_up2 = new ConcurrentSkipListMap<String, String>();
    public static ConcurrentSkipListMap<String, String> back_up3 = new ConcurrentSkipListMap<String, String>();
    public static ConcurrentSkipListMap<String, String> back_up4 = new ConcurrentSkipListMap<String, String>();
    public static ConcurrentSkipListMap<String, String> back_up5 = new ConcurrentSkipListMap<String, String>();
    public static HashMap<String, String> map_for_sending = new HashMap<String, String>();
    public static HashMap<String, String> map_for_temp = new HashMap<String, String>();
    public static Lock lock = new ReentrantLock();
    public static Lock unit_lock=new ReentrantLock();
    public static Lock unit_backlock=new ReentrantLock();
    public static Lock Send_lock=new ReentrantLock();
    public static boolean is_doing_sending=false;

    public synchronized ConcurrentSkipListMap<String, String> get_Key_value(){
        return key_value;
    }

    public  ConcurrentSkipListMap<String, String> get_back_up(){
        int l=back_up1.size();
        ConcurrentSkipListMap<String, String> back_up=back_up1;

        if (back_up2.size()>l){
            l=back_up2.size();
            back_up=back_up2;
        }

        if (back_up3.size()>l){
            l=back_up3.size();
            back_up=back_up3;
        }


        if (back_up4.size()>l){
            l=back_up4.size();
            back_up=back_up4;
        }

        if (back_up5.size()>l){
            l=back_up5.size();
            back_up=back_up4;
        }


        return back_up;
    }


    public void insert (String key, String value){
        unit_lock.lock();
        key_value.put(key, value);


        unit_lock.unlock();
        //delete_mes.remove(key);


    }
    public synchronized  void set_is_sending(){
        is_doing_sending=true;
    }
    public synchronized  void finish_sending(){
        is_doing_sending=false;
    }

    public void update_snapshot(){
        unit_lock.lock();

        Iterator iter = key_value.entrySet().iterator();
        while (iter.hasNext()) {
            unit_lock.lock();
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            map_for_sending.put(key.toString(), value.toString());
            unit_lock.unlock();

            }


        unit_lock.unlock();
    }

    public String query(String key){
        unit_lock.lock();
        String ans;
        if (key_value.get(key)!=null) {
            ans= key_value.get(key);

        }
        else
            ans= get_back_up().get(key);
        unit_lock.unlock();
        return ans;


    }

    public void Back_up_insert(String key, String value){
            get_back_up().put(key,value);
    }

    public  synchronized ConcurrentSkipListMap<String, String> unit (){

        int back_size=get_back_up().size();
        int key_size=key_value.size();


        if (key_size>=back_size) {
            Iterator iter = get_back_up().entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object key = entry.getKey();
                if (!key_value.containsKey(key.toString())) {
                    Object value = entry.getValue();
                    key_value.put(key.toString(), value.toString());

                }
            }

            //back_up.clear();
            return key_value;
        }

        if (key_size<back_size){
            Iterator iter = key_value.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object key = entry.getKey();
                Object value = entry.getValue();

                get_back_up().put(key.toString(), value.toString());


            }

            //key_value.clear();
            return get_back_up();
        }
        return null;
    }

    public void getlock(){
        lock.lock();
    }

    public void unlock(){
        lock.unlock();
    }

    public void get_unitlock(){
        unit_lock.lock();
    }
    public void unlock_unitlock(){
        unit_lock.unlock();

    }

    public void delete(String key){
        //delete_mes.put(key, "0");
        key_value.remove(key);
        back_up1.remove(key);
        back_up2.remove(key);
        back_up3.remove(key);
        back_up4.remove(key);
        back_up5.remove(key);
    }
}
