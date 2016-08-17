package edu.buffalo.cse.cse486586.simpledynamo;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;

/**
 * Created by user on 4/20/16.
 */
public class Thread_cp implements Runnable {
    private String mes;
    private ContentResolver rs;
    private static final String KEY_FIELD = "key";
    private static final String VALUE_FIELD = "value";
    private final Uri mUri;


    @Override
    public void run() {

        System.out.println("into Thread");
        String[] exc=mes.split("--");
        System.out.println(exc[0]);

        if (exc[0].equals("insert")){
            ContentValues cv = new ContentValues();
            cv.put(KEY_FIELD, exc[1]);
            cv.put(VALUE_FIELD, exc[2] + "--rep" + "--" + exc[4] + "--" + exc[5]);
            System.out.println(exc[2]);
            new cache().getlock();
            direct_return dr=new direct_return();
            dr.insert(cv);
            new cache().unlock();


        }

        if (exc[0].equals("query")){
            PortNum PN=new PortNum();
            int myself=PN.get_port_num();
            int tport=Integer.valueOf(exc[2]);



                 direct_return dr=new direct_return();
                 dr.find(exc[1]+"--"+exc[2]+"--"+exc[3]);


        }


        if (exc[0].equals("query*")){
            PortNum PN=new PortNum();
            int myself=PN.get_port_num();
            int tport=Integer.valueOf(exc[1]);
            System.out.println(myself);
            System.out.println(tport);




                direct_return dr=new direct_return();
                System.out.println("into direct_return");
                dr.query_all("*--" + exc[1] + "--" + exc[2]);


        }

        if (exc[0].equals("delete")){
            String selection="--"+exc[1];
//            rs = new Get_context().get_context().getContentResolver();
//            String[] ss={selection};
//            rs.delete(mUri, selection, null);
            cache cachemap=new cache();
            cachemap.delete(exc[1]);
        }

        if (exc[0].equals("delete*")){
            String selection="--*";
            rs = new Get_context().get_context().getContentResolver();
            String[] ss={selection};
            rs.delete(mUri, selection, null);
        }



    }

    public Thread_cp(String Rec){
        mes=Rec;
        rs=new Get_context().get_context().getContentResolver();
        mUri = buildUri("content", "edu.buffalo.cse.cse486586.simpledynamo.provider");
    }

    private Uri buildUri(String scheme, String authority) {
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.authority(authority);
        uriBuilder.scheme(scheme);
        return uriBuilder.build();
    }
}
