package com.example.smj.domain.usecase;

import android.util.Log;

import com.example.smj.callback.RetrofitOnSuccess;
import com.example.smj.callback.ScheduleOnSuccess;
import com.example.smj.data.entity.Schedule.Alarm;
import com.example.smj.data.repository.ScheduleRepository;
import com.example.smj.ui.Alarms.AlarmPostData;
import com.example.smj.ui.Alarms.ScheduleAlarmListAdapter;
import com.example.smj.ui.Alarms.ScheduleAlarmListPopupActivity;
import com.example.smj.ui.Alarms.ScheduleAlarmPageActivity;
import com.example.smj.ui.Alarms.ScheduleFragment;

import java.util.ArrayList;
import java.util.List;

public class ScheduleUseCase implements RetrofitOnSuccess, ScheduleOnSuccess {
    private ScheduleRepository scheduleRepository;
    private ScheduleFragment scheduleFragment;
    private ScheduleAlarmListPopupActivity scheduleAlarmListPopupActivity;
    private ScheduleAlarmPageActivity scheduleAlarmPageActivity;
    private List<Alarm> list = new ArrayList<>();
    private Alarm alarm;

    public ScheduleUseCase(ScheduleFragment scheduleFragment){
        scheduleRepository = new ScheduleRepository();
        this.scheduleFragment = scheduleFragment;
    }
    public ScheduleUseCase(ScheduleAlarmListPopupActivity scheduleAlarmListPopupActivity){
        scheduleRepository = new ScheduleRepository();
        this.scheduleAlarmListPopupActivity = scheduleAlarmListPopupActivity;
    }
    public ScheduleUseCase(ScheduleAlarmPageActivity scheduleAlarmPageActivity){
        scheduleRepository = new ScheduleRepository();
        this.scheduleAlarmPageActivity = scheduleAlarmPageActivity;
    }
    /* GET으로 데이터 받아올때 키값 전달 */
    public void sendKey(String key){
        //키값 전달
        scheduleRepository.retrieveLocals(key, this);
    }

    /* POST로 Alarm데이터 전송 */
    public void sendData(AlarmPostData alarm, String key){
        scheduleRepository.postData(alarm, key);
    }

    /* PUT Alarm데이터 수정 */
    public void changeData(AlarmPostData alarm, String key, String id){
        scheduleRepository.putData(alarm, key, id);
    }

    /* DELETE로 Alarm데이터 삭제 */
    public void deleteData(String key, String id){
        scheduleRepository.deleteData(key, id);
    }

    /* GET으로 데이터 받아올때 키값, 시작 날짜 전달 */
    public void sendKeyDate(String key,String startDate){
        //키값 전달
        scheduleRepository.retrieveDateLocals(key, startDate,this);
    }
    public void sendIdDate(String key,String id){
        //키값 전달
        scheduleRepository.retrieveIdLocals(key,id,this);
    }

    @Override
    public void onSuccess(Object object) {
        if(object != null){
            list = (List<Alarm>)object;
            scheduleFragment.clickSuccess(list);
        }
    }

    @Override
    public void retrieveSuccess(Object object) {
        if(object != null){
            list = (List<Alarm>)object;
            scheduleAlarmListPopupActivity.clickSuccess(list);
        }
    }

    @Override
    public void retrieveIdSuccess(Object object) {
        if(object != null){
            scheduleAlarmPageActivity.retrieveSuccess((Alarm)object);

        }
    }
}
