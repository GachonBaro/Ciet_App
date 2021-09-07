package com.barofutures.seco.fragments.authorize;

import static android.app.Activity.RESULT_OK;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.barofutures.seco.MainActivity;
import com.barofutures.seco.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraAuthFragment extends Fragment {
    private ViewGroup viewGroup;
    private ImageButton activityPicButton, billPicButton;
    private Button submitButton;

    // 카메라 관련 변수
    static final int REQUEST_IMAGE_CAPTURE = 1;
    final static int TAKE_PICTURE = 1;
    // 경로 변수와 요청변수 생성
    private String mCurrentPhotoPath;
    final static int REQUEST_TAKE_PHOTO_ACTIVITY = 1;
    final static int REQUEST_TAKE_PHOTO_BILL= 2;

    public CameraAuthFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View 초기화 및 연결
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_camera_auth, container, false);
        activityPicButton = viewGroup.findViewById(R.id.fragment_authorize_camera_shot_activity);
        billPicButton = viewGroup.findViewById(R.id.fragment_authorize_camera_shot_bill);
        submitButton = viewGroup.findViewById(R.id.fragment_authorize_camera_submit);


        // 카메라 권한 요청
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((getContext()).checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    || (getContext()).checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                Log.d("camera", "권한 설정 요청");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            } else {
                Log.d("camera", "권한 설정 완료");
            }
        }

        // TODO : 사진 선택하면 그 사진으로 버튼 배경 바뀌고, 하나라도 등록하면 올리기 불들어 오도록
        activityPicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent(REQUEST_TAKE_PHOTO_ACTIVITY);     // 사진 찍고 저장하기
                submitButton.setBackgroundResource(R.drawable.button_initq_checked);
            }
        });

        billPicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent(REQUEST_TAKE_PHOTO_BILL);        // 사진 찍고 저장하기
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "사진 업로드 필요", Toast.LENGTH_SHORT).show();
            }
        });

        return viewGroup;
    }

    /*
     * Camera
     */
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
                            ImageDecoder.Source source = ImageDecoder.createSource(getContext().getContentResolver(), Uri.fromFile(file));
                            try {
                                bitmap = ImageDecoder.decodeBitmap(source);
                                if (bitmap != null) {
                                    activityPicButton.setImageBitmap(bitmap);
                                    galleryAddPic();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), Uri.fromFile(file));
                                if (bitmap != null) {
                                    activityPicButton.setImageBitmap(bitmap);
                                    galleryAddPic();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
                }

                case REQUEST_TAKE_PHOTO_BILL:{
                    if (resultCode == RESULT_OK) {
                        File file = new File(mCurrentPhotoPath);
                        Bitmap bitmap;
                        if (Build.VERSION.SDK_INT >= 29) {
                            ImageDecoder.Source source = ImageDecoder.createSource(getContext().getContentResolver(), Uri.fromFile(file));
                            try {
                                bitmap = ImageDecoder.decodeBitmap(source);
                                if (bitmap != null) {
                                    billPicButton.setImageBitmap(bitmap);
                                    galleryAddPic();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), Uri.fromFile(file));
                                if (bitmap != null) {
                                    billPicButton.setImageBitmap(bitmap);
                                    galleryAddPic();
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

    // 사진 저장
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        Log.d("mmPath", imageFileName);
        File storageDir = (getContext()).getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        Log.d("mmPath", storageDir.toString());
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.d("mmPath", mCurrentPhotoPath);
        return image;
    }

    private void dispatchTakePictureIntent(int REQUEST_BUTTON) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
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
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "com.barofutures.seco.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_BUTTON);
            }
        }
    }

    // 갤러리에 사진 추가
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        ((MainActivity) getActivity()).sendBroadcast(mediaScanIntent);
        Toast.makeText(getContext(), "사진이 저장되었습니다", Toast.LENGTH_LONG).show();
    }
}