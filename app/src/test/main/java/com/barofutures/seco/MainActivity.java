package com.barofutures.seco;

import static com.barofutures.seco.PushListenerService.TAG;

import android.content.Context;
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
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity implements RoutineStatusAdapter.UpdateUI {
    // Splash?????? ?????? ????????? ????????????
    private FirebaseUser currentUser;
    private String userName;

    // Toolbar: ?????? ?????? ?????????, ?????? ??????(text) ?????? ???
    private Toolbar toolbar;
    private ActionBar actionBar;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    // Bottom Navigation View: ?????? ???, ??????, ?????? ?????? ??????
    private BottomNavigationView bottomNavigationView;

    // Drawer Navigation View: ??? -> ??????????????? ?????????????????? ?????? ?????? (????????? ????????? ??????, ?????? ??????, ?????? ???)
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView drawerUserName, drawerEmail, drawerBadgeAmount, drawerCoinAmount;
    private SpannableString badgeAmount, coinAmount;
    private View navigationHeaderView;
    private boolean isRecipe = true;


    // Fragments
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Fragment_Home fragHome;
    private Fragment_Wallet frag2;
    private Fragment_Recipe fragRecipe;
    private Fragment_MyPage fragMypage;
    private Fragment_Commerce fragShopping;

    // ??????????????? ???????????? ????????? ????????? ?????? ??????
    private long backKeyPressedTime = 0;
    private Toast toast;

    // ScrollView
    private NestedScrollView homeScrollView;

    // AWS Pinpoint manager
    private static PinpointManager pinpointManager;

//    // Routine Floating Button
//    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ????????? ?????? ??????
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        getHashKey();

        // ?????? ???, ??????????????? ????????? ??? ??????
        initView();

//        // FAB ?????????
//        floatingActionButton.hide();

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

        //App Bar??? ?????? ????????? Drawer??? Open ?????? ?????? Icon ??????
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_button_toolbar_menu);

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);    //remove the basic title


        //get id of bottomNavigationView
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        //get id of drawerLayout
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        /*
         * Drawer Navigation View: ??? -> ??????????????? ?????????????????? ?????? ?????? (????????? ????????? ??????, ?????? ??????, ?????? ???)
         */

        // Drawer??? User ?????? ??????
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        userName = currentUser.getDisplayName();
        drawerUserName.setText(userName);
        drawerEmail.setText(currentUser.getEmail());
        badgeAmount=new SpannableString("?????? 12???");
        coinAmount=new SpannableString("?????? 32???");
        badgeAmount.setSpan(new ForegroundColorSpan(Color.parseColor("#29D29A")), 3, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        badgeAmount.setSpan(new StyleSpan(Typeface.BOLD), 3, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        coinAmount.setSpan(new ForegroundColorSpan(Color.parseColor("#29D29A")), 3, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        coinAmount.setSpan(new StyleSpan(Typeface.BOLD), 3, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        drawerBadgeAmount.setText(badgeAmount);
        drawerCoinAmount.setText(coinAmount);

        /*
         *  ActionBar
         */
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close
        ) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        // NavigationView
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.drawer_carbon_trading:
                        Toast.makeText(getApplicationContext(), "?????? ?????????????????? ??????", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.drawer_my_seti:
                        Toast.makeText(getApplicationContext(), "MY SETI??? ??????", Toast.LENGTH_SHORT).show();
                        bottomNavigationView.setSelectedItemId(R.id.bottomnavigation_menu_mypage);
                        break;
                    case R.id.drawer_my_cmi:
                        Toast.makeText(getApplicationContext(), "MY CMI??? ??????", Toast.LENGTH_SHORT).show();
                        bottomNavigationView.setSelectedItemId(R.id.bottomnavigation_menu_mypage);
                        break;
                    case R.id.drawer_status:
                        Toast.makeText(getApplicationContext(), "?????????????????? ??????", Toast.LENGTH_SHORT).show();
                        bottomNavigationView.setSelectedItemId(R.id.bottomnavigation_menu_home);
                        break;
                    case R.id.drawer_reward_history:
                        Toast.makeText(getApplicationContext(), "????????? ???????????? ??????", Toast.LENGTH_SHORT).show();
                        bottomNavigationView.setSelectedItemId(R.id.bottomnavigation_menu_mypage);
                        break;
                    case R.id.drawer_order_history:
                        Toast.makeText(getApplicationContext(), "?????????????????? ??????", Toast.LENGTH_SHORT).show();
                        bottomNavigationView.setSelectedItemId(R.id.bottomnavigation_menu_mypage);
                        break;
                }

                //When MenuItem is clicked, close the drawer.
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

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
                    case R.id.bottomnavigation_menu_set:
                        changeFragment("set", frag2);
                        return true;
                    case R.id.bottomnavigation_menu_recipe:
                        changeFragment("suggestion", fragRecipe);
                        return true;
                    case R.id.bottomnavigation_menu_mypage:
                        changeFragment("mypage", fragMypage);
                        return true;
                    case R.id.bottomnavigation_menu_commerce:
                        changeFragment("commerce", fragShopping);
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
        // back button??? ???????????? drawer??? ???????????? ???
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);          // Drawer??? ??????
        } else {
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
        }
    }

    public FragmentManager passFragmentManager() {
        return this.fragmentManager;
    }

    private void getHashKey() {
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
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
        //get id of drawerLayout
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        //Apply the toolbar as action bar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        // Create Fragments
        fragHome = new Fragment_Home();////////////
        frag2 = new Fragment_Wallet();
        fragRecipe = new Fragment_Recipe();/////////
        fragMypage = new Fragment_MyPage();/////////////
        fragShopping = new Fragment_Commerce();///////////////


        // get id of drawer navigationView
        navigationView = (NavigationView) findViewById(R.id.drawer_navigation_view);
        navigationHeaderView = navigationView.getHeaderView(0);

        // get id of drawer user profile
        drawerUserName = navigationHeaderView.findViewById(R.id.drawer_text_user_name);
        drawerEmail =navigationHeaderView.findViewById(R.id.drawer_text_email);
        drawerBadgeAmount=navigationHeaderView.findViewById(R.id.drawer_text_badge_amount);
        drawerCoinAmount=navigationHeaderView.findViewById(R.id.drawer_text_coin_amount);
    }

    /*
     * Toolbar ?????? ??????
     */
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.routine_toolbar_menu, menu);
//        setToolbarItemsVisibleState(false);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.toolbar_menu:
////                drawerLayout.openDrawer(GravityCompat.START);
//                return true;
//            default:
//                return true;
//        }
//    }

//    // ?????? ?????? ?????? ?????? ??????/??????
//    public void setToolbarItemsVisibleState(boolean isVisible) {
//        // ?????? ?????? ?????? ?????? ?????? ??????/??????
//        Menu toolbarMenu = toolbar.getMenu();
//        MenuItem menu1 = toolbarMenu.getItem(0);
//        MenuItem menu2 = toolbarMenu.getItem(1);
//
//        menu1.setVisible(isVisible);
//        menu2.setVisible(isVisible);
//    }

    // Drawer??? ????????? ?????? ??????
    public boolean getIsRecipe() {
        return isRecipe;
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

    // ?????? ???????????? ?????? ?????? ??? ??????????????? ?????? ??????
    @Override
    public void setBottomNavButtonToRecipe() {
        bottomNavigationView.setSelectedItemId(R.id.bottomnavigation_menu_recipe);
    }
}