package com.example.swip;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public  class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {
    private Context mContext;
    private List<Post_Model> postModels;

    public RecyclerAdapter(Context context, List<Post_Model> uploads) {
        mContext = context;
        postModels = uploads;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.row_layout_home, parent, false);
        RecyclerViewHolder hotelView = new RecyclerViewHolder(v);

        return hotelView;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        final Post_Model currentPostModel = postModels.get(position);

        holder.postTitle.setText(currentPostModel.getPostTtile());
        holder.postContent.setText(currentPostModel.getPostContent());




        Picasso.get()
                .load(currentPostModel.getImageUri())
                .placeholder(R.drawable.placeholder)
                .fit()
                .centerCrop()
                .into(holder.hotelImage);


        holder.clickedLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent passIntent = new Intent(mContext, PostDetailActivity.class);
                passIntent.putExtra("postTitle1", currentPostModel.getPostTtile());
                passIntent.putExtra("postContent1", currentPostModel.getPostContent());
                passIntent.putExtra("imageUri1", currentPostModel.getImageUri());



                mContext.startActivity(passIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        //return hotels.size();

        if (postModels != null) {
            return postModels.size();
        }
        return 0;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView postTitle, postContent;
        public ImageView hotelImage;
        CardView clickedLayout;


        public RecyclerViewHolder(View itemView) {
            super(itemView);
            postTitle = itemView.findViewById(R.id.postTitle);
            postContent = itemView.findViewById(R.id.postContent);
            hotelImage = itemView.findViewById(R.id.postImage);
            clickedLayout = itemView.findViewById(R.id.hotelCard);




        }
    }
}