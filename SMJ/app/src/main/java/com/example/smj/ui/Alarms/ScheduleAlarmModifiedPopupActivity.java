package com.example.smj.ui.Alarms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.smj.R;

public class ScheduleAlarmModifiedPopupActivity extends Activity {
    private Button submit, cancel;
    private TextView context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_schedule_alarmmodified_popup);
        init();
        getData();
    }
    public void init(){
        context = findViewById(R.id.schedule_alarmmodified_popup_modifiedquestion);
        submit = findViewById(R.id.schedule_alarmmodified_popup_modified);
        cancel = findViewById(R.id.schedule_alarmmodified_popup_cancel);
        submit.setOnClickListener((view) ->{
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        });
        cancel.setOnClickListener((view) ->{
           finish();
        });
    }
    public void getData(){
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        if(data.equals("1")){
            context.setText(R.string.schedule_submit);
            submit.setText("등록");
        }
    }
}
