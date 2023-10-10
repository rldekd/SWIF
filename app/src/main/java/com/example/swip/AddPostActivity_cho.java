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

public class AddPostActivity_cho extends AppCompatActivity {

    ImageView ivBack, postImage;
    EditText etTitle, etContent;
    Button btnSave;
    TextView tvUpload;
    Uri image_uri;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1; // 이미지 권한 요청
    private static final int REQUEST_CODE_SELECT_IMAGE = 2; // 갤러리 권한 요청


    private StorageTask mUploadTask; // 업로드 기능을 위한 참조
    private CardView mProgress; // 로딩

    Post_Model postModel; // 포스트 모델 정의

    long maxid = 0;

    String mTitle, mContent;


    ActivityAddPostBinding addPostBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        addPostBinding = ActivityAddPostBinding.inflate(getLayoutInflater()); // inflate는 xml에 있는 뷰를 객체화
        View view = addPostBinding.getRoot(); // 생성한 루트 뷰 넘겨주기
        setContentView(view);

        storageReference = FirebaseStorage.getInstance().getReference("초등학생"); // 데이터 생성 - 모든 게시판 동일 적용
        databaseReference = FirebaseDatabase.getInstance().getReference("초등학생"); // 파이어베이스에서 데이터를 추가하거나 조회 (참조)

        addPostBinding.progressBar.setVisibility(View.GONE); // 로딩 화면 없어지게 보이기



        ivBack = findViewById(R.id.ivBack);
        postImage = findViewById(R.id.postImage);
        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);
        btnSave = findViewById(R.id.btnSave);
        tvUpload = findViewById(R.id.tvUploadPhoto);
        mProgress = findViewById(R.id.progressBar);

        postModel = new Post_Model(); // 포스트 모델 정의


        /* 하단바 */

        /* 하단바 - 공지사항 */
        ImageButton nav_menu = (ImageButton) findViewById(R.id.nav_menu); // 리소스 아이디 파일에서 nav_menu 아이디를 찾아 이미지 버튼에 할당
        nav_menu.setOnClickListener(new View.OnClickListener() { // nav_menu를 클릭할 시 setOnClickListener 이벤트 발생
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NoticeActivity.class); // intent를 사용해 NoticeActivity.class를 불러옴. getApplicationContext()를 사용해 앱이 종료될 때까지 이 엑티비티는 살아있게 됨.
                startActivity(intent); // intent를 사용해 NoticeActivity를 호출하는 작업을 요청함
            }
        }); // 종료


        /* 하단바 - 타이머 */
        ImageButton nav_timer = (ImageButton) findViewById(R.id.nav_timer); // 리소스 아이디 파일에서 nav_timer 아이디를 찾아 이미지 버튼에 할당
        nav_timer.setOnClickListener(new View.OnClickListener() { // nav_timer를 클릭할 시 setOnClickListener 이벤트 발생
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TimerActivity.class); // intent를 사용해 TimerActivity.class를 불러옴. getApplicationContext()를 사용해 앱이 종료될 때까지 이 엑티비티는 살아있게 됨.
                startActivity(intent); // intent를 사용해 TimerActivity를 호출하는 작업을 요청함
            }
        }); // 종료


        /* 하단바 - 홈 */
        ImageButton nav_home = (ImageButton) findViewById(R.id.nav_home); // 리소스 아이디 파일에서 nav_home 아이디를 찾아 이미지 버튼에 할당
        nav_home.setOnClickListener(new View.OnClickListener() {  // nav_home을 클릭할 시 setOnClickListener 이벤트 발생
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class); // intent를 사용해 MainActivity.class를 불러옴. getApplicationContext()를 사용해 앱이 종료될 때까지 이 엑티비티는 살아있게 됨.
                startActivity(intent); // intent를 사용해 MainActivity를 호출하는 작업을 요청함
            }
        }); // 종료


        /* 하단바 - 게시판 */
        ImageButton nav_post = (ImageButton) findViewById(R.id.nav_post); // 리소스 아이디 파일에서 nav_post 아이디를 찾아 이미지 버튼에 할당
        nav_post.setOnClickListener(new View.OnClickListener() {  // nav_post를 클릭할 시 setOnClickListener 이벤트 발생
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InformationBoardActivity.class); // intent를 사용해 InformationBoardActivity.class를 불러옴. getApplicationContext()를 사용해 앱이 종료될 때까지 이 엑티비티는 살아있게 됨.
                startActivity(intent); // intent를 사용해 InformationBoardActivity를 호출하는 작업을 요청함
            }
        }); // 종료



        /* 하단바 - 마이페이지 */
        ImageButton nav_friend = (ImageButton) findViewById(R.id.nav_friend); // 리소스 아이디 파일에서 nav_friend 아이디를 찾아 이미지 버튼에 할당
        nav_friend.setOnClickListener(new View.OnClickListener() {  // nav_friend를 클릭할 시 setOnClickListener 이벤트 발생
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyPageActivity.class); // intent를 사용해 MyPageActivity.class를 불러옴. getApplicationContext()를 사용해 앱이 종료될 때까지 이 엑티비티는 살아있게 됨.
                startActivity(intent); // intent를 사용해 MyPageActivity를 호출하는 작업을 요청함
            }
        }); // 종료



        tvUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // // Android Marshmallow 이상에서만 실행되는 코드

                    if (ContextCompat.checkSelfPermission(getApplicationContext(), // 권한 확인
                            Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED // 권한 없음
                    ) {
                        ActivityCompat.requestPermissions(AddPostActivity_cho.this, // 권한 요청
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                REQUEST_CODE_STORAGE_PERMISSION);
                    } else { // 권한이 있다면
                        selectImage(); // selectImage 실행
                    } } else {
                    selectImage();
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() { // 저장 버튼 클릭 시
            @Override
            public void onClick(View v) {
                receiveEntries(); // receiveEntries 실행
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() { // 뒤로 가기 버튼 클릭 시
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), InformationBoardActivity.class)); // 게시판 페이지로 이동
                finish(); // 액티비티 종료
            }
        });
    }

    private void uploadFile() {

        if (image_uri != null) { // 이미지가 null이 아니라면 = 이미지가 잘 선택이 되었다면
            StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(image_uri)); //스토리지베이스 참조 (onCreate 에서 참조한 영역 / 파일명.확장자메서드)

            UploadTask uploadTask = fileReference.putFile(image_uri); // 참조영역(파일명)에 이미지 파일 업로드


            uploadTask.addOnFailureListener(new OnFailureListener() { // 업로드 실패시 리스너 (addOnFailureListener)
                @Override
                public void onFailure(@NonNull Exception e) { // 업로드 실패 시
                    Toast.makeText(AddPostActivity_cho.this, "글이 등록되지 않았습니다.", Toast.LENGTH_SHORT).show(); // 토스트 메시지 띄우기
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() { // 업로드 완료시 리스너 (addOnCompleteListener)
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) { // 업로드 완료 시
                    Toast.makeText(AddPostActivity_cho.this, "글이 등록되었습니다.", Toast.LENGTH_LONG).show(); // 완료 토스트 메시지 띄우기

                    if (taskSnapshot.getMetadata() != null)
                        if (taskSnapshot.getMetadata().getReference() != null) { // 이미지가 저장소에 있다면
                            Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl(); // 이미지 url을 다운 받는다.
                            // firestore에는 이미지를 직접적으로 저장하는 기능이 없어서, storage에 사진을 저장 한 후 다운로드 Url를 받아서 firestore에 저장해야된다.
                            result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) { // Url 받는곳
                                    String sImage = uri.toString(); // Uri를 String으로 바꾸기

                                    mProgress.setVisibility(View.VISIBLE); // 로딩 화면 실행
                                    Handler handler = new Handler(); // API 기본 핸들러 객체 생성
                                    handler.postDelayed(new Runnable() { // 몇 초 뒤 실행
                                        @Override
                                        public void run() {
                                            addPostBinding.mprogressBar.setProgress(0);
                                        }
                                    }, 500); // 5초 딜레이

                                    postModel = new Post_Model(mTitle, mContent, sImage); // 포스트 모델 정의
                                    String key = databaseReference.push().getKey(); // Key 생성
                                    postModel.setID(key); // 키 지정
                                    databaseReference.child(key).setValue(postModel);

                                    mProgress.setVisibility(View.INVISIBLE); // 로딩 화면 보이기
                                    backToProfile(mTitle, mContent, sImage); // 확인 페이지
                                    etContent.setText(""); // 내용 초기화
                                    etTitle.setText(""); // 제목 초기화
                                    Picasso.get().load("null").placeholder(R.drawable.placeholder).into(postImage); // 사진 초기화
                                }
                            });
                            result.addOnFailureListener(new OnFailureListener() { // 저장 실패 리스터
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    mProgress.setVisibility(View.INVISIBLE); // 로딩 화면 보이기
                                    Toast.makeText(AddPostActivity_cho.this, "Database Fail...", Toast.LENGTH_SHORT).show(); // 토스트 메시지 띄우기
                                }
                            });

                        }
                }
            });


        }
        else {
            Toast.makeText(this, "사진을 올려주세요", Toast.LENGTH_SHORT).show(); // 사진이 없다면 토스트 메시지 띄우기
        }


    }

    private void backToProfile(String mTitle, String mContent, String sImage) {

        Intent backIntent = new Intent(this, PostConfirmActivity.class); // 이동할 페이지 설정
        backIntent.putExtra("postTitle1",mTitle ); // PostConfirmActivity에 제목 전달
        backIntent.putExtra("postContent1",mContent ); // PostConfirmActivity에 내용 전달
        backIntent.putExtra("imageUri1",sImage); // PostConfirmActivity에 사진 전달

        startActivity(backIntent); // PostConfirmActivity로 이동
    }



    private void receiveEntries() {

        mTitle = etTitle.getText().toString().trim(); // 가져온 텍스트를 문자열로 변경, 공백이 포함된 경우 제거
        mContent = etContent.getText().toString().trim();// 가져온 텍스트를 문자열로 변경, 공백이 포함된 경우 제거
        checkFields(); // checkFields 함수 실행
    }

    private void checkFields() {

        if (etTitle.getText().toString().isEmpty()) { // 제목이 비어있다면
            etTitle.setError("Title of The hotel is required."); // 에러 메시지 출력
        } else if (etContent.getText().toString().isEmpty()) { // 내용이 비어있다면
            etContent.setError("Name of The hotel is required.");// 에러 메시지 출력
        } else {
            if (mUploadTask != null && mUploadTask.isInProgress()) {
            } else {
                if (isNetworkConnected()) {
                    uploadFile();
                    finish();
                }
                else {
                    Toast.makeText(AddPostActivity_cho.this, "No Internet Connection", Toast.LENGTH_SHORT).show(); // 인터넷 연결 오류 메시지 출력
                }


            }
        }

    }

    private boolean isNetworkConnected() { // 인터넷 연결 상태 확인 객체
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();

    }

    private void selectImage() { // 앨범으로 이동

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION && grantResults.length > 0) { // 권한 체크
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) { // 권한이 있다면
                selectImage(); // 갤러리로 이동
            } else {
                Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show(); // 권한 거부 시 토스트 메시지 출력
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK
                && data != null && data.getData() != null) {  // 이미지를 하나만 선택한 경우
            image_uri = data.getData(); // data에서 절대경로로 이미지를 가져옴
            Picasso.get().load(image_uri).into(postImage); //앨범에서 이미지를 읽고 postImage에 표시
        }
    }

    private String getFileExtension(Uri uri) { // 이미지 파일 확장자 얻기
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton(); // 주소에 포함된 파일 확장자명 추출
        return mime.getExtensionFromMimeType(cR.getType(uri)); // 확장자 가져오기
    }

}