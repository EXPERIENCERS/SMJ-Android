package com.example.smj.ui.Boards.Transaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.smj.callback.TransactionGetData;
import com.example.smj.data.entity.board.boardData;
import com.example.smj.domain.usecase.TransactionUseCase;

import java.util.ArrayList;
import java.util.List;

public class TransactionFragment extends Fragment implements TransactionGetData {
    private List<TransactionPostData> data = new ArrayList<>();
    private RecyclerView recyclerView;
    private TransactionPostAdapter adapter;
    private TransactionUseCase transactionUseCase;
    private Button writeButton;
    private String token;
    private EditText search;

    public TransactionFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        transactionUseCase = new TransactionUseCase(this);

        View view = inflater.inflate(R.layout.activity_transaction_main,container,false);

        recyclerView = (RecyclerView) view.findViewById(R.id.transaction_post_list);
        search = view.findViewById(R.id.search_text);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        token =  JWTManager.getSharedPreference(getContext(),getString(R.string.saved_JWT));
        //데이터 받아오기
        transactionUseCase.getData(token);
        writeButton = (Button) view.findViewById(R.id.write_btn);
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TransactionCreateActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onSuccess(List<boardData> list) {
        data.clear();
        //list를 받았을때 값을 add, 리사이클러뷰에 뿌림
        int getListSize = list.size();
        for(int i = 0; i<getListSize; i++) {
            if(list.get(i).getType().equals("TRADE")) {
                data.add(new TransactionPostData(list.get(i).getCategory().getName(),list.get(i).getTitle(),list.get(i).getContent(),list.get(i).getMember().getNickName(),list.get(i).getCreatedAt(),
                        list.get(i).getMember().getImage(),list.get(i).getImageOne(),list.get(i).getImageTwo(),list.get(i).getImageThree(),list.get(i).getId()));
            }
        }
        recyclerView.setHasFixedSize(true);
        adapter = new TransactionPostAdapter(getActivity(), data, transactionUseCase);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onResume(){
        super.onResume();
        update();
    }
    public void update(){
        transactionUseCase.getData(token);
        adapter = new TransactionPostAdapter(getActivity(), data,transactionUseCase);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }
}