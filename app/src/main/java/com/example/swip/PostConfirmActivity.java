package com.example.swip;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class PostConfirmActivity extends AppCompatActivity {
    private  ImageView ivBack, ivhotelImage;
    private  TextView tvLocation, tvHotelName;
    Button btnHome;
    ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ivBack = findViewById(R.id.ivBack);
        ivhotelImage = findViewById(R.id.cImage);
        tvLocation = findViewById(R.id.cTitle);
        tvHotelName = findViewById(R.id.cContent);

        btnHome = findViewById(R.id.btnBackHome);

        mProgress = findViewById(R.id.progressBar);


        personalProductsIntents();

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logIntent = new Intent(PostConfirmActivity.this, InformationBoardActivity.class);
                startActivity(logIntent);
                return;
            }
        });
    }

    private void personalProductsIntents() {
        String mpostTitle,
                mpostContent, mpostImage;
        if (getIntent().hasExtra("postTitle1")
                && getIntent().hasExtra("postContent1")
                && getIntent().hasExtra("imageUri1")
        ) {

            mpostTitle = getIntent().getStringExtra("postTitle1");
            mpostContent = getIntent().getStringExtra("postContent1");
            mpostImage = getIntent().getStringExtra("imageUri1");;


            tvLocation.setText(mpostTitle); ;
            tvHotelName .setText(mpostContent);

            Picasso.get().load(mpostImage).fit().placeholder(R.drawable.placeholder).into(ivhotelImage);

        }

    }
}