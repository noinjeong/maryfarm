package com.numberONE.maryfarm.ui.diary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.numberONE.maryfarm.MainActivity;
import com.numberONE.maryfarm.R;
import com.numberONE.maryfarm.Retrofit.Diary.DiaryInit;
import com.numberONE.maryfarm.Retrofit.RetrofitApiSerivce;
import com.numberONE.maryfarm.Retrofit.RetrofitClient;
import com.numberONE.maryfarm.databinding.FragmentWriteBinding;
import com.numberONE.maryfarm.ui.myfarm.MyfarmFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WriteFragment extends Fragment {

    private static final String TAG="WriteFragment";
    FragmentWriteBinding binding;
    String filePath;
    Bitmap ImgPath;
    Uri photoURI;
    String input_title,input_name,input_content;

    public WriteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWriteBinding.inflate(inflater,container,false);
        Log.d(TAG, "onCreateView: 실행");

        // 키보드 외 화면 클릭시 키보드 내려가기
        binding.writeFragment.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                hideKeyboard();
                return false;
            }
        });

        //카메라
        ActivityResultLauncher<Intent> cameraFileLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        int calRatio = calculateInSampleSize(
                                Uri.fromFile(new File(filePath)),
                                getResources().getDimensionPixelSize(R.dimen.imgSize),
                                getResources().getDimensionPixelSize(R.dimen.imgSize)
                        );

                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = calRatio;
                        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
                        if (bitmap != null) {
                            ImgPath=bitmap; // 작성 버튼 클릭시 넘겨주기 위해
                            binding.Image.setImageBitmap(bitmap);
                        }
                    }
                }
        );
        // 카메라
        binding.CameraBtn.setOnClickListener(view -> {
            try {
                // 현재시간을 기준으로 파일명 생성
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                File file = File.createTempFile(
                        "JPEG" + timeStamp + "_",
                        ".jpg",
                        storageDir
                );
                filePath = file.getAbsolutePath();

                //fileprovider를 이용해서 외부에 공개 ,mainfest에 지정한 authority와 일치하게 작성
                photoURI = FileProvider.getUriForFile(
                        getActivity(),
                        "com.numberONE.maryfarm.fileprovider",
                        file
                );

                if(binding.CameraBtn.getVisibility() == View.VISIBLE){
                    binding.GalleryBtn.setVisibility(View.GONE);
                    binding.CameraBtn.setVisibility(View.GONE);
                    binding.ImageSpinner.setVisibility(View.VISIBLE);
                }

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                cameraFileLauncher.launch(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        // 갤러리와 연동
        ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getData() != null) {
                        int calRatio = calculateInSampleSize(
                                result.getData().getData(),
                                getResources().getDimensionPixelSize(R.dimen.imgSize),
                                getResources().getDimensionPixelSize(R.dimen.imgSize)
                        );
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = calRatio;

                        try {
                            // 사진 읽어오기
                            InputStream inputStream = getActivity().getContentResolver().openInputStream(result.getData().getData());
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
                            if (bitmap != null) {
                                ImgPath = bitmap; // 작성 버튼 클릭시 넘겨주기 위해
                                binding.Image.setImageBitmap(bitmap);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

        //갤러리
        binding.GalleryBtn.setOnClickListener(view -> {

            try {
                // 현재시간을 기준으로 파일명 생성
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                File file = File.createTempFile(
                        "file_" + timeStamp + "_",
                        ".jpg",
                        storageDir
                );
                filePath = file.getAbsolutePath();

                //fileprovider를 이용해서 외부에 공개 ,mainfest에 지정한 authority와 일치하게 작성
                photoURI = FileProvider.getUriForFile(
                        getActivity(),
                        "com.numberONE.maryfarm.fileprovider",
                        file
                );

                // 중앙에 카메라,앨범 버튼 사라지고 왼쪽 상단에 스피너 띄우기
                if(binding.GalleryBtn.getVisibility() == View.VISIBLE){
                    binding.GalleryBtn.setVisibility(View.GONE);
                    binding.CameraBtn.setVisibility(View.GONE);
                    binding.ImageSpinner.setVisibility(View.VISIBLE);
                }

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI); // 갤러리 지정
                intent.setType("image/*");
                galleryLauncher.launch(intent);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

//       사진 선택 후 이미지 위 스피너
        binding.ImageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).toString().equals("카메라")){
                    try {
                        // 현재시간을 기준으로 파일명 생성
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                        File file = File.createTempFile(
                                "file_"+ timeStamp + "_",
                                ".jpg",
                                storageDir
                        );
                        filePath = file.getAbsolutePath();

                        //fileprovider를 이용해서 외부에 공개 ,mainfest에 지정한 authority와 일치하게 작성
                        photoURI = FileProvider.getUriForFile(
                                getActivity(),
                                "com.numberONE.maryfarm.fileprovider",
                                file
                        );

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        cameraFileLauncher.launch(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }else if(parent.getItemAtPosition(position).toString().equals("갤러리")){
                    try {
                        // 현재시간을 기준으로 파일명 생성
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                        File file = File.createTempFile(
                                "file_" + timeStamp + "_",
                                ".jpg",
                                storageDir
                        );
                        filePath = file.getAbsolutePath();

                        //fileprovider를 이용해서 외부에 공개 ,mainfest에 지정한 authority와 일치하게 작성
                        photoURI = FileProvider.getUriForFile(
                                getActivity(),
                                "com.numberONE.maryfarm.fileprovider",
                                file
                        );

                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI); // 갤러리 지정
                        intent.setType("image/*");
                        galleryLauncher.launch(intent);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        binding.writeBtn.setOnClickListener(view -> {


//  ---------------- intent 로직 안할 경우 지워도 되는 코드 --------------------------

            SharedPreferences preferences= getActivity().getSharedPreferences("write",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor =preferences.edit();
            editor.putString("code","100");
            editor.putBoolean("flag",true);
            editor.commit();

            Intent intent=new Intent(getActivity(),MainActivity.class);
            startActivity(intent);
        });
//  ---------------- intent 로직 안할 경우 지워도 되는 코드 마지막 줄 -----------------


////          --- 일단 서버 전송  스탑 --------------------------
////            input_title=binding.title.getText().toString();
////            input_name=binding.plantsTypeSpinner.getSelectedItem().toString();
////            input_content=binding.content.getText().toString();
////
////            Log.d(TAG, "input_title_  " + input_title );
////            Log.d(TAG, "input_plant_option  " + input_name );
////            Log.d(TAG, "input_content  " + input_content );
////
////            // 비트맵 put할 때 40kb넘어가면 오류생겨서 byte배열로 압축해서 넘겨주기
////            ByteArrayOutputStream stream = new ByteArrayOutputStream();
////            ImgPath.compress(Bitmap.CompressFormat.PNG,20,stream);
////            byte[] bytes= stream.toByteArray();
////
////            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),filePath);
////            MultipartBody.Part uploadFile = MultipartBody.Part.createFormData("image", filePath ,requestBody);
////
////            //SharedPreferences 아이디 가져오기
//////            SharedPreferences preferences = getActivity().getSharedPreferences("writeSP", Context.MODE_PRIVATE);
//////            String id = preferences.getString("user_id",null);
////
////            RequestBody name = RequestBody.create(MediaType.parse("text/plain"),input_name);
////            RequestBody title = RequestBody.create(MediaType.parse("text/plain"),input_title);
////            RequestBody content = RequestBody.create(MediaType.parse("text/plain"),input_content);
////            RequestBody userid = RequestBody.create(MediaType.parse("text/plain"),"1234");
//////          RequestBody userid = RequestBody.create(MediaType.parse("text/plain"),id);
////
////            HashMap<String, RequestBody> input =new HashMap<>();
////            input.put("name",name);
////            input.put("title",title);
////            input.put("content",content);
////            input.put("userid",userid);
////
////            Log.d(TAG, "entrySet"+input.entrySet());
////            Log.d(TAG, "이름 input :"+input.get("name"));
////            Log.d(TAG, "타이틀 input : "+input.get("title"));
////
////            Log.d(TAG, "image " + uploadFile.body());
////            Log.d(TAG, "image " + uploadFile);
////            Log.d(TAG, "image " + uploadFile.headers());
////
////            RetrofitApiSerivce service= RetrofitClient.getInstance().create(RetrofitApiSerivce.class);
////            Log.d(TAG, "onCreateView: !!!!"+service);
////            service.postInitFeed(uploadFile,input).enqueue(new Callback<DiaryInit>() {
////                @Override
////                public void onResponse(Call<DiaryInit> call, Response<DiaryInit> response) {
////                    Log.d(TAG, " 일지 작성 response body :" + response.body());
////                    Log.d(TAG, " 일지 작성 응답코드 :" + response.code());
////                    if (response.isSuccessful() ) {
////                        Log.d(TAG, "일지 작성 isSuccessful 응답코드 :" + response.code());
////                        Log.d(TAG, "서버로 전송 성공 ");
////                    }
////                }
////
////                @Override
////                public void onFailure(Call<DiaryInit> call, Throwable t) {
////                    t.printStackTrace();
////                    Log.d(TAG, "서버로 전송 실패 ");
////                }
////            });
////
////            Log.d(TAG, filePath+ ": 파일 path ");
////
////            Log.d("onCreate: ","데이터 전송완료" );
////        });
//////       --------------일단 서버 전송 중단 마지막 코드 -------------------------



//  ---------------- intent 로직 안할 경우 지워도 되는 코드 마지막 줄 -----------------

////          --- 일단 서버 전송  스탑 --------------------------
////            input_title=binding.title.getText().toString();
////            input_name=binding.plantsTypeSpinner.getSelectedItem().toString();
////            input_content=binding.content.getText().toString();
////
////            Log.d(TAG, "input_title_  " + input_title );
////            Log.d(TAG, "input_plant_option  " + input_name );
////            Log.d(TAG, "input_content  " + input_content );
////
////            // 비트맵 put할 때 40kb넘어가면 오류생겨서 byte배열로 압축해서 넘겨주기
////            ByteArrayOutputStream stream = new ByteArrayOutputStream();
////            ImgPath.compress(Bitmap.CompressFormat.PNG,20,stream);
////            byte[] bytes= stream.toByteArray();
////
////            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),filePath);
////            MultipartBody.Part uploadFile = MultipartBody.Part.createFormData("image", filePath ,requestBody);
////
////            //SharedPreferences 아이디 가져오기
//////            SharedPreferences preferences = getActivity().getSharedPreferences("writeSP", Context.MODE_PRIVATE);
//////            String id = preferences.getString("user_id",null);
////
////            RequestBody name = RequestBody.create(MediaType.parse("text/plain"),input_name);
////            RequestBody title = RequestBody.create(MediaType.parse("text/plain"),input_title);
////            RequestBody content = RequestBody.create(MediaType.parse("text/plain"),input_content);
////            RequestBody userid = RequestBody.create(MediaType.parse("text/plain"),"1234");
//////          RequestBody userid = RequestBody.create(MediaType.parse("text/plain"),id);
////
////            HashMap<String, RequestBody> input =new HashMap<>();
////            input.put("name",name);
////            input.put("title",title);
////            input.put("content",content);
////            input.put("userid",userid);
////
////            Log.d(TAG, "entrySet"+input.entrySet());
////            Log.d(TAG, "이름 input :"+input.get("name"));
////            Log.d(TAG, "타이틀 input : "+input.get("title"));
////
////            Log.d(TAG, "image " + uploadFile.body());
////            Log.d(TAG, "image " + uploadFile);
////            Log.d(TAG, "image " + uploadFile.headers());
////
////            RetrofitApiSerivce service= RetrofitClient.getInstance().create(RetrofitApiSerivce.class);
////            Log.d(TAG, "onCreateView: !!!!"+service);
////            service.postInitFeed(uploadFile,input).enqueue(new Callback<DiaryInit>() {
////                @Override
////                public void onResponse(Call<DiaryInit> call, Response<DiaryInit> response) {
////                    Log.d(TAG, " 일지 작성 response body :" + response.body());
////                    Log.d(TAG, " 일지 작성 응답코드 :" + response.code());
////                    if (response.isSuccessful() ) {
////                        Log.d(TAG, "일지 작성 isSuccessful 응답코드 :" + response.code());
////                        Log.d(TAG, "서버로 전송 성공 ");
////                    }
////                }
////
////                @Override
////                public void onFailure(Call<DiaryInit> call, Throwable t) {
////                    t.printStackTrace();
////                    Log.d(TAG, "서버로 전송 실패 ");
////                }
////            });
////
////            Log.d(TAG, filePath+ ": 파일 path ");
////
////            Log.d("onCreate: ","데이터 전송완료" );
////        });
//////       --------------일단 서버 전송 중단 마지막 코드 -------------------------



        //식물타입 콤보박스
        ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(getActivity(),
                R.array.plants_type, R.layout.custom_spinner_layout);

        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);

        binding.plantsTypeSpinner.setAdapter(adapter);

        binding.plantsTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getActivity(),"값을 선택해주세요",Toast.LENGTH_SHORT).show();
            }
        });


        ViewGroup view =binding.getRoot();
        return view;
    }

    private int calculateInSampleSize(Uri fileUri, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;
        try {
            InputStream inputStream = getActivity().getContentResolver().openInputStream(fileUri);

            //inJustDecodeBounds 값을 true 로 설정한 상태에서 decodeXXX() 를 호출.
            //로딩 하고자 하는 이미지의 각종 정보가 options 에 설정 된다.
            BitmapFactory.decodeStream(inputStream, null, options);
            inputStream.close();
            inputStream = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        //비율 계산........................
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;

        //inSampleSize 비율 계산
        if (height > reqHeight || width > reqWidth) {

            int halfHeight = height / 2;
            int halfWidth = width / 2;

            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    // 키보드 내리기 메서드
    private void hideKeyboard()
    {
        if (getActivity() != null && getActivity().getCurrentFocus() != null)
        {
            // 프래그먼트기 때문에 getActivity() 사용
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}

