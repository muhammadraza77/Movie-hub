package com.example.muhammadrazavasnan.moviehub;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    RecyclerView rec;
    List<videodetail> data;
    static String  username;
    static String  userid;
    static String  type;
    public static Context con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        con = MainActivity.this;
        getSupportActionBar().hide();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new dashboard_fragment()).commit();
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment f=null;
                switch (item.getItemId()){
                    case R.id.home:
                        f = new dashboard_fragment();
                        break;
                    case R.id.livestreaming:
                        f=new Livestreaming_fragment();
                        break;
                    case R.id.upload:
                        f=new upload_fragment();
                        break;
                    case R.id.invite:
                        f= new invite_fragment();
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container,f).commit();
                return true;
            }
        });

    }
    private void updateUI(){
        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
//        Toast.makeText(this,uid, Toast.LENGTH_LONG).show();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").orderByChild("userid").equalTo(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                for (final DataSnapshot child : dataSnapshot.getChildren()) {
                    username = child.child("name").getValue(String.class);
                    userid = child.child("userid").getValue(String.class);
                    type = child.child("type").getValue(String.class);
                    TextView t=(TextView)findViewById(R.id.text1);
                    t.setText("Hello "+ username+"\nWelcome to Movie Hub");
                    loaddata(type);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void loaddata(String ss) {
        int iop=0;
        if(ss.isEmpty()){
            iop=0;
        }
        else if(ss.equals("Basic")){
            iop=0;
        }else{
            iop=1;
        }
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("videos").orderByChild("special").equalTo(iop).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                for (final DataSnapshot child : dataSnapshot.getChildren()) {
                    String name = child.child("name").getValue(String.class);
                    String upvote = child.child("upvote").getValue(Long.class).toString();
                    String downvote = child.child("downvote").getValue(Long.class).toString();
                    String thumbnail = child.child("thumbnail").getValue(String.class);
                    String link = child.child("link").getValue(String.class);
                    String desc = child.child("desc").getValue(String.class);
                    String id = child.child("vid").getValue(String.class);

                    data.add(new videodetail(name, upvote, downvote, thumbnail,desc,link,id));

                    adapter adapter = new adapter(data, MainActivity.this);
                    rec.setAdapter(adapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        MenuItem item = menu.findItem(R.id.logout);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
int id = item.getItemId();
        if(id==R.id.logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this,SignIn.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }
}