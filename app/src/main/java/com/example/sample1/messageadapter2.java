package com.example.sample1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class messageadapter2  extends RecyclerView.Adapter<chatviewholder> {
    private List<messagemodel2> msgDtoList=null;
    public messageadapter2(List<messagemodel2> msgDtoList) {
        this.msgDtoList = msgDtoList;
    }
    @Override
    public void onBindViewHolder(chatviewholder holder, int position) {
        messagemodel2 msgDto = this.msgDtoList.get(position);
        // If the message is a received message.
        if(msgDto.MSG_TYPE_RECEIVED.equals(msgDto.getMsgType()))
        {
            // Show received message in left linearlayout.
            holder.leftMsgLayout.setVisibility(LinearLayout.VISIBLE);
            holder.leftMsgTextView.setText(msgDto.getMsgContent());
            holder.rightMsgLayout.setVisibility(LinearLayout.GONE);
        }
        // If the message is a sent message.
        else if(msgDto.MSG_TYPE_SENT.equals(msgDto.getMsgType()))
        {
            // Show sent message in right linearlayout.
            holder.rightMsgLayout.setVisibility(LinearLayout.VISIBLE);
            holder.rightMsgTextView.setText(msgDto.getMsgContent());
            holder.leftMsgLayout.setVisibility(LinearLayout.GONE);
        }
    }
    @Override
    public chatviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.itemchat, parent, false);
        return new chatviewholder(view);
    }
    @Override
    public int getItemCount() {
        if(msgDtoList==null)
        {
            msgDtoList = new ArrayList<messagemodel2>();
        }
        return msgDtoList.size();
    }
}
