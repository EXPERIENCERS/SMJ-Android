package com.example.smj.data.repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.smj.data.datasource.NetworkManager;
import com.example.smj.data.entity.Comments.CommentData;
import com.example.smj.data.entity.Comments.CommentsPostData;
import com.example.smj.data.entity.Comments.Entity_Comments;
import com.example.smj.domain.usecase.CommentsUseCase;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentsRepository {

    private Entity_Comments entityComments;

    public void retrieveData(String key, int id, CommentsUseCase commentsUseCase){
        entityComments = (Entity_Comments) NetworkManager.getInstance(Entity_Comments.class).apiService;
        Call <List<CommentData>> call = entityComments.getComments(key, id);
        call.enqueue(new Callback<List<CommentData>>() {
            @Override
            public void onResponse(Call<List<CommentData>> call, Response<List<CommentData>> response) {
                if(response.isSuccessful()){
                    Log.d("댓글 데이터 GET 성공","댓글 데이터 GET 성공");
                    commentsUseCase.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<CommentData>> call, Throwable t) {
                Log.d("댓글 데이터 GET 실패","댓글 데이터 GET 실패");
            }
        });
    }

    public void deleteData(String key, int id, Context context){
        entityComments = (Entity_Comments) NetworkManager.getInstance(Entity_Comments.class).apiService;
        Call<Void> call = entityComments.deleteComments(key,id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("데이터 전송 성공","성공");
                Toast.makeText(context,"댓글이 삭제됐습니다.",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("데이터 전송 실패","실패");
            }
        });
    }

    public void updateData(CommentsPostData data, String key, int id, Context context, CommentsUseCase commentsUseCase){
        entityComments = (Entity_Comments) NetworkManager.getInstance(Entity_Comments.class).apiService;
        Call<CommentData>call = entityComments.putComments(key, data, id);
        call.enqueue(new Callback<CommentData>() {
            @Override
            public void onResponse(Call<CommentData> call, Response<CommentData> response) {
                Log.d("데이터 전송 성공","성공");
                Toast.makeText(context,"댓글이 수정됐습니다.",Toast.LENGTH_LONG).show();
                //transactionUseCase.updateSuccess();
            }

            @Override
            public void onFailure(Call<CommentData> call, Throwable t) {
                Log.d("데이터 전송 실패","실패");
            }
        });
    }

    public void postData(CommentsPostData data, String key, int id, Context context, CommentsUseCase commentsUseCase){
        entityComments = (Entity_Comments) NetworkManager.getInstance(Entity_Comments.class).apiService;
        Call<CommentData>call = entityComments.postComments(key, data, id);
        call.enqueue(new Callback<CommentData>() {
            @Override
            public void onResponse(Call<CommentData> call, Response<CommentData> response) {
                Log.d("데이터 전송 성공","성공");
                Toast.makeText(context,"댓글이 등록됐습니다.",Toast.LENGTH_LONG).show();
                //transactionUseCase.postSuccess();
            }

            @Override
            public void onFailure(Call<CommentData> call, Throwable t) {
                Log.d("데이터 전송 실패","실패");
            }
        });
    }
}