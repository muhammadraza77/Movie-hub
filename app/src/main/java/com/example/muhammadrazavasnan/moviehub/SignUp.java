package com.example.muhammadrazavasnan.moviehub;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

//
public class SignUp extends AppCompatActivity {
    Button register ;
    TextView signin;
    String abc="Basic" ;
    private FirebaseAuth obj;
    private DatabaseReference mDatabase;
    String id, pass,name, age, genre, country , phone;
    FirebaseAuth f;
    String codeSent;

    EditText username, useremail, userpassword, userage, userphone,usercountry, usergenre;
    RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();
        f=FirebaseAuth.getInstance();

        radioGroup = (RadioGroup) findViewById(R.id.accounttype);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.radio1:
                        abc = "Basic";
                        break;
                    case R.id.radio2:
                        abc = "Exclusive";
                        break;
                }
            }
        });

        obj=FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        username = (EditText)findViewById(R.id.username);
        userpassword = (EditText)findViewById(R.id.userpassword) ;
        register = (Button)findViewById(R.id.register);
        userphone = (EditText)findViewById(R.id.userphone) ;
        useremail = (EditText)findViewById(R.id.useremail);
        usercountry = (EditText)findViewById(R.id.usercountry);
        usergenre = (EditText)findViewById(R.id.usergenre);
        signin = (TextView)findViewById(R.id.signIn1);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = useremail.getText().toString();
                pass = userpassword.getText().toString();
                name = username.getText().toString();
                country = usercountry.getText().toString();
                phone = userphone.getText().toString();
                genre = usergenre.getText().toString();
                Toast.makeText(SignUp.this, abc+"", Toast.LENGTH_SHORT).show();

                signUp (id, pass);

                sendCode();
                AlertDialog.Builder builder =new AlertDialog.Builder(SignUp.this);
                builder.setTitle("Confirm Account");
                View viewInflated = LayoutInflater.from(SignUp.this).inflate(R.layout.dialog, (ViewGroup) v.getRootView(), false);
                builder.setView(viewInflated);
                final EditText input = (EditText) viewInflated.findViewById(R.id.input);
                builder.setMessage("Enter 6 Digit Code Sent To your Phone Number ?").setCancelable(false)
                        .setNegativeButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //db
                                String code = input.getText().toString();

                                PhoneAuthCredential credential= PhoneAuthProvider.getCredential(codeSent,code);
                                AuthCredential tempCred=PhoneAuthProvider.getCredential(codeSent,code);
                                f.getCurrentUser().linkWithCredential(tempCred).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            FirebaseUser user = task.getResult().getUser();
                                        } else {

                                        }

                                    }
                                });
                                signInWithPhoneAuthCredential(credential);

                                updateDatabase();
                                startActivity(new Intent(SignUp.this, MainActivity.class));
                                finish();

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
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    public void signUp(String id, String pass){
        obj.createUserWithEmailAndPassword(id,pass);
    }
    public void updateDatabase(){
        zcustomer a = new zcustomer(obj.getCurrentUser().getUid(), name, id, country, phone,abc, genre);
        mDatabase.child("users").child(obj.getCurrentUser().getUid()).setValue(a);

    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(SignUp.this,"Error in Verification" , Toast.LENGTH_SHORT).show();

            if (e instanceof FirebaseAuthInvalidCredentialsException) {

            } else if (e instanceof FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                // ...
            }

        }
        @Override
        public void onCodeSent(String s,PhoneAuthProvider.ForceResendingToken t){
            super.onCodeSent(s,t);
            codeSent=s;
            Toast.makeText(SignUp.this, "Code Sent to Phone Number", Toast.LENGTH_SHORT).show();
        }
    };

    private void sendCode(){

        PhoneAuthProvider.getInstance().verifyPhoneNumber("+92"+phone,60, TimeUnit.SECONDS,SignUp.this,mCallback);

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        f.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = task.getResult().getUser();
                            Toast.makeText(SignUp.this, "Login", Toast.LENGTH_SHORT).show();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

}
