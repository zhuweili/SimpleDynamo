package edu.buffalo.cse.cse486586.simpledynamo;

import android.os.Message;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Created by user on 4/23/16.
 */
public class Send_all_message implements Runnable {
    public Integer my_port_num;

    public Send_all_message(Integer portnum) {
        my_port_num = portnum;
    }

    @Override
    public void run() {
        cache cachemap = new cache();
        Ring rs = new Ring();


        Integer next_1 = rs.return_next(my_port_num);
        Integer next_2=rs.return_next(next_1);

        Integer pre_1 = rs.return_prev(my_port_num);
        Integer pre_2= rs.return_prev(pre_1);

        Integer pp1 = next_1;
        Integer pp2 = pre_1;


        PortNum PN = new PortNum();
        Integer myself = PN.get_port_num();

        Message msg = new Message();
        Tv_Handler th = new Tv_Handler();



            msg = new Message();
            msg.obj = "recover from " + my_port_num.toString();
            th = new Tv_Handler();
            th.get_handler().sendMessage(msg);


//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }




            while(new recover().is_process_back()){

            }
            cachemap.set_is_sending();



        //cachemap.unit_lock.lock();
        int l=cachemap.back_up1.size();
        ConcurrentSkipListMap<String, String> back_up=cachemap.back_up1;

        if (cachemap.back_up2.size()>l){
            l=cachemap.back_up2.size();
            back_up=cachemap.back_up2;
        }

        if (cachemap.back_up3.size()>l){
            l=cachemap.back_up3.size();
            back_up=cachemap.back_up3;
        }


        if (cachemap.back_up4.size()>l){
            l=cachemap.back_up4.size();
            back_up=cachemap.back_up4;
        }

        if (cachemap.back_up5.size()>l){
            l=cachemap.back_up5.size();
            back_up=cachemap.back_up5;
        }



        Iterator iter = back_up.entrySet().iterator();

        String recover = null;

        //rec=rec+"drec--"+PN.get_port_num().toString();

        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key1 = entry.getKey().toString();
            String value1 = entry.getValue().toString();
            String key2="";
            String value2="";

            if(iter.hasNext()){
                entry = (Map.Entry) iter.next();
                key2=entry.getKey().toString();
                value2=entry.getValue().toString();
                String Send_out=null;

                    Send_out=PN.get_port_num()+"--"+key1+"--"+value1+"--"+key2+"--"+value2;

                new Send_message(Send_out,my_port_num).exc();
            }

            else{
                String Send_out=null;

                    Send_out=PN.get_port_num()+"--"+key1+"--"+value1+"--"+"fakekey"+"--"+"fakevalue";

                new Send_message(Send_out,my_port_num).exc();

            }


        }

//            if (n%2==1){
//                for (int i=1;i<=64;i++){
//                    recover+=" ";
//                }
//            }
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //cachemap.unit_lock.unlock();






            cachemap.Send_lock.lock();
            //cachemap.unit_backlock.lock();

        for (int i=0;i<3;i++) {

            iter = cachemap.key_value.entrySet().iterator();
            if (i==2) {
                msg = new Message();
                msg.obj = cachemap.key_value.size();
                th = new Tv_Handler();
                th.get_handler().sendMessage(msg);
            }

            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                String key1 = entry.getKey().toString();
                String value1 = entry.getValue().toString();
                String key2 = "";
                String value2 = "";

                if (iter.hasNext()) {
                    entry = (Map.Entry) iter.next();
                    key2 = entry.getKey().toString();
                    value2 = entry.getValue().toString();
                    String Send_out = null;

                        Send_out = PN.get_port_num()+"--" + key1 + "--" + value1 + "--" + key2 + "--" + value2;

                    new Send_message(Send_out, my_port_num).exc();


                } else {
                    String Send_out = null;
                    Send_out = PN.get_port_num()+"--" + key1 + "--" + value1 + "--" + "fakekey" + "--" + "fakevalue";

                    new Send_message(Send_out, my_port_num).exc();

                }


            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

           //cachemap.unit_backlock.unlock();

           cachemap.Send_lock.unlock();



            cachemap.finish_sending();













                String Send_out = "d_p--"+PN.get_port_num();
                for (int i = 10; i <= 150; i++)
                    Send_out = Send_out + " ";
                System.out.println(recover + Send_out);
                //System.out.println(recover.length());

                new Send_message(Send_out,my_port_num).exc();







            //cachemap.unlock();

    }
}
