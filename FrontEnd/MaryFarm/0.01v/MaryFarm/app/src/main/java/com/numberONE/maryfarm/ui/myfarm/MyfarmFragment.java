package com.numberONE.maryfarm.ui.myfarm;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.numberONE.maryfarm.Diary.DiaryAddActivity;
import com.numberONE.maryfarm.Diary.DiaryDetailActivity;
import com.numberONE.maryfarm.Pick.PickAlgorithm;
import com.numberONE.maryfarm.R;

public class MyfarmFragment extends Fragment {

    public MyfarmFragment() {
        // Required empty public constructor
    }



    // 리사이클러 뷰 작동 코드




    private View view;

    // 버튼 클릭시 디테일 화면으로 이동
    private Button detailBtn;

    // 취향 추천 알고리즘 페이지로 리다이렉트 시키는 버튼인데, 임시로 첫글 작성 버튼으로 만듦
    private ImageButton recommendBtn;

    // 취향 추천 알고리즘 페이지로 리다이렉트 시키는 버튼
    private ImageButton recommendMonthBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_myfarm_profile,container,false);

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