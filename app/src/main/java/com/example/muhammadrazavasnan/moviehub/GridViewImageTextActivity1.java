package com.example.muhammadrazavasnan.moviehub;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GridViewImageTextActivity1 extends AppCompatActivity {

    GridView androidGridView;

    ArrayList <String>  gridViewString;

    ArrayList <Integer> gridViewImageId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridview_image_text_example);

        gridViewImageId = new ArrayList<Integer>();
        gridViewString = new ArrayList<String>();

        gridViewString.add("Zaya");

        gridViewString.add("Zaya");
        gridViewString.add("Zaya");

        Toast.makeText(this, "Read from db", Toast.LENGTH_SHORT).show();
        readDatabase();

        for (int i = 0; i < gridViewString.size(); i++)
            gridViewImageId.add(R.drawable.ic_basicuser);

        CustomGridViewActivity adapterViewAndroid = new CustomGridViewActivity(GridViewImageTextActivity1.this, gridViewString, gridViewImageId);
        androidGridView=(GridView)findViewById(R.id.grid_view_image_text);
        androidGridView.setAdapter(adapterViewAndroid);
        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int i, long id) {
                Toast.makeText(GridViewImageTextActivity1.this, "GridView Item: " + gridViewString.get(i), Toast.LENGTH_LONG).show();
            }
        });

    }
    void readDatabase(){
        Toast.makeText(this, "in Read from db", Toast.LENGTH_SHORT).show();
        DatabaseReference db=FirebaseDatabase.getInstance().getReference();
        db.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot child:dataSnapshot.getChildren()){
                    gridViewString.add(child.child("name").getValue(String.class));
                    Log.i("Users from db", child.child("name").getValue(String.class));
//                    child.child("type").getValue(String.class);

                    Toast.makeText(GridViewImageTextActivity1.this,child.child("name").getValue(String.class) , Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(GridViewImageTextActivity1.this, " db", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

