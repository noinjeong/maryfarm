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

//import io.socket.client.IO;
//import io.socket.client.Socket;

public class ChatFragment extends Fragment {

    private FragmentChatBinding binding;
    private Socket mSocket;
    private String username;
    private String roomNumber;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ChatViewModel dashboardViewModel =
                new ViewModelProvider(this).get(ChatViewModel.class);

        binding = FragmentChatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
//         init();

        return root;
    }

//    private void init() {
//        try {
//            mSocket = IO.socket("http://10.0.2.2:80/");
//            Log.d("SOCKET", "Connection success : " + mSocket.id());
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//
//        Intent intent = getActivity().getIntent(); //getIntent()만 안 되서 바꿔봄
//        username = intent.getStringExtra("username");
//
//        mSocket.connect();
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        mSocket.disconnect();
        binding = null;
    }
}