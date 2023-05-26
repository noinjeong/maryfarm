package com.numberONE.maryfarm.ui.chat;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.numberONE.maryfarm.R;
import com.numberONE.maryfarm.Retrofit.Chat.ChatModel;
import com.numberONE.maryfarm.Retrofit.dto.RoomListView.RoomDTO;
import com.numberONE.maryfarm.Retrofit.dto.RoomListView.RoomListDTO;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

// 달력 진입 시 키우고 있는 작물 리스트 체크박스 반환~
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    //    public static List<RoomDTO> chatList;
    private String[] nickname, content, date;
    private int[] profile, tier;
    ViewHolder viewHolder;

    // 리사이클러 뷰로 들어갈 내용들 받는 부분
    public ChatAdapter(String[] nickname, String[] content, String[] date, int[] profile, int[] tier){
        this.nickname = nickname;
        this.content = content;
        this.profile = profile;
        this.date = date;
        this.tier = tier;
    }
// 서버 통신으로 받는 코드
//    public ChatAdapter(List<RoomDTO> list){
//        ChatAdapter.chatList = list;
//        Log.i(TAG, "ChatAdapter: "+chatList.get(0).getLatestTimestamp().toString());
//    }

    // 커스텀 리스너 인터페이스 정의
    public interface OnChatClickListener {
        // 클릭 시 동작할 함수
        void onChatClick(View v, int position);
//        void onChatClick(View v, int position, String roomId);
    }

    // 리스너 객체 참조를 저장하는 변수
    static OnChatClickListener onChatClickListener = null;
    // 서버 통신으로 받는 코드
    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public static void setOnChatClickListener(OnChatClickListener listener) {
        onChatClickListener = listener;
    }

    // 뷰홀더
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public String roomId;
        public String OpponentId;
        public String OpponentName;
        public CircleImageView chatProfileImg;
        public TextView chatNickname;
        public TextView chatContent;
        public ImageView chat_new;
        public TextView chatDate;
        public ImageView tier;

        public ViewHolder(View view){
            super(view);
            chatProfileImg = view.findViewById(R.id.chatProfileImg);
            chatNickname = view.findViewById(R.id.chatNickname);
            chatContent = view.findViewById(R.id.chatContent);
            chat_new = view.findViewById(R.id.chat_new);
            chatDate = view.findViewById(R.id.chatDate);
            tier = view.findViewById(R.id.chatTierImg);
            //아이템 클릭 시, 커스텀 이벤트 메서드를 호출
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 존재하는 포지션인지 확인
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        // 리스너 객체의 메서드 호출.
                        if (onChatClickListener != null) {
                            // 날짜 표시하러 ㄱㄱ
                            onChatClickListener.onChatClick(v, pos);
                        }}}
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_chat, parent, false);
        viewHolder = new ViewHolder(holderView);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder mainHolder, int i) {
        mainHolder.chatNickname.setText(this.nickname[i]);
        mainHolder.chatContent.setText(this.content[i]);
        mainHolder.chatDate.setText(this.date[i]);
        mainHolder.chatProfileImg.setImageResource(this.profile[i]);
        mainHolder.tier.setImageResource(this.tier[i]);
        if (i == 0 || i == 1) {
            mainHolder.chat_new.setVisibility(View.VISIBLE);
        }
    }
// 서버 통신으로 받는 코드
//        mainHolder.roomId = this.chatList.get(i).getRoomId();
//        mainHolder.OpponentId = this.chatList.get(i).getOpponentId();
//        mainHolder.OpponentName = this.chatList.get(i).getOpponentName();
////        mainHolder.chatProfileImg.setImageURI(Uri.parse(this.chatList.get(i).getOpponentProfilePath()));
//        mainHolder.chatNickname.setText(this.chatList.get(i).getOpponentName());
//        mainHolder.chatContent.setText(this.chatList.get(i).getLatestMessage());
//        mainHolder.chatDate.setText(this.chatList.get(i).getLatestTimestamp().substring(0, 10)+"\n"+this.chatList.get(i).getLatestTimestamp().substring(12, 17));

    @Override
    public int getItemCount() {
        return nickname.length;
    }

}

