package com.example.sample1;

import static android.content.ContentValues.TAG;
import static java.lang.Integer.parseInt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class chatActivity extends AppCompatActivity {
    static final int STATE_LISTENING = 1;
    static final int STATE_CONNECTING = 2;
    static final int STATE_CONNECTED = 3;
    static final int STATE_CONNECTION_FAILED = 4;
    static final int STATE_MESSAGE_RECEIVED = 5;
    RecyclerView msgRecyclerView;
    recycleadapter recycleadapter;
    ImageView msgSendButton, chat_image, image1;
    EditText msgInputText;
    TextView right_msg_text, txt_msg;
    String BT_address;
    String receiver_id;
    String BT_name;
    String Sender_id;
    String my_id;
    TextView toolbartitle;
    BluetoothAdapter bluetoothAdapter;
    ChatadapterDoa chatadapterDoa;
    int newMsgPosition;
    String image;
    String tempMessage;
    SendReceive sendReceive;
    BluetoothDevice bluetoothDevice;
    List<String> my_list;
    List<User> users;
    database db;
    UserDoa userdao;
    ImageView toolbaricon;
    SimpleDateFormat formatter;
    String strDate;

    //List<String> mylist = new ArrayList<String>(Arrays.asList(tempMessage));
    private static final String APP_NAME = "BluetoothChatApp";
    private static final UUID MY_UUID = UUID.fromString("c413e31a-3766-48f9-8a20-e9f5b7b77b99");
    private static final String TAG = "Bluetooth";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        set_up_action_and_status_bar();
        RedScreenOfDeath.init(getApplication());
        //     try {
        db = Room.databaseBuilder(getApplicationContext(), database.class, "Chat-table").allowMainThreadQueries().build();
        userdao = db.userDao();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (ActivityCompat.checkSelfPermission(chatActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

        }
        Sender_id = bluetoothAdapter.getAddress();
        Log.e("h", "h" + Sender_id);
        Toast.makeText(this, "" + Sender_id, Toast.LENGTH_SHORT).show();
        Log.e("Chat_BT", "Address" + Sender_id);
        toolbaricon = findViewById(R.id.toolbar_icon);
        toolbartitle = findViewById(R.id.toolbar_title);
        right_msg_text = findViewById(R.id.chat_right_msg_text_view);
        bluetoothDevice = getIntent().getExtras().getParcelable("bluetoothdevice");
        Log.e("tag", "h1" + bluetoothDevice);
        BT_address = bluetoothDevice.getAddress();
        Toast.makeText(this, "" + BT_address, Toast.LENGTH_SHORT).show();
        toolbartitle.setText(BT_address);
        ServerClass1 serverClass1 = new ServerClass1();
        serverClass1.start();
        ClientClass clientClass = new ClientClass(bluetoothDevice);
        clientClass.start();
        msgRecyclerView = findViewById(R.id.recview);
        msgRecyclerView.setLayoutManager(new LinearLayoutManager(chatActivity.this));
        userdao = db.userDao();
        users = userdao.getallusers();
        //  users = userdao.getchat(Sender_id);
        chatadapterDoa = new ChatadapterDoa(users,Sender_id);
        msgRecyclerView.setAdapter(chatadapterDoa);
        newMsgPosition = users.size() - 1;
        chatadapterDoa.notifyItemInserted(newMsgPosition);
        msgRecyclerView.scrollToPosition(newMsgPosition);
        chatadapterDoa.notifyDataSetChanged();


//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        // toolbartitle.setText(bluetoothDevice);
//        SharedPreferences sharedPreferences = getSharedPreferences("My", MODE_PRIVATE);
//               devices = sharedPreferences.getInt(bluetoothDevices "_size", 0);

        toolbaricon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chatActivity.this, bottomnavig.class);
                startActivity(intent);
            }
        });
        //

        chat_image = findViewById(R.id.chat_camera);
        //image1 = findViewById(R.id.image1);
        msgInputText = findViewById(R.id.chat_input_msg);
        msgSendButton = findViewById(R.id.chat_send_msg);
        txt_msg = findViewById(R.id.text_msg);
        // getroomdata();
        chat_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(chatActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(chatActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                } else {
                    loadimage();
                }

            }
        });
//        chatadapterDoa = new ChatadapterDoa(my_list);
//        final List<messagemodel> msgDtoList = new ArrayList<messagemodel>();
//        messagemodel msgDto = new messagemodel(messagemodel.MSG_TYPE_RECEIVED, "hello");
//        msgDtoList.add(msgDto);
//        chatadapterDoa = new ChatadapterDoa(msgDtoList, my_list);
//        msgRecyclerView.setAdapter(chatadapterDoa);
        msgSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = msgInputText.getText().toString();
                sendReceive.write(message.getBytes());
                formatter = new SimpleDateFormat("dd MMMM ,yyyy HH:mm:ss ");
                strDate = formatter.format(new Date().getTime());
                userdao.insertAll(new User(null,Sender_id,BT_address, message, strDate, null, null));

                //  stringtobitmap();
                //bitmaptostring();
//                String msgContent = msgInputText.getText().toString();
//                if (!TextUtils.isEmpty(msgContent)) {
//                messagemodel msgDto = new messagemodel(messagemodel.MSG_TYPE_SENT,message);
//                  msgDtoList.add(msgDto);
//                    Log.e("M", "msg " + msgDto);
//                    Log.e("M", "msglist " + msgDtoList);
//                    int newMsgPosition = msgDtoList.size() - 1;
//                    Log.e("M", "msgposition " + newMsgPosition);
//                    MsgAdapter.notifyItemInserted(newMsgPosition);
//                    msgRecyclerView.scrollToPosition(newMsgPosition);
//                       msgInputText.setText("");
//                }
                //  stringtobitmap();
                //    insertroomdb();
                //    getroomdata();


//                    chatadapterDoa = new ChatadapterDoa(,mylist);
//                    msgRecyclerView.setAdapter(chatadapterDoa);
//                    chatadapterDoa.notifyDataSetChanged();
                //  msgInputText.setText("");
                // String msgContent = msgInputText.getText().toString();
//                if (!TextUtils.isEmpty(msgContent)) {
//                    Log.e("M", "msgposition " + newMsgPosition);
//                    chatadapterDoa.notifyItemInserted(newMsgPosition);
//                    msgRecyclerView.scrollToPosition(newMsgPosition);
//
//                }
            }

        });

    }

    public void insertroomdb(String message) {

        Log.e("Tag", "Insert_Method");
       formatter = new SimpleDateFormat("dd MMMM ,yyyy HH:mm:ss ");
        //   SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
         strDate = formatter.format(new Date().getTime());
        userdao.insertAll(new User(null,BT_address,Sender_id, message, strDate, null, null));
        //userdao.insertAll(new User(null,BT_address,Sender_id, message, strDate, null, null));
        //userdao.insertAll(new User(null, Sender_id, BT_address, msgInputText.getText().toString(), strDate, null, image));
       // userdao.insertAll(new User(null,Sender_id,BT_address, message, strDate, null, null));
    }

    public void getroomdata() {

    }

    private void loadimage() {
//        Intent i = new Intent();
//        i.setType("image/*");
//        i.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(i, "select"), SELECT_PICTURE);
//        textView.setText("");
//        imageView.setImageBitmap(null);
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        // start activity result
        startActivityForResult(Intent.createChooser(intent, "Select Image"), 100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadimage();
        } else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
        }
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 40, stream);
                byte[] bytes = stream.toByteArray();
                image = Base64.encodeToString(bytes, Base64.DEFAULT);
                Log.e("img", "l" + image);
//                sharedPreferences = getSharedPreferences("MyShared", Context.MODE_PRIVATE);
//                 SharedPreferences.Editor myEdit = sharedPreferences.edit();
//                myEdit.putString("bitmap", image);
//                 myEdit.commit();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private class ServerClass1 extends Thread {
        private BluetoothServerSocket serverSocket;

        public ServerClass1() {
            try {

                if (ActivityCompat.checkSelfPermission(chatActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    serverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(APP_NAME, MY_UUID);
                    Log.e(TAG, "server socket" + serverSocket);
                }

            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "E");
            }
        }

        public void run() {
            BluetoothSocket socket = null;
            while (true) {
                try {
                    socket = serverSocket.accept();
                    Log.e(TAG, "Socket accepted" + socket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (socket != null) {
                    sendReceive = new SendReceive(socket);
                    sendReceive.start();
                    break;
                }
            }
        }
    }

    private class ClientClass extends Thread {
        private BluetoothDevice device;
        private BluetoothSocket socket;

        public ClientClass(BluetoothDevice device1) {
            device = device1;
            try {
                if (ActivityCompat.checkSelfPermission(chatActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    socket = device.createRfcommSocketToServiceRecord(MY_UUID);
                    Log.e(TAG, "setting up " + device);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                if (ActivityCompat.checkSelfPermission(chatActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

                }
                socket.connect();
                Log.e("j", "j");
                Log.e("tag", "connect" + socket);
                sendReceive = new SendReceive(socket);
                sendReceive.start();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

    private class SendReceive extends Thread {
        private final BluetoothSocket bluetoothSocket;
        private final InputStream inputStream;
        private final OutputStream outputStream;

        public SendReceive(BluetoothSocket socket) {
            bluetoothSocket = socket;
            InputStream tempInput = null;
            OutputStream tempOutput = null;

            try {
                tempInput = bluetoothSocket.getInputStream();
                tempOutput = bluetoothSocket.getOutputStream();

            } catch (IOException e) {
                e.printStackTrace();
            }
            inputStream = tempInput;
            outputStream = tempOutput;
            Log.e("tag", "h" + inputStream);
        }

        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;
            while (true) {
                try {
                    bytes = inputStream.read(buffer);
                    Log.e(TAG, "Read bytes " + bytes);
                    String str = new String(buffer);
                    Log.e(TAG, " read bytes " + str);
                    handler.obtainMessage(STATE_MESSAGE_RECEIVED, bytes, -1, buffer).sendToTarget();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void write(byte[] bytes) {
            try {
                outputStream.write(bytes);
                Log.e(TAG, "write bytes" + bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            if (ActivityCompat.checkSelfPermission(chatActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                switch (msg.what) {
                    case STATE_LISTENING:
                        toolbartitle.setText("Listening");
                        break;
                    case STATE_CONNECTING:
                        toolbartitle.setText("Connecting");
                        break;
                    case STATE_CONNECTED:
                        toolbartitle.setText("Connected");
                        Log.e(TAG, "Connected");
                        break;
                    case STATE_CONNECTION_FAILED:
                        toolbartitle.setText("Connection Failed");
                        break;
                    case STATE_MESSAGE_RECEIVED:
                        byte[] readBuffer = (byte[]) msg.obj;
                        String tempMessage = new String(readBuffer, 0, msg.arg1);
                        my_list = new ArrayList<String>(Arrays.asList(tempMessage));
                        Log.e(TAG, " message received " + my_list);
                        insertroomdb(tempMessage);
                        txt_msg.setText(tempMessage);
                        break;
                }
            }
            return true;
        }
    });

    RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }

        @Override
        public void onDestructiveMigration(@NonNull SupportSQLiteDatabase db) {
            super.onDestructiveMigration(db);
        }
    };
}
