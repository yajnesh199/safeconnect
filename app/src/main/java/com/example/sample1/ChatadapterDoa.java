package com.example.sample1;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
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


    public ChatadapterDoa(List<User> msgDtoList, String my_id) {
        this.msgDtoList = msgDtoList;
        this.my_id = my_id;
    }
    @Override
    public void onBindViewHolder(chatviewholder holder, int position) {
        User msgDto = this.msgDtoList.get(position);
        Log.e("tag","msg" +msgDto);
        if (msgDto.imageList != null) {
            byte[] bytes = Base64.decode(msgDto.imageList, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            Log.e("tag", "m" + bitmap);
        }
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
            holder.leftMsgLayout.setVisibility(LinearLayout.VISIBLE);
            holder.leftimage.setVisibility(LinearLayout.VISIBLE);

            if(msgDto.messageInfo !=null || msgDto.messageInfo.equalsIgnoreCase("null")){
                Log.e("tag", "message1" + msgDto.messageInfo);
                holder.leftimage.setImageBitmap(bitmap);
                holder.rightMsgLayout.setVisibility(LinearLayout.GONE);
                holder.rightimage.setVisibility(LinearLayout.VISIBLE);
            }else{

                holder.leftMsgTextView.setText(msgDto.messageInfo);
                holder.rightMsgLayout.setVisibility(RelativeLayout.VISIBLE);
                holder.rightimage.setVisibility(LinearLayout.GONE);
            }
            holder.lefttimeview.setText(msgDto.messageDatetime);
            Log.e("tag", "bit" + bitmap);

        }
        // If the message is a sent message.
        else if (msgDto.senderID.equalsIgnoreCase(my_id)) {
         //  holder.rightMsgLayout.setVisibility(LinearLayout.VISIBLE);

            // Show sent message in right linearlayout.

            if(msgDto.messageInfo !=null || msgDto.messageInfo.equalsIgnoreCase("null")){
               // holder.leftMsgLayout.setVisibility(LinearLayout.GONE);
                Log.e("tag", "message2" + msgDto.messageInfo);
                holder.rightimage.setImageBitmap(bitmap);
                holder.rightimage.setVisibility(RelativeLayout.VISIBLE);
                holder.rightMsgTextView.setVisibility(RelativeLayout.GONE);
                Log.e("tag","bit" + bitmap);
//               holder.leftimage.setVisibility(LinearLayout.VISIBLE);
//                holder.leftMsgLayout.setVisibility(LinearLayout.GONE);
                holder.leftimage.setVisibility(LinearLayout.GONE);
                holder.leftMsgTextView.setVisibility(RelativeLayout.GONE);
                holder.lefttimeview.setVisibility(RelativeLayout.GONE);
            }
            else{
                Log.e("tag", "message3" + msgDto.messageInfo);
                holder.leftMsgLayout.setVisibility(LinearLayout.GONE);
                holder.rightMsgTextView.setText(msgDto.messageInfo);
                holder.leftimage.setVisibility(LinearLayout.GONE);
                holder.leftMsgTextView.setVisibility(RelativeLayout.GONE);
                holder.lefttimeview.setVisibility(RelativeLayout.GONE);
            }
            holder.righttimeview.setText(msgDto.messageDatetime);
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
