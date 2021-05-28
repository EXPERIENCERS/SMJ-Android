package com.example.smj.ui.manage;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smj.Manager.JWTManager;
import com.example.smj.R;
import com.example.smj.data.entity.board.boardData;
import com.example.smj.domain.usecase.LivingTipUseCase;
import com.example.smj.ui.Boards.LivingTip.LivingTipPostAdapter;
import com.example.smj.ui.Boards.LivingTip.LivingTipPostData;
import com.example.smj.ui.login.LoginActivity;;import java.util.ArrayList;
import java.util.List;

public class PostManageActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    ArrayList<LivingTipPostData> data = new ArrayList<>();
    LivingTipUseCase livingTipUseCase = new LivingTipUseCase(this);
    String key;
    List<boardData> list;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postmanage);
        init();
        livingTipUseCase.getMyData(key);
    }

    private void init(){
        recyclerView = findViewById(R.id.postmanage_recyclerView);
        key = JWTManager.getSharedPreference(this, getString(R.string.saved_JWT));
    }

    public void onSuccess(List<boardData> list){
        data.clear();

        for(boardData board : list){
            Log.d("포스트 매니저 onSuccess","onSuccess");
            data.add(new LivingTipPostData(board.getId(), board.getCategory().getName(),board.getTitle(),board.getContent(),board.getMember().getNickName(),
                    board.getCreatedAt(),board.getMember().getEmail(),board.getImageOne(),board.getImageTwo(),board.getImageThree()));
        }

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //data.add(new LivingTipPostData(1,"2","3","4","5", new String[]{"6", "d","s"},"7","8","9"));
        adapter = new LivingTipPostAdapter(this,data);
        recyclerView.setAdapter(adapter);
    }
}
