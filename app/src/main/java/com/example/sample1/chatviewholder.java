package com.example.sample1;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class chatviewholder extends RecyclerView.ViewHolder {
    RelativeLayout leftMsgLayout;
    RelativeLayout rightMsgLayout;
    LinearLayout leftImageLayout;
    LinearLayout rightImageLayout;
    TextView leftMsgTextView;
    TextView rightMsgTextView;
    TextView righttimeview;
    TextView lefttimeview;
    ImageView rightimage;
    ImageView leftimage;
    public chatviewholder(View itemView) {
        super(itemView);
        if(itemView!=null) {
            leftMsgLayout =  itemView.findViewById(R.id.chat_left_msg_layout);
            rightMsgLayout =  itemView.findViewById(R.id.chat_right_msg_layout);
            leftImageLayout=itemView.findViewById(R.id.image_linear_layout1);
            rightImageLayout=itemView.findViewById(R.id.image_linear_layout2);
            leftMsgTextView = itemView.findViewById(R.id.chat_left_msg_text_view);
            rightMsgTextView =  itemView.findViewById(R.id.chat_right_msg_text_view);
            righttimeview=itemView.findViewById(R.id.chat_right_time_view);
            lefttimeview= itemView.findViewById(R.id.chat_left_time_view);
            leftimage=itemView.findViewById(R.id.left_imageview);
            rightimage=itemView.findViewById(R.id.right_imageview);
        }
    }
}
