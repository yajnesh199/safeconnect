package com.example.sample1;

import static java.lang.Integer.parseInt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class chatActivity extends AppCompatActivity {
    RecyclerView msgRecyclerView;
    recycleadapter recycleadapter;
    ImageButton msgSendButton;
    EditText msgInputText;
    TextView right_msg_text;
    // private  int lastposition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        set_up_action_and_status_bar();
        String str = getIntent().getStringExtra("key");
        Toast.makeText(this, "id " + str, Toast.LENGTH_SHORT).show();
        ImageView toolbaricon = findViewById(R.id.toolbar_icon);
        TextView toolbartitle = findViewById(R.id.toolbar_title);
        right_msg_text = findViewById(R.id.chat_right_msg_text_view);
        toolbaricon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chatActivity.this, bottomnavig.class);
                startActivity(intent);
            }
        });

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
        msgRecyclerView = findViewById(R.id.recview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        msgRecyclerView.setLayoutManager(linearLayoutManager);
        //
//        SharedPreferences getPrefs= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        lastposition=getPrefs.getInt("lastpos",-1);
//        msgRecyclerView.scrollToPosition(lastposition);
//        //
//        msgRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                lastposition=linearLayoutManager.findFirstVisibleItemPosition();
//                Log.e("M","msgposition " + lastposition);
//            }
//        });
        final List<messagemodel2> msgDtoList = new ArrayList<messagemodel2>();
        messagemodel2 msgDto = new messagemodel2(messagemodel2.MSG_TYPE_RECEIVED, "hello");
        msgDtoList.add(msgDto);
        final messageadapter2 MsgAdapter = new messageadapter2(msgDtoList);
        msgRecyclerView.setAdapter(MsgAdapter);
        linearLayoutManager.smoothScrollToPosition(msgRecyclerView, null, 1);
        msgInputText = findViewById(R.id.chat_input_msg);
        msgSendButton = findViewById(R.id.chat_send_msg);
        msgSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msgContent = msgInputText.getText().toString();
                if (!TextUtils.isEmpty(msgContent)) {
                    messagemodel2 msgDto = new messagemodel2(messagemodel2.MSG_TYPE_SENT, msgContent);
                    msgDtoList.add(msgDto);
                    Log.e("M", "msg " + msgDto);
                    Log.e("M", "msglist " + msgDtoList);
                    int newMsgPosition = msgDtoList.size() - 1;
                    Log.e("M", "msgposition " + newMsgPosition);
                    MsgAdapter.notifyItemInserted(newMsgPosition);
                    msgRecyclerView.scrollToPosition(newMsgPosition);
                    msgInputText.setText("");
                }
            }
        });
    }
//    public  void insertroomdb(){
//        database  db = Room.databaseBuilder(chatActivity.this,
//                database.class, "chat_DB").allowMainThreadQueries().build();
//        UserDoa userdao = db.userDao();
//        userdao.insertAll(new User(parseInt(null),parseInt(null),right_msg_text.getText().toString(),null,null,null));
//    }

//        public  void send(){
//        btnsend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                database  db = Room.databaseBuilder(getApplicationContext(),
//                       database.class, "chat-DB").allowMainThreadQueries().build();
//                UserDoa userdao = db.userDao();
//            });
//        }
//    }

    //    public void getroomdata() {
//        database db = Room.databaseBuilder(getApplicationContext(),
//                database.class, "database-name").allowMainThreadQueries().build();
//        UserDoa userdao = db.userDao();
//       msgRecyclerView = findViewById(R.id.recview);
//        msgRecyclerView.setLayoutManager(new LinearLayoutManager(chatActivity.this));
//        List<User> users = userdao.getallusers();
//      messageadapter2 adapter = new messageadapter2(Users);
//        msgRecyclerView.setAdapter(adapter);
//    }
    private void set_up_action_and_status_bar() {
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
        //to get transparent status bar, try changing the themes
        Window window = getWindow();
        window.setStatusBarColor(Color.WHITE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        SharedPreferences getPrefs= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        SharedPreferences.Editor e=getPrefs.edit();
//        e.putInt("lastpos",lastposition);
//        e.apply();
//    }
}
