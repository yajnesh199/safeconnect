package com.example.sample1;

import static java.lang.Integer.parseInt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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

import com.melegy.redscreenofdeath.RedScreenOfDeath;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class chatActivity extends AppCompatActivity {
    RecyclerView msgRecyclerView;
    recycleadapter recycleadapter;
    ImageButton msgSendButton;
    EditText msgInputText;
    TextView right_msg_text;
    String BT_address;
    String receiver_id;
    String BT_name;
    String Sender_id;
    String my_id;
    BluetoothAdapter bluetoothAdapter;
    ChatadapterDoa chatadapterDoa;
   // List<User> users;
    int newMsgPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        RedScreenOfDeath.init(getApplication());
        set_up_action_and_status_bar();
        BT_address = getIntent().getStringExtra("key");
        Toast.makeText(this, "" + BT_address, Toast.LENGTH_SHORT).show();
        Log.e("str", "str" + BT_address);
        ImageView toolbaricon = findViewById(R.id.toolbar_icon);
        TextView toolbartitle = findViewById(R.id.toolbar_title);
        right_msg_text = findViewById(R.id.chat_right_msg_text_view);
    //    SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
//        BT_name = sh.getString("name", " ");
//        Sender_id = sh.getString("address", " ");
      //  my_id = sh.getString("string_id", " ");
        Log.e("Chat_BT", "Address" + Sender_id);
        toolbaricon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chatActivity.this, bottomnavig.class);
                startActivity(intent);
            }
        });
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
        //final List<User> msgDtoList = new ArrayList<User>();
        messagemodel2 msgDto = new messagemodel2(messagemodel2.MSG_TYPE_RECEIVED, "hello");
        msgDtoList.add(msgDto);
        final messageadapter2 MsgAdapter = new messageadapter2(msgDtoList);
        //final  ChatadapterDoa MsgAdapter = new ChatadapterDoa();
        msgRecyclerView.setAdapter(MsgAdapter);
        // linearLayoutManager.smoothScrollToPosition(msgRecyclerView, null, 1);
        msgInputText = findViewById(R.id.chat_input_msg);
        msgSendButton = findViewById(R.id.chat_send_msg);
        msgSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               insertroomdb();
               getroomdata();
//                String msgContent = msgInputText.getText().toString();
//                if (!TextUtils.isEmpty(msgContent)) {
//                    messagemodel2 msgDto = new messagemodel2(messagemodel2.MSG_TYPE_SENT, msgContent);
//                    msgDtoList.add(msgDto);
//                    Log.e("M", "msg " + msgDto);
//                    Log.e("M", "msglist " + msgDtoList);
//                    int newMsgPosition = msgDtoList.size() - 1;
//                    Log.e("M", "msgposition " + newMsgPosition);
//                    MsgAdapter.notifyItemInserted(newMsgPosition);
//                    msgRecyclerView.scrollToPosition(newMsgPosition);
//                       msgInputText.setText("");
//                }

                String msgContent = msgInputText.getText().toString();
                if (!TextUtils.isEmpty(msgContent)) {
                    Log.e("M", "msgposition " + newMsgPosition);
                    chatadapterDoa.notifyItemInserted(newMsgPosition);
                    msgRecyclerView.scrollToPosition(newMsgPosition);
                    msgInputText.setText("");
                }
            }
        });
    }

    @SuppressLint("MissingPermission")
    public void insertroomdb() {
        Log.e("M", "msg ");
        database db = Room.databaseBuilder(chatActivity.this,
                database.class, "Chat-table").allowMainThreadQueries().build();
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Sender_id = bluetoothAdapter.getAddress();
        Log.e("h", "h" + Sender_id);
        UserDoa userdao = db.userDao();
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM ,yyyy HH:mm:ss ");
        String strDate = formatter.format(new Date().getTime());
        // userdao.insertAll(new User(parseInt("74"),BT_address,Sender_id,msgInputText.getText().toString(), strDate, null, null));
        userdao.insertAll(new User(null,Sender_id, BT_address, msgInputText.getText().toString(), strDate, null, null));
    }

    public void getroomdata() {
        database db = Room.databaseBuilder(getApplicationContext(),
                database.class, "Chat-table").allowMainThreadQueries().build();
        msgRecyclerView = findViewById(R.id.recview);
        msgRecyclerView.setLayoutManager(new LinearLayoutManager(chatActivity.this));
        UserDoa userdao = db.userDao();
        List<User> users = userdao.getallusers();
        //List<User> users = userdao.getchat(Sender_id);
        chatadapterDoa = new ChatadapterDoa(users, Sender_id);
        msgRecyclerView.setAdapter(chatadapterDoa);
        newMsgPosition = users.size() - 1;
        chatadapterDoa.notifyDataSetChanged();

//
//        messageadapter2 messageadapter2=new messageadapter2(users);
//       msgRecyclerView.setAdapter(messageadapter2);

        //  Log.e("tag","postion" + newMsgPosition);
        Log.e("List", "List" + users);
    }

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
