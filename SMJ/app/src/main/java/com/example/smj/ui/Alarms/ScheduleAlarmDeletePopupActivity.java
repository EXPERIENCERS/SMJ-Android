package com.example.smj.ui.Alarms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

import com.example.smj.R;

public class ScheduleAlarmDeletePopupActivity extends Activity {
    private Button cancel, delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_schedule_alarmdelete_popup);
        init();
    }
    void init(){
        cancel = findViewById(R.id.schedule_alarmdelete_popup_cancel);
        delete = findViewById(R.id.schedule_alarmdelete_popup_delete);
        delete.setOnClickListener((view) ->{
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        });
        cancel.setOnClickListener((view) ->{
            finish();
        });
    }


}
