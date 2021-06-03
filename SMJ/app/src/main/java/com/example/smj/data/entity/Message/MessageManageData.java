package com.example.smj.data.entity.Message;

import java.util.List;

public class MessageManageData {
    private List<MessageData>data;

    public MessageManageData(List<MessageData> data) {
        this.data = data;
    }

    public List<MessageData> getData() {
        return data;
    }

    public void setData(List<MessageData> data) {
        this.data = data;
    }
}
