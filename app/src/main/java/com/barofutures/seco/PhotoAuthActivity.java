package com.barofutures.seco;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhotoAuthActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView titleTextView;
    private ImageButton takePhotoImageButton;
    private Button uploadPhotoButton;

    // Intent values
    private String title;
    private String badgeNum;
    private String carbonReduction;     // 1회 완료 시 탄소 감축량
    private String startTime;           // 플로깅인 경우만

    private String dateStr;
    private String endTime;

    // 경로 변수, 요정 변수 설정
    private String mCurrentPhotoPath;
    final static int REQUEST_TAKE_PHOTO_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_auth);

        // 카메라 권한 요청
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                    || (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                Log.d("camera", "권한 설정 요청");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            } else {
                Log.d("camera", "권한 설정 완료");
            }
        }

        // intent 값 받아오기
        Intent authIntent = getIntent();
        title = authIntent.getExtras().getString("title");
        badgeNum = authIntent.getExtras().getString("badgeNum");
        carbonReduction = authIntent.getExtras().getString("carbonReduction");
        if(title.equalsIgnoreCase("플로깅")) {     // 플로깅인 경우만
            startTime = authIntent.getExtras().getString("startTime");
        }

        // 상단바 완전 투명
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // actionBar 설정
        toolbar=findViewById(R.id.activity_photo_auth_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Status Bar 높이만큼 Padding 부여
        toolbar.setPadding(0,getStatusBarHeight(), 0, 0);

        titleTextView = findViewById(R.id.activity_photo_auth_title);
        takePhotoImageButton = findViewById(R.id.activity_photo_auth_take_photo_imagebutton);
        uploadPhotoButton = findViewById(R.id.activity_photh_auth_upload_button);

        // toolbar title 변경
        titleTextView.setText(title + " 인증하기");

        takePhotoImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 현재 시간 받아오기
                dateStr = getDate();
                endTime = dateStr.substring(8, 10) + ":" + dateStr.substring(10, 12);

                dispatchTakePictureIntent(REQUEST_TAKE_PHOTO_ACTIVITY);     // 사진 찍고 저장하기
                // 업로드 버튼 활성화
                uploadPhotoButton.setBackgroundResource(R.drawable.button_initq_checked);
                uploadPhotoButton.setEnabled(true);
            }
        });

        uploadPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent completionIntent = new Intent(getApplicationContext(), AuthCompletionActivity.class);
                completionIntent.putExtra("title", title);
                completionIntent.putExtra("badgeNum", badgeNum);
                completionIntent.putExtra("endTime", endTime);
                completionIntent.putExtra("carbonReduction", carbonReduction);
                if(title.equalsIgnoreCase("플로깅")) {     // 플로깅인 경우만
                    completionIntent.putExtra("startTime", startTime);
                }
                startActivity(completionIntent);
                finish();
            }
        });

    }

    private String getDate() {
        SimpleDateFormat day = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        return day.format(date);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            Log.d("camera", "Permission: " + permissions[0] + "was " + grantResults[0]);
        }

    }

    // 카메라로 찍은 이미지 가져옴
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {
                case REQUEST_TAKE_PHOTO_ACTIVITY: {
                    if (resultCode == RESULT_OK) {
                        File file = new File(mCurrentPhotoPath);
                        Bitmap bitmap;
                        if (Build.VERSION.SDK_INT >= 29) {
                            ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), Uri.fromFile(file));
                            try {
                                bitmap = ImageDecoder.decodeBitmap(source);
                                if (bitmap != null) {
                                    takePhotoImageButton.setImageBitmap(bitmap);
                                    // 갤러리에 저장
                                    storeBitmapToGallery(bitmap);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(file));
                                if (bitmap != null) {
                                    takePhotoImageButton.setImageBitmap(bitmap);
                                    // 갤러리에 저장
                                    storeBitmapToGallery(bitmap);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private void dispatchTakePictureIntent(int REQUEST_BUTTON) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getApplicationContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.barofutures.seco.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_BUTTON);
            }
        }
    }

    // 경로 설정
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        Log.d("mmPath", imageFileName);
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        Log.d("mmPath", storageDir.toString());
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.d("mmPath", "mCurrentPhotoPath: " + mCurrentPhotoPath);
        return image;
    }

    // Bitmap을 갤러리에 저장
    private void storeBitmapToGallery(Bitmap bitmap) {
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) +"";

//        SimpleDateFormat day = new SimpleDateFormat("yyyyMMddHHmmss");
//        Date date = new Date();

        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        if (!file.exists()) {
            file.mkdirs();
            Toast.makeText(getApplicationContext(), "폴더가 생성되었습니다.", Toast.LENGTH_SHORT).show();

        }

//        String dateStr = day.format(date);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path + "/" + dateStr + "_" + title + ".jpeg");
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path + "/" + dateStr + "_" + title + ".JPEG")));
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Toast.makeText(getApplicationContext(), "사진이 갤러리에 저장되었습니다.", Toast.LENGTH_SHORT).show();
    }
    
}