package edu.buffalo.cse.cse486586.simpledynamo;

import android.content.ContentResolver;
import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 4/19/16.
 */
public class back_join implements Runnable {
    @Override



    public void run() {

        List<Integer> portList=new ArrayList<Integer>();
        portList.add(5554);
        portList.add(5556);
        portList.add(5558);
        portList.add(5560);
        portList.add(5562);

        PortNum PN=new PortNum();
        while(PN.get_port_num()==-1){

        }
        Integer pot=PN.get_port_num();
        String ps=PN.get_port_num().toString();
        System.out.println(ps);
        String joinmes="join--"+ps;
        for (int i = 0; i < 5; i++) {

            if (!portList.get(i).equals(pot)) {
                new Send_message(joinmes,portList.get(i)).exc();

            }

        }

        joinmes="hello--"+PN.get_port_num();

        while(true){

            for (int i = 0; i < 5; i++) {

                if (!portList.get(i).equals(pot)) {
                    new Send_message(joinmes,portList.get(i)).exc();

                }

            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }




    }
}
