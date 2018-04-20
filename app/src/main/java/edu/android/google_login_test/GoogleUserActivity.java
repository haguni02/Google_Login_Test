package edu.android.google_login_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class GoogleUserActivity extends AppCompatActivity {

    private String mReceivedImage;
    private String mReceivedName;
    private String mReceivedEmail;

    private ImageView mUserImageView;
    private TextView mUserNameTxtView;
    private TextView muserEmailTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_user);

        // 초기화
        mUserImageView = findViewById(R.id.userImage);
        mUserNameTxtView = findViewById(R.id.userName);
        muserEmailTxtView = findViewById(R.id.userEmail);

        // 인텐트로 받은 정보 저장하기
        mReceivedImage = getIntent().getStringExtra("USER_IMAGE");
        mReceivedName = getIntent().getStringExtra("USER_NAME");
        mReceivedEmail = getIntent().getStringExtra("USER_EMAIL");

        // 받은 데이터 UI에 설정하기
        Picasso.with(this).load(mReceivedImage).placeholder(R.mipmap.ic_launcher).into(mUserImageView);
        mUserNameTxtView.setText(mReceivedName);
        muserEmailTxtView.setText(mReceivedEmail);
    }
}
