package edu.buffalo.cse.cse486586.simpledynamo;

/**
 * Created by user on 4/22/16.
 */
public class test_alive {
    public Integer port_num;
    public test_alive(Integer port_num){
        this.port_num=port_num;
    }

    public boolean is_alive(){
        Mes_center mess = new Mes_center();
        Integer mess_id=mess.apply_mes();
        PortNum PN=new PortNum();


        String gossipe="hello--"+PN.get_port_num().toString()+"--"+mess_id.toString();
        Thread t = new Thread(new Client_Thread(gossipe, port_num));
        t.start();
        int n=0;

        while(!mess.is_returned(mess_id) && n<=20){

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            n++;
            mess=new Mes_center();
            System.out.println("!!!!!!!!!!waiting!!!!!!!!!!!!!!!!");
        }

        if (mess.is_returned(mess_id)){
            return true;
        }else{
            Ring rs=new Ring();
            rs.kill_node(port_num);
            String gg="dead--"+port_num.toString();
            Integer[] port_list={5554,5556,5558,5560,5562};

            for (int i=0;i<4;i++){
                if ( !port_list[i].equals(port_num) && !port_list[i].equals(PN.get_port_num()) ){
                    Thread t1 = new Thread(new Client_Thread(gg, port_list[i]));
                    t1.start();
                }
            }
        }

        return false;

    }
}
