package com.example.swip;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.InputStream;

public class ProfileModify extends AppCompatActivity {

    private final int GALLERY_CODE = 10;
    ImageView photo;
    private FirebaseStorage storage;
    ImageView load;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_modify);
        findViewById(R.id.profileimg).setOnClickListener(onClickListener);
        photo = (ImageView) findViewById(R.id.profileimg);
        storage = FirebaseStorage.getInstance();

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


        load=(ImageView)findViewById(R.id.profileimg);

        FirebaseStorage storage = FirebaseStorage.getInstance(); //스토리지에 접근하기 위해 인스턴스 생성
        StorageReference storageReference = storage.getReference();
        StorageReference pathReference = storageReference.child("photo"); //스토리지에 어떤 폴더에 접근할 것인지 알려주는 코드로 폴더명을 "" 안에 작성
        // 만일 스토리지 내부에 폴더가 없다면 폴더 명 대신 불러올 이미지 이름을 입력

        if (pathReference == null) { // 스토리지에 pathReference에 입력된 폴더가 없을 경우 null 값이 들어가게 되므로 해당 코드는 child에 적힌 폴더가 스토리지 내부에 존재하지 않을 경우 실행됨
            Toast.makeText(ProfileModify.this, "저장소에 사진이 없습니다." ,Toast.LENGTH_SHORT).show();
        } else {
            StorageReference submitProfile = storageReference.child("photo/1.png"); //pathReference에서 지정한 폴더 안에 어떤 파일을 가져올지 지정하는 코드로 폴더위치와 파일명을 정확하게 기입해야 함
            submitProfile.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) { //스토리지에 저장된 파일을 정상적으로 불러오면, Glide를 이용해 ImageView에 사진을 업로드
                    Glide.with(ProfileModify.this).load(uri).into(load);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
    }


    View.OnClickListener onClickListener = new View.OnClickListener() { //카드 뷰 안에 있는 이미지 뷰를 클릭하면 loadAlbum이 실행되도록 함
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.profileimg:
                    loadAlbum();
                    break;
            }
        }
    };

    private void loadAlbum() { //앱에서 휴대폰 갤러리를 실행할 수 있도록 해주는 함수
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, GALLERY_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, final int resultCode, final Intent data) { //갤러리에서 사용자가 선택한 사진을 파이어스토어에 올라가도록 하는 함수
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_CODE) {
            Uri file = data.getData();
            StorageReference storageRef = storage.getReference();
            StorageReference riversRef = storageRef.child("photo/1.png"); // () 안에는 이미지가 저장될 경로 입력
            UploadTask uploadTask = riversRef.putFile(file);

            try { //선택한 이미지를 비트맵으로 생성하여 처리하는 부분
                InputStream in = getContentResolver().openInputStream(data.getData());
                Bitmap img = BitmapFactory.decodeStream(in);
                in.close();
                photo.setImageBitmap(img);
            } catch (Exception e) {
                e.printStackTrace();
            }

            uploadTask.addOnFailureListener(new OnFailureListener() { //스토리지에 정상적으로 이미지 파일을 업로드할 수 없을 때 아래 있는 코드 수행
                @Override
                public void onFailure(@NonNull Exception exception) {//사진 업로드에 실패하면 업로드 되지 않았다는 토스트 메세지 출력
                    Toast.makeText(ProfileModify.this, "사진이 정상적으로 업로드 되지 않았습니다.", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() { //스토리지에 정상적으로 이미지 파일이 업로드 되었을 때 아래 있는 코드 수행
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {//사진 업로드에 성공하면 업로드 되었다는 토스트 메세지 출력
                    Toast.makeText(ProfileModify.this, "사진이 정상적으로 업로드 되었습니다.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
