package com.example.myapplication;


import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.helper.UserHelper;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity {

    private EditText userNameEditText;
    private EditText userDescriptionEditText;
    private EditText userEmailEditText;
    private EditText passwordEditText;
    private UserHelper userHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        userNameEditText = findViewById(R.id.user_name);
        userDescriptionEditText = findViewById(R.id.user_description);
        userEmailEditText = findViewById(R.id.user_email);
        passwordEditText = findViewById(R.id.pw);

        // 헬퍼 초기화
        userHelper = new UserHelper(this);

        // 뒤로 가기 버튼
        ImageView backButton = findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 애니메이션
                Animation animation = AnimationUtils.loadAnimation(view.getContext(), androidx.transition.R.anim.abc_fade_out);
                view.startAnimation(animation);

                finish();
            }
        });

        // 저장 버튼
        TextView saveButton = findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 애니메이션
                Animation animation = AnimationUtils.loadAnimation(view.getContext(), androidx.transition.R.anim.abc_fade_out);
                view.startAnimation(animation);

                // Edittext에 있는 값 변수에 저장
                String userName = userNameEditText.getText().toString();
                String userDescription = userDescriptionEditText.getText().toString();
                String userEmail = userEmailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // DB 처리
                int updateUser = userHelper.updateUser(userName, userDescription, userEmail, password);
                System.out.println("updateUser = " + updateUser);
                // 필요한 로직을 수행한 후 액티비티 종료
                finish();
            }
        });
    }
}
