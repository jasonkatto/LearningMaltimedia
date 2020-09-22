package jkatto.cse.edu.learningmaltimedia;

import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    Button startPlaying,stopPlaying,startRecording,stopRecording=null;
    EditText fileName = null;
    String songPath = null;
    MediaRecorder mediaRecorder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        startPlaying= findViewById(R.id.startRecording);
        stopPlaying= findViewById(R.id.stopRecording);
        startRecording= findViewById(R.id.startPlaying);
        stopRecording= findViewById(R.id.stopRecording);
        fileName =findViewById(R.id.fileName);



        stopRecording.setEnabled(false);
        startPlaying.setEnabled(false);
        stopPlaying.setEnabled(false);


        startRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String externalStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath();
                songPath = externalStoragePath + "/" + fileName.getText().toString() + "3gp";

                if (checkPermission())
                PrepareMedia();
                mediaRecorder.setOutputFile(songPath);
                try {
                    Toast.makeText((v.getContext()), "startRecording", Toast.LENGTH_LONG);
                    startRecording.setEnabled(false);
                    startRecording.setEnabled(true);
                }
                catch (IOException e ){
                    e.printStackTrace();
                    Toast.makeText((v.getContext()), " cant startRecording", Toast.LENGTH_LONG);

                }
            }
        });

        stopRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaRecorder.stop();
                mediaRecorder.release();
                startRecording.setEnabled(true);
                startRecording.setEnabled(false);
                startPlaying.setEnabled(true);
                stopPlaying.setEnabled(false);
                Toast.makeText((v.getContext()), "Stopped Recording", Toast.LENGTH_LONG);
            }
        });


        startPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(songPath);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException){
                    e.printStackTrace();
                }
            }
        });





        stopPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }


    private void prepareMedia(){
        mediaRecorder= new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);

    }

    public Boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        int result=ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        return (result == PackageManager.PERMISSION_GRANTED) && (result =PackageManager.PERMISSION_GRANTED);


    }



    public void requestPermission(){
        ActivityCompat.requestPermissions(this, new String
                [] WRITE_EXTERNLA_STORAGE , RECORD_AUDIO) ;
    }

    public void onRequestPermissionResult(int requestCode,
                                          String permissions[], int[] grantResults){

        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    boolean StoragePermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (StoragePermission && RecordPermission) {
                        Toast.makeText(this, "Permission  Granted", Toast.LENGTH_LONG);

                    } else {
                        Toast.makeText(this, "Permission  Denied", Toast.LENGTH_LONG);

                    }
                }
            default:
                Toast.makeText(this, "Permission  Granted", Toast.LENGTH_LONG);
                break;
        }
    }
}
