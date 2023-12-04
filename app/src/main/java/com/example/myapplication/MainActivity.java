package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.helper.ImageHelper;
import com.example.myapplication.helper.ImageTableHelper;
import com.example.myapplication.helper.LoginHelper;
import com.example.myapplication.helper.TableHelper;
import com.example.myapplication.helper.UserTableHelper;

public class MainActivity extends AppCompatActivity {

    private TableHelper tableHelper;
    private LoginHelper loginHelper;
    private ImageTableHelper imageTableHelper;
    private ImageHelper imageHelper;
    private ListView listView;

    private UserTableHelper userTableHelper;
    private EditText idInput;
    private EditText pwInput;
    private ILoginWithThis iLoginWithThis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        idInput = findViewById(R.id.id_input);
        pwInput = findViewById(R.id.pw_input);


        //헬퍼들 초기화
        tableHelper = new TableHelper(this);
        loginHelper = new LoginHelper(this);
        imageTableHelper = new ImageTableHelper(this);
        iLoginWithThis = new ILoginWithThis();
        imageHelper = new ImageHelper(this);
        userTableHelper = new UserTableHelper(this);

        //imageTableHelper.simpleFunc();
       // tableHelper.simpleFunc();
        userTableHelper.simpleFunc();

//        int result = tableHelper.insertSampleData();
//        System.out.println("followresult = " + result);
        Button loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 애니메이션
                Animation animation = AnimationUtils.loadAnimation(view.getContext(), androidx.transition.R.anim.abc_fade_out);
                view.startAnimation(animation);

                // 로그인 버튼이 클릭되었을 때 실행되는 코드
                String id = idInput.getText().toString();
                String password = pwInput.getText().toString();

                // 여기서 데이터베이스 처리
                boolean login = loginHelper.login(id, password);
                if(login){
                    iLoginWithThis.setMyId(id);
                    Intent intent = new Intent(MainActivity.this, Home.class);
                    startActivity(intent);
                }
                else{
                    showLoginFailedToast();
                }
            }
        });

        // 'Register' 텍스트를 눌렀을 때
        TextView registerTextView = findViewById(R.id.moveToRegisterBtn);
        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 애니메이션
                Animation animation = AnimationUtils.loadAnimation(view.getContext(), androidx.transition.R.anim.abc_fade_out);
                view.startAnimation(animation);

                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });





    }
    private void showLoginFailedToast() {
        Context context = getApplicationContext();
        CharSequence text = "login failed.";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}