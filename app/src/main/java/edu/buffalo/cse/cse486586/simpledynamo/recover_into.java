package edu.buffalo.cse.cse486586.simpledynamo;

import android.os.Message;

import java.util.ArrayList;

/**
 * Created by user on 4/23/16.
 */
public class recover_into  {
    public static String bb_pre=null;
    public static String bb_suc=null;
    public ArrayList<String> back_up;

    public recover_into(ArrayList<String> back_up){
        this.back_up=back_up;

    }



    public void add_pre(String tt)
    {   if (bb_pre!=null)
        bb_pre=bb_pre+tt;
        else
        bb_pre=tt;
    }

    public void add_suc(String tt)
    {  if(bb_suc!=null)
        bb_suc=bb_suc+tt;
        else
        bb_suc=tt;
    }

    public void deal_pre(){
        cache cachemap=new cache();
        cachemap.get_unitlock();
        if (bb_pre!=null) {
            //bb_pre.replace("$","");
            String[] temp = bb_pre.trim().split("--");
//                    Message msg = new Message();
//        msg.obj = bb_pre;
//        Tv_Handler th = new Tv_Handler();
//        th.get_handler().sendMessage(msg);
            System.out.println("len is+" + temp.length);
            if (temp.length>=2) {
                //cachemap.getlock();
                for (int i = 0; i <= temp.length-4; i+=3) {
                    if (temp[i].equals("p_e")) {
                        cachemap.Back_up_insert(temp[i+1].trim(), temp[i + 2].trim());
                        System.out.println(temp[i+1]);
                        System.out.println(temp[i + 2]);
                    }
                    else
                        break;
                }
                //cachemap.unlock();
            }
        }
        new recover().finsih_processing();
        cachemap.unlock_unitlock();

    }

    public void deal_suc(){
        cache cachemap=new cache();
        cachemap.get_unitlock();
        if (bb_suc!=null) {
            //bb_suc.replace("$","");
            String[] temp = bb_suc.trim().split("--");
//            Message msg = new Message();
//        msg.obj = bb_suc.trim();
//        Tv_Handler th = new Tv_Handler();
//        th.get_handler().sendMessage(msg);
            System.out.println("len is+" + temp.length);
            if (temp.length>=2) {
                //cachemap.getlock();
                for (int i = 0; i <= temp.length-4; i+=3) {
                    if (temp[i].equals("s_c")) {
                        cachemap.Back_up_insert(temp[i+1].trim(), temp[i + 2].trim());
                        System.out.println(temp[i+1]);
                        System.out.println(temp[i + 2]);
                    }
                    else
                        break;
                }
                //cachemap.unlock();
            }
        }
        new recover().finsih_processing();
//        Message msg = new Message();
//        msg.obj = bb_suc.trim();
//        Tv_Handler th = new Tv_Handler();
//        th.get_handler().sendMessage(msg);
        //cachemap.unit();
        cachemap.unlock_unitlock();
    }

    public void insert_ino_cache(){
        cache cachemap=new cache();

        cachemap.Send_lock.lock();

        for (int i=0;i<=back_up.size()-2;i+=2){
             cachemap.Back_up_insert(back_up.get(i),back_up.get(i+1));
        }
        new recover().finsih_processing();

        cachemap.Send_lock.unlock();
    }

}
