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
    String Decrypt_left_msg;
    String Decrypt_right_msg;

    public ChatadapterDoa(List<User> msgDtoList, String my_id) {
        this.msgDtoList = msgDtoList;
        this.my_id = my_id;


    }

    @Override
    public void onBindViewHolder(chatviewholder holder, int position) {

        User msgDto = this.msgDtoList.get(position);
        //messagemodel msgDto = this.msgDtoList.get(position);
        Log.e("tag", "msg");

        if (msgDto.imageList != null) {
            byte[] bytes = Base64.decode(msgDto.imageList, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            Log.e("tag", "m" + bitmap);
        }

        // User image=this.msgDtoList.get(position);
        //User image =this.msgDtoList.get(position);
//        if (msgDto.MSG_TYPE_RECEIVED.equals(msgDto.getMsgType())) {
//            // Show received message in left linearlayout.
//            holder.leftMsgLayout.setVisibility(LinearLayout.VISIBLE);
//            holder.leftMsgTextView.setText(msgDto.getMsgContent());
//            holder.rightMsgLayout.setVisibility(LinearLayout.GONE);
//        }
//        // If the message is a sent message.
//        else if (msgDto.MSG_TYPE_SENT.equals(msgDto.getMsgType())) {
//            // Show sent message in right linearlayout.
//            holder.rightMsgLayout.setVisibility(LinearLayout.VISIBLE);
//            holder.rightMsgTextView.setText(msgDto.getMsgContent());
//            holder.leftMsgLayout.setVisibility(LinearLayout.GONE);
//        }

        //uncomment
        if (msgDto.senderID.equalsIgnoreCase(my_id)) {
            // Show received message in left linearlayout.
            holder.leftMsgLayout.setVisibility(View.VISIBLE);
            holder.rightMsgLayout.setVisibility(View.GONE);
            if (msgDto.messageInfo == null || msgDto.messageInfo.equalsIgnoreCase("null")) {
                //if (msgDto.imageList.matches(val)) {
                Log.e("tag", "message1" + msgDto.messageInfo);
                holder.leftimage.setImageBitmap(bitmap);
                holder.leftimage.setVisibility(View.VISIBLE);
                holder.leftMsgTextView.setVisibility(View.GONE);
            } else {

            try {
                Decrypt_left_msg = com.scottyab.aescrypt.AESCrypt.decrypt("123", msgDto.messageInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            holder.leftMsgTextView.setText(Decrypt_left_msg);
            Log.e("tag", "messahe info");
            holder.leftimage.setVisibility(View.GONE);
            holder.leftMsgTextView.setVisibility(View.VISIBLE);
              }
            holder.lefttimeview.setText(msgDto.messageDatetime);
            Log.e("tag", "bit" + bitmap);

        }
        // If the message is a sent message.
        // else if (msgDto.senderID.equalsIgnoreCase(my_id)) {
        //   holder.rightMsgLayout.setVisibility(LinearLayout.VISIBLE);

        //uncomment
        else {
            // Show sent message in right linearlayout.
            holder.leftMsgLayout.setVisibility(View.GONE);
            holder.rightMsgLayout.setVisibility(View.VISIBLE);
            if (msgDto.messageInfo == null || msgDto.messageInfo.equalsIgnoreCase("null")) {
                //   if (msgDto.imageList.matches(val)) {
                Log.e("tag", "message2" + msgDto.messageInfo);
                holder.rightimage.setImageBitmap(bitmap);
                holder.rightimage.setVisibility(View.VISIBLE);
                holder.rightMsgTextView.setVisibility(View.GONE);
                Log.e("tag", "bit" + bitmap);
                //uncomment
            } else {

            try {
                Decrypt_right_msg = com.scottyab.aescrypt.AESCrypt.decrypt("123", msgDto.messageInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.e("tag", "message3" + msgDto.messageInfo);
            holder.rightMsgTextView.setText(Decrypt_right_msg);
            holder.rightimage.setVisibility(View.GONE);
            holder.rightMsgTextView.setVisibility(View.VISIBLE);

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

        // return  msgDtoList.size();

    }

}
