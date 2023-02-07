package com.numberONE.maryfarm.ui.home.FarmFeed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.numberONE.maryfarm.R;
import com.numberONE.maryfarm.Retrofit.Diary.DiaryTopRecommend;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FeedOthersAdapter extends RecyclerView.Adapter<FeedOthersAdapter.ViewHolder> {

    ViewHolder viewHolder;

    Context context;
    List<DiaryTopRecommend> diaryTopRecommendList;

    // 생성자
    public FeedOthersAdapter(Context context, List<DiaryTopRecommend> list){
        this.context=context;
        this.diaryTopRecommendList=list;
    }


    public static class ViewHolder extends  RecyclerView.ViewHolder{
        public TextView nickname, title;
        public ImageView image1,image2,image3;
        public CircleImageView profile;

        public ViewHolder(View view){
            super(view);
            this.nickname = view.findViewById(R.id.other_nickname);
            this.title = view.findViewById(R.id.other_title);
            this.image1=view.findViewById(R.id.other_image_1);
            this.image2=view.findViewById(R.id.other_image_2);
            this.image3=view.findViewById(R.id.other_image_3);
            this.profile=view.findViewById(R.id.other_profile);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_feed_others, parent, false);
        viewHolder = new ViewHolder(holderView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder mainHolder, int i) {
        Glide
    }

    @Override
    public int getItemCount() {
        return diaryTopRecommendList.size();
    }
}

