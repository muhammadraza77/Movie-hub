package com.example.muhammadrazavasnan.moviehub;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.muhammadrazavasnan.moviehub.MainActivity.userid;

/**
 * Created by Muhammad Raza Vasnan on 3/17/2019.
 */

public class invite_fragment extends Fragment {
    DatabaseReference md;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.invite_fragment,container,false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInsntanceState) {
        super.onViewCreated(view, savedInsntanceState);
        RelativeLayout r1=(RelativeLayout)view.findViewById(R.id.upgrade);
        RelativeLayout r2=(RelativeLayout)view.findViewById(R.id.inviteFriend);
        md=FirebaseDatabase.getInstance().getReference();

        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent ();
                i.setData(Uri.parse("email"));
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT,"Hello Mrs.X Please download this new awsome app !!!"+"https://play.google.com/store/apps/details?id=com.google.android.youtube&hl=en");
                Intent choser = Intent.createChooser(i,"Send Via ");
                startActivity(choser);
            }
        });

        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());
                builder.setTitle("Give Payment Details");
                View viewInflated = LayoutInflater.from(getActivity()).inflate(R.layout.dialog, (ViewGroup) view.getRootView(), false);
                builder.setView(viewInflated);
                final EditText input = (EditText) viewInflated.findViewById(R.id.input);
                builder.setMessage("Enter Crediet Card Number to make payment").setCancelable(false)
                        .setNegativeButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //db
                                String f=FirebaseAuth.getInstance().getCurrentUser().getUid();
                                String code = input.getText().toString();
                                md.child("users").child(f).child("type").setValue("Exclusive");
                                Toast.makeText(getActivity(), "Congratulations!!!Welcome to our premium community", Toast.LENGTH_SHORT).show();

                            }
                        }).setPositiveButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });
    }

}
