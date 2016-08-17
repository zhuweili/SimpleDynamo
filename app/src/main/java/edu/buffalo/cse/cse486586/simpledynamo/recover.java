package edu.buffalo.cse.cse486586.simpledynamo;

/**
 * Created by user on 4/22/16.
 */
public class recover {
    public static boolean is_recing_suc=true;
    public static boolean is_processing=false;
    public static boolean need_recing=false;

    public  boolean is_recovering(){
        return is_recing_suc && need_recing ;
    }

    public void finish_recocering_suc(){
        is_recing_suc=false;
    }

    public void finsih_processing(){
        is_processing=false;
    }

    public void set_proc_back(){
        is_processing=true;
    }

    public boolean is_process_back(){
        return is_processing;
    }


    public boolean is_rec_suc(){
        return is_recing_suc;
    }

    public boolean need_recovering(){
        return need_recing;
    }

    public void set_needing(){
        need_recing=true;
    }
}
