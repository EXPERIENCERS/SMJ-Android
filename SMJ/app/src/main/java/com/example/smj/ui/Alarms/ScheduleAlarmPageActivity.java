package com.example.smj.ui.Alarms;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Scene;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smj.Manager.JWTManager;
import com.example.smj.R;
import com.example.smj.data.entity.Schedule.Alarm;
import com.example.smj.domain.usecase.ScheduleUseCase;

public class ScheduleAlarmPageActivity extends AppCompatActivity {
    private ViewGroup alarmDelete, alarmIter, timerClick1, timerClick2;
    private TextView subject,submitModified;
    private TextView today, startTime,finishTime, repeat;
    private EditText title, content;
    private TimePicker timePicker,timePicker2;
    private Boolean checkFocus1 = true, checkFocus2 = true, clickFlag = false ,clickFlag2 = false;
    private String dateKey;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_schedule_alarmpage);
        init();
        getData();
        submitModified.setOnClickListener((view) ->{
            if(subject.getText().toString().equals("") || content.getText().toString().equals("")){
                Toast.makeText(this,"제목과 세부 내용을 입력하여 주십시오.", Toast.LENGTH_SHORT);
                return;
            }
            AlarmPostData alarm = new AlarmPostData(title.getText().toString(),content.getText().toString(),extractDate(),extractTime(startTime),extractTime(finishTime),extractRepeat());
            ScheduleUseCase scheduleUseCase = new ScheduleUseCase(this);
            //scheduleUseCase.sendData(alarm, JWTManager.getSharedPreference(this,getString(R.string.saved_JWT)));
            Intent intent = new Intent(this, ScheduleAlarmModifiedPopupActivity.class);
            Log.d("rtgtg", title.getText().toString());
            if(title.getText().toString().equals("알림 등록")){
                intent.putExtra("data", "1");
            }
            else{
                intent.putExtra("data", "2");
            }
            startActivityForResult(intent, 1);
        });
        alarmIter.setOnClickListener((view) ->{
            Intent intent = new Intent(this, ScheduleAlarmIterPopupActivity.class);
            intent.putExtra("data", "Test Popup");
            startActivityForResult(intent, 4);
        });
        alarmDelete.setOnClickListener((view) ->{
            Intent intent = new Intent(this, ScheduleAlarmDeletePopupActivity.class);
            intent.putExtra("data", "Test Popup");
            startActivityForResult(intent, 1);
        });
    }
    protected void init(){
        alarmIter = findViewById(R.id.schedule_allday_iterclicklayout);
        alarmDelete = findViewById(R.id.schedule_allday_clicklayout);
        submitModified = findViewById(R.id.schedule_alarmpage_submit_modified);
        subject = findViewById(R.id.schedule_alarmpage_subject);
        title = findViewById(R.id.schedule_alarmpage_alarmsubmit);
        content = findViewById(R.id.schedule_alarmpage_context);
        today = findViewById(R.id.schedule_alarmpage_today);
        startTime = findViewById(R.id.schedule_alarmpage_starttime);
        finishTime = findViewById(R.id.schedule_alarmpage_finishitime);
        repeat = findViewById(R.id.schedule_alarmpage_repeatcheck);
        title.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(checkFocus1) {
                    title.setText("");
                    checkFocus1 = false;
                }
            }
        });

        content.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(checkFocus2) {
                    content.setText("");
                    checkFocus2 = false;
                }
            }
        });
        submitModified.bringToFront();
        timePicker = findViewById(R.id.timePicker);
        timePicker2 = findViewById(R.id.timePicker2);
        timerClick1 = findViewById(R.id.schedule_timer_click);
        timerClick2 = findViewById(R.id.schedule_timer_click2);
        timerClick1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!clickFlag) timePicker.setVisibility(View.VISIBLE);
                else timePicker.setVisibility(View.GONE);
                clickFlag = !clickFlag;
            }
        });
        timerClick2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!clickFlag2) timePicker2.setVisibility(View.VISIBLE);
                else timePicker2.setVisibility(View.GONE);
                clickFlag2 = !clickFlag2;
            }
        });
    }
    protected void getData(){
        Intent intent = getIntent();
        String[] data = intent.getStringArrayExtra("data");
        if(data[0].equals("0")){
            subject.setText("알림 등록");
        }
        else{
            subject.setText("알림 수정");
        }
        dateKey = data[1];
        today.setText(dateKey.replace("-","."));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 4) {
            if(resultCode == RESULT_OK){
                int s = data.getIntExtra("iter",0);
                switch (s){
                    case 0:
                        break;
                    case 1:
                        repeat.setText("매일 반복");
                        break;
                    case 2:
                        repeat.setText("매주 반복");
                        break;
                    case 3:
                        repeat.setText("매달 반복");
                        break;
                    case 4:
                        repeat.setText("매년 반복");
                        break;

                }
            }
        }
    }
    public String extractDate(){
        return today.getText().toString().replace(".","-");
    }
    public String extractTime(TextView tv){
        String[] start = tv.getText().toString().split(":");
        int ampm = 0;
        if(start[0].equals("PM")) ampm = 12;
        String hourInt = String.valueOf(Integer.parseInt(start[1])+ampm);
        return hourInt+":"+start[2]+":00";
    }
    public String extractRepeat(){
        String rString = "";
        switch (repeat.getText().toString()){
            case "매일 반복":
                rString = "DAILY";
                break;
            case "매주 반복":
                break;
            case "매달 반복":
                rString = "MONTHLY";
                break;
            case "매년 반복":
                rString = "YEARLY";
                break;
        }
        return rString;
    }
}
