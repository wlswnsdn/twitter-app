package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.helper.ImageHelper;
import com.example.myapplication.helper.PostHelper;

import de.hdodenhof.circleimageview.CircleImageView;

public class activity_posting extends AppCompatActivity {

    private CircleImageView circleImageView;
    private EditText editText;
    private Button postingBtn;
    private ImageView cancelBtn;
    private PostHelper postHelper;
    private ImageHelper imageHelper;

    private ILoginWithThis iLoginWithThis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting);

        circleImageView = findViewById(R.id.circleImageView);
        editText = findViewById(R.id.posting_content);
        postingBtn = findViewById(R.id.postingBtn);
        cancelBtn = findViewById(R.id.cancel_posting);


        //헬퍼들 초기화
        postHelper = new PostHelper(this);
        imageHelper = new ImageHelper(this);
        // CircleImageView 설정 함수 호출
        setupCircleImageView();

        // 취소 버튼 클릭 시
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent를 종료하여 현재 액티비티를 닫음
                finish();
            }
        });

        // 게시 버튼 클릭 시
        postingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EditText의 내용을 변수에 저장
                String postContent = editText.getText().toString();

                // 데이터 베이스에 저장
                postHelper.addPost(postContent);

                // Intent를 종료하여 현재 액티비티를 닫음
                finish();
            }
        });
    }

    // CircleImageView 설정 함수
    private void setupCircleImageView() {
        // 원형 이미지뷰에 이미지 설정
        iLoginWithThis = new ILoginWithThis();
        byte[] image = imageHelper.getMyImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

        circleImageView.setImageBitmap(bitmap);

    }
}
