package com.barofutures.seco.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.barofutures.seco.GoogleLogInActivity;
import com.barofutures.seco.MyCmiActivity;
import com.barofutures.seco.MyOrderHistoryActivity;
import com.barofutures.seco.MySetiActivity;
import com.barofutures.seco.R;
import com.barofutures.seco.RewardHistoryAcitivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.MalformedURLException;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class Fragment_MyPage extends Fragment {
    private ViewGroup viewGroup;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private Bitmap bitmap;
    private TextView userEmail, userNickname, exampleNickname1, exampleNickname2, exampleNickname3, exampleEmail1, exampleEmail2, exampleEmail3, exampleGrade1, exampleGrade2, exampleGrade3, exampleRating1, exampleRating2, exampleRating3;
    private CircleImageView userPhoto, examplePhoto1, examplePhoto2, examplePhoto3;
    private Button logOutButton, setiButton, cmiButton, rewardButton, orderButton;

    // Instance 반환 메소드
    public static Fragment_MyPage newInstance(){
        return new Fragment_MyPage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View 초기화 및 연결
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_mypage, container, false);
        initView();

        // 구글 로그인 정보 가져오기
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);

        //로그아웃 버튼
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
                Intent intent = new Intent(getContext(), GoogleLogInActivity.class);
                startActivity(intent);
            }
        });

        setiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), MySetiActivity.class);
                startActivity(intent);
            }
        });

        cmiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), MyCmiActivity.class);
                startActivity(intent);
            }
        });

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), MyOrderHistoryActivity.class);
                startActivity(intent);
            }
        });


        // 유저 정보 표시
        if (currentUser != null) {
            Thread mThread = new Thread() {
                @Override
                public void run() {
                    try {
                        // 현재 유저 정보에서 이메일, 이름 표시
                        userEmail.setText(currentUser.getEmail());
                        userNickname.setText(currentUser.getDisplayName());
                        // 현재 유저 정보에서 사용자 정보를 통해 PhotoUrl 가져오기
                        URL url = new URL(currentUser.getPhotoUrl().toString());
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setDoInput(true);
                        conn.connect();
                        InputStream is = conn.getInputStream();
                        bitmap = BitmapFactory.decodeStream(is);
                    } catch (MalformedURLException ee) {
                        ee.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            mThread.start();
            try {
                mThread.join();
                //변환한 bitmap적용
                userPhoto.setImageBitmap(bitmap);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        // 버튼 클릭 리스너
        rewardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), RewardHistoryAcitivity.class);
                startActivity(intent);
            }
        });

        return viewGroup;
    }

    // View 초기화 및 연결
    private void initView(){
        userEmail = (TextView) viewGroup.findViewById(R.id.fragment_mypage_text_email);
        userNickname = (TextView) viewGroup.findViewById(R.id.fragment_mypage_text_nickname);
        userPhoto = (CircleImageView) viewGroup.findViewById(R.id.fragment_mypage_image_userphoto);
        logOutButton = (Button) viewGroup.findViewById(R.id.fragment_mypage_button_logout);
        examplePhoto1=(CircleImageView)viewGroup.findViewById(R.id.fragment_mypage_image_userphoto_1);
        examplePhoto2=(CircleImageView)viewGroup.findViewById(R.id.fragment_mypage_image_userphoto_2);
        examplePhoto3=(CircleImageView)viewGroup.findViewById(R.id.fragment_mypage_image_userphoto_3);
        exampleEmail1=viewGroup.findViewById(R.id.fragment_mypage_text_email_1);
        exampleEmail2=viewGroup.findViewById(R.id.fragment_mypage_text_email_2);
        exampleEmail3=viewGroup.findViewById(R.id.fragment_mypage_text_email_3);
        exampleNickname1=viewGroup.findViewById(R.id.fragment_mypage_text_nickname_1);
        exampleNickname2=viewGroup.findViewById(R.id.fragment_mypage_text_nickname_2);
        exampleNickname3=viewGroup.findViewById(R.id.fragment_mypage_text_nickname_3);
        exampleGrade1=viewGroup.findViewById(R.id.fragment_mypage_text_grade_1);
        exampleGrade2=viewGroup.findViewById(R.id.fragment_mypage_text_grade_2);
        exampleGrade3=viewGroup.findViewById(R.id.fragment_mypage_text_grade_3);
        exampleRating1=viewGroup.findViewById(R.id.fragment_mypage_text_rating_1);
        exampleRating2=viewGroup.findViewById(R.id.fragment_mypage_text_rating_2);
        exampleRating3=viewGroup.findViewById(R.id.fragment_mypage_text_rating_3);
        setiButton=viewGroup.findViewById(R.id.fragment_mypage_to_my_seti_button);
        cmiButton=viewGroup.findViewById(R.id.fragment_mypage_to_my_cmi_button);
        rewardButton=viewGroup.findViewById(R.id.fragment_mypage_to_reward_history_button);
        orderButton=viewGroup.findViewById(R.id.fragment_mypage_to_order_history_button);
        cmiButton=viewGroup.findViewById(R.id.fragment_mypage_to_my_cmi_button);
    }

    // 로그아웃
    private void signOut() {
        // Firebase sign out
        mAuth=FirebaseAuth.getInstance();
        mAuth.signOut();

        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener((Activity) getContext(),
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}