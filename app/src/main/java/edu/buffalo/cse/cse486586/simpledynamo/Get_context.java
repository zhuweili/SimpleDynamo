package edu.buffalo.cse.cse486586.simpledynamo;

import android.content.Context;

/**
 * Created by user on 4/20/16.
 */
public class Get_context {
    public static Context mycontext;

    public void set_context(Context context){
        mycontext=context;
    }

    public Context get_context(){
        return mycontext;
    }
}
