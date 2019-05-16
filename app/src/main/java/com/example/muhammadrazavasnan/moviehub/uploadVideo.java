package com.example.muhammadrazavasnan.moviehub;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import permissions.dispatcher.NeedsPermission;

import static com.example.muhammadrazavasnan.moviehub.dashboard_fragment.userid;

public class uploadVideo extends AppCompatActivity {




    private Button chooseFile, uploadButton;
    private Uri videoUri;
    private VideoView videoView;
    private static final String VIDEO_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2;
    StorageReference videoRef;
    FirebaseStorage storage;
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    ProgressBar pb;
    static String url;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_video);


//        videoView = (VideoView) findViewById(R.id.videoView);
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        pb = (ProgressBar) findViewById(R.id.uploadProgressBar);
        pb.setProgress(0);
        uploadButton = (Button) findViewById(R.id.uploadButton) ;
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoUri != null)
                    uploadFile (videoUri);
                else
                    Toast.makeText(uploadVideo.this, "Choose a Video", Toast.LENGTH_SHORT).show();

            }
        });
        chooseFile = (Button) findViewById(R.id.chooseFile);
        chooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });

    }

    private void uploadFile(Uri videoUri) {
        String fileName = System.currentTimeMillis() + "";


        final StorageReference storageReference = storage.getReference();
        storageReference.child("VideoUploads").child(fileName).putFile(videoUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                        taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                url = uri.toString();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(uploadVideo.this, "Getting Url Failed", Toast.LENGTH_LONG).show();
                            }
                        });

                        EditText et=(EditText)findViewById(R.id.name);
                        EditText et1=(EditText)findViewById(R.id.description);
                        EditText et2=(EditText)findViewById(R.id.thumbnail);
                        EditText et3=(EditText)findViewById(R.id.ques);
                        EditText et4=(EditText)findViewById(R.id.name);

                        Toast.makeText(uploadVideo.this, "Upload Completed to: " + url, Toast.LENGTH_LONG).show();
                        DatabaseReference mData=FirebaseDatabase.getInstance().getReference();
                        String key = mData.child("videos").push().getKey();
                        mData.child("videos").child(key).child("name").setValue(et.getText().toString());
                        mData.child("videos").child(key).child("desc").setValue(et.getText().toString());
                        mData.child("videos").child(key).child("downvote").setValue(0);
                        mData.child("videos").child(key).child("upvote").setValue(0);
                        mData.child("videos").child(key).child("link").setValue(url);
                        mData.child("videos").child(key).child("thumbnail").setValue("https://images.unsplash.com/photo-1485846234645-a62644f84728?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
                        mData.child("videos").child(key).child("vid").setValue(key);
                        mData.child("videos").child(key).child("uploaderID").setValue(userid);


                        if(et.getText().toString().equals("Yes")){
                            mData.child("videos").child(key).child("special").setValue(0);
                        }else{
                            mData.child("videos").child(key).child("special").setValue(1);

                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Upload Fail", "Upload Fail :/");
                Toast.makeText(uploadVideo.this, "File Not Uploaded", Toast.LENGTH_SHORT).show();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                pb = (ProgressBar) findViewById(R.id.uploadProgressBar);
                pb.setProgress((int) progress);

            }
        });

    }

    private void showPictureDialog() {



        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select video from gallery",
                "Record video from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                chooseVideoFromGallary();
                                break;
                            case 1:
                                takeVideoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();

    }

    private void takeVideoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    private void chooseVideoFromGallary() {
        Intent galleryIntent = new Intent();
        galleryIntent.setType("video/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(galleryIntent, GALLERY);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("result",""+resultCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            Log.d("what","cancel");
            return;
        }
        if (requestCode == GALLERY) {
            Log.d("what","gale");
            if (data != null) {
                Uri contentURI = data.getData();
                videoUri = contentURI;
                Toast.makeText(this, "File Chosen", Toast.LENGTH_SHORT).show();

                String selectedVideoPath = getPath(contentURI);
                Log.d("VideoURI",videoUri.toString());
//

            }
            else
                Log.d("Video Data", "empty");


        } else if (requestCode == CAMERA) {
            Uri contentURI = data.getData();
            String recordedVideoPath = getPath(contentURI);
            Log.d("frrr",recordedVideoPath);

        }
    }




    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Video.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

}