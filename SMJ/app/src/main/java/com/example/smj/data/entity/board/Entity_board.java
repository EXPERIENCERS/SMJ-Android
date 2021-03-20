package com.example.smj.data.entity.board;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Entity_board {

    @GET("api/boards")
    Call<List<boardData>> getLivingTIp(
            @Header("Authorization") String token
    );

    @POST("api/boards")
    Call<boardData> postLivingTip(
            @Header("Authorization") String token,
            @Body boardData post
    );

    @PUT("api/boards")
    Call<boardData> putLivingTip(
            @Header("Authorization") String token,
            @Body boardData put,
            @Path("id") String id
    );

    @DELETE("api/boards/{id}")
    Call<Void> deleteLivingTip(
            @Header("Authorization")String token,
            @Path("id")String id
    );

    @GET("api/boards/my")
    Call<List<boardData>> getMyLivingTip(
            @Header("Authorization") String token
    );
}