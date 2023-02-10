package com.numberONE.maryfarm.ui.myfarm;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.numberONE.maryfarm.Diary.DiaryDetailActivity;
import com.numberONE.maryfarm.R;
import com.numberONE.maryfarm.Retrofit.Thumbnail;
import com.numberONE.maryfarm.Retrofit.UserPlant;
import com.numberONE.maryfarm.databinding.RecyclerMyfarmBinding;

import java.util.List;

public class MyfarmAdapter extends RecyclerView.Adapter<MyfarmAdapter.MyViewHolder>{

    Context context;
    List<Thumbnail> items;

    public MyfarmAdapter(Context context, List<Thumbnail> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MyfarmAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_myfarm,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText((items.get(position).getTitle()));
        Glide.with(context)
                .load(items.get(position).getThumbImg1())
                .into(holder.first_photo);
        Glide.with(context)
                .load(items.get(position).getThumbImg2())
                .into(holder.second_photo);
        Glide.with(context)
                .load(items.get(position).getThumbImg3())
                .into(holder.third_photo);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(holder.itemView.getContext(),items.get(holder.getAdapterPosition()).getPlantId(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // viewholder 작성
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        ImageView first_photo, second_photo, third_photo;


        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            int pos = getAdapterPosition();

            title = itemView.findViewById(R.id.plantTitle);
            first_photo = itemView.findViewById(R.id.first_photo);
            second_photo = itemView.findViewById(R.id.second_photo);
            third_photo = itemView.findViewById(R.id.third_photo);
        }
    }
}
