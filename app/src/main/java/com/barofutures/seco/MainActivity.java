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
package com.barofutures.seco;

import static com.barofutures.seco.PushListenerService.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.PinpointConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager;
import com.barofutures.seco.fragments.Fragment_CarbonDiet;
import com.barofutures.seco.fragments.Fragment_Commerce;
import com.barofutures.seco.fragments.Fragment_Home;
import com.barofutures.seco.fragments.Fragment_MyPage;
import com.barofutures.seco.fragments.Fragment_Recipe;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity{
    // Splash?????? ?????? ????????? ????????????
    private FirebaseUser currentUser;
    private String userName;
    public static String userEmail;

    // Toolbar: ?????? ?????? ?????????, ?????? ??????(text) ?????? ???
    private Toolbar toolbar;
    private ActionBar actionBar;

    // Bottom Navigation View: ?????? ???, ??????, ?????? ?????? ??????
    private BottomNavigationView bottomNavigationView;

    // Fragments
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Fragment_Home fragHome;
    private Fragment_Recipe fragRecipe;
    private Fragment_MyPage fragMypage;
    private Fragment_Commerce fragShopping;
    private Fragment_CarbonDiet fragCarbonDiet;

    // ??????????????? ???????????? ????????? ????????? ?????? ??????
    private long backKeyPressedTime = 0;
    private Toast toast;

    // AWS Pinpoint manager
    private static PinpointManager pinpointManager;

    // ?????? ?????????????????? ?????? ?????? ?????? Context
    public static Context mainContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ????????? ?????? ??????
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        // ?????? ???, ??????????????? ????????? ??? ??????
        initView();


        // Intent
        Intent intent = getIntent();
        userEmail = intent.getExtras().getString("email");
        Log.d("MainActivity", "userEmail = " + userEmail);

        // ???????????? ?????? ?????? ??? ??? ??????????????? ????????????
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_layout, fragHome, "home").commitAllowingStateLoss();
        // ?????? ??????????????? ??? ?????? ??????????????? ??????
        bottomNavigationView.setSelectedItemId(R.id.bottomnavigation_menu_home);

        /*
         * AWS - FCM - Push notification
         */
        // Initialize PinpointManager
        getPinpointManager(getApplicationContext());

        /*
         * Toolbar: ?????? ?????? ?????????, ?????? ??????(text) ?????? ???
         */

        //Apply the toolbar as action bar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Status Bar ???????????? Padding ??????
        toolbar.setPadding(0,getStatusBarHeight(), 0, 0);
        bottomNavigationView.setPadding(0, 0, 0, getStatusBarHeight());

        actionBar = getSupportActionBar();

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);    //remove the basic title


        //get id of bottomNavigationView
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        /*
         * Botton Navigation View: ?????? ???, ??????, ?????? ?????? ??????
         */

        //When the icon of bottomNavigationView is selected, listener is added so that the desired fragment can be displayed.
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottomnavigation_menu_home:
                        changeFragment("home", fragHome);
                        return true;
                    case R.id.bottomnavigation_menu_recipe:
                        changeFragment("recipe", fragRecipe);
                        return true;
                    case R.id.bottomnavigation_menu_mypage:
                        changeFragment("mypage", fragMypage);
                        return true;
                    case R.id.bottomnavigation_menu_commerce:
                        changeFragment("commerce", fragShopping);
                        return true;
                    case R.id.bottomnavigation_menu_carbon_diet:
                        changeFragment("carbonDiet", fragCarbonDiet);
                        return true;
                }
                return false;
            }
        });
    }

    /*
     * back button click event
     */
    @Override
    public void onBackPressed() {
//        // back button??? ???????????? drawer??? ???????????? ???
//        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            drawerLayout.closeDrawer(GravityCompat.START);          // Drawer??? ??????
//        } else {
            if (fragmentManager.getBackStackEntryCount() == 0) {
                if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
                    backKeyPressedTime = System.currentTimeMillis();
                    toast = Toast.makeText(this, "???????????? ????????? ?????? ??? ????????? ???????????????.", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
                    // ?????? Kill
                    finishAffinity();
                    toast.cancel();
                }
            } else {
                super.onBackPressed();
            }
//        }
    }

    public FragmentManager passFragmentManager() {
        return this.fragmentManager;
    }


    /*
     * aws - FCM - push notification
     */


    // Create Amazon Pinpoint client
    public static PinpointManager getPinpointManager(final Context applicationContext) {
        if (pinpointManager == null) {
            final AWSConfiguration awsConfig = new AWSConfiguration(applicationContext);
            AWSMobileClient.getInstance().initialize(applicationContext, awsConfig, new Callback<UserStateDetails>() {
                @Override
                public void onResult(UserStateDetails userStateDetails) {
                    Log.i("INIT", userStateDetails.getUserState().toString());
                }

                @Override
                public void onError(Exception e) {
                    Log.e("INIT", "Initialization error.", e);
                }
            });

            PinpointConfiguration pinpointConfig = new PinpointConfiguration(
                    applicationContext,
                    AWSMobileClient.getInstance(),
                    awsConfig);

            pinpointManager = new PinpointManager(pinpointConfig);

            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "getInstanceId failed", task.getException());
                                return;
                            }
                            final String token = task.getResult().getToken();
                            Log.d(TAG, "Registering push notifications token: " + token);
                            pinpointManager.getNotificationClient().registerDeviceToken(token);
                        }
                    });
        }
        return pinpointManager;
    }

    // Fragment ??????
    private void changeFragment(String inFragStackName, Fragment frag) {
        if (fragmentManager.findFragmentByTag("detail") != null) {
            fragmentManager.popBackStack("detail", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        fragmentManager.popBackStack(inFragStackName, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_layout, frag);
        fragmentTransaction.commit();
    }

    // Fragment, View ????????? ??? ??????
    private void initView() {
        //get id of bottomNavigationView
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        //Apply the toolbar as action bar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        // Create Fragments
        fragHome = new Fragment_Home();
        fragRecipe = new Fragment_Recipe();
        fragMypage = new Fragment_MyPage();
        fragShopping = new Fragment_Commerce();
        fragCarbonDiet = new Fragment_CarbonDiet();

        mainContext=this;
    }

    //status bar??? ?????? ??????
    public int getStatusBarHeight()
    {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
            result = getResources().getDimensionPixelSize(resourceId);

        return result;
    }

}