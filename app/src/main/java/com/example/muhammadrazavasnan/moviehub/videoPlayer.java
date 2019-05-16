package com.example.muhammadrazavasnan.moviehub;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.muhammadrazavasnan.moviehub.MainActivity.userid;


public class videoPlayer extends AppCompatActivity {


    ImageButton thumbsUp;
    ImageButton thumbsUpSelected;
    ImageButton thumbsDown;
    ImageButton thumbsDownSelected;
    ImageButton share;
    TextView t1,t2;
    int like = 0;
    int dislike = 0;
    int dlike = 0;
    int ddislike = 0;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    List<String> name, comment;
    videodetail v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        name = new ArrayList<>();
        comment = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth= FirebaseAuth.getInstance();
        recyclerView = (RecyclerView)findViewById(R.id.commmentRV);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(videoPlayer.this);
        recyclerView.setLayoutManager(layoutManager);

        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        final VideoView videoView1 = (VideoView) findViewById(R.id.videoView1); // initiate a video view

        // create an object of media controller
        final MediaController mediaController = new MediaController(this);

        Intent inte=getIntent();
        v=(videodetail)inte.getSerializableExtra("info");

        t1=(TextView)findViewById(R.id.video_description);
        t2=(TextView)findViewById(R.id.video_title);
        t1.setText(v.desc);
        t2.setText(v.name);
        t1=(TextView)findViewById(R.id.upc);
        t2=(TextView)findViewById(R.id.downc);
        t1.setText(v.upvote);
        t2.setText(v.downvote);
        dlike=Integer.parseInt(v.upvote);
        ddislike=Integer.parseInt(v.downvote);

        Uri uri=Uri.parse(v.link);
        videoView1.setVideoURI(uri);

        videoView1.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoView1.start();
                videoView1.setMediaController(mediaController);
                mediaController.setAnchorView(videoView1);
            }
        });


        thumbsUp = (ImageButton) findViewById(R.id.thumbsUp);
        thumbsUp.setOnClickListener(thumbsUpHander);
        share = (ImageButton) findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent ();
                i.setData(Uri.parse("email"));
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT,"Hello Jee Mujtaba Bawani");
                Intent choser = Intent.createChooser(i,"Send Via ");
                startActivity(choser);
            }
        });

        loaddata();
        final EditText et=(EditText)findViewById(R.id.commentET);
        ImageButton bt=(ImageButton)findViewById(R.id.btComment);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cmm=et.getText().toString();

                comment cpp=new comment(userid,cmm);
                mDatabase = FirebaseDatabase.getInstance().getReference();
                String key=mDatabase.child("test").push().getKey();
                mDatabase.child("comments").child(key).child("name").setValue(cpp.name);
                mDatabase.child("comments").child(key).child("text").setValue(cpp.text);
                mDatabase.child("comments").child(key).child("vid").setValue(v.id);
            }
        });
        thumbsDown = (ImageButton) findViewById(R.id.thumbsDown);
        thumbsDown.setOnClickListener(thumbsDownHander);

    }
    private void loaddata() {

        final DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
        mDatabase.child("comments").orderByChild("vid").equalTo(v.id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                for(final DataSnapshot child:dataSnapshot.getChildren()){

                    comment.add(child.child("text").getValue(String.class));
                    name.add(child.child("userid").getValue(String.class));

                }
                adapter = new commentAdapter(name, comment,videoPlayer.this);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
    View.OnClickListener thumbsUpHander = new View.OnClickListener() {

        public void onClick(View veiw)
        {
            if (like == 0){
                thumbsUp.setImageResource(R.drawable.ic_thumbs_up_hand_symbolzayan);
                like++;
                dlike++;
                t1.setText(""+dlike);
                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("videos").child(v.id).child("upvote").setValue(dlike);

            }

            else if (like == 1){
                thumbsUp.setImageResource(R.drawable.ic_thumbs_up);
                like--;
                dlike--;
                t1.setText(""+dlike);
                mDatabase.child("videos").child(v.id).child("upvote").setValue(dlike);
            }

        }


    };

    View.OnClickListener thumbsDownHander = new View.OnClickListener() {

        public void onClick(View view) {

            if (dislike == 0){
                thumbsDown.setImageResource(R.drawable.ic_thumbs_down_silhouette);
                dislike++;
                ddislike++;
                t2.setText(""+ddislike);
                mDatabase.child("videos").child(v.id).child("upvote").setValue(dlike);
            }

            else if (dislike == 1){
                thumbsDown.setImageResource(R.drawable.ic_thumb_down);
                dislike--;
                ddislike--;
                t2.setText(""+ddislike);
                mDatabase.child("videos").child(v.id).child("upvote").setValue(dlike);
            }

        }
    };


}
