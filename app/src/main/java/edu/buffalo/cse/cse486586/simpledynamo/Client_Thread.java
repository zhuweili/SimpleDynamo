package edu.buffalo.cse.cse486586.simpledynamo;

/**
 * Created by user on 4/19/16.
 */

import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client_Thread implements Runnable{

    String mes;
    Integer portnum;
    //Integer portnum;
    static final String TAG = SimpleDynamoActivity.class.getSimpleName();
    @Override
    public void run() {
        //new RunHandlerQue().update_screen(pass_to_suc);
        //new ClientTask(new RingPos().get_suc()*2).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, pass_to_suc, "11112");
        System.out.println(mes);




        Socket_Map socket_map=new Socket_Map();


        if ((portnum.equals(5554)|| portnum.equals(5556) || portnum.equals(5558) || portnum.equals(5560) || portnum.equals(5562))) {


            if (!socket_map.find(portnum)) {
                try {


                    Socket socket = new Socket("10.0.2.2", portnum * 2);
                    socket.setKeepAlive(true);

                    String msgToSend = mes;
//                msgToSend+="--";
//                msgToSend+=n.toString();
//                n++;

                    int len = msgToSend.length();

                    for (int j = 1; j <= 150 - len; j++) {
                        msgToSend += " ";
                    }

                    OutputStream Send_out = socket.getOutputStream();
                    Send_out.write(msgToSend.getBytes("utf-8"));
                    //Send_out.close();

                    //socket.close();
                    socket_map.insert(portnum, socket);
                    System.out.println("message send");

                } catch (UnknownHostException e) {


//                    socket_map.delete(portnum);
//
//                    Log.e(TAG, "ClientTask UnknownHostException");
                } catch (IOException e) {
//                    socket_map.delete(portnum);
//                    Log.e(TAG, "ClientTask socket IOException");
                }
            } else {

                try {


                    String msgToSend = mes;


//                msgToSend+="--";
//                msgToSend+=n.toString();
//                n++;
                    if (msgToSend != null) {
                        int len = msgToSend.length();
                        for (int j = 1; j <= 150 - len; j++) {
                            msgToSend += " ";
                        }

                        Socket socket=socket_map.socket_map.get(portnum);

                        if (socket==null){
                            socket=new Socket("10.0.2.2", portnum * 2);
                            socket.setKeepAlive(true);
                            socket_map.socket_map.put(portnum,socket);
                        }
                        OutputStream Send_out = socket_map.socket_map.get(portnum).getOutputStream();
                        Send_out.write(msgToSend.getBytes("utf-8"));
                        //Send_out.close();

                        //socket.close();

                        System.out.println("message send");
                    }

                } catch (UnknownHostException e) {

//                    socket_map.delete(portnum);
//                    Log.e(TAG, "ClientTask UnknownHostException");
                } catch (IOException e) {
//                    socket_map.delete(portnum);
//                    Log.e(TAG, "ClientTask socket IOException");
                }

            }

        }

    }

    public Client_Thread(String s, Integer decnum){
        mes=s;
        portnum=decnum;
    }

}
