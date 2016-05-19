package edu.scu.sgoyal.photonotes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;

import java.io.FileOutputStream;

/**
 * Created by shubhamgoyal on 5/18/16.
 */
public class Thumbify
{
    static public void generateThumbnail(String imgFile, String thumbFile)
    {
        try
        {
            Bitmap picture = BitmapFactory.decodeFile(imgFile);
            Bitmap resized = ThumbnailUtils.extractThumbnail(picture, 120, 120);
            FileOutputStream fos = new FileOutputStream(thumbFile);
            resized.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            fos.flush();
            fos.close();
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
