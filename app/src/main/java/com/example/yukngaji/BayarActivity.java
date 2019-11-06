package com.example.yukngaji;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yukngaji.setting.UserPreference;
import com.example.yukngaji.ui.Utils.FilePath;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.Objects;

public class BayarActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int PICK_FILE_REQUEST = 1;
    private static final String TAG = MainActivity.class.getSimpleName();
    private String selectedFilePath;
    ImageView ivAttachment,gambar;
    Button bUpload;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setContentView(R.layout.activity_bayar);
        ivAttachment = findViewById(R.id.ivAttachment);
        gambar=findViewById(R.id.tampilgambar);
        bUpload = findViewById(R.id.b_upload);
        ivAttachment.setOnClickListener(this);
        bUpload.setOnClickListener(this);
        setTitle("Bayar");
    }

    @Override
    public void onClick(View v) {
        if(v== ivAttachment){

            //on attachment icon click
            showFileChooser();
        }
        if(v== bUpload){

            //on upload button Click
            if(selectedFilePath != null){
                dialog = ProgressDialog.show(BayarActivity.this,"","Uploading File...",true);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //creating new thread to handle Http Operations
                        File imgFile = new File(selectedFilePath);
                        if (imgFile.exists()) {
                            uploadFile(selectedFilePath);
                        }
                    }
                }).start();
            }else{
                Toast.makeText(BayarActivity.this,"Please choose a File First",Toast.LENGTH_SHORT).show();
            }

        }
    }
    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent,"Choose File to Upload.."),PICK_FILE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == PICK_FILE_REQUEST){
                if(data == null){
                    //no data present
                    return;
                }


                Uri selectedFileUri = data.getData();
                selectedFilePath = FilePath.getPath(this,selectedFileUri);
                Log.i(TAG,"Selected File Path:" + selectedFilePath);

                if(selectedFilePath != null && !selectedFilePath.equals("")){
                    File imgFile = new  File(selectedFilePath);

                    if(imgFile.exists()){
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        gambar.setImageBitmap(myBitmap);

                    }
                }else{
                    Toast.makeText(this,"Cannot upload file to server",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        }
    }

    public void uploadFile(final String selectedFilePath) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        Uri file = Uri.fromFile(new File(selectedFilePath));
        StorageReference riversRef = storageRef.child("images/" + file.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(file);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(BayarActivity.this, "File Not Found" + exception, Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                DatabaseReference reference;
                reference = FirebaseDatabase.getInstance().getReference();
                UserPreference preference = new UserPreference(BayarActivity.this);
                final String[] parts = selectedFilePath.split("/");
                final String fileName = parts[parts.length - 1];
                reference.child("Murid").child(preference.getUid()).child("gambar").setValue("https://firebasestorage.googleapis.com/v0/b/yoi-bro.appspot.com/o/images%2F" + fileName + "?alt=media");
                preference.setTunggu(true);
                Intent intent = new Intent(BayarActivity.this, TungguKonfirmasi.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
