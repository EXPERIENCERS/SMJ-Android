package com.example.smj.data.repository;

import android.util.Log;

import com.example.smj.data.datasource.JWTRemoteDataSource;
import com.example.smj.data.entity.Entity_JWT;
import com.example.smj.domain.usecase.JWTUseCase;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JwtRepository {
    private String jwt;
    public void retrieveLocals(String at, JWTUseCase jwtUseCase){
        Entity_JWT entityJWT = JWTRemoteDataSource.getInstance().create(Entity_JWT.class);
        Call<String> call = entityJWT.getAuthorizationKey(at);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    jwt = response.body().toString();
                    jwtUseCase.onSuccess(jwt);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("실패","123");
            }
        });
    }
}


