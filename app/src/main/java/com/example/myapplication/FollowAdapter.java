package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.ViewHolder> {

    private List<FollowItem> followItemList;

    public FollowAdapter(List<FollowItem> followingItemList) {
        this.followItemList = followingItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.follow_recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FollowItem item = followItemList.get(position);

        // 이미지 설정 (이미지를 가져오는 방법에 따라 수정 필요)
        holder.imageView.setImageBitmap(item.getProfileImageResource());

        holder.userNameTextView.setText(item.getUserName());
        holder.userDescriptionTextView.setText(item.getUserDescription());
    }

    @Override
    public int getItemCount() {
        return followItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView userNameTextView;
        TextView userDescriptionTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.user_profile_img);
            userNameTextView = itemView.findViewById(R.id.user_name);
            userDescriptionTextView = itemView.findViewById(R.id.user_description);
        }
    }
}
