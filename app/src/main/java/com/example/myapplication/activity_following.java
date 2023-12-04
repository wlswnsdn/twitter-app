package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.dto.UserDTO;
import com.example.myapplication.helper.FindPostHelper;
import com.example.myapplication.helper.FollowerHelper;
import com.example.myapplication.helper.ImageHelper;
import com.example.myapplication.helper.UserHelper;

import java.util.ArrayList;
import java.util.List;

public class activity_following extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FollowAdapter adapter;
    private FindPostHelper findPostHelper;
    private ImageHelper imageHelper;
    private UserHelper userHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);

        recyclerView = findViewById(R.id.following_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //헬퍼 초기화
        findPostHelper = new FindPostHelper(this);
        userHelper = new UserHelper(this);
        imageHelper = new ImageHelper(this);

        //List<FollowItem> followingItemList = generateSampleData(); // 여기에 실제 데이터를 추가하세요.
        ArrayList<Integer> myFollowing = findPostHelper.getMyFollowing();
        List<FollowItem> itemList = new ArrayList<>();
        for (Integer following_id : myFollowing) {
            UserDTO user = userHelper.getUser(following_id);
            FollowItem followItem = new FollowItem(user.getUsername());
            byte[] image = imageHelper.getImage(following_id);
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            System.out.println("bitmap = " + bitmap);
            itemList.add(new FollowItem(bitmap,user.getLogin_id(), user.getUser_description()));
        }
        adapter = new FollowAdapter(itemList);
        recyclerView.setAdapter(adapter);

        ImageView backImageView = findViewById(R.id.back);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // '뒤로' 버튼을 클릭했을 때 현재 액티비티를 종료합니다.
                finish();
            }
        });
    }

}
