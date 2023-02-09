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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.numberONE.maryfarm.databinding.FragmentChatBinding;

import java.net.Socket;
import java.net.URISyntaxException;

import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;


public class ChatFragment extends Fragment {

    FragmentChatBinding binding;
//    String url = "ws://example.com:8080/socket/websocket"; // 소켓에 연결하는 엔드포인트가 /socket일때 다음과 같음
//    StompClient stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url);

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentChatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        mSocket.disconnect();
        binding = null;
    }
}