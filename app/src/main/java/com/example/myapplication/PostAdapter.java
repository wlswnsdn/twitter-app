package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.dto.PostDTO;
import com.example.myapplication.helper.LikeHelper;
import com.example.myapplication.helper.PostHelper;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

    private List<PostItem> itemList;
    private Context context;

    private static ProfileFragment profileFragment;
    private static HomeFragment homeFragment;
    private PostHelper postHelper;
    private LikeHelper likeHelper;


    public PostAdapter(Context context, List<PostItem> itemList) {
        this.context = context;
        this.itemList = itemList;
        this.postHelper = new PostHelper(context);
        this.likeHelper = new LikeHelper(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_recyclerview_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PostItem currentItem = itemList.get(position);

        // Set data to views
        holder.userProfileImageView.setImageBitmap(currentItem.getProfileImageResource());
        holder.userNameTextView.setText(currentItem.getUserName());
        holder.postContentTextView.setText(currentItem.getPostContent());
        holder.likeCountTextView.setText(String.valueOf(currentItem.getLikeCount()));
        holder.commentCountTextView.setText(String.valueOf(currentItem.getCommentCount()));
        // 수정, 삭제 버튼 투명 여부
        if(currentItem.getIsEditBtnVisible()) {
            holder.editImageView.setVisibility(View.VISIBLE);
            holder.deleteImageView.setVisibility(View.VISIBLE);
        }
        else {
            holder.editImageView.setVisibility(View.GONE);
            holder.deleteImageView.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView userProfileImageView;
        TextView userNameTextView;
        TextView postContentTextView;
        TextView likeCountTextView;
        TextView commentCountTextView;
        ImageView commentImageView;
        ImageView likeImageView;
        ImageView editImageView;
        ImageView deleteImageView;
        RelativeLayout postLayout;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            postLayout = itemView.findViewById(R.id.post_layout);
            userProfileImageView = itemView.findViewById(R.id.user_profile_img);
            userNameTextView = itemView.findViewById(R.id.user_name);
            postContentTextView = itemView.findViewById(R.id.post_content);
            likeCountTextView = itemView.findViewById(R.id.post_like_count);
            commentCountTextView = itemView.findViewById(R.id.comment_count);
            commentImageView = itemView.findViewById(R.id.comment);
            likeImageView = itemView.findViewById(R.id.post_like);
            editImageView = itemView.findViewById(R.id.editImageView);
            deleteImageView = itemView.findViewById(R.id.deleteBtn);

            // 댓글 ImageView에 대한 클릭 이벤트 설정
            commentImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 클릭 이벤트가 발생했을 때 CommentPage를 실행하는 코드
                    Context context = v.getContext();
                    profileFragment = new ProfileFragment();
                    homeFragment = new HomeFragment();
                    //조건문: 홈이냐 내 프로필이냐 myFriendPost or myPost
                    List<PostDTO> myFriendPost = homeFragment.getMyFriendPost();
                    List<PostDTO> myPost = profileFragment.getMyPost();

//                    List<PostDTO> allPost = new ArrayList<>(myFriendPost);
//                    allPost.addAll(myPost);
//                    System.out.println("allPost.size() = " + allPost.size());
                    int clickedPostId = myPost.get(getAbsoluteAdapterPosition()).getPost_id();
                    Intent intent = new Intent(context, CommentPage.class);
                    intent.putExtra("post_id", clickedPostId);
                    context.startActivity(intent);
                }
            });

            // 좋아요 대한 클릭 이벤트 설정
            likeImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 좋아요 클릭 여부에 따라 조건문으로 이미지 변경해야 함
                    // 좋아요 누른 상태 이미지
                    // 좋아요 버튼을 눌렀을 때
                    profileFragment = new ProfileFragment();
                    homeFragment = new HomeFragment();
                    //조건문: 홈이냐 내 프로필이냐 myFriendPost or myPost
                    List<PostDTO> myFriendPost = homeFragment.getMyFriendPost();
                    List<PostDTO> myPost = profileFragment.getMyPost();

                    List<PostDTO> allPost = new ArrayList<>(myFriendPost);
                    allPost.addAll(myPost);
                    int clickedPostId = allPost.get(getAbsoluteAdapterPosition()).getPost_id();
                    // 난 이미 눌렀었음
                    if(likeHelper.didILike(clickedPostId)){
                        likeHelper.deleteLike(clickedPostId);
                        likeImageView.setImageResource(R.drawable.baseline_favorite_border_black_24);
                    }
                    else {
                        likeHelper.addLike(clickedPostId);
                        likeImageView.setImageResource(R.drawable.baseline_favorite_24);
                    }



                }
            });

            // 수정 버튼
            editImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 클릭 했을 때 수정 페이지로 이동
                    Context context = v.getContext();
                    profileFragment = new ProfileFragment();
                    List<PostDTO> myPost = profileFragment.getMyPost();
                    int clickedPostId = myPost.get(getAbsoluteAdapterPosition()).getPost_id();
                    Intent intent = new Intent(context, EditPage.class);
                    intent.putExtra("post_id", clickedPostId);
                    context.startActivity(intent);
                }
            });

            // 글 누르면 글 자세히 보기 페이지로 이동
            postLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 클릭 했을 때 상세보기로 이동
                    Context context = v.getContext();
                    profileFragment = new ProfileFragment();
                    homeFragment = new HomeFragment();
                    //조건문: 홈이냐 내 프로필이냐 myFriendPost or myPost
                    List<PostDTO> myFriendPost = homeFragment.getMyFriendPost();
                    List<PostDTO> myPost = profileFragment.getMyPost();
                    //int clickedPostId = allPost.get(getAbsoluteAdapterPosition()).getPost_id();
//                    if(myPost.get(getAbsoluteAdapterPosition()).getPost_id()==1)
                    int position = getAbsoluteAdapterPosition();
                    int clickedPostId;
                    if (position != RecyclerView.NO_POSITION && position < myPost.size()) {
                        System.out.println("myPost에 있음");
                        clickedPostId = myPost.get(position).getPost_id();

                        // 이제 clickedPostId를 사용할 수 있음
                    } else {
                        // myPost에서 해당 위치에 대한 아이템이 없을 때 처리할 내용
                        System.out.println("myPost에 없음");
                        clickedPostId = myFriendPost.get(getAbsoluteAdapterPosition()).getPost_id();
                    }
                    Intent intent = new Intent(context, PostDetail.class);
                    intent.putExtra("post_id", clickedPostId);
                    context.startActivity(intent);


                }
            });

            // 게시글 삭제 클릭 이벤트
            deleteImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                    builder.setTitle("삭제").setMessage("게시글을 삭제하시겠습니까?");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 삭제 기능


                            profileFragment = new ProfileFragment();
                            List<PostDTO> myPost = profileFragment.getMyPost();
                            int clickedPostId = myPost.get(getAbsoluteAdapterPosition()).getPost_id();
                            postHelper.deletePost(clickedPostId);
                        }
                    });
                    builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 취소 기능이므로 아무 기능 없음

                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }
            });
        }
    }
}
