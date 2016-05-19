package edu.scu.sgoyal.photonotes;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class addNewPhoto extends AppCompatActivity
{
    Button takePhoto;
    Button savePhoto;
    EditText caption;
    public SQLiteDatabase myDB;

    Uri imageUri;
    String imagePath = "";

    @Override

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_photo);


        takePhoto = (Button) findViewById(R.id.button);
        savePhoto = (Button) findViewById(R.id.button2);
        caption = (EditText) findViewById(R.id.editText);

        takePhoto.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (intent.resolveActivity(getPackageManager()) == null)
                {
                    Toast.makeText(getApplicationContext(), "Not able to click pictures", Toast.LENGTH_SHORT).show();
                    return;
                }

                getOutputFileName();

                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                startActivityForResult(intent, 1);
            }
        });


        savePhoto.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(caption.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "caption cannot be empty", Toast.LENGTH_SHORT).show();
                }


                else
                {
                    addPhoto();

                }
            }
        });
    }

    private void addPhoto()
    {
        String caption1 = caption.getText().toString();
        File imageFilePath = new File(imagePath);
        String photoPath = imageFilePath.getAbsolutePath().toString();

        if (!photoPath.equals("/"))
        {
            myDB = this.openOrCreateDatabase("shubham", MODE_PRIVATE, null);
            myDB.execSQL("INSERT INTO PhotoNotes (caption, imagePath) VALUES ( '" + caption1 + "' , '" + photoPath + "');");
            displayMsg.toast(this, "Photo Note Saved!!");
            finish();
        }
        else{
            Toast.makeText(getApplicationContext(), "image needs to be captured", Toast.LENGTH_SHORT).show();
        }

    }

    private void getOutputFileName()
    {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        imagePath = storageDirectory.getAbsolutePath() + "/" + timeStamp + ".jpg";

        displayMsg.toast(this, imagePath);

        File newFile = new File(imagePath);
        imageUri = Uri.fromFile(newFile);
    }
}
