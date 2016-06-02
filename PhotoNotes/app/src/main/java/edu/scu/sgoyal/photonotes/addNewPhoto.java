package edu.scu.sgoyal.photonotes;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class addNewPhoto extends AppCompatActivity implements MediaPlayer.OnCompletionListener,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener
{
    //View Elements
    Button takePhoto;
    Button savePhoto;
    EditText caption;

    //For Media Recorder
    private MediaRecorder mRecorder = null;
    private MediaPlayer mPlayer = null;
    boolean isPlaying = true;

    //For Shake Changes
    private SensorManager mSensorManager;
    private ShakeListner mSensorListener;

    //DB object
    public SQLiteDatabase myDB;
    Uri imageUri;
    String imagePath = "";
    String longitude = null;
    String latitude = null;
    private static String audioPath;


    //Maps Activity
    private GoogleApiClient mGoogleApiClient;
    Location mLastLocation;

    //Canvas Elements
    CanvasView mCanvasView;

    @Override

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_photo);

        //acquiring run time permissions
        acquireRunTimePermissions();

        audioPath = null;

        takePhoto = (Button) findViewById(R.id.button);
        savePhoto = (Button) findViewById(R.id.button2);
        caption = (EditText) findViewById(R.id.editText);


        //Working with maps

        //Initializing Google API Client
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }


        //New Photo Button Click Listener
        takePhoto.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) == null) {
                    Toast.makeText(getApplicationContext(), "Cannot take pictures on this device!", Toast.LENGTH_SHORT).show();
                    return;
                }

                getOutputFileName();
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                startActivityForResult(intent, 1);

//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//                if (intent.resolveActivity(getPackageManager()) == null)
//                {
//                    Toast.makeText(getApplicationContext(), "Not able to click pictures", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                getOutputFileName();
//
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//
//                startActivityForResult(intent, 1);
            }
        });

        //Setting up canvas for drawing
        mCanvasView = (CanvasView) findViewById(R.id.signature_canvas);
        mCanvasView.setDrawingCacheEnabled(true);


        //Setting up sensor manager to clear the canvas
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorListener = new ShakeListner();
        mSensorListener.setOnShakeListener(new ShakeListner.OnShakeListener() {

            public void onShake() {

                if (mCanvasView.cleanCanvas == false) {
                    mCanvasView.clearCanvas();
                }
            }

        });


        //Recording audio saving data
        final TextView statusAudio = (TextView) findViewById(R.id.textView4);
        final Button recordButton = (Button) findViewById(R.id.button3);
        recordButton.setOnClickListener(new View.OnClickListener() {
            boolean mStartRecording = true;

            @Override
            public void onClick(View v) {
                if (mStartRecording) {
                    recordButton.setText("Stop recording");
                    statusAudio.setText("Recording...");
                    startRecording();
                } else {
                    recordButton.setText("New recording");
                    statusAudio.setText(" ");
                    stopRecording();
                }
                mStartRecording = !mStartRecording;

            }
        });



        //Playing the recorded audio
        final Button playButton = (Button) findViewById(R.id.replay);
        playButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(audioPath != null)
                {
                    mPlayer = new MediaPlayer();
                    mPlayer.setOnCompletionListener(addNewPhoto.this);
                        try {

                            statusAudio.setText("Playing...");
                            mPlayer.setDataSource(audioPath);
                            mPlayer.prepare();
                            mPlayer.start();
                        } catch (IOException e) {
//                            Log.e("media", "prepare() failed");
                        }

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "No Audio Found", Toast.LENGTH_SHORT).show();
                }
            }
        });



        //To save new photo note
        savePhoto.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(caption.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Caption can't be empty", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    addPhoto();
                }
            }
        });
    }


    //Main method for Saving photo in SQLlite
    private void addPhoto()
    {

        String caption1 = caption.getText().toString();
        File imageFilePath = new File(imagePath);
        String photoPath = imageFilePath.getAbsolutePath().toString();

//        LocationRequest mLocationRequest = new LocationRequest();
//        mLocationRequest.setInterval(2000);
//        mLocationRequest.setFastestInterval(500);
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, addNewPhoto.this);

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, addNewPhoto.this);



        if (!photoPath.equals("/"))
        {
            //Drawing over clicked image
            mCanvasView.setDrawingCacheEnabled(true);
            Bitmap b = mCanvasView.getDrawingCache();
            try{
                b.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(imagePath));
            }
            catch(FileNotFoundException e) {
                e.printStackTrace();
            }


            if(mLastLocation != null)
            {
                latitude = String.valueOf(mLastLocation.getLatitude());
                longitude = String.valueOf(mLastLocation.getLongitude());

                Log.i("location", String.valueOf(latitude));
                Log.i("location", String.valueOf(longitude));

            }
            else
            {
                Toast.makeText(getApplicationContext(),"Location not available" ,Toast.LENGTH_SHORT).show();
            }

            if(audioPath == null)
            {
                Toast.makeText(getApplicationContext(),"No Audio Recorded" ,Toast.LENGTH_SHORT).show();
            }
            Log.i("location", String.valueOf(latitude));
            Log.i("location", String.valueOf(longitude));


            photoPath = imageFilePath.getAbsolutePath().toString();

            displayMsg.toast(this, "Photo Note Saved!!");

            //Inserting all the values in database
            myDB = this.openOrCreateDatabase("shubham", MODE_PRIVATE, null);
            myDB.execSQL("INSERT INTO PhotoNotes (caption, imagePath, audioPath, lat, lan) VALUES ( '" + caption1 + "' , '" + photoPath + "' , '" + audioPath + "' , '" + latitude + "' , '" + longitude + "');");
            finish();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Image needs to be captured", Toast.LENGTH_SHORT).show();
        }

    }


    //Create output path name for Image file
    private void getOutputFileName()
    {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        imagePath = storageDirectory.getAbsolutePath() + "/" + timeStamp + ".jpg";

        //displayMsg.toast(this, imagePath);

        File newFile = new File(imagePath);
        imageUri = Uri.fromFile(newFile);
    }

    //Main method for recording audio note
    private void startRecording()
    {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        audioPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        audioPath += "/" + timeStamp + ".3gp";
        // Toast.makeText(getApplicationContext(),audioPath, Toast.LENGTH_LONG).show();

        mRecorder.setOutputFile(audioPath);
        try
        {
            mRecorder.prepare();
        }
        catch (IOException e)
        {
            //Log.e(LOG_TAG, "prepare() failed");
        }
        mRecorder.start();

    }

    //Method for Stopping the record
    private void stopRecording()
    {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

    //Will be called after getting response from Camera
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode != 1 || resultCode != RESULT_OK)
        {
            return;
        }

        Bitmap img = BitmapFactory.decodeFile(imagePath);
        Toast.makeText(getApplicationContext(), imagePath, Toast.LENGTH_LONG).show();
        final Drawable d = new BitmapDrawable(img);
        mCanvasView = (CanvasView) findViewById(R.id.signature_canvas);
        mCanvasView.setDrawingCacheEnabled(true);
        mCanvasView.setBackground(d);

    }

    //Getting run time permission if already not there
    //Can be omitted as minSDK is 22
    private void acquireRunTimePermissions()
    {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    111);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode != 111) return;
        if (grantResults.length >= 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "Great! We have the permission!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Cannot write to external storage! App will not work properly!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp)
    {
        Button playButton = (Button) findViewById(R.id.replay);
        playButton.setText("Replay");
        final TextView label = (TextView) findViewById(R.id.textView4);
        label.setText(" ");
        isPlaying = true;
        mPlayer.stop();


    }

    @Override
    public void onStart()
    {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        mGoogleApiClient.disconnect();

    }

    @Override
    public void onConnected(Bundle bundle)
    {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    }

    @Override
    public void onConnectionSuspended(int i)
    {

    }

    @Override
    public void onLocationChanged(Location location)
    {
//        mLastLocation = location;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult)
    {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }

}


