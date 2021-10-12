package com.barofutures.seco.firebase.firestore;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

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
import com.google.firestore.v1.WriteResult;

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

    // TODO: 하는 중
    // 해당 UID가 이미 있으면 true 반환(기존 유저), 아니면 false 반환(신규 유저)
    public void searchUser(String userID, String email, String name) {
//        UserSearchingThread userSearchingThread = new UserSearchingThread(userID);
//        Thread thread = new Thread(userSearchingThread);
//        try {
//            thread.start();
//            thread.join();
//            Log.d(TAG, "11userSearchingThread.isFound = " + userSearchingThread.isFound);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            Log.d(TAG, "22userSearchingThread.isFound = " + userSearchingThread.isFound);
//            return userSearchingThread.isFound;
//        }

        DocumentReference docRef = usersRef.document(userID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Intent intent = new Intent(context, SplashActivity.class);
                        intent.putExtra("nextActivity", "MainActivity");
                        intent.putExtra("UID", userID);
                        context.startActivity(intent);
                        googleLogInActivity.finish();
                    } else {
                        Log.d(TAG, "No such document");
                        updateServerTimestamp(userID, email, name);

//                        Intent intent = new Intent(context, SplashActivity.class);
//                        intent.putExtra("nextActivity", "InitialSurveyIntroActivity");
//                        context.startActivity(intent);
//                        googleLogInActivity.finish();
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                    Toast.makeText(context, "ERROR: 앱을 종료하고 다시 시작해주십시오.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    // update Server Timestamp
    public void updateServerTimestamp(String UID, String email, String name) {
        DocumentReference docRef = usersRef.document(UID);
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
        UserInfoData userInfoData = new UserInfoData(UID, name, email);

        DocumentReference docRef = usersRef.document(UID).collection("user_info").document("current");
//        DocumentReference docRef = usersRef.document(UID);
        docRef.set(userInfoData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "DocumentSnapshot successfully written!");

                // 기초 설문조사로 이동 (InitialSurveyIntroActivity.java로 이동)
                Intent intent = new Intent(context, SplashActivity.class);
                intent.putExtra("nextActivity", "InitialSurveyIntroActivity");
                intent.putExtra("UID", UID);
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


//    class UserSearchingThread implements Runnable {
//        String userID;
//        boolean isFound;        // true - UID 찾음(기존 유저임)
//
//        public UserSearchingThread(String userID) {
//            this.userID = userID;
//        }
//
//        @Override
//        public void run() {
//            try {
//                DocumentReference docRef = usersRef.document(userID);
//                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if (task.isSuccessful()) {
//                            DocumentSnapshot document = task.getResult();
//                            if (document.exists()) {
//                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
//                                isFound = true;
//                            } else {
//                                Log.d(TAG, "No such document");
//                                isFound = false;
//                            }
//                            Log.d(TAG, "isFound = " + isFound);
//                        } else {
//                            Log.d(TAG, "get failed with ", task.getException());
//                            Toast.makeText(context, "ERROR: 앱을 종료하고 다시 시작해주십시오.", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

}
