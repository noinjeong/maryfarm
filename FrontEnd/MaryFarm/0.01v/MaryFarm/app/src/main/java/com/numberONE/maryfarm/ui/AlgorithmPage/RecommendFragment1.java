package com.numberONE.maryfarm.ui.AlgorithmPage;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.numberONE.maryfarm.R;

public class RecommendFragment1 extends Fragment {
    private ImageButton mButtonSpring;
    private ImageButton mButtonSummer;

    public RecommendFragment1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recommend1, container, false);

        mButtonSpring = view.findViewById(R.id.section_1);
        mButtonSummer = view.findViewById(R.id.section_2);

        mButtonSpring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("season", "spring");

                RecommendFragment2 fragment = new RecommendFragment2();
                fragment.setArguments(bundle);

//                getFragmentManager().beginTransaction()
//                        .replace(R.id.container, fragment)
//                        .addToBackStack(null)
//                        .commit();
            }
        });

        mButtonSummer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("season", "summer");

                RecommendFragment2 fragment = new RecommendFragment2();
                fragment.setArguments(bundle);

//                getFragmentManager().beginTransaction()
//                        .replace(R.id.container, fragment)
//                        .addToBackStack(null)
//                        .commit();
            }
        });

        return view;
    }
}