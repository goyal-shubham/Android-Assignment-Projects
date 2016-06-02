package edu.scu.sgoyal.photonotes;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
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
//    RecyclerView rv;
//    RecycleViewAdapter rva;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        rv = (RecyclerView) findViewById(R.id.nameList);
        dbHelper = new PhotoDBHelper(this);
        cursor = dbHelper.fetchAll();
        ca = new PhotoAdapter(this, cursor, 0);

//        LinearLayoutManager llm = new LinearLayoutManager(this);
//        llm.setOrientation(LinearLayoutManager.VERTICAL);
//        rv.setLayoutManager(llm);
//
//        rva = new RecycleViewAdapter(this, cursor);
//        rv.setAdapter(rva);
//
//        TouchHelperCallback callback = new TouchHelperCallback(rva);
//        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
//        touchHelper.attachToRecyclerView(rv);

        list = (ListView) findViewById(R.id.listView);
        list.setAdapter(ca);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String path = cursor.getString(cursor.getColumnIndex("imagePath"));
                String caption = cursor.getString(cursor.getColumnIndex("caption"));
                String audioPath = cursor.getString(cursor.getColumnIndex("audioPath"));
                String lat = cursor.getString(cursor.getColumnIndex("lat"));
                String lon = cursor.getString(cursor.getColumnIndex("lan"));

                final Intent intent = new Intent(MainActivity.this, photoDetailActivity.class);
                intent.putExtra("currentPath", path);
                intent.putExtra("currentCaption", caption);
                intent.putExtra("audioPath", audioPath);
                intent.putExtra("lat", lat);
                intent.putExtra("lon", lon);
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
        displayMsg.toast(this , "Total Notes :  " + maxRecId);
    }

    @Override
    public void onResume(){
        super.onResume();
        dbHelper = new PhotoDBHelper(this);
        cursor = dbHelper.fetchAll();
        ca = new PhotoAdapter(this, cursor, 0);
//        rva = new RecycleViewAdapter(this, cursor);
//        rv = (RecyclerView) findViewById(R.id.nameList);
//        rv.setAdapter(rva);
        list = (ListView) findViewById(R.id.listView);
        list.setAdapter(ca);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        int id = item.getItemId();

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
