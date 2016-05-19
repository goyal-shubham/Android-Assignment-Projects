package edu.scu.sgoyal.photonotes;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        return LayoutInflater.from(context).inflate(R.layout.custom_row, parent, false);
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

//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//        String thumbnailPath = storageDirectory.getAbsolutePath() + "/" + timeStamp + ".jpg";
//
//        thumbify(path, thumbnailPath );
//
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inSampleSize = 10;
//        Bitmap bitmap = BitmapFactory.decodeFile(thumbnailPath, options);

        ((TextView)view.findViewById(R.id.textView2)).setText(caption);
        ((ImageView)view.findViewById(R.id.imageView2)).setImageBitmap(img);

    }

    static public void thumbify(String imgFile, String thumbFile) {
        try {
            Bitmap picture = BitmapFactory.decodeFile(imgFile);
            Bitmap resized = ThumbnailUtils.extractThumbnail(picture, 120, 120);
            FileOutputStream fos = new FileOutputStream(thumbFile);
            resized.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
