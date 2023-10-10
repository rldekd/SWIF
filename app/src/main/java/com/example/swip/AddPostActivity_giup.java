package com.example.swip;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.swip.databinding.ActivityAddPostBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class AddPostActivity_giup extends AppCompatActivity {

    ImageView ivBack, postImage;
    EditText etTitle, etContent;
    Button btnSave;
    TextView tvUpload;
    Uri image_uri;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;


    private StorageTask mUploadTask;
    private CardView mProgress;

    Post_Model postModel;

    long maxid = 0;

    String mTitle, mContent;


    ActivityAddPostBinding addPostBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        addPostBinding = ActivityAddPostBinding.inflate(getLayoutInflater());
        View view = addPostBinding.getRoot();
        setContentView(view);

        storageReference = FirebaseStorage.getInstance().getReference("기업"); // 얘가 이름인 것 같음
        databaseReference = FirebaseDatabase.getInstance().getReference("기업");

        addPostBinding.progressBar.setVisibility(View.GONE);



        ivBack = findViewById(R.id.ivBack);
        postImage = findViewById(R.id.postImage);
        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);

        btnSave = findViewById(R.id.btnSave);
        tvUpload = findViewById(R.id.tvUploadPhoto);
        mProgress = findViewById(R.id.progressBar);

        postModel = new Post_Model();


        /* 하단바 */

        /* 하단바 - 공지사항 */
        ImageButton nav_menu = (ImageButton) findViewById(R.id.nav_menu);
        nav_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NoticeActivity.class);
                startActivity(intent);
            }
        });


        /* 하단바 - 타이머 */
        ImageButton nav_timer = (ImageButton) findViewById(R.id.nav_timer);
        nav_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TimerActivity.class);
                startActivity(intent);
            }
        });


        /* 하단바 - 홈 */
        ImageButton nav_home = (ImageButton) findViewById(R.id.nav_home);
        nav_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


        /* 하단바 - 게시판 */
        ImageButton nav_post = (ImageButton) findViewById(R.id.nav_post);
        nav_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InformationBoardActivity.class);
                startActivity(intent);
            }
        });



        /* 하단바 - 마이페이지 */
        ImageButton nav_friend = (ImageButton) findViewById(R.id.nav_friend);
        nav_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
                startActivity(intent);
            }
        });



        tvUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (ContextCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(AddPostActivity_giup.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                REQUEST_CODE_STORAGE_PERMISSION);
                    } else {

                        selectImage();

                    }

                } else {
                    selectImage();
                }


            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                receiveEntries();


            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PostListActivity.class));
                finish();

            }
        });
    }

    private void uploadFile() {

        if (image_uri != null) {
            StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(image_uri));


            UploadTask uploadTask = fileReference.putFile(image_uri);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddPostActivity_giup.this, "글이 등록되지 않았습니다.", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(AddPostActivity_giup.this, "글이 등록되었습니다.", Toast.LENGTH_LONG).show();

                    if (taskSnapshot.getMetadata() != null)
                        if (taskSnapshot.getMetadata().getReference() != null) {
                            Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                            result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String sImage = uri.toString();

                                    mProgress.setVisibility(View.VISIBLE);
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            addPostBinding.mprogressBar.setProgress(0);
                                        }
                                    }, 500);

                                    postModel = new Post_Model(mTitle, mContent, sImage);
                                    String key = databaseReference.push().getKey();
                                    postModel.setID(key);
                                    databaseReference.child(key).setValue(postModel);

                                    mProgress.setVisibility(View.INVISIBLE);
                                    backToProfile(mTitle, mContent, sImage);
                                    etContent.setText("");
                                    etTitle.setText("");
                                    Picasso.get().load("null").placeholder(R.drawable.placeholder).into(postImage);
                                }
                            });
                            result.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    mProgress.setVisibility(View.INVISIBLE);
                                    Toast.makeText(AddPostActivity_giup.this, "Database Fail...", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                }
            });


        }
        else {
            Toast.makeText(this, "사진을 올려주세요", Toast.LENGTH_SHORT).show();
        }


    }

    private void backToProfile(String mTitle, String mContent, String sImage) {

        Intent backIntent = new Intent(this, PostConfirmActivity.class);
        backIntent.putExtra("postTitle1",mTitle );
        backIntent.putExtra("postContent1",mContent );
        backIntent.putExtra("imageUri1",sImage);

        startActivity(backIntent);
    }



    private void receiveEntries() {

        mTitle = etTitle.getText().toString().trim();
        mContent = etContent.getText().toString().trim();

        checkFields();
    }

    private void checkFields() {

        if (etTitle.getText().toString().isEmpty()) {
            etTitle.setError("제목을 입력해주세요.");
        } else if (etContent.getText().toString().isEmpty()) {
            etContent.setError("내용을 입력해주세요.");

        } else {

            if (mUploadTask != null && mUploadTask.isInProgress()) {
            } else {

                if (isNetworkConnected()) {
                    uploadFile();


                    startActivity(new Intent(getApplicationContext(), PostListActivity.class));
                    finish();
                }
                else {

                    Toast.makeText(AddPostActivity_giup.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }


            }
        }

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();

    }

    private void selectImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImage();
            } else {
                Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            image_uri = data.getData();

            Picasso.get().load(image_uri).into(postImage);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

}