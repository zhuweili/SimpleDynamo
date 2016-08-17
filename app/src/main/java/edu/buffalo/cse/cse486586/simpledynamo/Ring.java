package edu.buffalo.cse.cse486586.simpledynamo;

import android.os.Message;

import java.util.LinkedList;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;


/**
 * Created by user on 4/20/16.
 */
public class Ring {
    public static Integer[] port_list={0,0,0,0,0};
    public static String[] hash_list={"0","0","0","0","0"};
    public static Boolean[] alive_list={true,true,true,true,true};

    public void ini_Ring(){
        port_list[0]=5562;
        port_list[1]=5556;
        port_list[2]=5554;
        port_list[3]=5558;
        port_list[4]=5560;

        try {
            hash_list[0]=(genHash("5562"));
            hash_list[1]=(genHash("5556"));
            hash_list[2]=(genHash("5554"));
            hash_list[3]=(genHash("5558"));
            hash_list[4]=(genHash("5560"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        for (int i=0;i<5;i++){
            alive_list[i]=true;
        }

    }

    public  Integer return_part_ind(String key){

        String hash_key="";
        try {
             hash_key=genHash(key);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }




        if (hash_key.compareTo(hash_list[4])>0){
            return 0;
        }
        else{
            for (int i=0;i<5;i++){
                if (hash_key.compareTo(hash_list[i])<=0) {
                    //System.out.println(key);
                    //System.out.println(hash_key);
                    //System.out.println(hash_list[i]);
                    return i;
                }
            }
        }

        return 0;
    }



    public  Integer return_idpart_ind(String key){

        String hash_key="";
        try {
            hash_key=genHash(key);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

       if (hash_key.compareTo(hash_list[4])>0){
           return 0;
       }

        for (int i=0;i<5;i++){
            if (hash_key.compareTo(hash_list[i])<=0){
                return i;
            }
        }

        return 0;
    }









    public Integer return_next(Integer myself){


        int i=0;
        for (;i<5;i++){
            if (port_list[i].equals(myself)){
                break;
            }
        }

        boolean find_it=false;
        int j=i;

        while(!find_it){
            j++;
            if (j>4)
                j=0;

            if (alive_list[j])
                find_it=true;
        }

        return port_list[j];
    }


    public Integer return_prev(Integer myself){

        int i=0;
        for (;i<5;i++){
            if (port_list[i].equals(myself)){
                break;
            }
        }

        boolean find_it=false;
        int j=i;

        while (!find_it) {
            j--;
            if (j<0){
                j=4;
            }

            if (alive_list[j])
                find_it=true;
        }

        return port_list[j];

    }




    public Integer find_head(){
        for (int i=0;i<5;i++){
            if (alive_list[i])
                return port_list[i];
        }
        return 0;
    }

    public Integer find_tail(){
        for (int i=4;i>=0;i--){
            if (alive_list[i])
                    return port_list[i];
        }
        return 0;
    }


    public void set_alive(Integer pnum){
        for (int i=0;i<5;i++){
            //System.out.println("gfsdghfkdshvlkdfbnldf"+"  "+pnum);
            if (port_list[i].equals(pnum)){
                alive_list[i]=true;
                System.out.println(pnum.toString()+" is set to be true");

                Message msg=new Message();
                msg.obj=pnum.toString()+" is back!";
                Tv_Handler th=new Tv_Handler();
                th.get_handler().sendMessage(msg);
                Socket_Map smp=new Socket_Map();
                smp.delete(pnum);
                break;
            }
        }
    }

    public Integer return_my_position(Integer port_num){
        for (int i=0;i<5;i++){

            if (port_list[i].equals(port_num))
                return i;
        }
        return 0;
    }
    public void kill_node(Integer port_num){
        for (int i=0;i<5;i++){
            if (port_list[i].equals(port_num)){
                alive_list[i]=false;
                Message msg = new Message();
                msg.obj = port_num.toString()+" is dead";
                Tv_Handler th = new Tv_Handler();
                th.get_handler().sendMessage(msg);
                break;
            }

        }
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
}
