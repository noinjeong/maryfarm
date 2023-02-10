package com.numberONE.maryfarm.ui.AlgorithmPage;

import android.os.Bundle;

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
import com.numberONE.maryfarm.R;

public class RecommendActivity extends AppCompatActivity {

    private String [] data = {"Step 1", "Step 2", "Step 3", "Step 4", "Step 5"};

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
                        tab.setIcon(R.drawable.tier1);
                    }
                }
        ).attach();
    }

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
}