package com.example.smj.domain.usecase;

import android.util.Log;

import com.example.smj.callback.RetrofitOnSuccess;
import com.example.smj.data.entity.Schedule.Alarm;
import com.example.smj.data.entity.board.boardData;
import com.example.smj.data.repository.LivingTipApi;
import com.example.smj.data.repository.ScheduleApi;
import com.example.smj.ui.main.fragment.LivingTipFragment;

import java.util.ArrayList;
import java.util.List;

public class LivingTipUseCase implements RetrofitOnSuccess {
    private LivingTipApi livingTipApi;
    private LivingTipFragment livingTipFragment;
    private List<boardData> list = new ArrayList<>();

    public LivingTipUseCase(LivingTipFragment livingTipFragment){
        livingTipApi = new LivingTipApi();
        this.livingTipFragment = livingTipFragment;
    }

    //GET
    public void getData(String key){
        Log.d("살림팁 getData", "살림팁 getData");
        livingTipApi.getData(key, this);
    }

    //POST
    public void postData(boardData data, String key){
        livingTipApi.postData(data, key);
    }

    //PUT
    public void putData(boardData data, String key, String id){ livingTipApi.putData(data, key, id); }

    //DELETE
    public void deleteData(String key, String id){
        livingTipApi.deleteData(key, id);
    }

    @Override
    public void onSuccess(Object object){
        if(object != null){
            Log.d("useCase onSuccess", "useCase onSuccess");
            list = (List<boardData>)object;
            livingTipFragment.onSuccess(list);
        }
    }
}