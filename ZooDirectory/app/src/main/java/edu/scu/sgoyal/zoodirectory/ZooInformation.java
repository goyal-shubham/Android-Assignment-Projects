package edu.scu.sgoyal.zoodirectory;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class ZooInformation extends MenuActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoo_information);


        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String num = "tel:1800-123-1234";
                Intent myintent = new Intent(Intent.ACTION_CALL, Uri.parse(num));
                startActivity(myintent);
            }
        });

    }


}
