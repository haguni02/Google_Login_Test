package edu.android.google_login_test;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 10;

    private GoogleSignInClient mGoogleSignInClient;
    private SignInButton googleSignInButton;
    private GoogleSignInOptions gso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // 구체화된 GoogleSignInClient 만들기
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // 구글 로그인 버튼
        googleSignInButton = findViewById(R.id.sign_in_button);

        // 로그인 버튼 클릭했을때
        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    private void signIn() {
        // GoogleSignInClient 에서 인텐트 얻기
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        // 인텐트 시작 -> 구글 로그인화면으로 전환, 코드로는 10번 보내기
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // GoogleSignInClient intent 에서 받은 정보 결과
        // 요청 코드가 내가 보낸 코드가 맞다면
        if (requestCode == RC_SIGN_IN) {
            // Task 는 항상 완성된 호출만 받는다, 추가할수 없음
            // 구글 서버로부터 계정의 인텐트 결과 받기
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            // 로그인 결과 처리
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            // account 에 구글 로그인결과 받기 , 실패하면 에러처리
            GoogleSignInAccount account = task.getResult(ApiException.class);

            Log.i("Kim-Yoon", "success");

            // 로그인 성공하면 다음줄 실행
            // 계정 정보로부터 필요한거 뽑아먹기
            String image = null;
            if (account.getPhotoUrl() != null) {
                image = account.getPhotoUrl().toString();
            }
            String email = account.getEmail();
            String name = account.getDisplayName();

            // userActivity 로 뽑아놓은  Intent 정보 보내기
            sendAccountInfo(image, email, name);

        } catch (ApiException e) {
            // 로그인 실패시 익셉션 발생
            Log.w("Kim-Yoon", "signInResultCode: " + e.getStatusCode());
        }
    }

    private void sendAccountInfo(String image, String email, String name) {
        Intent goToGoogleUserActivity = new Intent(this, GoogleUserActivity.class);
        goToGoogleUserActivity.putExtra("USER_IMAGE", image);
        goToGoogleUserActivity.putExtra("USER_EMAIL", email);
        goToGoogleUserActivity.putExtra("USER_NAME", name);
        startActivity(goToGoogleUserActivity);
        finish();
    }
}
