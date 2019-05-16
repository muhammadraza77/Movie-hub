package com.example.muhammadrazavasnan.moviehub;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//import com.squareup.picasso.Picasso;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class adapter extends RecyclerView.Adapter<adapter.ViewHolder> {


    List<videodetail> data=new ArrayList<>();
    Context context;
    public adapter(List<videodetail> a,Context cont){
        data=a;
        context=cont;
    }


    public adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater minflator=LayoutInflater.from(parent.getContext());
        View view=minflator.inflate(R.layout.card,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(adapter.ViewHolder holder, int position) {
        final videodetail a =data.get(position);
        Picasso.with(context).load(a.thumbnail).fit().into(holder.thumb);
        holder.thumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent c=new Intent(v.getContext(),videoPlayer.class);
                c.putExtra("info",a);
                context.startActivity(c);

            }
        });
        holder.name.setText(a.name);
        holder.voteup.setText(a.upvote);
        holder.votedown.setText(a.downvote);

    }

    @Override
    public int getItemCount() {
        return data.size();

    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView thumb;
        TextView name,voteup,votedown;


        public ViewHolder(View itemView) {
            super(itemView);
            thumb=(ImageView) itemView.findViewById(R.id.thumb);
            name=(TextView) itemView.findViewById(R.id.name);
            voteup=(TextView) itemView.findViewById(R.id.voteup);
            votedown=(TextView) itemView.findViewById(R.id.votedown);

        }
    }
}
