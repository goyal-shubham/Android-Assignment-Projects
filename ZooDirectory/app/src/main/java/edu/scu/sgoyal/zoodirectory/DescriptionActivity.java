package edu.scu.sgoyal.zoodirectory;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class DescriptionActivity extends MenuActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        TextView textView = (TextView) findViewById(R.id.textView3);
        TextView textView2 = (TextView) findViewById(R.id.textView4);
        ImageView imageView = (ImageView) findViewById(R.id.imageView2);

        int[] id = {R.string.fish, R.string.owl,R.string.turtle, R.string.pig, R.string.tiger};

        Intent intent = getIntent();
        int position = intent.getIntExtra(MainActivity.POSITION_ID, 10);

        textView.setText(MainActivity.animals.get(position).getName().toUpperCase());

        try
        {
            String filename = MainActivity.animals.get(position).getFilename();
            InputStream inputStream = getResources().getAssets().open(filename);
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            imageView.setImageDrawable(drawable);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        String descptn = getResources().getString(id[position]);
        textView2.setText(descptn);

    }

}
