package com.numberONE.maryfarm.ui.home.Calendar;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.numberONE.maryfarm.R;

// 일자 선택 시 해당 일자에 키우고 있는 작물 정보 리스트 반환하는 리싸이클러뷰 어댑터~
public class CalendarPickPlantAdapter extends RecyclerView.Adapter<CalendarPickPlantAdapter.ViewHolder> {
    private String[] plantname, dday;
    ViewHolder viewHolder;

    // 리사이클러 뷰로 들어갈 내용들 받는 부분
    public CalendarPickPlantAdapter(String[] plantname, String[] dday){
        this.plantname = plantname;
        this.dday = dday;
    }
    // 클릭 이벤트
    private static OnItemClickListener onItemClickListener = null;

    //인터페이스 선언
    public interface OnItemClickListener{
        //클릭시 동작할 함수
        void onItemClick(View v, int pos);
    }

    public static void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener = listener;
    }
// 뷰홀더
    public static class ViewHolder extends  RecyclerView.ViewHolder{
        public TextView plantsNameTextView;
        public ImageButton calendar_pill;


        public ViewHolder(View view){
            super(view);
            plantsNameTextView = view.findViewById(R.id.textView);
            calendar_pill = view.findViewById(R.id.calendar_pill);

            calendar_pill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //존재하는 포지션인지 확인
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        //동작 호출 (onItemClick 함수 호출)
                        if(onItemClickListener != null){
                            onItemClickListener.onItemClick(v, pos);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_calendar_pick_plant, parent, false);
        viewHolder = new ViewHolder(holderView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder mainHolder, int i) {
        mainHolder.plantsNameTextView.setText(this.plantname[i]+"\n"+this.dday[i]);
    }

    @Override
    public int getItemCount() {
        return plantname.length;
    }

}

