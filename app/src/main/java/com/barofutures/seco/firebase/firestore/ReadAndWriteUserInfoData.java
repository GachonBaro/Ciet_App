package com.barofutures.seco.firebase.firestore;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.barofutures.seco.GoogleLogInActivity;
import com.barofutures.seco.SplashActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ReadAndWriteUserInfoData {
    private static final String TAG = "ReadAndWriteUserInfoData";

    // Access a Cloud Firestore instance from your Activity
    public FirebaseFirestore db;
    public CollectionReference usersRef;
    public Context context;
    public GoogleLogInActivity googleLogInActivity;

    public ReadAndWriteUserInfoData(Context context, GoogleLogInActivity googleLogInActivity) {
        this.db = FirebaseFirestore.getInstance();
        this.usersRef = db.collection("users");
        this.context = context;
        this.googleLogInActivity = googleLogInActivity;
    }

    // 해당 유저가 이미 있으면 true 반환(기존 유저), 아니면 false 반환(신규 유저)
    public void searchUser(String userID, String email, String name) {
        DocumentReference docRef = usersRef.document(email);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Intent intent = new Intent(context, SplashActivity.class);
                        intent.putExtra("nextActivity", "MainActivity");
                        intent.putExtra("email", email);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);
                        googleLogInActivity.finish();
                    } else {
                        Log.d(TAG, "No such document");
                        updateServerTimestamp(userID, email, name);

                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                    Log.d(TAG, "onComplete: " + task.getException().toString());
                    Toast.makeText(context, "FIREBASE get() method is failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    // update Server Timestamp
    public void updateServerTimestamp(String UID, String email, String name) {
        DocumentReference docRef = usersRef.document(email);
        Map<String, Object> timeStamp = new HashMap<>();
        timeStamp.put("timeStamp", FieldValue.serverTimestamp());
        docRef.set(timeStamp).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d(TAG, "successfully updated!");
                storeUIDAndNameAndEmail(UID, email, name);
            }
        });
    }

    // 사용자 데이터(UID, email, name) 저장
    public void storeUIDAndNameAndEmail(String UID, String email, String name) {
        Log.d(TAG, "storeUIDAndNameAndEmail()");
        UserInfoData userInfoData = new UserInfoData(UID, name, email);

        DocumentReference docRef = usersRef.document(email).collection("user_info").document("current");
        docRef.set(userInfoData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "DocumentSnapshot successfully written!");

                // 기초 설문조사로 이동 (InitialSurveyIntroActivity.java로 이동)
                Intent intent = new Intent(context, SplashActivity.class);
                intent.putExtra("nextActivity", "InitialSurveyIntroActivity");
                intent.putExtra("email", email);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
                googleLogInActivity.finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error writing document", e);
            }
        });
    }


}
