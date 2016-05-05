package edu.scu.sgoyal.zoodirectory;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends MenuActivity{

    public final static String POSITION_ID = "Shubham";

    public static List<Animal> animals;

    public static boolean[] fav = new boolean[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        animals = new ArrayList<>();
        animals.add(new Animal("Fish", "fish.png"));
        animals.add(new Animal("Owl", "owl.png"));
        animals.add(new Animal("Turtle", "turtle.png"));
        animals.add(new Animal("Pig", "pig.png"));
        animals.add(new Animal("Tiger", "tiger.png"));

//
//        animals.add(new Animal("Panther", "panther.png" ));
//        animals.add(new Animal("Monkey","monkey.png"));
//        animals.add(new Animal("Bear", "bear.png"));
//        animals.add(new Animal("Mowgli", "mowgli.png"));
//        animals.add(new Animal("Lion", "lion.png"));

        ListView lv = (ListView) findViewById(R.id.listView);

        lv.setAdapter(new AnimalArrayAdapter(this, R.layout.animal_list_element, animals));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(position == animals.size() - 1){

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle(R.string.app_name);
                    builder.setIcon(R.mipmap.ic_launcher);
                    builder.setMessage(R.string.scare_message)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int id) {

                                    Intent intent = new Intent(MainActivity.this, DescriptionActivity.class);

                                    intent.putExtra(POSITION_ID, 4);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });

                    builder.create();
                    builder.show();
                }
                else
                {
                    Intent intent = new Intent(MainActivity.this, DescriptionActivity.class);
                    intent.putExtra(POSITION_ID, position);
                    startActivity(intent);
                }

            }
        });


    }


}
