package edu.scu.sgoyal.photonotes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;


/**
 * Created by Qi on 5/17/2016.
 */
public class photoDetailActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener
{
    boolean isPlaying = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        TextView textView = (TextView) findViewById(R.id.textView);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        final Button playAudio = (Button) findViewById(R.id.button5);

        Bundle bundle = getIntent().getExtras();
        textView.setText(bundle.getString("currentCaption"));

        String filePath = bundle.getString("currentPath");
        imageView.setImageURI(Uri.parse(filePath));
        Bitmap img = BitmapFactory.decodeFile(filePath);
        imageView.setImageBitmap(img);

        final String mFileName = bundle.getString("audioPath");
        //Toast.makeText(getApplicationContext(), mFileName, Toast.LENGTH_LONG).show();

        if(mFileName.equals("null"))
        {
            playAudio.setVisibility(View.INVISIBLE);
            //Toast.makeText(getApplicationContext(), "No audio found", Toast.LENGTH_SHORT).show();
        }

        playAudio.setOnClickListener(new View.OnClickListener()
        {


            @Override
            public void onClick(View v)
            {
                MediaPlayer mPlayer = new MediaPlayer();

                mPlayer.setOnCompletionListener(photoDetailActivity.this);
                if (isPlaying) {
                    try {

                        mPlayer.setDataSource(mFileName);
                        mPlayer.prepare();
                        mPlayer.start();
                    } catch (IOException e) {
                        Log.e("media", "prepare() failed");
                    }
                   // playAudio.setText("Stop Audio");


                } else {

                    mPlayer.stop();
                    playAudio.setText("Play Audio");

                }
                isPlaying = !isPlaying;

            }
        });


        final String lat = bundle.getString("lat");
        final String lon = bundle.getString("lon");
        Button mapButton = (Button) findViewById(R.id.button4);

        if(lat.equals("null") || lon.equals("null"))
        {
            mapButton.setVisibility(View.INVISIBLE);
        }
        mapButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(photoDetailActivity.this, MapsActivity.class);
                intent.putExtra("lat", lat);
                intent.putExtra("lon", lon);
                startActivity(intent);

                Toast.makeText(getApplicationContext(), lat + " " + lon, Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public void onCompletion(MediaPlayer mp)
    {
        final Button playAudio = (Button) findViewById(R.id.button5);
        isPlaying = true;
        playAudio.setText("Play Audio");

    }
}
