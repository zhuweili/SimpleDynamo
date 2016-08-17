package edu.buffalo.cse.cse486586.simpledynamo;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by user on 4/20/16.
 */
public class Mes_center {
    public static ArrayList<String> mes=new ArrayList<String>(){{add("begin");}}; ;
    public static ArrayList<Boolean> is_return=new ArrayList<Boolean>(){{add(false);}};
    public static Boolean locked=false;

    public int return_index() {
        return mes.size()-1;
    }

    public synchronized int apply_mes(){
        set_lock();
        mes.add("waiting");
        is_return.add(false);
        leave_lock();
        return return_index();
    }

    public void set_lock(){
        locked=true;
    }

    public void leave_lock(){
        locked=false;
    }

    public Boolean is_locked(){
        return locked;
    }

    public synchronized Boolean is_returned(int ind){
        return is_return.get(ind);
    }

    public void set_return(int ind, String value){
        mes.set(ind,value);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        is_return.set(ind, true);
    }

    public synchronized void add_mes(int ind, String value){
        String temp=mes.get(ind);
        temp=temp+value;
        mes.set(ind,temp);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void set_unlock(int ind){
        add_mes(ind, "--end");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        is_return.set(ind,true);
    }

    public String get_mes(int ind){
        return mes.get(ind);
    }


}
