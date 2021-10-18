package com.barofutures.seco;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.barofutures.seco.firebase.firestore.ChallengeData;
import com.barofutures.seco.model.ContentsDetailData;
import com.skydoves.powerspinner.IconSpinnerAdapter;
import com.skydoves.powerspinner.IconSpinnerItem;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.skydoves.powerspinner.PowerSpinnerInterface;
import com.skydoves.powerspinner.PowerSpinnerView;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ChallengeSettingActivity extends AppCompatActivity {
    // 기간 선택
    private int term;                  // 몇 주 (1 ~ 5)

    // 날짜 선택
    private DatePickerDialog datePickerDialog;
    private Calendar mCalendar;
    private Calendar minDate;           // 오늘 날짜

    // 활동 리스트
    private ContentsDetailData activityData;

    private Toolbar toolbar;
    private SeekBar termSeekBar;        // 기간 설정
    private Button startDateButton;     // 시작일 설정
    private TextView endDateTextView;   // 종료일
    private ArrayList<ConstraintLayout> activityLayout;        // 활동 선택 layout
    private ArrayList<PowerSpinnerView> spinnerViewArr;   // 활동 선택 spinner
    private ArrayList<ArrayList<CheckedTextView>> daySelectionCheckedText;      // 활동별 요일 선태 checkedTextView
    private ImageButton addButton;      // 활동 추가 버튼
    private Button createButton;        // 챌린지 생성 버튼
    private ConstraintLayout badgeNumLayout;        // 추가 배지 수 레이아웃
    private TextView badgeNumTextView;              // 추가 배지 수 텍스트

    // 챌린지 설정 데이터
    private ChallengeData challengeData;
    private ArrayList<Map<String,ArrayList<String>>> activityList;      // key: 활동 이름, value: 활동 요일 arr

    // 추가한 활동 개수
    private int activityNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_setting);

        // 상단바 완전 투명
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // actionBar 설정
        toolbar=findViewById(R.id.activity_challenge_setting_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Status Bar 높이만큼 Padding 부여
        toolbar.setPadding(0,getStatusBarHeight(), 0, 0);

        // init
        term = 1;

        mCalendar = Calendar.getInstance();
        minDate = Calendar.getInstance();
        minDate.set(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog = new DatePickerDialog(
                ChallengeSettingActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mCalendar.set(Calendar.YEAR, year);
                        mCalendar.set(Calendar.MONTH, month);
                        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateStartDateText();
                        updateEndDateText();
                    }
                },
                mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.getDatePicker().setMinDate(minDate.getTime().getTime());

        challengeData = new ChallengeData();
        activityList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            activityList.add(new HashMap<>());
        }
        activityData = new ContentsDetailData();
        activityData.setMeal();
        activityData.setActivity();
        activityData.setQuest();

        termSeekBar = findViewById(R.id.activity_challenge_setting_term_seek_bar);
        startDateButton = findViewById(R.id.activity_challenge_setting_start_date_button);
        endDateTextView = findViewById(R.id.activity_challenge_setting_end_date_text_view);

        activityNum = 1;        // 추가한 활동 개수
        activityLayout = new ArrayList<>();
        activityLayout.add(findViewById(R.id.activity_challenge_setting_activity_selection_layout_1));
        activityLayout.add(findViewById(R.id.activity_challenge_setting_activity_selection_layout_2));
        activityLayout.add(findViewById(R.id.activity_challenge_setting_activity_selection_layout_3));
        activityLayout.add(findViewById(R.id.activity_challenge_setting_activity_selection_layout_4));
        activityLayout.add(findViewById(R.id.activity_challenge_setting_activity_selection_layout_5));

        spinnerViewArr = new ArrayList<>();
        spinnerViewArr.add(findViewById(R.id.activity_challenge_setting_activity_selection_activity_spinner_1));
        spinnerViewArr.add(findViewById(R.id.activity_challenge_setting_activity_selection_activity_spinner_2));
        spinnerViewArr.add(findViewById(R.id.activity_challenge_setting_activity_selection_activity_spinner_3));
        spinnerViewArr.add(findViewById(R.id.activity_challenge_setting_activity_selection_activity_spinner_4));
        spinnerViewArr.add(findViewById(R.id.activity_challenge_setting_activity_selection_activity_spinner_5));

        daySelectionCheckedText = new ArrayList<>();
        ArrayList<CheckedTextView> activity_1 = new ArrayList<>();
        activity_1.add(findViewById(R.id.activity_challenge_setting_activity_selection_day_sun_checked_text_view_1));
        activity_1.add(findViewById(R.id.activity_challenge_setting_activity_selection_day_mon_checked_text_view_1));
        activity_1.add(findViewById(R.id.activity_challenge_setting_activity_selection_day_tue_checked_text_view_1));
        activity_1.add(findViewById(R.id.activity_challenge_setting_activity_selection_day_wed_checked_text_view_1));
        activity_1.add(findViewById(R.id.activity_challenge_setting_activity_selection_day_thu_checked_text_view_1));
        activity_1.add(findViewById(R.id.activity_challenge_setting_activity_selection_day_fri_checked_text_view_1));
        activity_1.add(findViewById(R.id.activity_challenge_setting_activity_selection_day_sat_checked_text_view_1));
        daySelectionCheckedText.add(activity_1);
        ArrayList<CheckedTextView> activity_2 = new ArrayList<>();
        activity_2.add(findViewById(R.id.activity_challenge_setting_activity_selection_day_sun_checked_text_view_2));
        activity_2.add(findViewById(R.id.activity_challenge_setting_activity_selection_day_mon_checked_text_view_2));
        activity_2.add(findViewById(R.id.activity_challenge_setting_activity_selection_day_tue_checked_text_view_2));
        activity_2.add(findViewById(R.id.activity_challenge_setting_activity_selection_day_wed_checked_text_view_2));
        activity_2.add(findViewById(R.id.activity_challenge_setting_activity_selection_day_thu_checked_text_view_2));
        activity_2.add(findViewById(R.id.activity_challenge_setting_activity_selection_day_fri_checked_text_view_2));
        activity_2.add(findViewById(R.id.activity_challenge_setting_activity_selection_day_sat_checked_text_view_2));
        daySelectionCheckedText.add(activity_2);
        ArrayList<CheckedTextView> activity_3 = new ArrayList<>();
        activity_3.add(findViewById(R.id.activity_challenge_setting_activity_selection_day_sun_checked_text_view_3));
        activity_3.add(findViewById(R.id.activity_challenge_setting_activity_selection_day_mon_checked_text_view_3));
        activity_3.add(findViewById(R.id.activity_challenge_setting_activity_selection_day_tue_checked_text_view_3));
        activity_3.add(findViewById(R.id.activity_challenge_setting_activity_selection_day_wed_checked_text_view_3));
        activity_3.add(findViewById(R.id.activity_challenge_setting_activity_selection_day_thu_checked_text_view_3));
        activity_3.add(findViewById(R.id.activity_challenge_setting_activity_selection_day_fri_checked_text_view_3));
        activity_3.add(findViewById(R.id.activity_challenge_setting_activity_selection_day_sat_checked_text_view_3));
        daySelectionCheckedText.add(activity_3);
        ArrayList<CheckedTextView> activity_4 = new ArrayList<>();
        activity_4.add(findViewById(R.id.activity_challenge_setting_activity_selection_day_sun_checked_text_view_4));
        activity_4.add(findViewById(R.id.activity_challenge_setting_activity_selection_day_mon_checked_text_view_4));
        activity_4.add(findViewById(R.id.activity_challenge_setting_activity_selection_day_tue_checked_text_view_4));
        activity_4.add(findViewById(R.id.activity_challenge_setting_activity_selection_day_wed_checked_text_view_4));
        activity_4.add(findViewById(R.id.activity_challenge_setting_activity_selection_day_thu_checked_text_view_4));
        activity_4.add(findViewById(R.id.activity_challenge_setting_activity_selection_day_fri_checked_text_view_4));
        activity_4.add(findViewById(R.id.activity_challenge_setting_activity_selection_day_sat_checked_text_view_4));
        daySelectionCheckedText.add(activity_4);
        ArrayList<CheckedTextView> activity_5 = new ArrayList<>();
        activity_5.add(findViewById(R.id.activity_challenge_setting_activity_selection_day_sun_checked_text_view_5));
        activity_5.add(findViewById(R.id.activity_challenge_setting_activity_selection_day_mon_checked_text_view_5));
        activity_5.add(findViewById(R.id.activity_challenge_setting_activity_selection_day_tue_checked_text_view_5));
        activity_5.add(findViewById(R.id.activity_challenge_setting_activity_selection_day_wed_checked_text_view_5));
        activity_5.add(findViewById(R.id.activity_challenge_setting_activity_selection_day_thu_checked_text_view_5));
        activity_5.add(findViewById(R.id.activity_challenge_setting_activity_selection_day_fri_checked_text_view_5));
        activity_5.add(findViewById(R.id.activity_challenge_setting_activity_selection_day_sat_checked_text_view_5));
        daySelectionCheckedText.add(activity_5);

        addButton = findViewById(R.id.activity_challenge_setting_add_button);
        createButton = findViewById(R.id.activity_challenge_setting_next_button);
        badgeNumLayout = findViewById(R.id.activity_challenge_setting_badge_num_layout);
        badgeNumTextView = findViewById(R.id.activity_challenge_setting_badge_num_text_view);

        // 기간 설정 (term)
        termSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                term = seekBar.getProgress() + 1;
                if (challengeData.getEndDate() != null) {
                    updateEndDateText();
                }
            }
        });


        // 시작일 설정
        startDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        // 활동 설정 spinner
        List<IconSpinnerItem> iconSpinnerItems = new ArrayList<>();
        for (String item: activityData.getTitle()) {
            iconSpinnerItems.add(new IconSpinnerItem(item));
        }

        for (int a = 0; a < spinnerViewArr.size(); a++) {
            IconSpinnerAdapter iconSpinnerAdapter = new IconSpinnerAdapter(spinnerViewArr.get(a));
            spinnerViewArr.get(a).setSpinnerAdapter(iconSpinnerAdapter);
            spinnerViewArr.get(a).setItems(iconSpinnerItems);
            spinnerViewArr.get(a).setLifecycleOwner(this);

            int temp = a;
            spinnerViewArr.get(a).setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener<IconSpinnerItem>() {
                @Override
                public void onItemSelected(int i, @Nullable IconSpinnerItem iconSpinnerItem, int i1, IconSpinnerItem t1) {
                    Toast.makeText(getApplicationContext(), t1.getText().toString() + "이 선택되었습니다", Toast.LENGTH_SHORT).show();
                    if (iconSpinnerItem != null && activityList.get(temp).containsKey(iconSpinnerItem.getText().toString())) {
                        activityList.get(temp).remove(iconSpinnerItem.getText().toString());
                    }
                    activityList.get(temp).put(t1.getText().toString(), null);
                }
            });
        }

        // 요일 설정 CheckedTextView
        int i, j;
        for (i = 0; i < daySelectionCheckedText.size(); i++) {
            for (j = 0; j < daySelectionCheckedText.get(i).size(); j++) {
                CheckedTextView temp = daySelectionCheckedText.get(i).get(j);
                temp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckedTextView ctv = (CheckedTextView) v;
                        ctv.toggle();
                    }
                });
            }
        }

        // 활동 추가 버튼 listener
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityLayout.get(activityNum).setVisibility(View.VISIBLE);
                activityNum += 1;

                // 최대 활동 개수 5개를 넘기면
                if(activityNum >= 5) {
                    addButton.setVisibility(View.GONE);
                }
            }
        });

        // 챌린지 생성 버튼
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDayDataByActivity();
                challengeData.setMaxBadgeNum(calcMaxBadgeNum());
                challengeData.setAdditionalBadgeNum((long) (challengeData.getMaxBadgeNum() * 0.1 * term));

                badgeNumTextView.setText("X " + challengeData.getAdditionalBadgeNum() + "개");
                badgeNumLayout.setVisibility(View.VISIBLE);


                createButton.setText("챌린지 만드는 중...");
                createButton.setBackgroundResource(R.drawable.button_initq_unchecked);
                createButton.setClickable(false);

                // 챌린지 데이터 firestore에 저장
                storeChallengeData();
            }
        });
    }

    // 챌린지 데이터 firestore에 저장
    private void storeChallengeData() {

    }

    // 최대로 얻을 수 있는 배지 개수 계산
    private long calcMaxBadgeNum() {
        long num = 0;       // 최대 얻을 수 있는 배지 수
        for (int i = 0; i < activityNum; i++) {
            for (String key : activityList.get(i).keySet()) {
                int index = activityData.title.indexOf(key);        // key=활동명, activityData에서 해당 활동의 index
                int numOfDays = activityList.get(i).get(key).size();    // 해당 활동의 선택된 요일 수
                num += Integer.valueOf(activityData.badgeNum.get(index)) * numOfDays;      // 해당 활동의 뱃지 수 * 선택된 요일 수
            }
        }
        num *= term;        // 챌린지 기간(n주)를 곱함

        Toast.makeText(getApplicationContext(), "최대 배지 수: " + num, Toast.LENGTH_SHORT).show();

        return num;
    }

    // 각 활동별로 선택된 요일 데이터를 가져옴
    private void setDayDataByActivity() {
//        for (Map<String, ArrayList<String>> map : activityList) {
//            for (String key : map.keySet()) {
//                Log.d("ChallengeSettingActivity2222", key);
//
//            }
//        }

        String[] dayList = new String[]{"일", "월", "화", "수", "목", "금", "토"};
        for (int i = 0; i < activityNum; i++) {
            for (String key : activityList.get(i).keySet()) {
                ArrayList<String> days = new ArrayList<>();     // 선택 요일 담을 변수

                for (int j = 0; j < daySelectionCheckedText.get(i).size(); j++) {
                    CheckedTextView temp = daySelectionCheckedText.get(i).get(j);
                    if (temp.isChecked()) {
                        days.add(dayList[j]);
                    }
                }
                activityList.get(i).replace(key, days);
            }
        }

        for (int i = 0; i < activityNum; i++) {
//            Log.d("ChallengeSettingActivity2222", "i = " + i);
            for (String key : activityList.get(i).keySet()) {
//                Log.d("ChallengeSettingActivity2222", "key = " + key);
               String s = "";
                for (String day : activityList.get(i).get(key)) {
//                    Log.d("ChallengeSettingActivity2222", day);
                    s += day +" ";
                }
                Log.d("ChallengeSettingActivity111111", s);
            }
        }

    }


    // 시작일 업데이트
    private void updateStartDateText() {
        String myFormat = "yyyy-MM-dd";    // 출력형식
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);
        challengeData.setStartDate(sdf.format(mCalendar.getTime()));
        startDateButton.setText(challengeData.getStartDate());
    }

    // 종료일 업데이트
    private void updateEndDateText() {
        String myFormat = "yyyy-MM-dd";    // 출력형식
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        Calendar endDate = (Calendar) mCalendar.clone();
        endDate.add(Calendar.WEEK_OF_YEAR, term);
        challengeData.setEndDate(sdf.format(endDate.getTime()));
        endDateTextView.setText(challengeData.getEndDate());
        Toast.makeText(getApplicationContext(), "종료일: " + challengeData.getEndDate(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //status bar의 높이 계산
    public int getStatusBarHeight()
    {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
            result = getResources().getDimensionPixelSize(resourceId);

        return result;
    }
}