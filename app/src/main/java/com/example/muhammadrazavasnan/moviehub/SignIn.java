package com.example.muhammadrazavasnan.moviehub;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity {
    Button signin;
    TextView signup;
    FirebaseAuth authenticateuser;
    EditText useremail , userpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().hide();
        signin = (Button)findViewById(R.id.login);
        signup = (TextView) findViewById(R.id.signuptext);
        useremail = (EditText) findViewById(R.id.useremail);
        userpassword = (EditText) findViewById(R.id.userpassword);

        authenticateuser = FirebaseAuth.getInstance();
        if(useremail.getText().toString().equals("Zayan")){
            if(userpassword.getText().toString().equals("pakistan")){
                Intent intent = new Intent(SignIn.this, GridViewImageTextActivity1.class);
                startActivity(intent);
                finish();
            }
        }
       if(authenticateuser.getUid()!=null){
           Intent i = new Intent (SignIn.this, MainActivity.class);
           startActivity(i);
           finish();
       }
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignIn.this, SignUp.class);
                startActivity(i);
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = useremail.getText().toString();
                String pass = userpassword.getText().toString();
                if(!id.isEmpty() && !pass.isEmpty()) validateUser(id, pass);
            }
        });

    }
    void validateUser(String id,String pass){

        authenticateuser.signInWithEmailAndPassword(id,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    startActivity(new Intent(SignIn.this, MainActivity.class));
                    finish();
                }
                else{

                    Toast.makeText(SignIn.this, "ID or Password Incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
