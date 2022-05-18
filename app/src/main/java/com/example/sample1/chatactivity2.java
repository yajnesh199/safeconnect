package com.example.sample1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class chatactivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatactivity2);
        final RecyclerView msgRecyclerView = (RecyclerView)findViewById(R.id.recview);
        // Set RecyclerView layout manager.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(linearLayoutManager);
        // Create the initial data list.
        final List<messagemodel2> msgDtoList = new ArrayList<messagemodel2>();
       messagemodel2 msgDto = new messagemodel2(messagemodel2.MSG_TYPE_RECEIVED, "hello");
        msgDtoList.add(msgDto);
        // Create the data adapter with above data list.
        final messageadapter2 chatAppMsgAdapter = new messageadapter2(msgDtoList);
        // Set data adapter to RecyclerView.
        msgRecyclerView.setAdapter(chatAppMsgAdapter);
        final EditText msgInputText = (EditText)findViewById(R.id.chat_input_msg);
        Button msgSendButton = (Button)findViewById(R.id.chat_send_msg);
        msgSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msgContent = msgInputText.getText().toString();
                if(!TextUtils.isEmpty(msgContent))
                {
                    // Add a new sent message to the list.
                    messagemodel2 msgDto = new messagemodel2(messagemodel2.MSG_TYPE_SENT, msgContent);
                    msgDtoList.add(msgDto);
                    int newMsgPosition = msgDtoList.size() - 1;
                    // Notify recycler view insert one new data.
                    chatAppMsgAdapter.notifyItemInserted(newMsgPosition);
                    // Scroll RecyclerView to the last message.
                    msgRecyclerView.scrollToPosition(newMsgPosition);
                    // Empty the input edit text box.
                    msgInputText.setText("");
                }
            }
        });
    }
    }
