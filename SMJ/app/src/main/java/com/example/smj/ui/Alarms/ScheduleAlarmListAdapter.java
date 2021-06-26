package com.example.smj.ui.Alarms;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smj.Manager.JWTManager;
import com.example.smj.Manager.SharedPreferenceManager;
import com.example.smj.R;
import com.example.smj.data.entity.Schedule.Alarm;
import com.example.smj.domain.usecase.ScheduleUseCase;

import java.util.ArrayList;
import java.util.List;

public class ScheduleAlarmListAdapter extends RecyclerView.Adapter<ScheduleAlarmListAdapter.MainHolder> {
    private List<Alarm> getList = new ArrayList<>();
    ScheduleAlarmListAdapter.MainHolder mainHolder;
    public ScheduleAlarmListAdapter(List<Alarm> data) {
        this.getList = data;
    }

    public static class MainHolder extends RecyclerView.ViewHolder {
        public TextView title, date;
        public Switch onOff;
        public View view;
        public MainHolder(View view) {
            super(view);
            this.view = view;
            this.title = view.findViewById(R.id.schedule_alarmlist_popup_title);
            this.date = view.findViewById(R.id.schedule_alarmlist_popup_date);
            this.onOff = view.findViewById(R.id.schedule_alarmlist_popup_switch);

        }
    }

    @NonNull
    @Override
    public ScheduleAlarmListAdapter.MainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_schedule_alarmlist_popup_item, parent, false);
        mainHolder = new ScheduleAlarmListAdapter.MainHolder(holderView);
        return mainHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleAlarmListAdapter.MainHolder mainHolder, int i) {
        mainHolder.title.setText(getList.get(i).getTitle());
        String strArr[] = getList.get(i).getStartTime().split(":");
        StringBuilder sb = new StringBuilder();
        if (Integer.parseInt(strArr[0]) < 12) sb.append("AM ");
        else sb.append("PM ");
        sb.append(strArr[0]).append(":").append(strArr[1]);
        mainHolder.date.setText(sb);

        mainHolder.onOff.setChecked(Boolean.parseBoolean(SharedPreferenceManager.getSharedPreference(mainHolder.view.getContext(), mainHolder.title.getText().toString())));
        mainHolder.onOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPreferenceManager.putSharedPreference(mainHolder.view.getContext(), mainHolder.title.getText().toString(), "true");

                } else {
                    SharedPreferenceManager.putSharedPreference(mainHolder.view.getContext(), mainHolder.title.getText().toString(), "false");
                }
            }
        });
        mainHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = getList.get(i).getId();
                String date = getList.get(i).getstartDate();
                Intent intent = new Intent(v.getContext(), ScheduleAlarmPageActivity.class);
                String[] alarmCreateData = {"2",date,String.valueOf(id)};
                intent.putExtra("data", alarmCreateData);
                v.getContext().startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return getList.size();
    }

}
