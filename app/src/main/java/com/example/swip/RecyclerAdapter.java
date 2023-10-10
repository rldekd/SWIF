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
        View v = LayoutInflater.from(mContext).inflate(R.layout.row_layout_home, parent, false); // xml 레이아웃 정의
        RecyclerViewHolder postView = new RecyclerViewHolder(v);

        return postView;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        final Post_Model currentPostModel = postModels.get(position); // 포스트 모델에서 포지션 가져오기
        holder.postTitle.setText(currentPostModel.getPostTtile()); // 제목 가져오기
        holder.postContent.setText(currentPostModel.getPostContent()); // 내용 가져오기




        Picasso.get() // 이미지 가져오기
                .load(currentPostModel.getImageUri()) // 이미지 경로
                .placeholder(R.drawable.placeholder) // 이미지 로딩을 시작하기 전에 보여줄 이미지를 설정한다
                .fit() // 이미지 뷰의 dimension에 맞게 자동으로 이미지를 맞출 수 있다
                .centerCrop() // 중앙에 맞추어 자르기
                .into(holder.postImage); // 받아온 이미지 저장 공간


        holder.clickedLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent passIntent = new Intent(mContext, PostDetailActivity.class); // 글 상세 정보 확인
                passIntent.putExtra("postTitle1", currentPostModel.getPostTtile()); // 제목 불러오기
                passIntent.putExtra("postContent1", currentPostModel.getPostContent()); // 내용 불러오기
                passIntent.putExtra("imageUri1", currentPostModel.getImageUri()); // 이미지 불러오기
                mContext.startActivity(passIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        //return post.size();

        if (postModels != null) {
            return postModels.size();
        }
        return 0;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView postTitle, postContent;
        public ImageView postImage;
        CardView clickedLayout;
        public RecyclerViewHolder(View itemView) { // 각 xml 레이아웃에 맞는 포지션 설정
            super(itemView);
            postTitle = itemView.findViewById(R.id.postTitle);
            postContent = itemView.findViewById(R.id.postContent);
            postImage = itemView.findViewById(R.id.postImage);
            clickedLayout = itemView.findViewById(R.id.hotelCard);

        }
    }
}
