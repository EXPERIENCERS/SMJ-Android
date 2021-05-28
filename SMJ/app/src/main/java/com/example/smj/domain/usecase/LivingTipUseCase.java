package com.example.smj.domain.usecase;

import android.content.Context;
import android.util.Log;

import com.example.smj.callback.PostManageOnSuccess;
import com.example.smj.callback.RetrofitOnSuccess;
import com.example.smj.data.entity.board.boardData;
import com.example.smj.data.entity.board.boardPostData;
import com.example.smj.data.repository.LivingTipRepository;
import com.example.smj.ui.Boards.LivingTip.LivingTipFragment;
import com.example.smj.ui.Member.MyPageFragment;
import com.example.smj.ui.manage.PostManageActivity;

import java.util.ArrayList;
import java.util.List;

public class LivingTipUseCase implements RetrofitOnSuccess, PostManageOnSuccess {
    private LivingTipRepository livingTipRepository;
    private LivingTipFragment livingTipFragment;
    private List<boardData> list = new ArrayList<>();
    private PostManageActivity postManageActivity;
    private MyPageFragment myPageFragment;

    public LivingTipUseCase(LivingTipFragment livingTipFragment){
        livingTipRepository = new LivingTipRepository();
        this.livingTipFragment = livingTipFragment;
    }

    public LivingTipUseCase(PostManageActivity postManageActivity){
        livingTipRepository = new LivingTipRepository();
        this.postManageActivity = postManageActivity;
    }

    public LivingTipUseCase(MyPageFragment myPageFragment){
        livingTipRepository = new LivingTipRepository();
        this.myPageFragment = myPageFragment;
    }

    public LivingTipUseCase(){
        livingTipRepository = new LivingTipRepository();
    }

    //GET
    public void getData(String key){
        Log.d("살림팁 getData", "살림팁 getData");
        livingTipRepository.getData(key, this);
    }

    public void getMyData(String key){
        Log.d("살림팁 getData", "살림팁 getData");
        livingTipRepository.getMyData(key, this);
    }

    public void getMyData(String key, MyPageFragment myPageFragment){
        Log.d("살림팁 getData", "살림팁 getData");
        livingTipRepository.getMyData(key, this,myPageFragment);
    }

    //POST
    public void postData(boardPostData data, String key, Context context){
        livingTipRepository.postData(data, key, context, this);
    }

    //PUT
    public void putData(boardPostData data, String key, int id, Context context){
        livingTipRepository.putData(data, key, id, context);
    }

    //DELETE
    public void deleteData(String key, int id, Context context){
        livingTipRepository.deleteData(key, id, context);
    }

    @Override
    public void onSuccess(Object object){
        if(object != null){
            Log.d("useCase onSuccess", "useCase onSuccess");
            list = (List<boardData>)object;
            livingTipFragment.onSuccess(list);
        }
    }

    @Override
    public void onPostManageSuccess(Object object) {
        if(object != null){
            Log.d("useCase onSuccess", "useCase onSuccess");
            list = (List<boardData>)object;
            postManageActivity.onSuccess(list);
        }
    }

    @Override
    public void onMyPageSuccess(Object object) {
        if(object != null){
            Log.d("useCase onSuccess", "useCase onSuccess");
            list = (List<boardData>)object;
            myPageFragment.onSuccess(list);
        }
    }
}
