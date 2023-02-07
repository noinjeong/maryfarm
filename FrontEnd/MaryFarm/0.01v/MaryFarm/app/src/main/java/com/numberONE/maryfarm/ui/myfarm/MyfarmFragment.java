package com.numberONE.maryfarm.ui.myfarm;

import static androidx.recyclerview.widget.RecyclerView.*;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.numberONE.maryfarm.Diary.DiaryAddActivity;
import com.numberONE.maryfarm.Diary.DiaryDetailActivity;
import com.numberONE.maryfarm.R;
import com.numberONE.maryfarm.databinding.FragmentMyfarmProfileBinding;
import com.numberONE.maryfarm.databinding.ItemFarmFeedBinding;

import java.util.ArrayList;
import java.util.List;

public class MyfarmFragment extends Fragment {


    public MyfarmFragment() {
        // Required empty public constructor
    }
    private FragmentMyfarmProfileBinding binding;

    public String userId = "temp_123";


    // 리사이클러 뷰 선언 코드

    RecyclerView profile_rv;
    LayoutManager profileLayoutmanager;
    Adapter profileAdapter;

    ArrayList<FarmFeedData> arrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMyfarmProfileBinding.inflate(inflater, container, false);
        ViewGroup view = binding.getRoot();



        // 어댑터 연결: 뷰와 매니저 선언
        profile_rv = binding.profileRv;
        profileLayoutmanager = new LinearLayoutManager(getActivity());
        // 선언한 두 부분을 연결
        profile_rv.setLayoutManager(profileLayoutmanager);
        // 넣어줄 데이터를 담아서 가져가는 어댑터 ( 괄호안에 넣어서 어댑터로 슝 )
        profileAdapter = new FarmFeedAdapter(arrayList);
        // rv에 어댑터 연결
        profile_rv.setAdapter(profileAdapter);

        // 추천 페이지로 이동 버튼
        // 버튼 클릭시 디테일 화면으로 이동
        Button detailBtn;

        // 취향 추천 알고리즘 페이지로 리다이렉트 시키는 버튼인데, 임시로 첫글 작성 버튼으로 만듦
        ImageButton recommendBtn;

        // 취향 추천 알고리즘 페이지로 리다이렉트 시키는 버튼
        ImageButton recommendMonthBtn;

        detailBtn = (Button) view.findViewById(R.id.detailBtn);
        recommendBtn = (ImageButton) view.findViewById(R.id.recommendBtn);
        recommendMonthBtn = (ImageButton) view.findViewById(R.id.recommendMonthBtn);

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

//        recommendMonthBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), PickAlgorithm.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                startActivity(intent);
//            }
//        });
        return view;
    }}

