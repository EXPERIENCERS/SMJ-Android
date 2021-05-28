package com.example.smj.data.repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.smj.Manager.NetworkManager;
import com.example.smj.data.entity.board.Entity_board;
import com.example.smj.data.entity.board.boardData;
import com.example.smj.data.entity.board.boardPostData;
import com.example.smj.domain.usecase.LivingTipUseCase;
import com.example.smj.ui.Member.MyPageFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LivingTipRepository {
    private List<boardData> boardList = new ArrayList<>();
    private Entity_board entityBoard;

    public LivingTipRepository() {
        this.entityBoard = NetworkManager.getInstance().getRetrofit().create(Entity_board.class);
    }

    public void getData(String key, LivingTipUseCase livingTipUseCase){
        Call <List<boardData>> call = entityBoard.getLivingTIp(key);
        call.enqueue(new Callback<List<boardData>>() {
            @Override
            public void onResponse(Call<List<boardData>> call, Response<List<boardData>> response) {
                if(response.isSuccessful()){
                    Log.d("살림 팁 게시판 데이터 GET 성공","살림 팁 게시판 데이터 GET 성공");
                    boardList = response.body();
                    livingTipUseCase.onSuccess(boardList);
                    Log.d("살림 팁 게시판 데이터 GET 성공",Integer.toString(boardList.size()));
                }
            }

            @Override
            public void onFailure(Call<List<boardData>> call, Throwable t) {
                Log.d("살림 팁 게시판 데이터 GET 실패","살림 팁 게시판 데이터 GET 실패");
            }
        });
    }

    public void getMyData(String key, LivingTipUseCase livingTipUseCase){
        Call <List<boardData>> call = entityBoard.getMyLivingTip(key);
        call.enqueue(new Callback<List<boardData>>() {
            @Override
            public void onResponse(Call<List<boardData>> call, Response<List<boardData>> response) {
                if(response.isSuccessful()){
                    Log.d("포스트 매니저 데이터 GET 성공","포스트 매니저 데이터 GET 성공");
                    boardList = response.body();
                    livingTipUseCase.onPostManageSuccess(boardList);
                }
            }

            @Override
            public void onFailure(Call<List<boardData>> call, Throwable t) {
                Log.d("살림 팁 게시판 데이터 GET 실패","살림 팁 게시판 데이터 GET 실패");
            }
        });
    }

    public void getMyData(String key, LivingTipUseCase livingTipUseCase, MyPageFragment myPageFragment){
        Call <List<boardData>> call = entityBoard.getMyLivingTip(key);
        call.enqueue(new Callback<List<boardData>>() {
            @Override
            public void onResponse(Call<List<boardData>> call, Response<List<boardData>> response) {
                if(response.isSuccessful()){
                    Log.d("포스트 매니저 데이터 GET 성공","포스트 매니저 데이터 GET 성공");
                    boardList = response.body();
                    livingTipUseCase.onMyPageSuccess(boardList);
                }
            }

            @Override
            public void onFailure(Call<List<boardData>> call, Throwable t) {
                Log.d("살림 팁 게시판 데이터 GET 실패","살림 팁 게시판 데이터 GET 실패");
            }
        });
    }

    public void deleteData(String key, int id, Context context){
        Call<Void> call = entityBoard.deleteLivingTip(key,id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("데이터 전송 성공","성공");
                Toast.makeText(context,"게시글이 삭제됐습니다.",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("데이터 전송 실패","실패");
            }
        });
    }

    public void putData(boardPostData data, String key, int id, Context context){
        Call<boardData> call = entityBoard.putLivingTip(key, data, id);
        call.enqueue(new Callback<boardData>() {
            @Override
            public void onResponse(Call<boardData> call, Response<boardData> response) {
                Log.d("데이터 전송 성공","성공");
                Toast.makeText(context,"게시글이 등록됐습니다.",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<boardData> call, Throwable t) {
                Log.d("데이터 전송 실패","실패");
            }
        });
    }

    public void postData(boardPostData data, String key, Context context, LivingTipUseCase livingTipUseCase){
        Call<boardData>call = entityBoard.postLivingTip(key, data);
        call.enqueue(new Callback<boardData>() {
            @Override
            public void onResponse(Call<boardData> call, Response<boardData> response) {
                Log.d("데이터 전송 성공","성공");
                Toast.makeText(context,"게시글이 등록됐습니다.",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<boardData> call, Throwable t) {
                Log.d("데이터 전송 실패","실패");
            }
        });
    }
}
