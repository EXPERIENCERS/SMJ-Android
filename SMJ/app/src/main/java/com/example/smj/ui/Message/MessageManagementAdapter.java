package com.example.smj.ui.Message;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smj.R;
import com.example.smj.data.entity.Message.MessageData;
import com.example.smj.data.entity.Message.MessageManageData;

import java.util.List;

public class MessageManagementAdapter extends RecyclerView.Adapter<MessageManagementAdapter.ViewHolder>{

    private List<MessageData> managementData = null;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView image;
        private TextView name;
        private TextView message;
        private TextView ago;
        private TextView check;

        ViewHolder(View itemView){
            super(itemView);

            image = itemView.findViewById(R.id.message_management_profile_image);
            name = itemView.findViewById(R.id.message_management_name);
            message = itemView.findViewById(R.id.message_management_message);
            ago = itemView.findViewById(R.id.message_management_ago);
            check = itemView.findViewById(R.id.message_management_check);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MessageActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }

    public MessageManagementAdapter(Context context, List<MessageData> data){
        this.context = context;
        managementData = data;
    }

    @NonNull
    @Override
    public MessageManagementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.message_management_item,parent,false);
        MessageManagementAdapter.ViewHolder vh = new MessageManagementAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageManagementAdapter.ViewHolder holder, int position) {
        //String name = managementData.get(position).getNickName();
        String message = managementData.get(position).getContent();
        String[] ago = managementData.get(position).getCreateAt();
        boolean check = managementData.get(position).isCheck();

        //holder.name.setText(name);
        holder.message.setText(message);
    }

    @Override
    public int getItemCount() {
        return managementData.size();
    }
}
