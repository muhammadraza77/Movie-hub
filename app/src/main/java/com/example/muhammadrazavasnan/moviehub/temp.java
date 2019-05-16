package com.example.muhammadrazavasnan.moviehub;
public class temp{

}

//
///**
// * Created by Muhammad Raza Vasnan on 3/17/2019.
// */
//package com.example.mujtaba.ecommerce;
//
//
//        import android.content.DialogInterface;
//        import android.content.Intent;
//        import android.graphics.drawable.Drawable;
//        import android.support.annotation.NonNull;
//        import android.support.v7.app.ActionBar;
//        import android.support.v7.app.AlertDialog;
//        import android.support.v7.app.AppCompatActivity;
//        import android.os.Bundle;
//        import android.support.v7.widget.AppCompatTextView;
//        import android.support.v7.widget.Toolbar;
//        import android.util.Log;
//        import android.view.Gravity;
//        import android.view.LayoutInflater;
//        import android.view.View;
//        import android.view.ViewGroup;
//        import android.view.WindowManager;
//        import android.widget.EditText;
//        import android.widget.ImageButton;
//        import android.widget.TextView;
//        import android.widget.Toast;
//
//        import com.google.android.gms.tasks.OnCompleteListener;
//        import com.google.android.gms.tasks.Task;
//        import com.google.firebase.FirebaseException;
//        import com.google.firebase.FirebaseTooManyRequestsException;
//        import com.google.firebase.auth.AuthCredential;
//        import com.google.firebase.auth.AuthResult;
//        import com.google.firebase.auth.FirebaseAuth;
//        import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
//        import com.google.firebase.auth.FirebaseUser;
//        import com.google.firebase.auth.PhoneAuthCredential;
//        import com.google.firebase.auth.PhoneAuthProvider;
//        import com.google.firebase.database.DataSnapshot;
//        import com.google.firebase.database.DatabaseError;
//        import com.google.firebase.database.DatabaseReference;
//        import com.google.firebase.database.FirebaseDatabase;
//        import com.google.firebase.database.ValueEventListener;
//
//        import java.util.ArrayList;
//        import java.util.concurrent.TimeUnit;
//
//        import static java.security.AccessController.getContext;
//
//public class register extends AppCompatActivity {
//
//    private ImageButton back,next;
//    EditText fname,lname,gender,dob,country,mobilenum,weight,height;
//    String fnames,lnames,genders,dobs,countrys,mobilenums,weights,heights;
//    private DatabaseReference mDatabase;
//    FirebaseAuth f;
//    String codeSent;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);
//        getSupportActionBar().hide();
//
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        f=FirebaseAuth.getInstance();
//
//        final String flag=getIntent().getStringExtra("AfterLogin");
//
//        fname=(EditText)findViewById(R.id.fname);
//        lname=(EditText)findViewById(R.id.reg2_lastName);
//        gender=(EditText)findViewById(R.id.reg2_gender);
//        dob=(EditText)findViewById(R.id.reg2_dataOfBirth);
//        country=(EditText)findViewById(R.id.reg2_country);
//        mobilenum=(EditText)findViewById(R.id.reg2_phoneNum);
//        weight=(EditText)findViewById(R.id.reg2_weight);
//        height=(EditText)findViewById(R.id.reg2_height);
//
//        back=(ImageButton)findViewById(R.id.backButton);
//        next=(ImageButton)findViewById(R.id.next);
//
//        if(flag.equals("true")){
//            loadData();
//        }
//
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//
//        next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//                fnames = fname.getText().toString().trim();
//                lnames = lname.getText().toString().trim();
//                genders = gender.getText().toString().trim();
//                dobs = dob.getText().toString().trim();
//                countrys = country.getText().toString().trim();
//                mobilenums = mobilenum.getText().toString().trim();
//                weights = weight.getText().toString().trim();
//                heights = height.getText().toString().trim();
//
//                if(flag.equals("true")){
//                    AlertDialog.Builder builder =new AlertDialog.Builder(register.this);
//                    builder.setMessage("You are About to Updatem Information. Are You Sure you want to continue").setCancelable(false)
//                            .setNegativeButton("YES", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    //db
//                                    walkerInfo a = new walkerInfo(f.getCurrentUser().getUid(), fnames, lnames, genders, dobs, countrys, mobilenums, weights, heights);
//                                    mDatabase.child("walker").child(f.getCurrentUser().getUid()).setValue(a);
//                                    startActivity(new Intent(register.this, workout.class));
//                                    finish();
//                                }
//                            }).setPositiveButton("NO", new DialogInterface.OnClickListener() {
//
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                            dialog.cancel();
//                        }
//                    });
//                    AlertDialog alertDialog = builder.create();
//                    alertDialog.show();
//                }
//                else{
//                    sendCode();
//                    AlertDialog.Builder builder =new AlertDialog.Builder(register.this);
//                    builder.setTitle("Confirm Account");
//                    View viewInflated = LayoutInflater.from(register.this).inflate(R.layout.dialog, (ViewGroup) v.getRootView(), false);
//                    builder.setView(viewInflated);
//                    final EditText input = (EditText) viewInflated.findViewById(R.id.input);
//                    builder.setMessage("Enter 6 Digit Code Sent To your Phone Number ?").setCancelable(false)
//                            .setNegativeButton("YES", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    //db
//                                    String code = input.getText().toString();
//
//                                    PhoneAuthCredential credential= PhoneAuthProvider.getCredential(codeSent,code);
//                                    AuthCredential tempCred=PhoneAuthProvider.getCredential(codeSent,code);
//                                    f.getCurrentUser().linkWithCredential(tempCred).addOnCompleteListener(register.this, new OnCompleteListener<AuthResult>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<AuthResult> task) {
//                                            if (task.isSuccessful()) {
//                                                FirebaseUser user = task.getResult().getUser();
//                                            } else {
//
//                                            }
//
//                                        }
//                                    });
//                                    signInWithPhoneAuthCredential(credential);
//
//                                    walkerInfo a = new walkerInfo(f.getCurrentUser().getUid(), fnames, lnames, genders, dobs, countrys, mobilenums, weights, heights);
//                                    mDatabase.child("walker").child(f.getCurrentUser().getUid()).setValue(a);
//                                    startActivity(new Intent(register.this, workout.class));
//                                    finish();
//                                }
//                            }).setPositiveButton("NO", new DialogInterface.OnClickListener() {
//
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                            dialog.cancel();
//                        }
//                    });
//                    AlertDialog alertDialog = builder.create();
//                    alertDialog.show();
//
//                }
//
//
//            }
//        });
//
//    }
//    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//        @Override
//        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//
//        }
//
//        @Override
//        public void onVerificationFailed(FirebaseException e) {
//            Toast.makeText(register.this,"Error in Verification" , Toast.LENGTH_SHORT).show();
//
//            if (e instanceof FirebaseAuthInvalidCredentialsException) {
//
//            } else if (e instanceof FirebaseTooManyRequestsException) {
//                // The SMS quota for the project has been exceeded
//                // ...
//            }
//
//        }
//        @Override
//        public void onCodeSent(String s,PhoneAuthProvider.ForceResendingToken t){
//            super.onCodeSent(s,t);
//            codeSent=s;
//            Toast.makeText(register.this, "Code Sent to Phone Number", Toast.LENGTH_SHORT).show();
//        }
//    };
//
//    private void sendCode(){
//
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(mobilenums,60, TimeUnit.SECONDS,register.this,mCallback);
//
//    }
//
//    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
//        f.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//
//                            FirebaseUser user = task.getResult().getUser();
//                            Toast.makeText(register.this, "Login", Toast.LENGTH_SHORT).show();
//                            // ...
//                        } else {
//                            // Sign in failed, display a message and update the UI
//                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
//                                // The verification code entered was invalid
//                            }
//                        }
//                    }
//                });
//    }
//
//
//    private void loadData() {
//        DatabaseReference userInfo=mDatabase.child("walker").child(f.getCurrentUser().getUid());
//        userInfo.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String walkerID,first_name,last_name,gender1,DoB,country1,mobile_num,weight1,height1;
//                first_name=dataSnapshot.child("first_name").getValue(String.class);
//                last_name=dataSnapshot.child("last_name").getValue(String.class);
//                gender1=dataSnapshot.child("gender").getValue(String.class);
//                DoB=dataSnapshot.child("DoB").getValue(String.class);
//                country1=dataSnapshot.child("country").getValue(String.class);
//                mobile_num=dataSnapshot.child("mobile").getValue(String.class);
//                weight1=dataSnapshot.child("weight").getValue(String.class);
//                height1=dataSnapshot.child("height").getValue(String.class);
//
//                fname.setText(first_name);
//                lname.setText(last_name);
//                gender.setText(gender1);
//                dob.setText(DoB);
//
//                country.setText(country1);
//                mobilenum.setText(mobile_num);
//                weight.setText(weight1);
//                height.setText(height1);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
//}
//
