package edu.buffalo.cse.cse486586.simpledynamo;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by user on 4/22/16.
 */
public class gossipe_thread implements Runnable {
    String mes;
    Integer portnum;
    //Integer portnum;
    static final String TAG = SimpleDynamoActivity.class.getSimpleName();
    @Override
    public void run() {
        //new RunHandlerQue().update_screen(pass_to_suc);
        //new ClientTask(new RingPos().get_suc()*2).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, pass_to_suc, "11112");
        //System.out.println(mes);




        SocketMap_link socket_map1=new SocketMap_link();
        if (!socket_map1.find(portnum)) {
            try {


                Socket socket = new Socket("10.0.2.2", portnum*2);
                socket.setKeepAlive(true);

                String msgToSend = mes;
//                msgToSend+="--";
//                msgToSend+=n.toString();
//                n++;

                int len=msgToSend.length();

                for (int j=1;j<=150-len;j++) {
                    msgToSend+=" ";
                }

                OutputStream Send_out = socket.getOutputStream();
                Send_out.write(msgToSend.getBytes("utf-8"));
                //Send_out.close();

                //socket.close();
                socket_map1.insert(portnum, socket);
                System.out.println("message send");

            } catch (UnknownHostException e) {



                socket_map1.delete(portnum);

                Log.e(TAG, "ClientTask UnknownHostException");
            } catch (IOException e) {
                socket_map1.delete(portnum);
                Log.e(TAG, "ClientTask socket IOException");
            }
        } else{

            try {




                String msgToSend = mes;
                int len=msgToSend.length();

//                msgToSend+="--";
//                msgToSend+=n.toString();
//                n++;
                for (int j=1;j<=150-len;j++) {
                    msgToSend+=" ";
                }



                OutputStream Send_out = socket_map1.socket_map1.get(portnum).getOutputStream();
                Send_out.write(msgToSend.getBytes("utf-8"));
                //Send_out.close();

                //socket.close();

                System.out.println("message send");

            } catch (UnknownHostException e) {

                socket_map1.delete(portnum);
                Log.e(TAG, "ClientTask UnknownHostException");
            } catch (IOException e) {
                socket_map1.delete(portnum);
                Log.e(TAG, "ClientTask socket IOException");
            }

        }



    }

    public gossipe_thread(String s, Integer decnum){
        mes=s;
        portnum=decnum;
    }
}
