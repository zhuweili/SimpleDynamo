package edu.buffalo.cse.cse486586.simpledynamo;

import java.io.IOException;
import java.net.ServerSocket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;

public class SimpleDynamoProvider extends ContentProvider {
	static final String TAG = SimpleDynamoProvider.class.getSimpleName();
	public static final String PREFS_NAME = "PA4";
	SharedPreferences sp;
	SharedPreferences.Editor edit;
    public static Integer rep_num=3;
	public static Lock lock = new ReentrantLock();
	public static cache cachemap=new cache();

	@Override
	public synchronized int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub

		sp=this.getContext().getSharedPreferences(PREFS_NAME, 0);
		edit=sp.edit();
		if (selection.equals("@") || selection.equals("\"@\"")) {
			dump_old();
			cachemap.key_value.clear();

			return 0;
		}

		if (   (selection.contains("*") || selection.contains("\"*\"")) && selection.contains("--")){
			dump_old();
			cachemap.key_value.clear();
			return 0;
		}

		if (selection.contains("*") || selection.contains("\"*\"")){
			dump_old();
			cachemap.key_value.clear();
			String exc="delete*--rep";
			Integer[] port_num={5554,5556,5558,5560,5562};
			for (int i=0;i<5;i++){
				Thread t = new Thread(new Client_Thread(exc, port_num[i]));
				t.start();
			}

			return 0;
		}

		if (!selection.contains("--")){
		String exc="delete--"+selection;
		cachemap.key_value.remove(selection);
		edit.remove(selection);
		edit.commit();

		Integer[] port_num={5554,5556,5558,5560,5562};
		for (int i=0;i<5;i++){
			Thread t = new Thread(new Client_Thread(exc, port_num[i]));
			t.start();
		}

			return 0;
		}
		else{
			selection.replace("--", "");
			cachemap.key_value.remove(selection);
			edit.remove(selection);
			edit.commit();
		}
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public synchronized Uri  insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub


//		sp=this.getContext().getSharedPreferences(PREFS_NAME, 0);
//		edit=sp.edit();
		String key=(String)values.get("key");
		String value=(String)values.get("value");

//		System.out.println(key);
//		System.out.println(value);

		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

//        if (!value.contains("-rep")) {
//			Ring rs = new Ring();
//			Integer partion = rs.return_part(key);
//
//			Message msg = new Message();
//			msg.obj = partion.toString() + " for store!";
//			Tv_Handler th = new Tv_Handler();
//			th.get_handler().sendMessage(msg);
//
//			String exc = "insert--" + key + "--" + value + "-rep" + rep_num.toString();
//			Thread t = new Thread(new Client_Thread(exc, partion));
//			t.start();
//		}
//
//		if (value.contains("-rep")){
//
//			Ring rs = new Ring();
//			Integer mysely=new PortNum().get_port_num();
//			Integer next_p = rs.return_next(mysely);
//
//			String[] realvalue=value.split("-rep");
//			Integer rp_num=Integer.valueOf(realvalue[1]);
//			rp_num--;
//			value=realvalue[0];
//			edit.putString(key, value);
//			edit.commit();
//			if (rp_num>0){
//				String exc = "insert--" + key + "--" + value + "-rep" + rp_num.toString();
//				Thread t = new Thread(new Client_Thread(exc, next_p));
//				t.start();
//			}
//
//
//		}





			Ring rs = new Ring();
			Integer port_head = rs.find_head();

//			test_alive ts=new test_alive(port_head);
//			while(!ts.is_alive()){
//				port_head=rs.find_head();
//				ts=new test_alive(port_head);
//			}

			PortNum PN=new PortNum();
			Integer myself=PN.get_port_num();

               // cachemap.map_for_temp.put(key,value);

//				if (cachemap.unit_lock.tryLock()){

					//cachemap.unit_lock.unlock();}

//				else{
					//cachemap.unit_backlock.tryLock();
					//cachemap.back_up.put(key, value);
					//cachemap.unit_backlock.unlock();
				//}


//			try {
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}

			Mes_center mess = new Mes_center();
			Integer mess_id=mess.apply_mes();


			lock.lock();

			String exc = "insert--" + key + "--" + value + "--rep"+"--"+myself.toString()+"--"+mess_id.toString();
			Integer[] web={5554,5556,5558,5560,5562};

			for (int i=0;i<5;i++){
				if (!web[i].equals(PN.get_port_num())) {
					Thread t = new Thread(new Client_Thread(exc, web[i]));
					t.start();
				}
			}
			cachemap.key_value.put(key, value);
//
//			while(!mess.is_returned(mess_id)){
//
//				try {
//					Thread.sleep(50);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				mess=new Mes_center();
//				System.out.println("!!!!!!!!!!waiting!!!!!!!!!!!!!!!!");
//			}
            lock.unlock();
//			try {
//				Thread.sleep(150);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}




		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return null;
	}





	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		Ring rs=new Ring();
		rs.ini_Ring();
		dump_old();
		Thread t = new Thread(new back_join());
		t.start();

		try {

			ServerSocket serverSocket = new ServerSocket(10000);
			new ReceiveMessage().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, serverSocket);

		} catch (IOException e) {

			Log.e(TAG, "Can't create a ServerSocket");
			return false;
		}






		return false;
	}

	@Override
	public synchronized Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		// TODO Auto-generated method stub

		lock.lock();
		//sp=this.getContext().getSharedPreferences(PREFS_NAME, 0);

		if (selection.equals("@") || selection.equals("\"@\"") ){

			try {
				Thread.sleep(3500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}



			recover rc=new recover();

				int n=0;
				while (rc.is_process_back()) {
					Message msg = new Message();
					msg.obj = " still recovering!!!";
					Tv_Handler th = new Tv_Handler();
					th.get_handler().sendMessage(msg);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					rc = new recover();
					n++;
					if (n>=10){
						break;
					}
				}




			//Map<String,?> all_out = sp.getAll();

			String[] row_name={"key", "value"};
			MatrixCursor mc=new MatrixCursor(row_name);
            Ring rs=new Ring();


			Iterator iter = cachemap.unit().entrySet().iterator();

			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				Object key = entry.getKey();
				Object value = entry.getValue();
				Integer key_pos=rs.return_idpart_ind(key.toString());
				int[] pos_list={0,0,0};
				//edit.putString(key.toString(), value.toString());
				//edit.commit();

				pos_list[0]=key_pos;
				if (pos_list[0]+1>4){
					pos_list[1]=0;
				}
				else{
					pos_list[1]=pos_list[0]+1;
				}

				if (pos_list[1]+1>4){
					pos_list[2]=0;
				}
				else{
					pos_list[2]=pos_list[1]+1;
				}

				PortNum PN= new PortNum();
				Integer myself=PN.get_port_num();
				int node_pos=rs.return_my_position(myself);

//				Message msg = new Message();
//				msg.obj = (node_pos +"--"+pos_list[0]+"--"+pos_list[1]+"--"+pos_list[2]);
//				Tv_Handler th = new Tv_Handler();
//				th.get_handler().sendMessage(msg);


				for (int i=0;i<3;i++) {
					if (node_pos==pos_list[i]) {
						if (key.toString().length()==32 && value.toString().length()==32){
						MatrixCursor.RowBuilder add1 = mc.newRow();

						add1.add(key.toString());
						add1.add(value.toString());
						edit.putString(key.toString(), value.toString());
						edit.commit();}


					}
				}

			}

			lock.unlock();
			return mc;
		}


//			for(Map.Entry<String,?> entry : all_out.entrySet()){
//
//				String key=entry.getKey().toString();
//				String value=entry.getValue().toString();
//				Integer key_pos=rs.return_part_ind(key);
//				int[] pos_list={0,0,0};
//
//				pos_list[0]=key_pos;
//				if (pos_list[0]+1>4){
//					pos_list[1]=0;
//				}
//				else{
//					pos_list[1]=pos_list[0]+1;
//				}
//
//				if (pos_list[1]+1>4){
//					pos_list[2]=0;
//				}
//				else{
//					pos_list[2]=pos_list[1]+1;
//				}
//
//				PortNum PN= new PortNum();
//				Integer myself=PN.get_port_num();
//				int node_pos=rs.return_my_position(myself);
//
//
//
//				for (int i=0;i<3;i++) {
//					if (node_pos==pos_list[i]) {
//						MatrixCursor.RowBuilder add1 = mc.newRow();
//						add1.add(key);
//						add1.add(value);
//						break;
//					}
//				}
//
//			}
//			lock.unlock();
//			return mc;
//
//		}//return of @


		if (selection.equals("*")|| selection.equals("\"*\"")){//host

			Mes_center mess = new Mes_center();
			Integer mess_id=mess.apply_mes();
			Ring rs= new Ring();

//			PortNum PN=new PortNum();
//			Integer myself=PN.get_port_num();
//			int port_t=rs.return_prev(myself);
//			test_alive ts=new test_alive(port_t);
//			while(!ts.is_alive()){
//				port_t=rs.return_prev(myself);
//				ts=new test_alive(port_t);
//			}
//			port_t=rs.return_prev(port_t);
//			while(!ts.is_alive()){
//				port_t=rs.return_prev(port_t);
//				ts=new test_alive(port_t);
//			}
//
//
//
//
//			String exc="query*--"+myself.toString()+"--"+mess_id.toString();
//			Thread t = new Thread(new Client_Thread(exc, port_t));
//			t.start();
//
//			while(!mess.is_returned(mess_id)){
//
//				try {
//					Thread.sleep(100);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				mess=new Mes_center();
//				//System.out.println("!!!!!!!!!!waiting!!!!!!!!!!!!!!!!");
//			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}



			recover rc=new recover();

			int n=0;
			while (rc.is_process_back() && rc.need_recovering()) {
				Message msg = new Message();
				msg.obj = " still recovering!!!";
				Tv_Handler th = new Tv_Handler();
				th.get_handler().sendMessage(msg);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				rc = new recover();
				n++;
				if (n>=10){
					break;
				}
			}

			//String[] return_val=mess.get_mes(mess_id).split("--");
			String[] row_name = {"key", "value"};
			MatrixCursor mc = new MatrixCursor(row_name);




			Iterator iter = cachemap.key_value.entrySet().iterator();



			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				Object key = entry.getKey();
				Object value = entry.getValue();





				MatrixCursor.RowBuilder add1 = mc.newRow();
				add1.add(key.toString());
				add1.add(value.toString());


			}


			iter = cachemap.get_back_up().entrySet().iterator();

			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				Object key = entry.getKey();
				Object value = entry.getValue();
                if (!cachemap.key_value.containsKey(key.toString()))


				{

					MatrixCursor.RowBuilder add1 = mc.newRow();
					add1.add(key.toString());
					add1.add(value.toString());
				}


			}


			lock.unlock();

			try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			return mc;




		}


		if (selection.contains("*") && selection.contains("--")){ //not host
			String[] temp=selection.split("--");
			Integer t_portnum=Integer.valueOf(temp[1]);
			Integer t_mesnum=Integer.valueOf((temp[2]));

			Map<String,?> all_out = sp.getAll();

			for(Map.Entry<String,?> entry : all_out.entrySet()) {
				String key=entry.getKey().toString();
				String value=entry.getValue().toString();
				String exc_1="return*--"+key+"--"+value+"--"+t_mesnum.toString();
				Thread t = new Thread(new Client_Thread(exc_1, t_portnum));
				t.start();
				try {
					t.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String fin_mes="done*--"+t_mesnum.toString();
			Thread t = new Thread(new Client_Thread(fin_mes, t_portnum));
			t.start();
			lock.unlock();
            return null;
		}
        // end of query *



		if (!selection.contains("--")) {// begin of host query



//			String value=null;
//			value = cachemap.key_value.get(selection);
//
//
//			if (value == null) {
//				value = cachemap.get_back_up().get(selection);
//			}
//			else {
//				String[] row_name = {"key", "value"};
//				MatrixCursor mc = new MatrixCursor(row_name);
//				MatrixCursor.RowBuilder add1 = mc.newRow();
//				add1.add(selection);
//				add1.add(value);
//				lock.unlock();
//				return mc;
//
//			}
//
//			if (value!=null) {
//				String[] row_name = {"key", "value"};
//				MatrixCursor mc = new MatrixCursor(row_name);
//				MatrixCursor.RowBuilder add1 = mc.newRow();
//				add1.add(selection);
//				add1.add(value);
//				lock.unlock();
//				return mc;
//
//			}


			Mes_center mess = new Mes_center();
			Integer mess_ind = mess.apply_mes();

			Ring rs = new Ring();
			int port_tail = rs.find_tail();
			PortNum PN=new PortNum();
			Integer myself=PN.get_port_num();

            String exc="query--"+selection+"--"+myself.toString()+"--"+mess_ind.toString();

//			try {
//				Thread.sleep(250);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}


//			Integer key_pos=rs.return_part_ind(selection);
//			int[] pos_list={0,0,0};
//			int[] port_num={0,0,0};
//
//			pos_list[0]=key_pos;
//			port_num[0]=rs.port_list[pos_list[0]];
//
//			if (pos_list[0]+1>4){
//				pos_list[1]=0;
//
//			}
//			else{
//				pos_list[1]=pos_list[0]+1;
//			}
//
//			port_num[1]=rs.port_list[pos_list[1]];
//
//			if (pos_list[1]+1>4){
//				pos_list[2]=0;
//			}
//			else{
//				pos_list[2]=pos_list[1]+1;
//			}
//
//			port_num[2]=rs.port_list[pos_list[2]];
			Integer[] port_num1={5554,5556,5558,5560,5562};
			//new Send_message(exc,myself).exc();
//			Integer next_down;
//            if (myself==5562){
//				next_down=5554;
//			}
//			else{
//				next_down=myself+2;
//			}
			for (int i=0;i<5;i++){
//                        if (!next_down.equals(port_num1[i])) {
							Thread t = new Thread(new Client_Thread(exc, port_num1[i]));
							t.start();
//						}


			}





//			Message msg = new Message();
//			msg.obj = "apply message index "+ mess_ind;
//			Tv_Handler th = new Tv_Handler();
//			th.get_handler().sendMessage(msg);

			while(!mess.is_returned(mess_ind)){

				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				mess=new Mes_center();
				System.out.println("!!!!!!!!!!waiting!!!!!!!!!!!!!!!!");
			}

			String return_val=mess.get_mes(mess_ind);
					//cachemap.key_value.get(selection);

			String[] row_name = {"key", "value"};
			MatrixCursor mc = new MatrixCursor(row_name);
			MatrixCursor.RowBuilder add1 = mc.newRow();
			add1.add(selection);
			add1.add(return_val);

			lock.unlock();
			return mc;

		}


		if (selection.contains("--")){ // begin of tail for return query

			String[] key=selection.split("--");
			String real_key=key[0];
			String return_mess_ind=key[2];
			Integer return_address=Integer.valueOf(key[1]);

			sp=this.getContext().getSharedPreferences(PREFS_NAME, 0);
			String out_avd=sp.getString(real_key, null);
			String output_key=real_key;
			String value=out_avd;

			String exc="return_query--"+value+"--"+return_mess_ind;
			Thread t = new Thread(new Client_Thread(exc, return_address));
			t.start();

			lock.unlock();
			return null;


		}






		return null;

	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
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

	public void dump_old(){
		sp=this.getContext().getSharedPreferences(PREFS_NAME, 0);
		edit=sp.edit();
		edit.clear();
		edit.commit();
	}
}
