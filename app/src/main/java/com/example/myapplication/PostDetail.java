package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.myapplication.dto.CommentDTO;
import com.example.myapplication.dto.PostDTO;
import com.example.myapplication.helper.CommentHelper;
import com.example.myapplication.helper.FindPostHelper;
import com.example.myapplication.helper.ImageHelper;
import com.example.myapplication.helper.LikeHelper;
import com.example.myapplication.helper.PostHelper;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PostDetail extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CommentAdapter adapter;
    private List<CommentItem> itemList;

    // Add variables for profile image, username, and post content
    private ImageView userProfileImage;
    private TextView postUsername;
    private TextView postContent;
    private FindPostHelper findPostHelper;
    private PostHelper postHelper;
    private CommentHelper commentHelper;
    private LikeHelper likeHelper;
    private ImageHelper imageHelper;
    private TextView likeCount;
    private TextView commentCount;
    private Button aiBtn;
    private TextView aiTextView;




    private static List<CommentDTO> myComment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        //헬퍼 초기화
        findPostHelper = new FindPostHelper(this);
        postHelper = new PostHelper(this);
        commentHelper = new CommentHelper(this);
        likeHelper = new LikeHelper(this);
        imageHelper = new ImageHelper(this);

        // 게시글 정보 세팅
        // Initialize profile image, username, and post content views
        userProfileImage = findViewById(R.id.user_profile_img);
        postUsername = findViewById(R.id.post_username);
        postContent = findViewById(R.id.post_content);
        likeCount = findViewById(R.id.post_like_count);
        commentCount = findViewById(R.id.comment_count);
        aiTextView = findViewById(R.id.aiTextView);
        recyclerView = findViewById(R.id.recyclerView);

        // Load user data
        loadUserData();

        // 글에 달린 댓글들
        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize data
        itemList = new ArrayList<>();
        int post_id = getIntent().getIntExtra("post_id", -1);
        myComment = commentHelper.findCommentByPostId(post_id);
        for (CommentDTO commentDTO : myComment) {
            String login_id = postHelper.user_idToLogin_id(commentDTO.getUser_id());
            int user_id = findPostHelper.login_idToUser_id(login_id);
            byte[] image = imageHelper.getImage(user_id);
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            String comment_content = commentDTO.getComment_content();
            itemList.add(new CommentItem(bitmap, login_id, comment_content, 0, 0, true));
        }

        // Initialize adapter
        adapter = new CommentAdapter(this, itemList);
        recyclerView.setAdapter(adapter);

        // Setup back button click listener
        ImageView backButton = findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Finish the current activity when back button is clicked
                Animation animation = AnimationUtils.loadAnimation(view.getContext(), androidx.transition.R.anim.abc_fade_out);
                view.startAnimation(animation);
                finish();
            }

        });

        String userPrompt = postContent.getText().toString();
        new ChatGPTAsyncTask().execute(userPrompt);
//        likeCount.setText("1");
//        commentCount.setText("99999");
// Load user data (Replace with your actual data loading logic)
// loadUserData(R.drawable.user_profile); // Replace with your actual image resource

    }

    private void loadUserData() {
        // Replace the following lines with your actual data loading logic
        //userProfileImage.setImageResource(imgurl);
        int post_id = getIntent().getIntExtra("post_id", -1);

        PostDTO postDTO = findPostHelper.findWithPostId(post_id);
        String login_id = postHelper.user_idToLogin_id(postDTO.getUser_id());
        int postlike_cnt = likeHelper.getLike(postDTO.getPost_id());
        int commentNum = commentHelper.getCommentNum(post_id);
        int user_id = findPostHelper.login_idToUser_id(login_id);
        byte[] image = imageHelper.getImage(user_id);
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

        userProfileImage.setImageBitmap(bitmap);
        postUsername.setText(login_id);
        postContent.setText(postDTO.getPostcontent());
        likeCount.setText(String.valueOf(postlike_cnt));
        commentCount.setText(String.valueOf(commentNum));
        // You can fetch the actual user data from your backend or any other source
    }

    private class ChatGPTAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String userPrompt = params[0];
            return chatGPT(userPrompt);
        }

        @Override
        protected void onPostExecute(String result) {
            aiTextView.setText(result);
        }
    }

    private String chatGPT(String prompt) {
// Replace "YOUR_API_KEY_HERE" with your actual OpenAI API key
        String apiKey = "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";
        String model = "gpt-3.5-turbo";
        String url = "https://api.openai.com/v1/chat/completions";

        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");

            String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "Show me an emoji from my memory. Never engage in conversation and only show one emoji."+ "\"}]}";
            connection.setDoOutput(true);
            try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
                writer.write(body);
                writer.flush();
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
                return extractMessageFromJSONResponse(response.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
            return "Error communicating with ChatGPT" + e.getMessage();
        }
    }

    private String
    extractMessageFromJSONResponse(String response) {
        int start = response.indexOf("content") + 11;
        int end = response.indexOf("\"", start);
        return response.substring(start, end);
    }
}