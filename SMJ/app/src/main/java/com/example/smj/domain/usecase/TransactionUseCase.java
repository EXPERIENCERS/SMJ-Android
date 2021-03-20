package com.example.smj.domain.usecase;

import android.content.Context;

import com.example.smj.callback.RetrofitOnSuccess;
import com.example.smj.data.entity.Schedule.Alarm;
import com.example.smj.data.entity.board.boardData;
import com.example.smj.data.repository.LivingTipApi;
import com.example.smj.data.repository.TransactionApi;
import com.example.smj.ui.main.fragment.LivingTipFragment;
import com.example.smj.ui.main.fragment.TransactionFragment;
import com.example.smj.ui.transaction.TransactionDeletePopupActivity;

import java.util.ArrayList;
import java.util.List;

public class TransactionUseCase implements RetrofitOnSuccess {

    private TransactionApi transactionApi;
    private TransactionFragment transactionFragment;
    private List<boardData> list = new ArrayList<>();

    public TransactionUseCase(TransactionFragment transactionFragment){
        transactionApi = new TransactionApi();
        this.transactionFragment = transactionFragment;
    }
    public TransactionUseCase(){
        transactionApi = new TransactionApi();
    }

    //GET
    public void getData(String key){
        transactionApi.getData(key, this);
    }

    //POST
    public void postData(boardData data, String key){
        transactionApi.postData(data, key);
    }

    //PUT
    public void putData(boardData data, String key, String id){ transactionApi.putData(data, key, id); }

    //DELETE
    public void deleteData(String key, String id, Context context){
        transactionApi.deleteData(key, id, context);
    }

    @Override
    public void onSuccess(Object object) {
        if(object != null){
            list = (List<boardData>)object;
            transactionFragment.onSuccess(list);
        }
    }
}