package com.example.smj.ui.Boards.LivingTip;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smj.Manager.JWTManager;
import com.example.smj.R;
import com.example.smj.domain.usecase.LivingTipUseCase;
import com.example.smj.ui.Comments.LivingTip.LivingTipCommentActivity;
import com.example.smj.ui.login.LoginActivity;

public class LivingTipReadingActivity extends AppCompatActivity {

    Dialog moreView;
    ImageButton moreBtn;
    Button deleteBtn, modifyBtn;
    int id;
    String category, title, writer, date, content;
    TextView categoryView, titleView, writerView, dateView, contentView;
    private boolean check = false;
    ImageView image1, image2, image3;
    String imageUri1, imageUri2, imageUri3;
    ImageView commentBtn;
    String email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_living_tip_reading);
        Intent getIntent = getIntent();

        String key = JWTManager.getSharedPreference(this,getString(R.string.saved_JWT));

        moreBtn = findViewById(R.id.more_btn);
        moreView = new Dialog(LivingTipReadingActivity.this);
        moreView.requestWindowFeature(Window.FEATURE_NO_TITLE);
        moreView.setContentView(R.layout.reading_view_more);
        deleteBtn = (Button) moreView.findViewById(R.id.reading_delete);
        modifyBtn = (Button) moreView.findViewById(R.id.reading_modified);
        image1 = findViewById(R.id.living_tip_image1);
        image2 = findViewById(R.id.living_tip_image2);
        image3 = findViewById(R.id.living_tip_image3);

        categoryView = findViewById(R.id.living_tip_reading_post_category);
        titleView = findViewById(R.id.living_tip_reading_post_title);
        writerView = findViewById(R.id.living_tip_reading_post_writer);
        dateView = findViewById(R.id.living_tip_reading_post_date);
        contentView = findViewById(R.id.living_tip_reading_content);
        commentBtn = findViewById(R.id.living_tip_reading_comment_btn);

        id = getIntent.getExtras().getInt("id");
        category = getIntent.getExtras().getString("category");
        title = getIntent.getExtras().getString("title");
        writer = getIntent.getExtras().getString("writer");
        date = getIntent.getExtras().getString("date");
        content = getIntent.getExtras().getString("content");
        email = getIntent.getExtras().getString("email");
        imageUri1 = getIntent.getExtras().getString("image1");
        imageUri2 = getIntent.getExtras().getString("image2");
        imageUri3 = getIntent.getExtras().getString("image3");

        Log.d("Image1 URI",imageUri1);

        image1.setImageURI(Uri.parse(imageUri1));
        image2.setImageURI(Uri.parse(imageUri2));
        image3.setImageURI(Uri.parse(imageUri3));

        categoryView.setText(category);
        titleView.setText(title);
        writerView.setText(writer);
        dateView.setText(date);
        contentView.setText(content);

        moreBtn.setEnabled(false);
        moreBtn.setVisibility(View.INVISIBLE);

        if(email.equals(LoginActivity.myEmail)) {
            moreBtn.setEnabled(true);
            moreBtn.setVisibility(View.VISIBLE);
            moreBtn.setOnClickListener(v -> showMoreView());
        }


        //수정버튼 클릭
        modifyBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LivingTipModifyActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("category",category);
                intent.putExtra("title",title);
                intent.putExtra("content",content);
                intent.putExtra("image1",imageUri1);
                intent.putExtra("image2",imageUri2);
                intent.putExtra("image3",imageUri3);
                startActivity(intent);
                moreView.dismiss();
                finish();
            }
        });

        //삭제버튼 클릭
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("확인","클릭 확인");
                LivingTipUseCase livingTipUseCase = new LivingTipUseCase();
                livingTipUseCase.deleteData(key,id,getApplicationContext());
            }
        });

        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LivingTipCommentActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("category",category);
                intent.putExtra("title",title);
                intent.putExtra("content",content);
                intent.putExtra("image1",imageUri1);
                intent.putExtra("image2",imageUri2);
                intent.putExtra("image3",imageUri3);
                startActivity(intent);
            }
        });
    }

    public void showMoreView(){
        moreView.show();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        moreView.dismiss();
    }
}
