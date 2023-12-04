package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.helper.ImageHelper;
import com.example.myapplication.helper.ImageTableHelper;
import com.example.myapplication.helper.TableHelper;
import com.example.myapplication.helper.UserHelper;

import java.io.ByteArrayOutputStream;

public class Register extends AppCompatActivity {

    private EditText idInput;
    private EditText pwInput;
    private EditText nameInput;
    private EditText descriptionInput;
    private EditText emailInput;
    private UserHelper userHelper;
    private TableHelper tableHelper;
    private ImageHelper imageHelper;
    private ImageTableHelper imageTableHelper;
    private ILoginWithThis iLoginWithThis;
    private ImageView imageView;
    private Button uploadImageBtn;
    private static final int PICK_IMAGE_REQUEST = 1;
    SQLiteDatabase database;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        idInput = findViewById(R.id.id_input);
        pwInput = findViewById(R.id.pw_input);
        nameInput = findViewById(R.id.name_input);
        descriptionInput = findViewById(R.id.description_input);
        emailInput = findViewById(R.id.email_input);
        imageView = findViewById(R.id.imageView);
        uploadImageBtn = findViewById(R.id.uploadImageBtn);

        uploadImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openImageChooser();
            }
        });


        // 헬퍼 초기화
        userHelper = new UserHelper(this);
        tableHelper = new TableHelper(this);
        imageHelper = new ImageHelper(this);
        //imageTableHelper = new ImageTableHelper(this);

        Button registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String loginId = idInput.getText().toString().trim();
                String password = pwInput.getText().toString().trim();
                String username = nameInput.getText().toString().trim();
                String description = descriptionInput.getText().toString().trim();
                String email = emailInput.getText().toString().trim();
                // String imagePath = imagePathInput.getText().toString().trim(); /
                //saveImageToDatabase();

                // 입력값 검증
                if (loginId.isEmpty() || password.isEmpty() || username.isEmpty() || description.isEmpty() || email.isEmpty()) {
                    // 하나라도 비어 있으면 사용자에게 메시지를 표시하고 리턴
                    Toast.makeText(Register.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return; // 여기서 메소드를 종료하고 더 이상 진행하지 않음
                }



                // 사용자가 이미 존재하는지 확인
                if (tableHelper.isUserExists(loginId)) {
                    Toast.makeText(Register.this, "This user ID already exists", Toast.LENGTH_SHORT).show();
                } else {
                    // 새 사용자 추가
                    boolean success = tableHelper.addUser(loginId, password, username, email, description); // imagePath

                    if (success) {
                        Toast.makeText(Register.this, "Registration successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Register.this, Home.class);
                        iLoginWithThis = new ILoginWithThis();
                        iLoginWithThis.setMyId(loginId);

                        imageView.setDrawingCacheEnabled(true);
                        imageView.buildDrawingCache();
                        Bitmap bitmap = imageView.getDrawingCache();

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] imageByteArray = stream.toByteArray();
                        boolean isSuccess = imageHelper.addImage(imageByteArray);
                        System.out.println("isSuccess = " + isSuccess);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Register.this, "Registration failed", Toast.LENGTH_SHORT).show();
                    }
                }





            }


        });

    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Error loading the selected image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveImageToDatabase() {

        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = imageView.getDrawingCache();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] imageByteArray = stream.toByteArray();

        ContentValues values = new ContentValues();
        values.put("image", imageByteArray);

        long newRowId = database.insert("images", null, values);

        if (newRowId != -1) {
            Toast.makeText(this, "Image saved to database", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error saving image to database", Toast.LENGTH_SHORT).show();
        }
    }



}