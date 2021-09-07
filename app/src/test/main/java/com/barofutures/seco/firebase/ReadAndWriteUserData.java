package com.barofutures.seco.firebase;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.barofutures.seco.model.UserData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReadAndWriteUserData {
    private static final String TAG = "ReadAndWriteUserData";
    private Context context;

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]


    // TODO: database parameter가 왜 필요하지...?
    public ReadAndWriteUserData(DatabaseReference database, Context context) {
        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        this.context = context;
    }

    // 사용자 추가
    // [START rtdb_write_new_user]
    public void writeNewUser(String userId, String name, String email, String SETI, String CMI, String badge, String like, String SET) {
        UserData user = new UserData(name, email, SETI, CMI, badge, like, SET);

        mDatabase.child("users").child(userId).setValue(user);
        // mDatabase.child("users").child(userId).child("username").setValue(name);
    }
    // [END rtdb_write_new_user]

    public void writeNewUserWithTaskListeners(String userId, String name, String email) {
        UserData user = new UserData(name, email);

        // [START rtdb_write_new_user_task]
        mDatabase.child("users").child(userId).setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        Toast.makeText(context, "새 사용자 등록이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        Toast.makeText(context, "새 사용자 등록이 실패하었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
        // [END rtdb_write_new_user_task]
    }


    private void addPostEventListener(DatabaseReference mPostReference) {
        // [START post_value_event_listener]
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
//                Post post = dataSnapshot.getValue(Post.class);
                // ..
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mPostReference.addValueEventListener(postListener);
        // [END post_value_event_listener]
    }
}
