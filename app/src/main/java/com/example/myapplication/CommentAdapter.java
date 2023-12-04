package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {

    private List<CommentItem> itemList;
    private Context context;

    public CommentAdapter(Context context, List<CommentItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_recyclerview_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CommentItem currentItem = itemList.get(position);

        // Set data to views
        holder.userProfileImageView.setImageBitmap(currentItem.getProfileImageResource());
        holder.userNameTextView.setText(currentItem.getUserName());
        holder.commentContentTextView.setText(currentItem.getCommentContent());
//        holder.likeCountTextView.setText(String.valueOf(currentItem.getLikeCount()));
//        holder.commentCountTextView.setText(String.valueOf(currentItem.getCommentCount()));
//        if(currentItem.getIsEditBtnVisible())
//            holder.editImageView.setVisibility(View.VISIBLE);
//        else
//            holder.editImageView.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView userProfileImageView;
        TextView userNameTextView;
        TextView commentContentTextView;
//        TextView likeCountTextView;
//        TextView commentCountTextView;
//        ImageView commentImageView;
//        ImageView likeImageView;
//        ImageView editImageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            userProfileImageView = itemView.findViewById(R.id.user_profile_img);
            userNameTextView = itemView.findViewById(R.id.user_name);
            commentContentTextView = itemView.findViewById(R.id.comment_content);
//            likeCountTextView = itemView.findViewById(R.id.post_like_count);
//            commentCountTextView = itemView.findViewById(R.id.comment_count);
//            commentImageView = itemView.findViewById(R.id.comment);
//            likeImageView = itemView.findViewById(R.id.post_like);
//            editImageView = itemView.findViewById(R.id.editImageView);

//            // 대댓글 ImageView에 대한 클릭 이벤트 설정
//            commentImageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // 클릭 이벤트가 발생했을 때 CommentPage를 실행하는 코드
//                    Context context = v.getContext();
//                    Intent intent = new Intent(context, CommentPage.class);
//
//                    context.startActivity(intent);
//                }
//            });
//
//            // 댓글 좋아요 대한 클릭 이벤트 설정
//            likeImageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // 좋아요 클릭 여부에 따라 조건문으로 이미지 변경해야 함
//                    // 좋아요 누른 상태 이미지
//                    likeImageView.setImageResource(R.drawable.baseline_favorite_24);
//
//                    // 좋아요 안누른 상태 이미지
//                    //likeImageView.setImageResource(R.drawable.baseline_favorite_border_black_24);
//                }
//            });
//
//            // 댓글 수정 버튼
//            editImageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // 클릭 했을 때 수정 페이지로 이동
//                    Context context = v.getContext();
//                    Intent intent = new Intent(context, EditPage.class);
//
//                    context.startActivity(intent);
//                }
//            });
        }
    }
}
