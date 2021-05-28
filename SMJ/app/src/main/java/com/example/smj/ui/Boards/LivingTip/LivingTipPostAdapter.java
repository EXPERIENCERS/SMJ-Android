package com.example.smj.ui.Boards.LivingTip;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smj.R;

import java.util.ArrayList;

public class LivingTipPostAdapter extends RecyclerView.Adapter<LivingTipPostAdapter.ViewHolder> {

    private Context context;
    private ArrayList<LivingTipPostData> postData = null;
    int pos;

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView category;
        private TextView title;
        private TextView contents;
        private TextView writer;
        private TextView date;
        private ImageView mainImage;

        ViewHolder(View itemView){
            super(itemView);

            category = itemView.findViewById(R.id.post_item_category);
            title = itemView.findViewById(R.id.post_item_title);
            contents = itemView.findViewById(R.id.post_item_contents);
            writer = itemView.findViewById(R.id.post_item_writer);
            date = itemView.findViewById(R.id.post_item_date);
            mainImage = itemView.findViewById(R.id.post_item_profile_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(context, LivingTipReadingActivity.class);
                        intent.putExtra("id",postData.get(pos).getId());
                        intent.putExtra("category",postData.get(pos).getCategory());
                        intent.putExtra("title",postData.get(pos).getTitle());
                        intent.putExtra("writer",postData.get(pos).getWriter());
                        intent.putExtra("date",postData.get(pos).getDate());
                        intent.putExtra("content",postData.get(pos).getContents());
                        intent.putExtra("email",postData.get(pos).getEmail());
                        intent.putExtra("image1",postData.get(pos).getImageOne());
                        intent.putExtra("image2",postData.get(pos).getImageTwo());
                        intent.putExtra("image3",postData.get(pos).getImageThree());
                        context.startActivity(intent);
                    }
                }
            });
        }
    }

    public LivingTipPostAdapter(Context context, ArrayList<LivingTipPostData> data){
        this.context = context;
        postData = data;

    }

    @NonNull
    @Override
    public LivingTipPostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull LivingTipPostAdapter.ViewHolder holder, int position) {
        String category = postData.get(position).getCategory();
        String title = postData.get(position).getTitle();
        String contents = postData.get(position).getContents();
        String writer = postData.get(position).getWriter();

        String []getDate = postData.get(position).getDate();

        String date = getDate[0]+"-"+getDate[1]+"-"+getDate[2] + " " + getDate[3]+":"+getDate[4];

        holder.category.setText(category);
        holder.title.setText(title);
        holder.contents.setText(contents);
        holder.writer.setText(writer);
        holder.date.setText(date);
    }

    @Override
    public int getItemCount() {
        return postData.size();
    }

    public void adapterRefresh(){
        notifyDataSetChanged();
    }
}
