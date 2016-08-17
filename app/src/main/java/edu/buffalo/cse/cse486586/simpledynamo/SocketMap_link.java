package edu.buffalo.cse.cse486586.simpledynamo;

import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by user on 4/22/16.
 */
public class SocketMap_link {

    public static Map<Integer, Socket> socket_map1 = new LinkedHashMap<Integer, Socket>();

    public void insert(Integer port_num, Socket socket){
        socket_map1.put(port_num,socket);
    }

    public boolean find(Integer port_num){
        return socket_map1.containsKey(port_num);
    }

    public Socket get(Integer port_num) {
        return socket_map1.get(port_num);
    }
    public void delete(Integer port_num) {      socket_map1.remove(port_num);}
    public void clear(){    socket_map1.clear();}
}
