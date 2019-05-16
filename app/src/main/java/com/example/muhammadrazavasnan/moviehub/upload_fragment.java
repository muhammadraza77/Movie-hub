package com.example.muhammadrazavasnan.moviehub;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import static com.example.muhammadrazavasnan.moviehub.MainActivity.con;
import static com.example.muhammadrazavasnan.moviehub.dashboard_fragment.type;

/**
 * Created by Muhammad Raza Vasnan on 3/17/2019.
 */

public class upload_fragment extends Fragment {
    TextView heading, subheading;
Button btn ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.upload_fragment,container,false);
    }
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        heading = (TextView) view.findViewById(R.id.headingtxt);
        subheading = (TextView) view.findViewById(R.id.subheadingtxt);
        btn = (Button)view.findViewById(R.id.btn);
//        Toast.makeText(getActivity(), ""+type, Toast.LENGTH_SHORT).show();
        if(type.equals("Basic")){
            heading.setText("Sorry Cant Proceedv= !");
            subheading.setText("Please Upgrade your account to join the hub of Movies <3");
            btn.setVisibility(View.INVISIBLE);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),uploadVideo.class);
                startActivity(i);
            }
        });

    }

}

