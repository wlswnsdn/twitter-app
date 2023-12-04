//package com.example.myapplication;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.Button;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.TextView;
//
//public class MainActivity extends AppCompatActivity {
//
//    private EditText idInput;
//    private EditText pwInput;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        idInput = findViewById(R.id.id_input);
//        pwInput = findViewById(R.id.pw_input);
//
//        Button loginBtn = findViewById(R.id.loginBtn);
//        loginBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // 로그인 버튼이 클릭되었을 때 실행되는 코드
//                String id = idInput.getText().toString();
//                String password = pwInput.getText().toString();
//
//                // 여기서 데이터베이스 처리
//            }
//        });
//
//        // 'Register' 텍스트를 눌렀을 때
//        TextView registerTextView = findViewById(R.id.moveToRegisterBtn);
//        registerTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, Home.class);
//                startActivity(intent);
//            }
//        });
//    }
//
//
//}