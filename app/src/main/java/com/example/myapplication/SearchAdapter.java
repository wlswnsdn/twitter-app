package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.helper.FollowerHelper;
import com.example.myapplication.helper.PostHelper;
import com.example.myapplication.helper.UserHelper;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private List<SearchItem> searchList;
    private Context context;
    private ILoginWithThis iLoginWithThis;
    private FollowerHelper followerHelper;
    private UserHelper userHelper;
    private PostHelper postHelper;
    //private BtnHelper btnHelper;
    public SearchAdapter(List<SearchItem> searchList, Context context) {
        this.searchList = searchList;
        this.context = context;

      //  this.btnHelper = new BtnHelper(context);
        this.followerHelper = new FollowerHelper(context);
        this.userHelper = new UserHelper(context);
        this.postHelper = new PostHelper(context);
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_recyclerview_item, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        SearchItem searchItem = searchList.get(position);
        holder.userName.setText(searchItem.getUserName());
        int user_id = postHelper.login_idToUser_id(searchItem.getUserName());

        if (userHelper.checkFollowing(user_id)) {
            holder.followBtn.setText("팔로잉");
            holder.followBtn.setBackgroundResource(R.drawable.following_button);
            holder.followBtn.setTextColor(Color.WHITE);
            holder.followBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    iLoginWithThis = new ILoginWithThis();
                    //followerHelper.unfollow(searchItem.getUserName(), iLoginWithThis.getMyId());
                    int user_id = postHelper.login_idToUser_id(searchItem.getUserName());
                    userHelper.deleteFollowing(user_id);
                    userHelper.deleteFollower(user_id);
                    System.out.println("delete following" + user_id);
                    holder.followBtn.setText("팔로우");
                    holder.followBtn.setBackgroundResource(R.drawable.follow_button);
                    holder.followBtn.setTextColor(Color.BLACK);
                    Animation animation = AnimationUtils.loadAnimation(v.getContext(), androidx.transition.R.anim.abc_fade_out);
                    v.startAnimation(animation);
                }
            });
        }else {
            holder.followBtn.setText("팔로우");
            holder.followBtn.setBackgroundResource(R.drawable.follow_button);
            holder.followBtn.setTextColor(Color.BLACK);
            holder.followBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iLoginWithThis = new ILoginWithThis();
                    //followerHelper.following(searchItem.getUserName(), iLoginWithThis.getMyId());
                    int user_id = postHelper.login_idToUser_id(searchItem.getUserName());
                    userHelper.addFollowing(user_id);
                    userHelper.addFollower(user_id);

                    holder.followBtn.setText("팔로잉");
                    holder.followBtn.setBackgroundResource(R.drawable.following_button);
                    holder.followBtn.setTextColor(Color.WHITE);
                    Animation animation = AnimationUtils.loadAnimation(v.getContext(), androidx.transition.R.anim.abc_fade_out);
                    v.startAnimation(animation);

                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return searchList.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        public ImageView userProfileImg;
        public TextView userName;
        public Button followBtn;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            userProfileImg = itemView.findViewById(R.id.user_profile_img);
            userName = itemView.findViewById(R.id.user_name);
            followBtn = itemView.findViewById(R.id.followbtn);
        }


    }
}