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
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ReadAndWriteUserInfoData {
    private static final String TAG = "ReadAndWriteUserInfoData";

    // Access a Cloud Firestore instance from your Activity
    private FirebaseFirestore db;
    private CollectionReference usersRef;
    private Context context;
    private GoogleLogInActivity googleLogInActivity;

    public ReadAndWriteUserInfoData(Context context, GoogleLogInActivity googleLogInActivity) {
        this.db = FirebaseFirestore.getInstance();
        this.usersRef = db.collection("users");
        this.context = context;
        this.googleLogInActivity = googleLogInActivity;
    }

    // TODO: 하는 중
    // 해당 UID가 이미 있으면 true 반환(기존 유저), 아니면 false 반환(신규 유저)
    public void searchUser(String userID) {
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
                        context.startActivity(intent);
                        googleLogInActivity.finish();
                    } else {
                        Log.d(TAG, "No such document");
                        Intent intent = new Intent(context, SplashActivity.class);
                        intent.putExtra("nextActivity", "InitialSurveyIntroActivity");
                        context.startActivity(intent);
                        googleLogInActivity.finish();
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                    Toast.makeText(context, "ERROR: 앱을 종료하고 다시 시작해주십시오.", Toast.LENGTH_SHORT).show();
                }
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
