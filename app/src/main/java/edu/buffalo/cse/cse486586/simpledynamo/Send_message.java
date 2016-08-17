package edu.buffalo.cse.cse486586.simpledynamo;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by user on 4/25/16.
 */
public class Send_message {
    String mes;
    Integer portnum;
    //Integer portnum;
    static final String TAG = SimpleDynamoActivity.class.getSimpleName();

    public  Send_message (String mes, Integer portnum){
        this.mes=mes;
        this.portnum=portnum;
    }


    public void exc() {


        Socket_Map socket_map = new Socket_Map();
        if (!socket_map.find(portnum) && (portnum.equals(5554)|| portnum.equals(5556) || portnum.equals(5558) || portnum.equals(5560) || portnum.equals(5562))) {
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


                socket_map.delete(portnum);

                Log.e(TAG, "ClientTask UnknownHostException");
            } catch (IOException e) {
                socket_map.delete(portnum);
                Log.e(TAG, "ClientTask socket IOException");
            }
        } else {
            if ((portnum.equals(5554)|| portnum.equals(5556) || portnum.equals(5558) || portnum.equals(5560) || portnum.equals(5562))) {
                try {


                    String msgToSend = mes;
                    int len = msgToSend.length();

//                msgToSend+="--";
//                msgToSend+=n.toString();
//                n++;

                    for (int j = 1; j <= 150 - len; j++) {
                        msgToSend += " ";
                    }
                    Socket socket=socket_map.socket_map.get(portnum);

                    if (socket==null){
                        socket = new Socket("10.0.2.2", portnum * 2);
                        socket_map.insert(portnum,socket);
                    }


                    OutputStream Send_out = socket_map.socket_map.get(portnum).getOutputStream();
                    Send_out.write(msgToSend.getBytes("utf-8"));
                    //Send_out.close();

                    //socket.close();

                    System.out.println("message send");

                } catch (UnknownHostException e) {

                    socket_map.delete(portnum);
                    Log.e(TAG, "ClientTask UnknownHostException");
                } catch (IOException e) {
                    socket_map.delete(portnum);
                    Log.e(TAG, "ClientTask socket IOException");
                }
            }

        }
    }

}
