package com.numberONE.maryfarm.ui.AlgorithmPage;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.numberONE.maryfarm.R;
import com.numberONE.maryfarm.Retrofit.FollowFollowing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class RecommendActivity extends AppCompatActivity {

    // 각 뷰테이블에서 값을 저장해서 마지막에 쏴주는 설문조사 페이지를 작성할겁니다.

    // 일단 먼저 사용할 변수들을 선언해줍니다.
    private int buttonValue1;
    private int buttonValue2;
    private int buttonValue3;
    private int buttonValue4;
    private int buttonValue5;


    // 각 탭의 제목을 임시로 저장해놓는 스트링 리스트 입니다.
    // 필요성 보다는, 각각 페이지가 잘 이어져 있나 확인하는 용도입니다.
    private String [] data = {"개화 계절", "꽃잎 색깔", "잎 색깔", "급수 주기", "텃밭 광량"};


    // 추천 알고리즘 액티비티가 구동시 작동하는 코드입니다.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        ViewPager2 recommendViewPager = findViewById(R.id.recommendViewPager);


        recommendViewPager.setAdapter(
                new RecommendAdapter(this)
        );
        TabLayout recommendTabLayout = findViewById(R.id.recommendTabLayout);
        new TabLayoutMediator(
                recommendTabLayout,
                recommendViewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        tab.setText(data[position]);
//                        tab.setIcon(R.drawable.tier1);
                    }
                }
        ).attach();
    }

    // 디버깅용 okhttp
//    OkHttpClient client = new OkHttpClient.Builder()
//            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//            .build();

    // 각 뷰페이저를 만들어주는 Adapter 클래스를 선언해줍니다.

    class RecommendAdapter extends FragmentStateAdapter {

        public RecommendAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        public RecommendAdapter(@NonNull Fragment fragment) {

            super(fragment);
        }

        public RecommendAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new RecommendFragment1();
                case 1:
                    return new RecommendFragment2();
                case 2:
                    return new RecommendFragment3();
                case 3:
                    return new RecommendFragment4();
                case 4:
                    return new RecommendFragment5();
            }
            return null;
        }


        @Override
        public int getItemCount() {

            return data.length;
        }
    }

    // 각 뷰페이저에서 값을 저장하고 마지막에 던져주는 로직입니다. 총 5페이지가 있으니 5개를 던져줍니다.
    // 계절을 물어보는 부분입니다.
    // ignSeasonChk 를 사용합니다.
    public void setButtonValue1(int value) {
        buttonValue1 = value;
    }

    //
    public void setButtonValue2(int value) {
        buttonValue2 = value;
    }
    public void setButtonValue3(int value) {
        buttonValue3 = value;
    }
    public void setButtonValue4(int value) {
        buttonValue4 = value;
    }
    public void setButtonValue5(int value) {
        buttonValue5 = value;
    }


    public interface RecommendApi {

        // 일단 GET 으로 통신하는게 먼저니까, GET으로 던져줍니다.
        @GET("service/garden/gardenList/")
        Call<ResponseBody> getData(
                @Query("apiKey") String apiKey,
                @Query("lightChkVal") String lightChkVal,
//                @Query("flclrChkVal") String flclrChkVal,
//                @Query("lefcolrChkVal") String lefcolrChkVal,
                @Query("ignSeasonChkVal") String ignSeasonChkVal,
                @Query("waterCtcleSel") String waterCtcleSel
        );

        // 이후 POST 요청도 넣어야 합니다.
    }


    // 참고용 url 목록
    // List 전부를 받고 싶다면: "http://api.nongsaro.go.kr/service/garden/gardenList/?apiKey=20230207XQ7NCQDMG0SKVFKAW0YHNQ"
    // 디테일 페이지를 받고 싶다면:
    // 시진을 UI로 불러오고 싶다면: https://www.nongsaro.go.kr/cms_contents/301/
    // 정상적인 요청 예시: http://api.nongsaro.go.kr/service/garden/gardenList/?apiKey=20230207XQ7NCQDMG0SKVFKAW0YHNQ&ignSeasonChkVal=073002



    public void makeApiCall() {
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.nongsaro.go.kr")
                .addConverterFactory(GsonConverterFactory.create(gson))
//                .client(client)
                .build();

        RecommendApi recommendApi = retrofit.create(RecommendApi.class);

        Call<ResponseBody> call = recommendApi.getData(
                "20230207XQ7NCQDMG0SKVFKAW0YHNQ",
                "0" + buttonValue5,
//                "0" + buttonValue2,
//                "0" + buttonValue3,
                "0" + buttonValue1,
                "0" + buttonValue4

        );

        Log.d("", "makeApiCall: !!!"+call.toString());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "makeApiCall onresponse" + response.code());
                Log.d(TAG, "makeApiCall onresponse" + response);
                Log.d(TAG, "makeApiCall onresponse" + response.body());

                if (response.isSuccessful()) {
                    ArrayList<RecommendData> resultList = new ArrayList<>();
                    try {
                        String responseString = response.body().string();
                        Gson gson = new Gson();
                        RecommendData[] data = gson.fromJson(responseString,RecommendData[].class);
                        resultList = new ArrayList<>(Arrays.asList(data));
                        Log.d(TAG, "onResponse: 자, 이것은 리저트 리스트여 =  " + resultList);
                        Log.d(TAG, "onResponse: 자, 이것은 리스폰스 스트링이여" + responseString);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("API Response", "Request failed with code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Log.e("API Response", "Request failed with error: " + t.getMessage());
            }
        });
    }
}