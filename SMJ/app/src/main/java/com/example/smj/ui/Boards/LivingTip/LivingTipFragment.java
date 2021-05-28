package com.example.smj.ui.Boards.LivingTip;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smj.Manager.JWTManager;
import com.example.smj.R;
import com.example.smj.data.entity.board.boardData;
import com.example.smj.domain.usecase.LivingTipUseCase;

import java.util.ArrayList;
import java.util.List;

//홈 버튼
public class LivingTipFragment extends Fragment {

    ArrayList<LivingTipPostData> data = new ArrayList<>();
    ArrayList<LivingTipPostData> searchData = new ArrayList<>();
    RecyclerView recyclerView;
    LivingTipPostAdapter adapter;
    Button writeBtn;
    LivingTipUseCase livingTipUseCase = new LivingTipUseCase(this);
    String key;
    EditText search;
    List<boardData> boardList;

    public LivingTipFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.activity_living_tip_main,container,false);
        livingTipUseCase = new LivingTipUseCase(this);
        recyclerView = (RecyclerView) view.findViewById(R.id.living_tip_post_list);
        writeBtn = view.findViewById(R.id.write_btn);
        key = JWTManager.getSharedPreference(getActivity(),getString(R.string.saved_JWT));
        search = view.findViewById(R.id.trade_search_text);

        livingTipUseCase.getData(key);
        Log.d("JWT",key);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //searchData.clear();

                for(boardData board : boardList){
                    Log.d("살림 팁 텍스트 와처","살림 팁 텍스트 와처");
                    if(board.getTitle().contains(s) && board.getType().equals("LIVE")){
                        searchData.add(new LivingTipPostData(board.getId(), board.getCategory().getName(),board.getTitle(),board.getContent(),board.getMember().getNickName(),
                                board.getCreatedAt(),board.getMember().getEmail(),board.getImageOne(),board.getImageTwo(),board.getImageThree()));
                    }
                }

                recyclerView.setHasFixedSize(true);
                adapter = new LivingTipPostAdapter(getActivity(), searchData);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LivingTipCreateActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    public void onSuccess(List<boardData> list) {
        //data.clear();
        boardList = list;
        data.clear();
        for(boardData board : list){
            Log.d("살림 팁 onSuccess","onSuccess");
            if(board.getType().equals("LIVE")){
                data.add(new LivingTipPostData(board.getId(), board.getCategory().getName(),board.getTitle(),board.getContent(),board.getMember().getNickName(),
                        board.getCreatedAt(),board.getMember().getEmail(),board.getImageOne(),board.getImageTwo(),board.getImageThree()));
            }
        }
        recyclerView.setHasFixedSize(true);
        adapter = new LivingTipPostAdapter(getActivity(), data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        adapter.adapterRefresh();
    }

    @Override
    public void onResume(){
        super.onResume();
        key = JWTManager.getSharedPreference(getActivity(),getString(R.string.saved_JWT));
        livingTipUseCase.getData(key);
    }
}
