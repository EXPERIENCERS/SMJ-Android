package com.example.smj.data.repository;

import android.util.Log;

import com.example.smj.Manager.NetworkManager;
import com.example.smj.data.entity.Member.Entity_Member;
import com.example.smj.data.entity.Schedule.Alarm;
import com.example.smj.data.entity.Schedule.Entity_Schedule;
import com.example.smj.domain.usecase.ScheduleUseCase;
import com.example.smj.ui.Alarms.AlarmPostData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleRepository {

    private List<Alarm> localList = new ArrayList<>();
    private Entity_Schedule entitySchedule;

    public ScheduleRepository() {
        this.entitySchedule =  NetworkManager.getInstance().getRetrofit().create(Entity_Schedule.class);
    }

    public void retrieveLocals(String key, ScheduleUseCase scheduleUseCase){
        Call <List<Alarm>> call = entitySchedule.getAlarm(key);
        call.enqueue(new Callback<List<Alarm>>() {
            @Override
            public void onResponse(Call<List<Alarm>> call, Response<List<Alarm>> response) {
                if(response.isSuccessful()){
                    //알람 가져오기 성공
                    localList = response.body();
                    scheduleUseCase.onSuccess(localList);
                }
            }

            @Override
            public void onFailure(Call<List<Alarm>> call, Throwable t) {

            }
        });
    }

    public void deleteData(String key, String id){
        Call<Void>call = entitySchedule.deleteAlarm(key,id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("삭제 성공","굿");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("데이터 전송 실패",";;");
            }
        });
    }

    public void putData(AlarmPostData data, String key, String id){
        Call<Alarm>call = entitySchedule.putAlarm(key, data, id);
        call.enqueue(new Callback<Alarm>() {
            @Override
            public void onResponse(Call<Alarm> call, Response<Alarm> response) {
                Log.d("데이터 전송 성공","굿");
            }

            @Override
            public void onFailure(Call<Alarm> call, Throwable t) {
                Log.d("데이터 전송 실패",";;");
            }
        });
    }

    public void postData(AlarmPostData data, String key){
        Call<Alarm>call = entitySchedule.postAlarm(key, data);
        call.enqueue(new Callback<Alarm>() {
            @Override
            public void onResponse(Call<Alarm> call, Response<Alarm> response) {
                Log.d("알림 등록 데이터 전송 성공","굿");
            }

            @Override
            public void onFailure(Call<Alarm> call, Throwable t) {
                Log.d("데이터 전송 실패",";;");
            }
        });
    }

    public void retrieveDateLocals(String key,String startDate, ScheduleUseCase scheduleUseCase){
        Call <List<Alarm>> call = entitySchedule.getDateAlarm(key,startDate);
        call.enqueue(new Callback<List<Alarm>>() {
            @Override
            public void onResponse(Call<List<Alarm>> call, Response<List<Alarm>> response) {
                if(response.isSuccessful()){
                    //알람 가져오기 성공
                    localList = response.body();
                    scheduleUseCase.retrieveSuccess(localList);
                }
            }

            @Override
            public void onFailure(Call<List<Alarm>> call, Throwable t) {
                Log.d("데이터 전송 실패",";;");
            }
        });
    }
    public void retrieveIdLocals(String key, String id, ScheduleUseCase scheduleUseCase){
        Call <Alarm> call = entitySchedule.getIdAlarm(key,id);
        call.enqueue(new Callback<Alarm>() {
            @Override
            public void onResponse(Call<Alarm> call, Response<Alarm> response) {
                if(response.isSuccessful()){
                        scheduleUseCase.retrieveIdSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<Alarm> call, Throwable t) {
                Log.d("데이터 전송 실패",";;");
            }
        });
    }

}
