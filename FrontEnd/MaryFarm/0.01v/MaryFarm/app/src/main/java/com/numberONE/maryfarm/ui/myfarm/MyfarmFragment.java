package com.numberONE.maryfarm.ui.myfarm;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.numberONE.maryfarm.Diary.DiaryAddActivity;
import com.numberONE.maryfarm.Diary.DiaryDetailActivity;
import com.numberONE.maryfarm.Pick.PickAlgorithm;
import com.numberONE.maryfarm.R;
import com.numberONE.maryfarm.Retrofit.ServerAPI;
import com.numberONE.maryfarm.Retrofit.UserInfo;
import com.numberONE.maryfarm.Retrofit.UserPlant;
import com.numberONE.maryfarm.databinding.FragmentMyfarmProfileBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MyfarmFragment extends Fragment {

    // 제가 작성한 글이 없을 시, 하단 페이지에서 보여줄 각 버튼 집합
    private View view;

    // 버튼 클릭시 디테일 화면으로 이동
    private Button detailBtn;

    // 취향 추천 알고리즘 페이지로 리다이렉트 시키는 버튼인데, 임시로 첫글 작성 버튼으로 만듦
    private ImageButton recommendBtn;

    // 취향 추천 알고리즘 페이지로 리다이렉트 시키는 버튼
    private ImageButton recommendMonthBtn;

    private TextView nickname;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_myfarm_profile,container,false);

        SharedPreferences pref;
        String userId, userNickname;

        pref = getActivity().getSharedPreferences("pref", Activity.MODE_PRIVATE);
        userId = pref.getString("userId", "Null");
        userNickname = pref.getString("userNickname", "Null");

        detailBtn = (Button) view.findViewById(R.id.detailBtn);
        recommendBtn = (ImageButton) view.findViewById(R.id.recommendBtn);
        recommendMonthBtn = (ImageButton) view.findViewById(R.id.recommendMonthBtn);
        nickname = (TextView) view.findViewById(R.id.myFarmName);
        nickname.setText(userNickname);
        
        // 업로드한 작물 피드 유무 확인
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://985e5bce-3b72-4068-8079-d7591e5374c9.mock.pstmn.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServerAPI serverAPI = retrofit.create(ServerAPI.class);
        Call<List<UserPlant>> call = serverAPI.getUserPlant(userId);
        call.enqueue(new Callback<List<UserPlant>>() {
            @Override
            public void onResponse(Call<List<UserPlant>> call, Response<List<UserPlant>> response) {
                List<UserPlant> plantsId = response.body();
                Log.d("sss", "onResponse: !!!!!!!!!!!"+plantsId.toString());

                List<String> list = new ArrayList<>();
                for(UserPlant u : plantsId) {
                    list.add(u.getPlantId());
                }
                Log.d("sss", "onResponse: "+list.toString());

                if (response.body() == null){
                    recommendBtn.setVisibility(View.VISIBLE);
                    recommendMonthBtn.setVisibility(View.VISIBLE);
                } else {
                    detailBtn.setVisibility(View.VISIBLE);

                    for (int i=list.size()-1; i >= 0; i--) {
                        Log.d("!!!s", "onResponse: ~~~~~"+list.get(i));
                    }

//                    Retrofit retrofit2 = new Retrofit.Builder()
//                            .baseUrl("https://985e5bce-3b72-4068-8079-d7591e5374c9.mock.pstmn.io/api/")
//                            .addConverterFactory(GsonConverterFactory.create())
//                            .build();
//
//                    ServerAPI serverAPI2 = retrofit2.create(ServerAPI.class);
//                    Call<UserInfo> call2 = serverAPI2.getDiaries();

                }
            }

            @Override
            public void onFailure(Call<List<UserPlant>> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });
                
        detailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DiaryDetailActivity.class); //fragment라서 activity intent와는 다른 방식
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        recommendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DiaryAddActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        recommendMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PickAlgorithm.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
        return view;
    }

}