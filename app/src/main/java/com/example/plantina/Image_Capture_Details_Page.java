package com.example.plantina;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.tensorflow.lite.Interpreter;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.common.model.RemoteModelManager;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.automl.AutoMLImageLabelerLocalModel;
import com.google.mlkit.vision.label.automl.AutoMLImageLabelerOptions;
import com.google.mlkit.vision.label.automl.AutoMLImageLabelerRemoteModel;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Image_Capture_Details_Page  extends AppCompatActivity {
    private static final String TAG = "";
    private Button takeImage, nextbutton;
    private ImageView imageView;
    private TextView plantname;
    private ProgressDialog progressDialog;

    private static  final int ACCESS_FILE = 10;
    private static  final int PERMISSION_FILE = 20;
    private Uri imageuri;

    AutoMLImageLabelerRemoteModel remoteModel =
            new AutoMLImageLabelerRemoteModel.Builder("Plants_202092104923").build();

    DownloadConditions downloadConditions = new DownloadConditions.Builder()
            .requireWifi()
            .build();

    AutoMLImageLabelerLocalModel localModel =
            new AutoMLImageLabelerLocalModel.Builder()
                    .setAssetFilePath("manifest.json")
                    .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_capture_details_page);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Plant Capture");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nextbutton = findViewById(R.id.NextButon);
        imageView = findViewById(R.id.plantimageView);
        plantname = findViewById(R.id.plantpredictionname);
        takeImage = findViewById(R.id.takeimageButton);
        plantname.setMovementMethod(new ScrollingMovementMethod());

        progressDialog = new ProgressDialog(this);
        takeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(Image_Capture_Details_Page.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(Image_Capture_Details_Page.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_FILE);

                }else {

                    Intent intent=new Intent();
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent,"Select Picture"),ACCESS_FILE);

                }
            }
        });



        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlantPredictedName();
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACCESS_FILE && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            RemoteModelManager.getInstance().download(remoteModel, downloadConditions);
            setLabelFromLocalModel(uri);
            plantname.setText("");
            imageView.setImageURI(uri);
        }

    }

    private void setLabelFromLocalModel(Uri uri) {
        showProgressDialog();
        AutoMLImageLabelerOptions options = new AutoMLImageLabelerOptions.Builder(localModel)
                .setConfidenceThreshold(0.0f).build();
        ImageLabeler labeler = ImageLabeling.getClient(options);
        try {
            InputImage image = InputImage.fromFilePath(Image_Capture_Details_Page.this, uri);
            processImageLabeler(labeler,image);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void processImageLabeler(ImageLabeler labeler, InputImage image) {
        labeler.process(image)
                .addOnSuccessListener(new OnSuccessListener<List<ImageLabel>>() {
                    @Override
                    public void onSuccess(List<ImageLabel> labels) {
                        // Task completed successfully
                            progressDialog.dismiss();
                        for (ImageLabel label : labels) {
                            String text = label.getText();
                            float confidence = label.getConfidence();
                            int index = label.getIndex();
                            plantname.append(text+ ":"+ (""+confidence * 100).subSequence(0,4)+"%"+"\n");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Task failed with an exception
                        Toast.makeText(Image_Capture_Details_Page.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private  void showProgressDialog(){
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void PlantPredictedName(){
        String name = plantname.getText().toString();
        String[] splitname = name.split(":");
        String getfirst=splitname[0];

        Intent intent = new Intent(Image_Capture_Details_Page.this, Soil_Details_Page.class);
        intent.putExtra("Prediction", getfirst);
        startActivity(intent);
    }
}
