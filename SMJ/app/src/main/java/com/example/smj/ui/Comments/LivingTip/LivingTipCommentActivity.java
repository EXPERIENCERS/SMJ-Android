package com.example.smj.ui.Comments.LivingTip;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smj.Manager.JWTManager;
import com.example.smj.R;
import com.example.smj.callback.CommentOnSuccess;
import com.example.smj.callback.MemberOnSuccess;
import com.example.smj.data.entity.Comments.CommentData;
import com.example.smj.data.entity.Comments.CommentsPostData;
import com.example.smj.data.entity.Member.MemberData;
import com.example.smj.domain.usecase.CommentsUseCase;
import com.example.smj.domain.usecase.LivingTipCommentsUseCase;
import com.example.smj.domain.usecase.LivingTipMemberUseCase;
import com.example.smj.domain.usecase.MemberUseCase;
import com.example.smj.ui.Comments.Transaction.Adapter.TransactionCommentAdapter;
import com.example.smj.ui.Comments.Transaction.TransactionCommentData;

import java.util.ArrayList;
import java.util.List;

public class LivingTipCommentActivity extends AppCompatActivity implements CommentOnSuccess, MemberOnSuccess {

    RecyclerView recyclerView;
    ArrayList<LivingTipCommentData> commentData = new ArrayList<>();
    private List<MemberData> memberData = new ArrayList<>();
    private LivingTipCommentAdapter adapter;
    private LivingTipCommentsUseCase livingTipCommentsUseCase;
    private LivingTipMemberUseCase livingTipMemberUseCase;
    private String token;
    private int boardId;
    private Button upload;
    private EditText content;
    private InputMethodManager inputMethodManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_living_tip_comment);

        init();
    }

    private void init(){
        recyclerView = findViewById(R.id.living_tip_comment_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        livingTipCommentsUseCase = new LivingTipCommentsUseCase(this);
        livingTipMemberUseCase = new LivingTipMemberUseCase(this);
        upload = findViewById(R.id.living_tip_comment_write_btn);
        Intent intent = getIntent();
        boardId = intent.getIntExtra("id",9998);
        token =  JWTManager.getSharedPreference(this,getString(R.string.saved_JWT));
        content = findViewById(R.id.living_tip_comment_write_text);
        inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(content.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"댓글을 입력해주시기 바랍니다.",Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.d("댓글 업로드 버튼",content.getText().toString());
                    livingTipCommentsUseCase.postData(new CommentsPostData(content.getText().toString()),token,boardId,getApplicationContext());
                    content.setText("");
                    content.clearFocus();
                    inputMethodManager.hideSoftInputFromWindow(upload.getWindowToken(),0);
                }
            }
        });

        livingTipCommentsUseCase.getData(token,boardId);
        livingTipMemberUseCase.getData(token);
    }

    public void onSuccess(List<CommentData> list){
        Log.d("댓글 getSuccess", "댓글 getSuccess");
        commentData.clear();
        int getListSize = list.size();
        for(int i = 0; i<getListSize; i++){
            commentData.add(new LivingTipCommentData(list.get(i).getCreatedAt(),list.get(i).getMember().getNickName(),list.get(i).getContent(),list.get(i).getMember().getEmail(),list.get(i).getId()));
        }

        adapter = new LivingTipCommentAdapter(commentData, memberData, this, livingTipCommentsUseCase ,token,boardId);
        adapter.refreshAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void dataChangeSuccess() {
        livingTipCommentsUseCase.getData(token,boardId);
        adapter.refreshAdapter();
    }

    @Override
    public void onDataSuccess(List<MemberData> body){
        memberData.clear();
        memberData = body;
    }

    @Override
    public void onResume(){
        super.onResume();
        livingTipCommentsUseCase.getData(token,boardId);
    }
}
