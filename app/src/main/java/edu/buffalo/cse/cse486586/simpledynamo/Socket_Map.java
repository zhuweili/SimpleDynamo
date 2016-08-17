package edu.buffalo.cse.cse486586.simpledynamo;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;
/**
 * Created by user on 4/19/16.
 */
public class Socket_Map {
    public static Map<Integer, Socket> socket_map = new LinkedHashMap<Integer, Socket>();

    public void insert(Integer port_num, Socket socket){
        socket_map.put(port_num,socket);
    }

    public boolean find(Integer port_num){
        return socket_map.containsKey(port_num);
    }

    public Socket get(Integer port_num) {
        return socket_map.get(port_num);
    }
    public void delete(Integer port_num) {      socket_map.remove(port_num);}
}
