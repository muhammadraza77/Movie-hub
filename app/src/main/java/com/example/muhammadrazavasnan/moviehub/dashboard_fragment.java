package com.example.muhammadrazavasnan.moviehub;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class dashboard_fragment extends Fragment{
    RecyclerView rec;
    List<videodetail> data;
    static String  username;
    static String  userid;
    static String  type;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.dashboard_fragment,container,false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInsntanceState){
        super.onViewCreated(view,savedInsntanceState);
        data = new ArrayList<>();
        rec = (RecyclerView) view.findViewById(R.id.recycler);
        rec.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        updateUI(view);

    }

    private void updateUI(final View view){
        String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
//        Toast.makeText(this,uid, Toast.LENGTH_LONG).show();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").orderByChild("userid").equalTo(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                for (final DataSnapshot child : dataSnapshot.getChildren()) {
                    username = child.child("name").getValue(String.class);
                    userid = child.child("userid").getValue(String.class);
                    type = child.child("type").getValue(String.class);
                    TextView t=(TextView)view.findViewById(R.id.text1);
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

                    adapter adapter = new adapter(data,getContext());
                    rec.setAdapter(adapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}
