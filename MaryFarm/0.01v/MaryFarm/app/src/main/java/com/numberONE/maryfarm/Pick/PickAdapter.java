//package com.numberONE.maryfarm.Pick;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.numberONE.maryfarm.R;
//
//public class PickAdapter extends RecyclerView.Adapter<PickAdapter.ViewHolder> {
//
//    private int image;
//    private String name;
//
//    public PickAdapter(int image, String name) {
//        this.image = image;
//        this.name = name;
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder{
//
//        public ImageView image;
//        public TextView name;
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            this.image = itemView.findViewById(R.id.pick_one_Img);
//            this.name = itemView.findViewById(R.id.pick_one_Title);
//        }
//    }
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.recycler_pick_one, parent, false);
//
//        viewHolder=new ViewHolder(view);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//
//        try {
//            viewHolder.image.setImageResource(this.image);
//            viewHolder.name.setImageResource(this.name);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public int getItemCount() { return image.length }
//}
