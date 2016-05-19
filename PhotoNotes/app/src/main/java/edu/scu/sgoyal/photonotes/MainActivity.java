package edu.scu.sgoyal.photonotes;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity
{

    PhotoAdapter ca;
    PhotoDBHelper dbHelper;
    Cursor cursor;
    ListView list;
    int maxRecId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new PhotoDBHelper(this);
        cursor = dbHelper.fetchAll();
        ca = new PhotoAdapter(this, cursor, 0);

        list = (ListView) findViewById(R.id.listView);
        list.setAdapter(ca);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String path = cursor.getString(cursor.getColumnIndex("imagePath"));
                String caption = cursor.getString(cursor.getColumnIndex("caption"));
                final Intent intent = new Intent(MainActivity.this, photoDetailActivity.class);
                intent.putExtra("currentPath", path);
                intent.putExtra("currentCaption", caption);
                startActivity(intent);
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, addNewPhoto.class);
                startActivity(intent);
            }
        });

        maxRecId = dbHelper.getMaxRecID();
        displayMsg.toast(this , "MacRecID is " + maxRecId);
    }

    @Override
    public void onResume(){
        super.onResume();
        dbHelper = new PhotoDBHelper(this);
        cursor = dbHelper.fetchAll();
        ca = new PhotoAdapter(this, cursor, 0);
        list = (ListView) findViewById(R.id.listView);
        list.setAdapter(ca);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {

            case R.id.action_add:
                Intent intent = new Intent(MainActivity.this, addNewPhoto.class);
                startActivity(intent);
                break;
            case R.id.action_uninstall:
                Uri packageURI = Uri.parse("package:edu.scu.sgoyal.photonotes");
                Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
                startActivity(uninstallIntent);
                break;
            default:
        }

        return super.onOptionsItemSelected(item);
    }
}
