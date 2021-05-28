package com.example.smj.ui.Comments.LivingTip;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smj.Manager.JWTManager;
import com.example.smj.R;
import com.example.smj.data.entity.Member.MemberData;
import com.example.smj.domain.usecase.LivingTipCommentsUseCase;
import com.example.smj.ui.login.LoginActivity;

import java.util.List;

public class LivingTipCommentAdapter extends RecyclerView.Adapter<LivingTipCommentAdapter.ViewHolder> {

    private List<LivingTipCommentData> commentData = null;
    private List<MemberData> memberData = null;
    private Context context;
    private LivingTipCommentsUseCase livingTipCommentsUseCase;
    private String token;
    private int id;
    private Dialog moreView;
    private Button deleteBtn, modifyBtn;

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView date;
        private TextView commenter;
        private TextView contents;
        private ImageButton moreBtn;

        ViewHolder(View itemView){
            super(itemView);

            date = itemView.findViewById(R.id.comment_date);
            commenter = itemView.findViewById(R.id.commenter);
            contents = itemView.findViewById(R.id.comment_contents);
            moreBtn = itemView.findViewById(R.id.comment_more_btn);
            moreView = new Dialog(context);
            moreView.requestWindowFeature(Window.FEATURE_NO_TITLE);
            moreView.setContentView(R.layout.comment_view_more);
            deleteBtn = (Button) moreView.findViewById(R.id.delete_comment);
            modifyBtn = (Button) moreView.findViewById(R.id.modify_comment);
        }
    }

    public LivingTipCommentAdapter(List<LivingTipCommentData> commentData, List<MemberData> memberData, Context context, LivingTipCommentsUseCase livingTipCommentsUseCase, String token, int id) {
        this.commentData = commentData;
        this.memberData = memberData;
        this.context = context;
        this.livingTipCommentsUseCase = livingTipCommentsUseCase;
        this.token = token;
        this.id = id;
    }

    @NonNull
    @Override
    public LivingTipCommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.comment_item,parent,false);
        LivingTipCommentAdapter.ViewHolder vh = new LivingTipCommentAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull LivingTipCommentAdapter.ViewHolder holder, int position) {
        String[] getDate = commentData.get(position).getDate();
        String commenter = commentData.get(position).getCommenter();
        String contents = commentData.get(position).getContents();
        String email = commentData.get(position).getEmail();
        String date = getDate[0]+"-"+getDate[1]+"-"+getDate[2] + " " + getDate[3]+":"+getDate[4];

        holder.date.setText(date);
        holder.commenter.setText(commenter);
        holder.contents.setText(contents);
        holder.moreBtn.setEnabled(false);
        holder.moreBtn.setVisibility(View.INVISIBLE);

        if(email.equals(LoginActivity.myEmail)){
            holder.moreBtn.setEnabled(true);
            holder.moreBtn.setVisibility(View.VISIBLE);

            holder.moreBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moreView.show();
                }
            });
        }

        modifyBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LivingTipCommentModifyActivity.class);
                intent.putExtra("Data", commentData.get(position));
                context.startActivity(intent);
                moreView.dismiss();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                livingTipCommentsUseCase.deleteData(token,commentData.get(position).getCommentId(),context);
                moreView.dismiss();
                refreshAdapter();
            }
        });
    }

    @Override
    public int getItemCount() {
        return commentData.size();
    }

    public void refreshAdapter(){
        notifyDataSetChanged();
    }
}

