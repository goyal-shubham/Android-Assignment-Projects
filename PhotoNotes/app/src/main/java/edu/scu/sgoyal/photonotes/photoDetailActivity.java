package edu.scu.sgoyal.photonotes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Qi on 5/17/2016.
 */
public class photoDetailActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        TextView textView = (TextView) findViewById(R.id.textView);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        Bundle bundle = getIntent().getExtras();

        textView.setText(bundle.getString("currentCaption"));

        String filePath = bundle.getString("currentPath");
        imageView.setImageURI(Uri.parse(filePath));

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 10;
        Bitmap img = BitmapFactory.decodeFile(filePath, options);

        imageView.setImageBitmap(img);
    }
}
