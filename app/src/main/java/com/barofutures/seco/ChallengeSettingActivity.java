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

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.DatePickerDialog;
import android.content.Intent;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import com.skydoves.powerspinner.IconSpinnerAdapter;
import com.skydoves.powerspinner.IconSpinnerItem;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.skydoves.powerspinner.PowerSpinnerInterface;
import com.skydoves.powerspinner.PowerSpinnerView;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ChallengeSettingActivity extends AppCompatActivity {
    // ?????? ??????
    private int term;                  // ??? ??? (1 ~ 5)

    // ?????? ??????
    private DatePickerDialog datePickerDialog;
    private Calendar mCalendar;         // ?????????
    private Calendar minDate;           // ?????? ??????

    // ?????? ?????????
    private ContentsDetailData activityData;

    private Toolbar toolbar;
    private SeekBar termSeekBar;        // ?????? ??????
    private Button startDateButton;     // ????????? ??????
    private TextView endDateTextView;   // ?????????
    private ArrayList<ConstraintLayout> activityLayout;        // ?????? ?????? layout
    private ArrayList<PowerSpinnerView> spinnerViewArr;   // ?????? ?????? spinner
    private ArrayList<ArrayList<CheckedTextView>> daySelectionCheckedText;      // ????????? ?????? ?????? checkedTextView
    private ImageButton addButton;      // ?????? ?????? ??????
    private Button createButton;        // ????????? ?????? ??????
    private ConstraintLayout badgeNumLayout;        // ?????? ?????? ??? ????????????
    private TextView badgeNumTextView;              // ?????? ?????? ??? ?????????

    // ????????? ?????? ?????????
    private ChallengeData challengeData;
    private ArrayList<Map<String,ArrayList<String>>> activityList;      // key: ?????? ??????, value: ?????? ?????? arr
    private Map<String, Object> activityByDay;      // ????????? ?????? ?????????
    private Map<String, Object> missionCompletion;       // ??? ????????? ?????? ?????? ??????

    // ????????? ?????? ??????
    private int activityNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_setting);

        // ????????? ?????? ??????
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // actionBar ??????
        toolbar=findViewById(R.id.activity_challenge_setting_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Status Bar ???????????? Padding ??????
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

        activityNum = 1;        // ????????? ?????? ??????
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

        // ?????? ?????? (term)
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


        // ????????? ??????
        startDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        // ?????? ?????? spinner
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
//                    Toast.makeText(getApplicationContext(), t1.getText().toString() + "??? ?????????????????????", Toast.LENGTH_SHORT).show();
                    if (iconSpinnerItem != null && activityList.get(temp).containsKey(iconSpinnerItem.getText().toString())) {
                        activityList.get(temp).remove(iconSpinnerItem.getText().toString());
                    }
                    activityList.get(temp).put(t1.getText().toString(), null);
                }
            });
        }

        // ?????? ?????? CheckedTextView
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

        // ?????? ?????? ?????? listener
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityLayout.get(activityNum).setVisibility(View.VISIBLE);
                activityNum += 1;

                // ?????? ?????? ?????? 5?????? ?????????
                if(activityNum >= 5) {
                    addButton.setVisibility(View.GONE);
                }
            }
        });

        // ????????? ?????? ??????
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                if (button.getText().equals("?????? ??????")) {
                    setDayDataByActivity();
                    challengeData.setMaxBadgeNum(calcMaxBadgeNum());
                    challengeData.setAdditionalBadgeNum((long) (challengeData.getMaxBadgeNum() * 0.1 * term));

                    badgeNumTextView.setText("X " + challengeData.getAdditionalBadgeNum() + "???");
                    badgeNumLayout.setVisibility(View.VISIBLE);

                    // activityByDay, missionCompletion ??????
                    setActivityByDayAndMissionCompletion();

                    createButton.setText("????????? ????????? ???...");
                    createButton.setBackgroundResource(R.drawable.button_initq_unchecked);
                    createButton.setClickable(false);

                    // ????????? ????????? firestore??? ?????? & button UI ????????????
                    storeChallengeData();
                }
                else if (button.getText().equals("????????????")) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("challengeMode", true);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }


            }
        });
    }

    // ????????? ????????? firestore??? ??????
    private void storeChallengeData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference ref = db.collection("users").document(email).collection("challenge");
        DocumentReference challengeRef = ref.document(challengeData.getStartDate());

        challengeRef.set(challengeData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("ChallengeSettingActivity", "DocumentSnapshot successfully written!");
                storeActivityByDay(challengeRef);
                switchChallengeModeOn();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("ChallengeSettingActivity", "Error writing document", e);
            }
        });

    }

    // challenge mode??? on(true)?????? ??????
    private void switchChallengeModeOn() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        WriteBatch batch = db.batch();
        CollectionReference ref = db.collection("users").document(email).collection("user_info");
        DocumentReference currentRef = ref.document("current");

        batch.update(currentRef, "challengeMode", true);

        // Commit the batch
        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("ChallengeSettingActivity", "init survey update completed!!");
            }
        });

    }

    // ????????? ?????? ????????? ??????
    private void storeActivityByDay(DocumentReference ref) {
        DocumentReference activityByDayRef = ref.collection("activity_list").document("activity_by_day");
        activityByDayRef.set(activityByDay).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("ChallengeSettingActivity", "DocumentSnapshot successfully written!");
                storeMissionCompletion(ref);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("ChallengeSettingActivity", "Error writing document", e);
            }
        });
    }

    //??? ????????? ?????? ?????? ?????? ??????
    private void storeMissionCompletion(DocumentReference ref) {
        DocumentReference missionCompletionRef = ref.collection("activity_list").document("mission_completion");
        missionCompletionRef.set(missionCompletion).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("ChallengeSettingActivity", "DocumentSnapshot successfully written!");
                updateNextButtonUI();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("ChallengeSettingActivity", "Error writing document", e);
            }
        });
    }

    // ???????????? ?????? ?????????
    private void updateNextButtonUI() {
        createButton.setText("????????????");
        createButton.setBackgroundResource(R.drawable.bg_rounded_green);
        createButton.setClickable(true);
    }

    // ????????? ?????? ????????? ?????? & ??? ????????? ?????? ?????? ?????? ??????
    private void setActivityByDayAndMissionCompletion() {
        activityByDay = new HashMap<>();
        missionCompletion = new HashMap<>();

        ArrayList<String> sunStr = new ArrayList<>();
        ArrayList<String> monStr = new ArrayList<>();
        ArrayList<String> tueStr = new ArrayList<>();
        ArrayList<String> wedStr = new ArrayList<>();
        ArrayList<String> thuStr = new ArrayList<>();
        ArrayList<String> friStr = new ArrayList<>();
        ArrayList<String> satStr = new ArrayList<>();

        ArrayList<Boolean> sunBool = new ArrayList<>();
        ArrayList<Boolean> monBool = new ArrayList<>();
        ArrayList<Boolean> tueBool = new ArrayList<>();
        ArrayList<Boolean> wedBool = new ArrayList<>();
        ArrayList<Boolean> thuBool = new ArrayList<>();
        ArrayList<Boolean> friBool = new ArrayList<>();
        ArrayList<Boolean> satBool = new ArrayList<>();

        for (int i = 0; i < activityNum; i++) {

            for (String key : activityList.get(i).keySet()) {
                for (String day : activityList.get(i).get(key)) {
                    if (day.equalsIgnoreCase("???")) {
                        sunStr.add(key);
                        sunBool.add(false);
                    } else if (day.equalsIgnoreCase("???")) {
                        monStr.add(key);
                        monBool.add(false);
                    } else if (day.equalsIgnoreCase("???")) {
                        tueStr.add(key);
                        tueBool.add(false);
                    } else if (day.equalsIgnoreCase("???")) {
                        wedStr.add(key);
                        wedBool.add(false);
                    } else if (day.equalsIgnoreCase("???")) {
                        thuStr.add(key);
                        thuBool.add(false);
                    } else if (day.equalsIgnoreCase("???")) {
                        friStr.add(key);
                        friBool.add(false);
                    } else if (day.equalsIgnoreCase("???")) {
                        satStr.add(key);
                        satBool.add(false);
                    }
                }

            }
        }

        activityByDay.put("???", sunStr);
        activityByDay.put("???", monStr);
        activityByDay.put("???", tueStr);
        activityByDay.put("???", wedStr);
        activityByDay.put("???", thuStr);
        activityByDay.put("???", friStr);
        activityByDay.put("???", satStr);

        // ???????????? ?????? ????????????
        int dayOfStartDate = mCalendar.get(Calendar.DAY_OF_WEEK);   // ????????? ?????? (int)

        String myFormat = "yyyy-MM-dd";    // ????????????
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        Calendar temp = (Calendar) mCalendar.clone();
        for (int i = dayOfStartDate; i < dayOfStartDate + term * 7; i++) {

            switch (temp.get(Calendar.DAY_OF_WEEK)) {
                case Calendar.SUNDAY:       //1
                    missionCompletion.put(sdf.format(temp.getTime()), sunBool);
                    break;
                case Calendar.MONDAY:       //2
                    missionCompletion.put(sdf.format(temp.getTime()), monBool);
                    break;
                case Calendar.TUESDAY:       //3
                    missionCompletion.put(sdf.format(temp.getTime()), tueBool);
                    break;
                case Calendar.WEDNESDAY:       //4
                    missionCompletion.put(sdf.format(temp.getTime()), wedBool);
                    break;
                case Calendar.THURSDAY:       //5
                    missionCompletion.put(sdf.format(temp.getTime()), thuBool);
                    break;
                case Calendar.FRIDAY:       //6
                    missionCompletion.put(sdf.format(temp.getTime()), friBool);
                    break;
                case Calendar.SATURDAY:       //7
                    missionCompletion.put(sdf.format(temp.getTime()), satBool);
                    break;
            }
            temp.add(Calendar.DATE, 1);     // ????????? ??????
        }

    }


    // ????????? ?????? ??? ?????? ?????? ?????? ??????
    private long calcMaxBadgeNum() {
        long num = 0;       // ?????? ?????? ??? ?????? ?????? ???
        for (int i = 0; i < activityNum; i++) {
            for (String key : activityList.get(i).keySet()) {
                int index = activityData.title.indexOf(key);        // key=?????????, activityData?????? ?????? ????????? index
                int numOfDays = activityList.get(i).get(key).size();    // ?????? ????????? ????????? ?????? ???
                num += Integer.valueOf(activityData.badgeNum.get(index)) * numOfDays;      // ?????? ????????? ?????? ??? * ????????? ?????? ???
            }
        }
        num *= term;        // ????????? ??????(n???)??? ??????

//        Toast.makeText(getApplicationContext(), "?????? ?????? ???: " + num, Toast.LENGTH_SHORT).show();

        return num;
    }

    // ??? ???????????? ????????? ?????? ???????????? ?????????
    private void setDayDataByActivity() {

        String[] dayList = new String[]{"???", "???", "???", "???", "???", "???", "???"};
        for (int i = 0; i < activityNum; i++) {
            for (String key : activityList.get(i).keySet()) {
                ArrayList<String> days = new ArrayList<>();     // ?????? ?????? ?????? ??????

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
            for (String key : activityList.get(i).keySet()) {
               String s = "";
                for (String day : activityList.get(i).get(key)) {
                    s += day +" ";
                }
                Log.d("ChallengeSettingActivity", s);
            }
        }

    }


    // ????????? ????????????
    private void updateStartDateText() {
        String myFormat = "yyyy-MM-dd";    // ????????????
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);
        challengeData.setStartDate(sdf.format(mCalendar.getTime()));
        startDateButton.setText(challengeData.getStartDate());
    }

    // ????????? ????????????
    private void updateEndDateText() {
        String myFormat = "yyyy-MM-dd";    // ????????????
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        Calendar endDate = (Calendar) mCalendar.clone();
        endDate.add(Calendar.WEEK_OF_YEAR, term);
        challengeData.setEndDate(sdf.format(endDate.getTime()));
        endDateTextView.setText(challengeData.getEndDate());
//        Toast.makeText(getApplicationContext(), "?????????: " + challengeData.getEndDate(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar??? back??? ????????? ??? ??????
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
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