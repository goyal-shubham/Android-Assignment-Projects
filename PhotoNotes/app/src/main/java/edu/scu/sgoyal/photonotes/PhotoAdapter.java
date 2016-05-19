package edu.scu.sgoyal.photonotes;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by shubhamgoyal on 5/18/16.
 */

public class PhotoAdapter extends CursorAdapter
{

    public PhotoAdapter(Context context, Cursor c, int flags)
    {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        return LayoutInflater.from(context).inflate(R.layout.cutome_row, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        String caption = cursor.getString(cursor.getColumnIndex("caption"));
        String path = cursor.getString(cursor.getColumnIndex("imagePath"));

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 10;
        Bitmap img = BitmapFactory.decodeFile(path, options);

        ((TextView)view.findViewById(R.id.textView2)).setText(caption);
        ((ImageView)view.findViewById(R.id.imageView2)).setImageBitmap (img);

    }

}
