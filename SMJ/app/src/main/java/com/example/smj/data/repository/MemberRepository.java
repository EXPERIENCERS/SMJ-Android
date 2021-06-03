package com.example.smj.data.repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.smj.Manager.NetworkManager;
import com.example.smj.data.entity.Comments.Entity_Comments;
import com.example.smj.data.entity.Member.Entity_Member;
import com.example.smj.data.entity.Member.MemberData;
import com.example.smj.data.entity.Member.MemberPostData;
import com.example.smj.data.entity.board.Entity_board;
import com.example.smj.domain.usecase.MemberUseCase;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemberRepository {

    private Entity_Member entityMember;

    public MemberRepository() {
        this.entityMember = NetworkManager.getInstance().getRetrofit().create(Entity_Member.class);;
    }

    public void retrieveData(String key, MemberUseCase memberUseCase){
        Call <List<MemberData>> call = entityMember.getData(key);
        call.enqueue(new Callback<List<MemberData>>() {
            @Override
            public void onResponse(Call<List<MemberData>> call, Response<List<MemberData>> response) {
                if(response.isSuccessful()){
                    Log.d("사용자 데이터 GET 성공","사용자 데이터 GET 성공");
                    memberUseCase.onDataSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<MemberData>> call, Throwable t) {
                Log.d("사용자 데이터 GET 실패","사용자 데이터 GET 실패");
            }
        });
    }

    public void deleteData(String key, int id, Context context){
        Call<Void> call = entityMember.deleteData(key,id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Log.d("데이터 전송 성공","성공");
                    Toast.makeText(context,"사용자 정보가 삭제됐습니다.",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("데이터 전송 실패","실패");
            }
        });
    }

    public void updateData(MemberPostData data, String key, int id, Context context, MemberUseCase memberUseCase){
        Call<MemberData>call = entityMember.putData(key, data, id);
        call.enqueue(new Callback<MemberData>() {
            @Override
            public void onResponse(Call<MemberData> call, Response<MemberData> response) {
                if(response.isSuccessful()){
                    Log.d("데이터 전송 성공","성공");
                    Toast.makeText(context,"사용자 정보가 수정됐습니다.",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MemberData> call, Throwable t) {
                Log.d("데이터 전송 실패","실패");
            }
        });
    }
}
