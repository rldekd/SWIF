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
    private static final String TAG = "hotelAdapter";
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

        holder.hotelLocation.setText(currentPostModel.getHotelLocation());
        holder.hotelName.setText(currentPostModel.getHotelName());




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
                passIntent.putExtra("hotelLocation1", currentPostModel.getHotelLocation());
                passIntent.putExtra("hotelName1", currentPostModel.getHotelName());

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

        public TextView hotelLocation, hotelName;
        public ImageView hotelImage;
        CardView clickedLayout;


        public RecyclerViewHolder(View itemView) {
            super(itemView);
            hotelLocation = itemView.findViewById(R.id.hotelLocation);
            hotelName = itemView.findViewById(R.id.hotelName);
            hotelImage = itemView.findViewById(R.id.hotelImage);
            clickedLayout = itemView.findViewById(R.id.hotelCard);




        }
    }
}