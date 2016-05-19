package edu.scu.sgoyal.photonotes;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by shubhamgoyal on 5/18/16.
 */
public class displayMsg
{
    public static void toast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

}
