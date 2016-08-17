package edu.buffalo.cse.cse486586.simpledynamo;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;


/**
 * Created by user on 4/19/16.
 */
public class linkClickListener implements View.OnClickListener {

    private final TextView mTextView;
    private final ContentResolver mContentResolver;
    private final Uri mUri;

    public linkClickListener(TextView _tv, ContentResolver _cr) {
        mTextView = _tv;
        mContentResolver = _cr;
        mUri = buildUri("content", "edu.buffalo.cse.cse486586.simpledynamo.provider");
        //mContentValues = initTestValues();
    }

    private Uri buildUri(String scheme, String authority) {
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.authority(authority);
        uriBuilder.scheme(scheme);
        return uriBuilder.build();
    }
    @Override
    public void onClick(View v) {
        List<Integer> portList=new ArrayList<Integer>();
        portList.add(5554);
        portList.add(5556);
        portList.add(5558);
        portList.add(5560);
        portList.add(5562);

        PortNum pp=new PortNum();
        String ps=pp.get_port_num().toString();
        System.out.println(ps);
        String joinmes="join--"+ps;
        for (int i=0;i<5;i++) {
            Thread t = new Thread(new Client_Thread(joinmes, portList.get(i) ));
            t.start();
        }


    }



}
