package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.dto.PostDTO;
import com.example.myapplication.dto.UserDTO;
import com.example.myapplication.helper.CommentHelper;
import com.example.myapplication.helper.FindPostHelper;
import com.example.myapplication.helper.ImageHelper;
import com.example.myapplication.helper.LikeHelper;
import com.example.myapplication.helper.PostHelper;
import com.example.myapplication.helper.UserHelper;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private ImageView userProfileImage;
    private TextView userName;
    private TextView userDescription;
    private TextView userFollowing;
    private TextView userFollower;
    private RecyclerView userPosts;
    private RecyclerView recyclerView;
    private PostAdapter adapter;
    private List<PostItem> itemList;
    private Button editProfileBtn;
    private ILoginWithThis iLoginWithThis;
    private FindPostHelper findPostHelper;
    private PostHelper postHelper;
    private CommentHelper commentHelper;
    private LikeHelper likeHelper;
    private UserHelper userHelper;
    private ImageHelper imageHelper;
    private static List<PostDTO> myPost;
    public List<PostDTO> getMyPost() {
        return myPost;
    }

    public void setMyPost(List<PostDTO> myPost) {
        this.myPost = myPost;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize views
        userProfileImage = view.findViewById(R.id.user_profile_img);
        userName = view.findViewById(R.id.user_name);
        userDescription = view.findViewById(R.id.user_description);
        userFollowing = view.findViewById(R.id.user_following);
        userFollower = view.findViewById(R.id.user_follower);
        userPosts = view.findViewById(R.id.user_posts);
        editProfileBtn = view.findViewById(R.id.editProfileBtn);



        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.user_posts);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));


        // Initialize data

        //헬퍼 초기화
        findPostHelper = new FindPostHelper(requireContext());
        postHelper = new PostHelper(requireContext());
        commentHelper = new CommentHelper(requireContext());
        likeHelper = new LikeHelper(requireContext());
        userHelper = new UserHelper(requireContext());
        imageHelper = new ImageHelper(requireContext());

        // Set user profile
        setUserProfile();

        itemList = new ArrayList<>();
        // loginid -> userid
        iLoginWithThis = new ILoginWithThis();
        int myUser_id = postHelper.login_idToUser_id(iLoginWithThis.getMyId());

        myPost = findPostHelper.findMyPost(myUser_id);
        for (PostDTO postDTO : myPost) {
            PostItem postItem = new PostItem(postDTO.getPost_id());
            byte[] image = imageHelper.getMyImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);


            String postcontent = postDTO.getPostcontent();
            int postlike_cnt = likeHelper.getLike(postDTO.getPost_id());
            int commentNum = commentHelper.getCommentNum(postDTO.getPost_id());
            itemList.add(new PostItem(bitmap, iLoginWithThis.getMyId(), postcontent, postlike_cnt,commentNum,true));
        }



        // Initialize adapter
        adapter = new PostAdapter(requireContext(), itemList);
        recyclerView.setAdapter(adapter);

        // Set click listener for '팔로잉' TextView
        userFollowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 애니메이션
                Animation animation = AnimationUtils.loadAnimation(requireContext(), androidx.transition.R.anim.abc_fade_out);
                v.startAnimation(animation);

                // 클릭 시 activity_following으로 이동하는 Intent 생성
                Intent intent = new Intent(requireContext(), activity_following.class);
                startActivity(intent);
            }
        });

        // Set click listener for '팔로우' TextView
        userFollower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 애니메이션
                Animation animation = AnimationUtils.loadAnimation(requireContext(), androidx.transition.R.anim.abc_fade_out);
                v.startAnimation(animation);

                // 클릭 시 activity_follower으로 이동하는 Intent 생성
                Intent intent = new Intent(requireContext(), Follower.class);
                startActivity(intent);
            }
        });

        // Set click listener for '프로필 수정' 버튼
        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 애니메이션
                Animation animation = AnimationUtils.loadAnimation(requireContext(), androidx.transition.R.anim.abc_fade_out);
                v.startAnimation(animation);

                // 클릭 시 activity_follower으로 이동하는 Intent 생성
                Intent intent = new Intent(requireContext(), EditProfile.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void setUserProfile() {
        iLoginWithThis = new ILoginWithThis();
        int myUser_id = findPostHelper.login_idToUser_id(iLoginWithThis.getMyId());
        UserDTO user = userHelper.getUser(myUser_id);
        byte[] image = imageHelper.getMyImage();
        // byte 배열을 Bitmap으로 변환
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);


        // Set user name and description
        userName.setText(iLoginWithThis.getMyId());
        userDescription.setText(user.getUser_description());

        // Set user profile image
        userProfileImage.setImageBitmap(bitmap);

        // For demonstration purposes, set some dummy follower and following counts
        ArrayList<Integer> myFollowing = findPostHelper.getMyFollowing();
        userFollowing.setText(myFollowing.size() + " 팔로잉");
        ArrayList<Integer> myFollower = findPostHelper.getMyFollower();

        userFollower.setText(myFollower.size() + " 팔로워");
    }
}
