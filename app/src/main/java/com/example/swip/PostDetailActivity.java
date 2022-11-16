package com.example.swip;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.squareup.picasso.Picasso;

public class PostDetailActivity extends AppCompatActivity {





    private static final int CALL_PERMISSION = 30;

    ImageView ivhotelImage;
    TextView tvratings,tvHotelEmail,tvHotelPhone,tvhotelLocation,
            tvhotelNames,tvtagsList, tvHotelPrice,tvMapUrlLoaccation, tvHotelWebsite;

    String mratings,mtvHotelEmail,mtvHotelPhone,mhotelLocation,
            mhotelNames,mtagsList, mhotelImage, mhotelPrice, mhotelMapUrl,mhotelWebsite;
    WebView tvHotelDirection;



    private void initializeWidgets(){
        ivhotelImage        = findViewById(R.id.ivHotelImage);
        tvhotelLocation     = findViewById(R.id.tvHotelLocation);
        tvhotelNames        = findViewById(R.id.tvHotelName);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detail);
        initializeWidgets();
        recieveIntents();

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Post_Detail.class);
                startActivity(intent);
            }
        });


    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(PostDetailActivity.this, new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION);
    }

    private boolean checkedPermission() {
        int callPermission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE);

        return callPermission == PackageManager.PERMISSION_GRANTED;
    }




    private void recieveIntents() {
        if (getIntent().hasExtra("hotelLocation1")
                && getIntent().hasExtra("hotelName1")
                && getIntent().hasExtra("imageUri1")) {

            mhotelLocation = getIntent().getStringExtra("hotelLocation1");
            mhotelNames = getIntent().getStringExtra("hotelName1");
            mhotelImage = getIntent().getStringExtra("imageUri1");

            provision(mhotelLocation,mhotelNames,mratings,mtagsList,
                    mhotelImage,mtvHotelEmail, mtvHotelPhone, mhotelMapUrl,  mhotelWebsite,mhotelPrice);
        }
    }

    private void provision(String mhotelLocation, String mhotelNames, String mratings,
                           String mtagsList, String mhotelImage,
                           String mtvHotelEmail, String mtvHotelPhone,
                           String mhotelMapUrl, String mhotelWebsite, String mhotelPrice) {

        tvhotelLocation.setText(mhotelLocation); ;
        tvhotelNames .setText(mhotelNames);



        Picasso.get().load(mhotelImage).fit().placeholder(R.drawable.placeholder).into(ivhotelImage);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CALL_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission For Calling has been Accepted...", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission For Calling has been Denied...", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}