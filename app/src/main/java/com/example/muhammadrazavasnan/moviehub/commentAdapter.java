package com.example.muhammadrazavasnan.moviehub;

/**
 * Created by Muhammad Raza Vasnan on 3/16/2019.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Mujtaba on 3/16/2019.
 */

public class commentAdapter extends RecyclerView.Adapter<commentAdapter.ViewHolder> {
    private List<String> comment;
    private List<String> name;

    private Context context;
    private int index;
    public commentAdapter(List<String> name, List<String> comment,Context context)
    {
        this.context = context;
        this.comment = comment;
        this.name = name;
    }

    @Override
    public commentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_card,parent,false);
        return new commentAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final commentAdapter.ViewHolder holder, int position) {
        final String comment1= comment.get(position);
        final String name1= name.get(position);

        holder.usercomment.setText(comment1);
        holder.username.setText(name1);
    }
    @Override
    public int getItemCount() {
        return name.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView username, usercomment;
        public ViewHolder(View itemView) {
            super(itemView);
            usercomment= (TextView)itemView.findViewById(R.id.commentcomment);
            username = (TextView)itemView.findViewById(R.id.commentname);

        }
    }
}
