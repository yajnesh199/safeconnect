package com.example.sample1;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChatadapterDoa extends RecyclerView.Adapter<chatviewholder> {
    private List<User> msgDtoList;
    private String my_id;
    SharedPreferences sharedPreferences;
    Context context;
     Bitmap bitmap;



    public ChatadapterDoa(List<User> msgDtoList, String my_id,Bitmap bitmap) {
        this.msgDtoList = msgDtoList;
        this.my_id = my_id;
        this.bitmap=bitmap;
    }

    @Override
    public void onBindViewHolder(chatviewholder holder, int position) {
        User msgDto = this.msgDtoList.get(position);
       // User image=this.msgDtoList.get(position);
        //User image =this.msgDtoList.get(position);
        // If the message is a received message.
//        if(msgDto.MSG_TYPE_RECEIVED.equals(msgDto.getMsgType()))
//        {
//            // Show received message in left linearlayout.
//            holder.leftMsgLayout.setVisibility(LinearLayout.VISIBLE);
//            holder.leftMsgTextView.setText(msgDto.getMsgContent());
//            holder.rightMsgLayout.setVisibility(LinearLayout.GONE);
//        }
//        // If the message is a sent message.
//        else if(msgDto.MSG_TYPE_SENT.equals(msgDto.getMsgType()))
//        {
//            // Show sent message in right linearlayout.
//            holder.rightMsgLayout.setVisibility(LinearLayout.VISIBLE);
//            holder.rightMsgTextView.setText(msgDto.getMsgContent());
//            holder.leftMsgLayout.setVisibility(LinearLayout.GONE);
//        }


//        SharedPreferences sh = context.getSharedPreferences("MyShared", MODE_PRIVATE);
//       bitmap= sh.getString("bitmap", "");
//        Log.e("tag","b" + bitmap);
        if (msgDto.receiverID.equalsIgnoreCase(my_id)) {
            // Show received message in left linearlayout.
            holder.leftMsgLayout.setVisibility(RelativeLayout.VISIBLE);
            holder.leftImageLayout.setVisibility(LinearLayout.VISIBLE);
            holder.leftMsgTextView.setText(msgDto.messageInfo);
            holder.lefttimeview.setText(msgDto.messageDatetime);
            holder.leftimage.setImageBitmap(bitmap);
            Log.e("tag","bit" +bitmap);
            holder.rightMsgLayout.setVisibility(RelativeLayout.GONE);
            holder.rightImageLayout.setVisibility(LinearLayout.GONE);
        }
        // If the message is a sent message.
        else if (msgDto.senderID.equals(my_id)) {
            // Show sent message in right linearlayout.
            holder.rightMsgLayout.setVisibility(RelativeLayout.VISIBLE);
            holder.rightImageLayout.setVisibility(LinearLayout.VISIBLE);
            holder.rightMsgTextView.setText(msgDto.messageInfo);
            holder.righttimeview.setText(msgDto.messageDatetime);
            holder.rightimage.setImageBitmap(bitmap);
            Log.e("tag","bit" +bitmap);
            holder.leftMsgLayout.setVisibility(RelativeLayout.GONE);
            holder.leftImageLayout.setVisibility(LinearLayout.GONE);
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
        if (msgDtoList == null) {
            msgDtoList = new ArrayList<User>();
        }
        return msgDtoList.size();
    }
}
