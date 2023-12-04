package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
import java.util.Collection;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private PostAdapter adapter;
    private List<PostItem> itemList;
    private static List<PostDTO> myFriendPost;

    public List<PostDTO> getMyFriendPost() {
        return myFriendPost;
    }

    public void setMyFriendPost(List<PostDTO> myFriendPost) {
        HomeFragment.myFriendPost = myFriendPost;
    }

    private FindPostHelper findPostHelper;
    private PostHelper postHelper;
    private LikeHelper likeHelper;
    private CommentHelper commentHelper;
    private UserHelper userHelper;
    private ImageHelper imageHelper;
    private ILoginWithThis iLoginWithThis;
    private ImageView userProfileImg;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //헬퍼 초기화
        findPostHelper = new FindPostHelper(requireContext());
        likeHelper = new LikeHelper(requireContext());
        commentHelper = new CommentHelper(requireContext());
        postHelper = new PostHelper(requireContext());
        imageHelper = new ImageHelper(requireContext());
        userHelper = new UserHelper(requireContext());

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.home_posts);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        userProfileImg = view.findViewById(R.id.user_profile_img);

        setUserImage();
        // Initialize data
        itemList = new ArrayList<>();
        iLoginWithThis = new ILoginWithThis();
        myFriendPost = findPostHelper.findMyFriendPost();
        System.out.println("myFriendPost = " + myFriendPost.size());
        if(!myFriendPost.isEmpty()){
            //팔로우한 애들 게시물이 안 떠.. 이미지도 올리자
            for (PostDTO postDTO : myFriendPost) {
                PostItem postItem= new PostItem(postDTO.getPost_id());
                String login_id = postHelper.user_idToLogin_id(postDTO.getUser_id());
                byte[] image = imageHelper.getImage(postHelper.login_idToUser_id(login_id));
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

                String friendLogin = postHelper.user_idToLogin_id(postDTO.getUser_id());
                String postcontent = postDTO.getPostcontent();
                int postlike_cnt = likeHelper.getLike(postDTO.getPost_id());
                int commentNum = commentHelper.getCommentNum(postDTO.getPost_id());
                itemList.add(new PostItem(bitmap, friendLogin, postcontent, postlike_cnt, commentNum, false));
            }
            //Add more items as needed
        }


        // Initialize adapter
        adapter = new PostAdapter(requireContext(), itemList);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void setUserImage() {
        iLoginWithThis = new ILoginWithThis();
        int myUser_id = findPostHelper.login_idToUser_id(iLoginWithThis.getMyId());
        UserDTO user = userHelper.getUser(myUser_id);
        byte[] image = imageHelper.getMyImage();
        // byte 배열을 Bitmap으로 변환
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        userProfileImg.setImageBitmap(bitmap);

    }
}
