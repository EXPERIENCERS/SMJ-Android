package com.example.smj.ui.Alarms;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smj.Manager.JWTManager;
import com.example.smj.R;
import com.example.smj.callback.ScheduleGetData;
import com.example.smj.callback.ScheduleOnSuccess;
import com.example.smj.data.entity.Schedule.Alarm;
import com.example.smj.domain.usecase.ScheduleUseCase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;

public class ScheduleAlarmListPopupActivity extends Activity implements ScheduleGetData {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private FloatingActionButton fab;
    private TextView main;
    private String dateKey;
    private ScheduleUseCase scheduleUseCase;
    private List<Alarm>list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_schedule_alarmlist_popup);
        init();
        clickEvent();
        getData();
        getDataFromServer();
    }
    protected void init(){
        fab = findViewById(R.id.schedule_popup_fab);
        recyclerView = findViewById(R.id.schedule_popup_recyclerView);
        main = findViewById(R.id.schedule_popup_date);
        scheduleUseCase = new ScheduleUseCase(this);
    }
    protected void clickEvent(){
         fab.setOnClickListener((view) ->{
             Intent intent = new Intent(this, ScheduleAlarmPageActivity.class);
             String[] alarmCreateData = {"0",dateKey};
             intent.putExtra("data", alarmCreateData);
             startActivityForResult(intent, 1);
         });
         recyclerView.setOnClickListener((view)->{
             Intent intent = new Intent(this, ScheduleAlarmPageActivity.class);
             String[] alarmCreateData = {"1",dateKey};
             intent.putExtra("data", alarmCreateData);
             startActivityForResult(intent, 1);
         });
    }
    protected void setRecyclerView(){
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ScheduleAlarmListAdapter(list);
        recyclerView.setAdapter(adapter);

    }
    protected void getData(){
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        main.setText(data);
    }
    protected void setData(){
        Intent intent = new Intent();
        intent.putExtra("result", "Close Popup");
        setResult(RESULT_OK, intent);
    }
    //확인 버튼 클릭
    public void mOnClose(View v){
        setData();
        finish();
    }
    public void getDataFromServer(){
        StringBuilder sb = new StringBuilder();
        String str = (String)main.getText();
        String[] strArr = str.split(" ");
        sb.append(strArr[0].substring(0,strArr[0].length()-1)).append("-");
        convertDatetoServerFormat(strArr[1], sb);
        convertDatetoServerFormat(strArr[2], sb);
        dateKey = sb.substring(0,sb.length()-1);
        scheduleUseCase.sendKeyDate(JWTManager.getSharedPreference(this,getString(R.string.saved_JWT)), dateKey);

    }
    @Override
    public void clickSuccess(List<Alarm> list) {
        this.list.addAll(list);
        setRecyclerView();
    }
    public void convertDatetoServerFormat(String date, StringBuilder sb){
        String FirstStr = "0";
        String SecondStr = date.substring(0,date.length()-1);
        if(Integer.parseInt(SecondStr) <10) {
            FirstStr = FirstStr.concat(SecondStr);
            sb.append(FirstStr).append("-");
        }
        else{
            sb.append(SecondStr).append("-");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
