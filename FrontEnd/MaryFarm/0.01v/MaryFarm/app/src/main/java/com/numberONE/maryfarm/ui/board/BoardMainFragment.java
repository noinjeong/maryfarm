package com.numberONE.maryfarm.ui.board;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.numberONE.maryfarm.R;
import com.numberONE.maryfarm.databinding.FragmentBoardMainBinding;

public class BoardMainFragment extends Fragment {

    FragmentBoardMainBinding binding;

    RecyclerView recyclerView_board;
    RecyclerView.LayoutManager layoutManager_board;
    RecyclerView.Adapter adapter_board;

    // 지역선택 스피너
    ArrayAdapter<CharSequence> spinnerAdapter=null;
    Spinner spinner = null;

    public BoardMainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding =FragmentBoardMainBinding.inflate(inflater,container,false);

        spinnerAdapter=ArrayAdapter.createFromResource(getActivity(),R.array.board_spinner,android.R.layout.simple_spinner_dropdown_item);
        binding.boardSpinner.setAdapter(spinnerAdapter);


//        recyclerView_board=binding.boardRecycler;

        recyclerView_board.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        layoutManager_board=new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        recyclerView_board.setLayoutManager(layoutManager_board);

        String[] title=new String[11];
        String[] nickname=new String[11];
        String[] date=new String[11];
        String[] viewCnt=new String[11];
        String[] commentCnt=new String[11];

        int[] image={R.drawable.gallery_icon,R.drawable.gallery_icon,R.drawable.gallery_icon,R.drawable.gallery_icon,R.drawable.gallery_icon,
                R.drawable.gallery_icon,R.drawable.gallery_icon,R.drawable.gallery_icon,R.drawable.gallery_icon,R.drawable.gallery_icon};

        for(int i=1;i<=10;i++){
            title[i]="타이틀"+i; nickname[i]="닉네임"+i ; date[i]="23.02."+(i+10);
            viewCnt[i]=i+""; commentCnt[i]=i+"";
        }

        adapter_board=new BoardAdapter(title,nickname,date,image,viewCnt,commentCnt);

        recyclerView_board.setAdapter(adapter_board);

        ViewGroup view =binding.getRoot();
        return view;
    }
}