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
        ivhotelImage = findViewById(R.id.hotelImage);
        tvLocation = findViewById(R.id.tvHotelName);
        tvHotelName = findViewById(R.id.tvLocation);

        btnHome = findViewById(R.id.btnBackHome);

        mProgress = findViewById(R.id.progressBar);


        personalProductsIntents();

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logIntent = new Intent(PostConfirmActivity.this, PostListActivity.class);
                startActivity(logIntent);
                return;
            }
        });
    }

    private void personalProductsIntents() {
        String mhotelLocation,
                mhotelNames, mhotelImage;
        if (getIntent().hasExtra("hotelLocation1")
                && getIntent().hasExtra("hotelName1")
                && getIntent().hasExtra("imageUri1")
        ) {

            mhotelLocation = getIntent().getStringExtra("hotelLocation1");
            mhotelNames = getIntent().getStringExtra("hotelName1");
            mhotelImage = getIntent().getStringExtra("imageUri1");;


            tvLocation.setText(mhotelLocation); ;
            tvHotelName .setText(mhotelNames);


            Picasso.get().load(mhotelImage).fit().placeholder(R.drawable.placeholder).into(ivhotelImage);

        }
    }
}