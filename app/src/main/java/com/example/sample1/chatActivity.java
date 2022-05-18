package com.example.sample1;

import static java.lang.Integer.parseInt;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class chatActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    recycleadapter recycleadapter;
    ImageButton btnsend;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        set_up_action_and_status_bar();
        String str = getIntent().getStringExtra("key");
        Toast.makeText(this, "id " + str, Toast.LENGTH_SHORT).show();

//        btnsend = findViewById(R.id.btnsend);
//        editText = findViewById(R.id.textInput);
//        ArrayList<messagemodel> messagesList = new ArrayList<>();
//        for (int i=0;i<10;i++) {
//            messagesList.add(new messagemodel("Hi", i % 2 == 0 ? messageadapter.MESSAGE_TYPE_IN : messageadapter.MESSAGE_TYPE_OUT));
//        }
//
//        messageadapter adapter = new messageadapter(this, messagesList);
//
//        recyclerView = findViewById(R.id.chatrecycleview);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(adapter);

         RecyclerView msgRecyclerView = findViewById(R.id.recview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        msgRecyclerView.setLayoutManager(linearLayoutManager);
        final List<messagemodel2> msgDtoList = new ArrayList<messagemodel2>();
        messagemodel2 msgDto = new messagemodel2(messagemodel2.MSG_TYPE_RECEIVED, "hello");
        msgDtoList.add(msgDto);
        final messageadapter2 chatAppMsgAdapter = new messageadapter2(msgDtoList);
        msgRecyclerView.setAdapter(chatAppMsgAdapter);
        EditText msgInputText = findViewById(R.id.chat_input_msg);
       ImageButton msgSendButton = findViewById(R.id.chat_send_msg);
        msgSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msgContent = msgInputText.getText().toString();
                if(!TextUtils.isEmpty(msgContent))
                {
                    messagemodel2 msgDto = new messagemodel2(messagemodel2.MSG_TYPE_SENT, msgContent);
                    msgDtoList.add(msgDto);
                    int newMsgPosition = msgDtoList.size() - 1;
                    chatAppMsgAdapter.notifyItemInserted(newMsgPosition);
                    msgRecyclerView.scrollToPosition(newMsgPosition);
                    msgInputText.setText("");
                }
            }
        });
    }


    private void set_up_action_and_status_bar() {
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
        //to get transparent status bar, try changing the themes
        Window window = getWindow();
        window.setStatusBarColor(Color.WHITE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    //    public  void send(){
//        btnsend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                chatDatabse  db = Room.databaseBuilder(getApplicationContext(),
//                        chatDatabse.class, "chat-DB").allowMainThreadQueries().build();
//                UserDoa userdao = db.userDao();
//            });
//        }

}
