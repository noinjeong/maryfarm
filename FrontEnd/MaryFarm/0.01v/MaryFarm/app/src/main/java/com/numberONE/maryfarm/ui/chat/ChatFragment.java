package com.numberONE.maryfarm.ui.chat;

import static android.content.ContentValues.TAG;
import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.numberONE.maryfarm.R;
import com.numberONE.maryfarm.Retrofit.Calendar.ItemModel;
import com.numberONE.maryfarm.Retrofit.Chat.ChatModel;
import com.numberONE.maryfarm.Retrofit.Chat.RetrofitChatFactory;
import com.numberONE.maryfarm.Retrofit.Chat.RetrofitChatService;
import com.numberONE.maryfarm.databinding.FragmentChatBinding;
import com.numberONE.maryfarm.ui.home.Calendar.CalendarPlantsAdapter;
import com.numberONE.maryfarm.ui.search.SearchMainFragment;

import java.net.Socket;
import java.net.URISyntaxException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;


public class ChatFragment extends Fragment {

    FragmentChatBinding binding;
    String userId;
    CircleImageView chatProfileImg;
    TextView chatNickname;
    TextView chatContent;
    ImageView chat_new;
    TextView chatDate;

    //프래그먼트 전환용
    FragmentTransaction ft;
//    String url = "ws://example.com:8080/socket/websocket"; // 소켓에 연결하는 엔드포인트가 /socket일때 다음과 같음
//    StompClient stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url);

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // 레트로핏으로 채팅방 목록 가져오기
        RetrofitChatService networkService = RetrofitChatFactory.create();
        networkService.getChat(userId)
                .enqueue(new Callback<ChatModel>() {
                    @Override
                    public void onResponse(Call<ChatModel> call, Response<ChatModel> response){
                        if(response.isSuccessful()){
                            Log.i(TAG, "onResponse: 서버와 연결");
                            ChatModel body = response.body();
                            for(ChatModel m : body) {

                            }
                            // 어댑터 연결
                            recyclerView_chat = binding.calendarPlantsType;
                            layoutManager_chat = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
                            recyclerView_chat.setLayoutManager(layoutManager_chat);
                            adapter_chat = new CalendarPlantsAdapter(plantName[0], plantCreatedAt[0], plantHarvestTime[0]);
                            recyclerView_chat.setAdapter(adapter_chat);
                        }
                    }
                    @Override
                    public void onFailure(Call<List<ItemModel>> call, Throwable t){
                        Log.e(TAG, "onFailure: 서버 연결 실패");
                        Log.e(TAG, "onFailure:", t);
                    }
                });

        binding = FragmentChatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        chatContent = binding.chatText;
        chatContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//            ft=getSupportFragmentManager().beginTransaction();
            ChatRoomFragment chatRoomFragment = new ChatRoomFragment();
            ft.replace(R.id.main_activity,chatRoomFragment);
            ft.commit();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        mSocket.disconnect();
        binding = null;
    }
}