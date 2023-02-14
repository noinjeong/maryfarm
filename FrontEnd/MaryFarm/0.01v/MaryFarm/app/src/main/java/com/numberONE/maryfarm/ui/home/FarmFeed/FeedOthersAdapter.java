package com.numberONE.maryfarm.ui.home.FarmFeed;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.numberONE.maryfarm.R;
import com.numberONE.maryfarm.Retrofit.Diary.DiaryTopRecommend;
import com.numberONE.maryfarm.Retrofit.ServerAPI;
import com.numberONE.maryfarm.Retrofit.Thumbnail;
import com.numberONE.maryfarm.Retrofit.dto.DetailDiariesPerPlantView.DetailDiariesPerPlantDTO;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FeedOthersAdapter extends RecyclerView.Adapter<FeedOthersAdapter.ViewHolder> {
    private String URL = "https://s3.ap-northeast-2.amazonaws.com/maryfarm.bucket/";
    ViewHolder viewHolder;

    Context context;
    List<Thumbnail> diaryTopRecommendList;

    // 생성자
    public FeedOthersAdapter(Context context, List<Thumbnail> list){
        this.context=context;
        this.diaryTopRecommendList=list;
    }


    public static class ViewHolder extends  RecyclerView.ViewHolder{
        public TextView title, created_at;
        public ImageView image1,image2,image3, profile;

        public ViewHolder(View view){
            super(view);
            this.title = view.findViewById(R.id.other_title);
            this.image1=view.findViewById(R.id.other_image_1);
            this.image2=view.findViewById(R.id.other_image_2);
            this.image3=view.findViewById(R.id.other_image_3);
            this.profile=view.findViewById(R.id.other_profile);
            this.created_at=view.findViewById(R.id.thunbnailStartDate);
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
//     리스트로 받아와서 이미지 처리로직 다시 정리하기
        //      이미지 1 ,2 ,3
        Glide.with(context)
                .load(URL + diaryTopRecommendList.get(i).getThumbImg1())
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(mainHolder.image1);
        Glide.with(context)
                .load(URL + diaryTopRecommendList.get(i).getThumbImg2())
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(mainHolder.image2);
        Glide.with(context)
                .load(URL + diaryTopRecommendList.get(i).getThumbImg3())
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(mainHolder.image3);

        String plantId = diaryTopRecommendList.get(i).getPlantId();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maryfarm.shop/maryfarm-plant-service/api/diary/group/"+plantId+"/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ServerAPI serverAPI = retrofit.create(ServerAPI.class);
        Call<DetailDiariesPerPlantDTO> call = serverAPI.getDiaries(plantId);
        call.enqueue(new Callback<DetailDiariesPerPlantDTO>() {
            @Override
            public void onResponse(Call<DetailDiariesPerPlantDTO> call, Response<DetailDiariesPerPlantDTO> response) {
                Log.d("", "onResponse: !!!!!!!"+response.body().getTitle().toString());
                mainHolder.title.setText(response.body().getTitle());
                mainHolder.created_at.setText(response.body().getPlantCreatedDate().substring(0, 10));
                Glide.with(context)
                        .load(URL + response.body().getProfilePath())
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(mainHolder.profile);
            }

            @Override
            public void onFailure(Call<DetailDiariesPerPlantDTO> call, Throwable t) {
                Log.d("", "onFailure: "+t.toString());
            }
        });

    }

    @Override
    public int getItemCount() {
        return diaryTopRecommendList!=null ? diaryTopRecommendList.size() : 0 ;
    }

}

