package com.example.smj.ui.Message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.smj.R;

import java.util.ArrayList;

public class MessageItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ChatMessageData> myDataList = null;

    MessageItemAdapter(ArrayList<ChatMessageData> dataList) {
        myDataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (viewType == 1) {
            view = inflater.inflate(R.layout.left_message_box, parent, false);
            return new LeftViewHolder(view);
        } else {
            view = inflater.inflate(R.layout.right_message_box, parent, false);
            return new RightViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof LeftViewHolder) {
            ((LeftViewHolder) viewHolder).message.setText(myDataList.get(position).getMessage());
        } else {
            ((RightViewHolder) viewHolder).message.setText(myDataList.get(position).getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return myDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (myDataList.get(position).getId().equals("my")) {
            return 2;
        } else {
            return 1;
        }

    }


    public class LeftViewHolder extends RecyclerView.ViewHolder {
        TextView message;

        LeftViewHolder(View itemView) {
            super(itemView);

            message = itemView.findViewById(R.id.left_message);
        }
    }

    public class RightViewHolder extends RecyclerView.ViewHolder {
        TextView message;

        RightViewHolder(View itemView) {
            super(itemView);

            message = itemView.findViewById(R.id.right_message);
        }
    }

}
