package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.dto.PostDTO;
import com.example.myapplication.helper.CommentHelper;
import com.example.myapplication.helper.FindPostHelper;
import com.example.myapplication.helper.ImageHelper;
import com.example.myapplication.helper.PostHelper;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentPage extends Activity {

    private EditText commentEditText;
    private String commentText = "";
    private TextView commentTo;
    private Button replyButton;
    private ImageView cancelCommentImageView;
    private CommentHelper commentHelper;
    private PostHelper postHelper;
    private FindPostHelper findPostHelper;
    private ImageHelper imageHelper;
    private ILoginWithThis iLoginWithThis;
    private CircleImageView circleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_page);

        //circleImageView = findViewById(R.id.circleImageView);
        commentEditText = findViewById(R.id.comment_content);
        replyButton = findViewById(R.id.commentBtn);
        //setCircleImageView();
        //setCommentTo();
        // CircleImageView에 이미지를 설정하는 함수 호출

        // 누구에게 답글 다는지 표시
         //setCommentTo();

        //헬퍼 초기화
        commentHelper = new CommentHelper(this);
        postHelper = new PostHelper(this);
        findPostHelper = new FindPostHelper(this);
        imageHelper = new ImageHelper(this);

        cancelCommentImageView = findViewById(R.id.cancel_comment);
        cancelCommentImageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 클릭 이벤트가 발생했을 때 현재 액티비티 종료
                finish();
            }
        });

        // 답글 버튼 클릭 이벤트 처리
        replyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                //setCircleImageView();
                // EditText에서 텍스트 가져오기
                commentText = commentEditText.getText().toString();
                int post_id = getIntent().getIntExtra("post_id", -1);
                //데이터 베이스 처리
                commentHelper.addComment(commentText, post_id);
                // 화면 종료
                finish();
            }
        });
    }
    private void setCircleImageView() {
        iLoginWithThis = new ILoginWithThis();
        int user_id = postHelper.login_idToUser_id(iLoginWithThis.getMyId());
        byte[] image = imageHelper.getImage(user_id);
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        System.out.println("bitmap = " + bitmap);
        circleImageView.setImageBitmap(bitmap);
    }

    private void setCommentTo() {
        commentTo = findViewById(R.id.commentTo);
        int post_id = getIntent().getIntExtra("post_id", -1);
        PostDTO postDTO = findPostHelper.findWithPostId(post_id);
        String name = postHelper.user_idToLogin_id(postDTO.getUser_id());
        commentTo.setText(name + " 님에게 보내는 답글");
    }
}
