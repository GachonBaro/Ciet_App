package com.barofutures.seco.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.barofutures.seco.ChallengeModeStatusActivity;
import com.barofutures.seco.ModeChangeConfirmDialog;
import com.barofutures.seco.R;
import com.barofutures.seco.freeModeStatusActivity;
import com.barofutures.seco.adapter.HomeBannerAdapter;
import com.barofutures.seco.model.UserData;
import com.kyleduo.switchbutton.SwitchButton;

public class Fragment_Home extends Fragment implements ModeChangeConfirmDialog.UpdateUI {
    private ViewGroup viewGroup;
    // 상단 배너
    private ViewPager2 viewPager2;
    private LinearLayout indicator;
    private HomeBannerAdapter homeBannerAdapter;
    // 더보기 버튼
    private TextView moreTextFree, moreTextChallenge;
    // Free, challenge모드 텍스트
    private TextView textView;
    private SwitchButton modeChangeToggle;
    // 진행상황, CMI 수치
    private TextView veganNumber, walkNumber, braveNumber, cmiTotal;
    private SpannableString vegan, walk, brave, cmi;

    // root layout
    private ConstraintLayout mainContent;
    private RelativeLayout cmiCard, root_layout;

    // free, challenge mode 구별하는 flag
    public static boolean isChallenge = false, update=false;
    private RelativeLayout modeLayout;
    private RelativeLayout freeModeProgressContent, challengeModeProgressContent;

    // 배너 자동스크롤
    private Handler bannerHandler;
    private Runnable bannerRunnable;

    // 확인창
    private ModeChangeConfirmDialog dialog;

    private String[] images = new String[]{
            "https://img.global.news.samsung.com/global/wp-content/uploads/2020/07/GalaxyZFlip5G_pr_main2.jpg",
            "https://mblogthumb-phinf.pstatic.net/MjAyMDA4MTJfMjE3/MDAxNTk3MjMxMjE1ODA5.UxcA7DLzX-aKmT0Jb06ns0aOv9n0NolDERRwq_nVIw0g.nZIQ865-Hl97greAHWII_gBrbInITlZkY_zq5CZo2uAg.JPEG.bhs7849/1597231213759.jpeg?type=w800",
            "https://image.news1.kr/system/photos/2021/5/24/4784614/article.jpg/dims/optimize",
            "https://cdn.shortpixel.ai/spai/w_812+q_lossy+ret_img+to_webp/https://stealthoptional.com/wp-content/uploads/2020/08/Samsung-Note20-Ultra-SO-feature.jpg"
    };

    // Instance 반환 메소드
    public static Fragment_Home newInstance() {
        return new Fragment_Home();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // View 초기화 및 연결
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
        initView();

        // 토글 클릭시 free, challenge 전환
        modeChangeToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 확인창 띄우기
                // TODO : 버튼 클릭 결과가 나오면 UI가 업데이트 되어야 하는데....
                dialog.callFunction(isChallenge, getActivity());
                if(!isChallenge){
                    if(update){
                        activateChallengeMode();
                    }
                }else{
                    if(update){
                        deactivateChallengeMode();
                    }
                }
            }
        });

        // 계단 오르기 Color Text
        TextView progressText = viewGroup.findViewById(R.id.home_info_card_progress_free_mode_stairs_number);
        SpannableString spannableString = new SpannableString("(12/12)");
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(progressText.getContext(), R.color.seco_green)), 1, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        progressText.setText(spannableString);

        // 배너 Adapter 연결
        homeBannerAdapter = new HomeBannerAdapter(images);
        viewPager2.setAdapter(homeBannerAdapter);
        viewPager2.setOverScrollMode(View.OVER_SCROLL_NEVER);

        // Indicator를 위한 Viewpager의 callback 사용
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                bannerHandler.removeCallbacks(bannerRunnable);
                bannerHandler.postDelayed(bannerRunnable, 2000);
                setCurrentIndicator(position);
                // TODO: 무한스크롤 완성
//                if(position==images.length-2){
//                    viewPager2.post(runnable)
//                }
            }
        });
        setUpIndicator(images.length);

//        private Runnable runnable=new Runnable() {
//            @Override
//            public void run() {
//                images.
//            }
//        }

        // 더보기 버튼 클릭 이벤트
        moreTextFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 챌린지 모드가 아닐 때
                Intent intent = new Intent(getActivity(), freeModeStatusActivity.class);
                startActivity(intent);
            }
        });

        moreTextChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 챌린지 모드일 때
                Intent intent = new Intent(getActivity(), freeModeStatusActivity.class);
                startActivity(intent);
            }
        });

        // 진행 상황 숫자 설정
        // TODO : DB연결
        vegan = new SpannableString("(0/0)");
        walk = new SpannableString("(2345/10000)");
        brave = new SpannableString("(1/2)");
        cmi = new SpannableString("총 932Kcal 입니다.");
        walk.setSpan(new ForegroundColorSpan(Color.parseColor("#29D29A")), 1, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        brave.setSpan(new ForegroundColorSpan(Color.parseColor("#29D29A")), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        cmi.setSpan(new ForegroundColorSpan(Color.parseColor("#FF000000")), 2, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        cmi.setSpan(new StyleSpan(Typeface.BOLD), 2, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        veganNumber.setText(vegan);
        walkNumber.setText(walk);
        braveNumber.setText(brave);
        cmiTotal.setText(cmi);

        // cmi chart 설정
        // 그래프 먼저
        //w,h,weight
        LinearLayout cmiChart = viewGroup.findViewById(R.id.home_info_card_cmi_pie);
        float[] pieWeights = {10f, 20f, 30f};
        int[] colors = {R.color.seco_deepgray, R.color.seco_tooltipgray, R.color.seco_green};
        LinearLayout.LayoutParams[] params = new LinearLayout.LayoutParams[pieWeights.length];
        TextView[] pieTextViews = new TextView[pieWeights.length];
        for (int i = 0; i < pieWeights.length; i++) {
            params[i] = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, pieWeights[i]);
            pieTextViews[i] = new TextView(getActivity());
            pieTextViews[i].setLayoutParams(params[i]);
            pieTextViews[i].setBackgroundResource(colors[i]);
            cmiChart.addView(pieTextViews[i]);
        }

        // 그래프 레이블
        LinearLayout cmiRatio = viewGroup.findViewById(R.id.home_info_card_cmi_pie_ratio);
        float[] weights = {10f, 20f, 30f};
        String[] ratios = {"18%", "28%", "15%"};
        LinearLayout.LayoutParams[] params_ratio = new LinearLayout.LayoutParams[weights.length];
        TextView[] textViews_ratio = new TextView[weights.length];

        for (int i = 0; i < textViews_ratio.length; i++) {
            params_ratio[i] = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, weights[i]);
            textViews_ratio[i] = new TextView(getActivity());
            textViews_ratio[i].setLayoutParams(params_ratio[i]);
            textViews_ratio[i].setText(ratios[i]);
            textViews_ratio[i].setTextSize(11);
            textViews_ratio[i].setGravity(Gravity.CENTER);

            Typeface face = ResourcesCompat.getFont(getActivity(), R.font.noto_sans_cjk_kr_bold);
            textViews_ratio[i].setTypeface(face);
            cmiRatio.addView(textViews_ratio[i]);
        }

        // 배너 자동 스크롤
        bannerHandler = new Handler();
        bannerRunnable = new Runnable() {
            @Override
            public void run() {
                if (viewPager2.getCurrentItem() == images.length - 1) {
                    viewPager2.setCurrentItem(0);
                } else {

                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
                }
            }
        };

        return viewGroup;
    }

    // 프래그먼트가 일시정지 일때 배너 자동스크롤 정지, 다시 시작 될때 재개
    @Override
    public void onPause() {
        super.onPause();
        bannerHandler.removeCallbacks(bannerRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        bannerHandler.postDelayed(bannerRunnable, 2000);
    }

    // 모드 활성화, 비활성화
    @Override
    public void deactivateChallengeMode(){
        // 활성화 상태에서 모드 변경
        // 취소 시
        textView.setText("챌린지 모드 비활성화");
        // flag 설정
        isChallenge = false;

        // 모드 부분 테두리 없어지게
        modeLayout.setBackgroundResource(R.drawable.item_white_rounded_background);

        // background 색 변환
        root_layout.setBackgroundColor(getResources().getColor(R.color.seco_background));

        //  진행상황 내용 Free 모드로 변경
        freeModeProgressContent.setVisibility(View.VISIBLE);
        challengeModeProgressContent.setVisibility(View.INVISIBLE);
        update=false;
    }

    @Override
    public void activateChallengeMode(){
        // 확인 시
        textView.setText("챌린지 모드 활성화");

        // flag 설정
        isChallenge = true;

        // 모드 부분 테두리 생기게
        modeLayout.setBackgroundResource(R.drawable.item_white_rounded_background_green_frame);

        // background 색 변환
        root_layout.setBackgroundColor(getResources().getColor(R.color.home_challenge_enable_mode_bg_color));

        // 진행상황 내용 Free 모드로 변경
        freeModeProgressContent.setVisibility(View.INVISIBLE);
        challengeModeProgressContent.setVisibility(View.VISIBLE);
        update=false;
    }

    // Indicator 세팅
    private void setUpIndicator(int count) {
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(16, 8, 16, 8);

        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getActivity());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.bg_indicator_inactive));
            indicators[i].setLayoutParams(params);
            indicator.addView(indicators[i]);
        }
        setCurrentIndicator(0);
    }

    private void setCurrentIndicator(int position) {
        int childCount = indicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) indicator.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.bg_indicator_active));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.bg_indicator_inactive));
            }
        }
    }

    // 모드 전환 함수
    public static void changeMode(){

    }

    // 초기 모드 설정
    @Override
    public void onStart() {
        super.onStart();
        // 초기 모드 설정
        if (!isChallenge) {
            textView.setText("챌린지 모드 비활성화");

            // 버튼 끄기
//            modeChangeToggle.setChecked(false);

            // 모드 부분 테두리 없게
            modeLayout.setBackgroundResource(R.drawable.item_white_rounded_background);

            // background 색 변환
            root_layout.setBackgroundColor(getResources().getColor(R.color.seco_background));

            // 진행상황 내용 Free 모드로 변경
            freeModeProgressContent.setVisibility(View.VISIBLE);
            challengeModeProgressContent.setVisibility(View.INVISIBLE);

        } else {
            // 확인 시
            textView.setText("챌린지 모드 활성화");

            // 버튼 켜기
//            modeChangeToggle.setChecked(true);

            // 모드 부분 테두리 생기게
            modeLayout.setBackgroundResource(R.drawable.item_white_rounded_background_green_frame);

            // background 색 변환
            root_layout.setBackgroundColor(getResources().getColor(R.color.home_challenge_enable_mode_bg_color));

            // 진행상황 내용 Free 모드로 변경
            freeModeProgressContent.setVisibility(View.INVISIBLE);
            challengeModeProgressContent.setVisibility(View.VISIBLE);
        }

    }

    private void initView() {
        viewPager2 = viewGroup.findViewById(R.id.home_viewpager2_banner);
        indicator = viewGroup.findViewById(R.id.home_banner_indicator);
        textView = viewGroup.findViewById(R.id.home_text_current_mode);
        modeChangeToggle = viewGroup.findViewById(R.id.home_button_set_mode);
        veganNumber = viewGroup.findViewById(R.id.home_info_card_progress_free_mode_container_challenge_number);
        walkNumber = viewGroup.findViewById(R.id.home_info_card_progress_free_mode_imperfect_number);
        braveNumber = viewGroup.findViewById(R.id.home_info_card_progress_free_mode_stairs_number);
        cmiTotal = viewGroup.findViewById(R.id.home_info_card_cmi_total);

        // init root layout
        root_layout = viewGroup.findViewById(R.id.fragment_home_root_relative_layout);
        mainContent = viewGroup.findViewById(R.id.home_scroll_info_cards);

        // 모드 부분 레이아웃
        modeLayout = viewGroup.findViewById(R.id.home_relativelayout_mode);

        // 진행상황 부분 레이아웃
        freeModeProgressContent = viewGroup.findViewById(R.id.home_info_card_progress_free_mode);
        challengeModeProgressContent = viewGroup.findViewById(R.id.home_info_card_progress_challenge_mode);

        // 더보기 버튼
        moreTextFree = viewGroup.findViewById(R.id.home_text_progress_free_mode_more);
        moreTextChallenge = viewGroup.findViewById(R.id.home_text_progress_challenge_mode_more);

        // CMI 정보 레이아웃
        cmiCard = viewGroup.findViewById(R.id.home_info_card_cmi);

        // 확인창
        dialog = new ModeChangeConfirmDialog(getActivity());

        // 초기 모드 설정
        if (!isChallenge) {
            textView.setText("챌린지 모드 비활성화");

            // 버튼 끄기
//            modeChangeToggle.setChecked(false);

            // 모드 부분 테두리 없게
            modeLayout.setBackgroundResource(R.drawable.item_white_rounded_background);

            // background 색 변환
            root_layout.setBackgroundColor(getResources().getColor(R.color.seco_background));

            // 진행상황 내용 Free 모드로 변경
            freeModeProgressContent.setVisibility(View.VISIBLE);
            challengeModeProgressContent.setVisibility(View.INVISIBLE);

        } else {
            // 확인 시
            textView.setText("챌린지 모드 활성화");

            // 버튼 켜기
//            modeChangeToggle.setChecked(true);

            // 모드 부분 테두리 생기게
            modeLayout.setBackgroundResource(R.drawable.item_white_rounded_background_green_frame);

            // background 색 변환
            root_layout.setBackgroundColor(getResources().getColor(R.color.home_challenge_enable_mode_bg_color));

            // 진행상황 내용 Free 모드로 변경
            freeModeProgressContent.setVisibility(View.INVISIBLE);
            challengeModeProgressContent.setVisibility(View.VISIBLE);
        }
    }

}