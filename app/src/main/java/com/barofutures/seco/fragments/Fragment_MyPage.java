/*
Copyright 2021 Baro Futures

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
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

import com.barofutures.seco.ChallengeHistoryActivity;
import com.barofutures.seco.DonationHistoryActivity;
import com.barofutures.seco.GoogleLogInActivity;
import com.barofutures.seco.LicenceInfoActivity;
import com.barofutures.seco.MainActivity;
import com.barofutures.seco.MySetiActivity;
import com.barofutures.seco.R;
import com.barofutures.seco.VerifiedHistoryActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class Fragment_MyPage extends Fragment {
    private ViewGroup viewGroup;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private Bitmap bitmap;
    private TextView userEmail, userNickname;
    private CircleImageView userPhoto;
    private Button logOutButton, licenseButton, setiButton, verifiedButton, challengeHistoryButton, donationHistoryButton;

    // Instance ?????? ?????????
    public static Fragment_MyPage newInstance(){
        return new Fragment_MyPage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View ????????? ??? ??????
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_mypage, container, false);
        initView();

        // ?????? ????????? ?????? ????????????
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);

        // ?????? ?????? ??????
        if (currentUser != null) {
            Thread mThread = new Thread() {
                @Override
                public void run() {
                    try {
                        // ?????? ?????? ???????????? ?????????, ?????? ??????
                        userEmail.setText(currentUser.getEmail());
                        userNickname.setText(currentUser.getDisplayName());
                        // ?????? ?????? ???????????? ????????? ????????? ?????? PhotoUrl ????????????
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
                //????????? bitmap??????
                userPhoto.setImageBitmap(bitmap);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        //???????????? ??????
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
//                intent.putExtra("email", currentUser.getEmail());
                intent.putExtra("email", MainActivity.userEmail);
                // ????????? ??????: ?????? ??????????????? ??????????????? ????????? onCreate??? ???????????? ?????? onNewIntent??? ???????????? ?????? ??????
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        verifiedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), VerifiedHistoryActivity.class);
                intent.putExtra("email", MainActivity.userEmail);
                startActivity(intent);
            }
        });

        challengeHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ChallengeHistoryActivity.class);
                intent.putExtra("email", MainActivity.userEmail);
                startActivity(intent);
            }
        });

        // ?????? ?????? ?????????
        donationHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), DonationHistoryActivity.class);
                intent.putExtra("email", MainActivity.userEmail);
                startActivity(intent);
            }
        });

        // ???????????? ?????? ?????? ?????? ?????????
        licenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(getActivity(), LicenceInfoActivity.class);
//                startActivity(intent);

                // When the user selects an option to see the licenses:
                startActivity(new Intent(getActivity(), OssLicensesMenuActivity.class));
            }
        });

        return viewGroup;
    }

    // View ????????? ??? ??????
    private void initView(){
        userEmail = (TextView) viewGroup.findViewById(R.id.fragment_mypage_text_email);
        userNickname = (TextView) viewGroup.findViewById(R.id.fragment_mypage_text_nickname);
        userPhoto = (CircleImageView) viewGroup.findViewById(R.id.fragment_mypage_image_userphoto);

        logOutButton = (Button) viewGroup.findViewById(R.id.fragment_mypage_button_logout);
        licenseButton = (Button) viewGroup.findViewById(R.id.fragment_mypage_license_button);

        setiButton = viewGroup.findViewById(R.id.fragment_mypage_to_my_seti_button);
        verifiedButton = viewGroup.findViewById(R.id.fragment_mypage_to_my_verified_button);
        challengeHistoryButton = viewGroup.findViewById(R.id.fragment_mypage_to_challenge_history_button);
        donationHistoryButton = viewGroup.findViewById(R.id.fragment_mypage_to_donation_history_button);
    }

    // ????????????
    private void signOut() {
        // Firebase sign out
        mAuth=FirebaseAuth.getInstance();
        mAuth.signOut();

        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener((Activity) getContext(),
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getContext(), "???????????? ???????????????.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}