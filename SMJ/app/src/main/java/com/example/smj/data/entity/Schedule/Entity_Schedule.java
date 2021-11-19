package com.example.smj.data.entity.Schedule;

import com.example.smj.ui.Alarms.AlarmPostData;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface Entity_Schedule {
    @GET("api/alarms")
    Call <List<Alarm>> getAlarm(
            @Header("Authorization") String token
    );
    @POST("api/alarms")
    Call<Alarm> postAlarm(
            @Header("Authorization")String token,
            @Body AlarmPostData post
    );
    @PUT("api/alarms/{alarm_id}")
    Call<Alarm> putAlarm(
            @Header("Authorization")String token,
            @Body AlarmPostData put,
            @Path("alarm_id")String id
    );
    @DELETE("api/alarms/{alarm_id}")
    Call<Void> deleteAlarm(
            @Header("Authorization")String token,
            @Path("alarm_id")String id
    );
    @GET("api/alarms/startDate/{startDate}")
    Call<List<Alarm>> getDateAlarm(
            @Header("Authorization")String token,
            @Path("startDate")String startDate
    );
    @GET("api/alarms/id/{id}")
    Call<Alarm> getIdAlarm(
            @Header("Authorization")String token,
            @Path("id")String id
    );

}
