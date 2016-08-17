package edu.buffalo.cse.cse486586.simpledynamo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 4/22/16.
 */
public class gossipe implements Runnable{
    @Override
    public void run() {

        List<Integer> portList = new ArrayList<Integer>();
        portList.add(5554);
        portList.add(5556);
        portList.add(5558);
        portList.add(5560);
        portList.add(5562);

        PortNum PN = new PortNum();
        while (PN.get_port_num() == -1) {

        }
        Integer pot = PN.get_port_num();
        String ps = PN.get_port_num().toString();
        System.out.println(ps);
        String joinmes = "hello--" + ps;

        while (true){
            for (int i = 0; i < 5; i++) {


                Thread t = new Thread(new gossipe_thread(ps, portList.get(i)));
                t.start();


            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
