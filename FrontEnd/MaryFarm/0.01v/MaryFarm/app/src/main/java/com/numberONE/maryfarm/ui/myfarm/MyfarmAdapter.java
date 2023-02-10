package com.numberONE.maryfarm.ui.myfarm;

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
import com.numberONE.maryfarm.Retrofit.UserPlant;

import java.util.List;

public class MyfarmAdapter extends RecyclerView.Adapter<MyfarmAdapter.MyViewHolder>{

    Context context;
    List<UserPlant> items;

    public MyfarmAdapter(Context context, List<UserPlant> items) {
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

//        holder.title.setText((items.get(position).getTitle()));
//        Glide.with(context)
//                .load(items.get(position).getImagepath())
//                .into(holder.first_photo);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // viewholder 작성
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        ImageView first_photo;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            title = itemView.findViewById(R.id.title);
            first_photo = itemView.findViewById(R.id.first_photo);
        }
    }
}
