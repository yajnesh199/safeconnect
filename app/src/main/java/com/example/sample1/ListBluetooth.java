//package com.example.sample1;
//
//import android.Manifest;
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothDevice;
//import android.bluetooth.BluetoothServerSocket;
//import android.bluetooth.BluetoothSocket;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.content.pm.PackageManager;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.Window;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.app.AppCompatDelegate;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.Set;
//import java.util.UUID;
//
//public class ListBluetooth extends AppCompatActivity {
//
//    private chatutils chatutil;
//    private BluetoothAdapter bluetoothAdapter;
//    private final int SELECT_DEVICE = 102;
//    private static final String TAG = "BluetoothActivity";
//    public static final int MESSAGE_STATE_CHANGED = 0;
//    public static final int MESSAGE_READ = 1;
//    public static final int MESSAGE_WRITE = 2;
//    public static final int MESSAGE_DEVICE_NAME = 3;
//    public static final int MESSAGE_TOAST = 4;
//    private String connectedDevice;
//    public static final String DEVICE_NAME = "deviceName";
//    public static final String TOAST = "toast";
//
//
//
//    private ListView listView;
//    private EditText etsendmsg;
//    private Button btnsendmsg;
//    private  TextView textView;
//    private ArrayAdapter<String> sndmessage;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_list_bluetooth);
//        //set_up_action_and_status_bar();
//
//        initBluetooth();
//        init();
//        chatutil = new chatutils(ListBluetooth.this, handler);
//    }
//
//    private void init() {
//        listView = findViewById(R.id.l1);
//        etsendmsg = findViewById(R.id.et_message);
//        btnsendmsg = findViewById(R.id.btnsndmsg);
//       // textView=findViewById(R.id.txtmsg);
//        sndmessage = new ArrayAdapter<String>(ListBluetooth.this, android.R.layout.simple_list_item_1);
//        listView.setAdapter(sndmessage);
//        btnsendmsg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String message = etsendmsg.getText().toString();
//                if (!message.isEmpty()) {
//                    etsendmsg.setText("");
//                    chatutil.write(message.getBytes());
//                }
//            }
//        });
//    }
//
//    private Handler handler = new Handler(new Handler.Callback() {
//        @Override
//        public boolean handleMessage(@NonNull Message message) {
//            switch (message.what) {
//                case MESSAGE_STATE_CHANGED:
//                    switch (message.arg1) {
//                        case chatutils.STATE_NONE:
//                            setState("Not connected");
//                            break;
//                        case chatutils.STATE_LISTEN:
//                            setState("Nott connect");
//                            break;
//                        case chatutils.STATE_CONNECTING:
//                            setState("connecting..");
//                            //    Log.e(TAG, "connect " + getMessage());
//                            Log.e(TAG, "connecting ");
//                            break;
//                        case chatutils.STATE_CONNECTED:
//                            setState("Connected :" + connectedDevice);
//                            Log.e(TAG, "connected " + connectedDevice);
//                            break;
//                    }
//                    break;
//                case MESSAGE_WRITE:
//                    byte[] buffer1 = (byte[]) message.obj;
//                    String outputbuffer = new String(buffer1);
//                    Log.e(TAG, "write msg " + outputbuffer);
//                    sndmessage.add("Me : " + outputbuffer);
//                    break;
//                case MESSAGE_READ:
//                    Log.e(TAG, "mesage read");
//                    byte[] buffer2 = (byte[]) message.obj;
//                    String inputbuffer = new String(buffer2, 0, message.arg1);
//                    Log.e(TAG, "read msg " + inputbuffer);
//                    sndmessage.add(connectedDevice + " : " + inputbuffer);
//
//                    break;
//                case MESSAGE_DEVICE_NAME:
//                    connectedDevice = message.getData().getString(DEVICE_NAME);
//                    Toast.makeText(ListBluetooth.this, connectedDevice, Toast.LENGTH_SHORT).show();
//                    break;
//                case MESSAGE_TOAST:
//                    Toast.makeText(ListBluetooth.this, message.getData().getString(TOAST), Toast.LENGTH_SHORT).show();
//                    break;
//            }
//            return false;
//        }
//    });
//
//    private void setState(CharSequence subTitle) {
//        getSupportActionBar().setSubtitle(subTitle);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_activity, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_item_search:
//                Toast.makeText(this, "search clicked", Toast.LENGTH_SHORT).show();
//                checkpermissions();
//                return true;
//            case R.id.menu_item_enable:
//                enableBluetooth();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//
//    private void initBluetooth() {
//        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        if (bluetoothAdapter == null) {
//            Toast.makeText(ListBluetooth.this, "No Bluetooth found", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void enableBluetooth() {
//        if (!bluetoothAdapter.isEnabled()) {
//            if (ActivityCompat.checkSelfPermission(ListBluetooth.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
//                bluetoothAdapter.enable();
//                return;
//            }
//        }
//        if (bluetoothAdapter.getScanMode() != bluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
//            Intent discoveryIntent = new Intent(bluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
//            discoveryIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
//            startActivity(discoveryIntent);
//        }
//    }
//
//    private void checkpermissions() {
//        if (ContextCompat.checkSelfPermission(ListBluetooth.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(ListBluetooth.this,
//                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//        } else {
//            Intent intent = new Intent(ListBluetooth.this, pairing_list.class);
//            startActivityForResult(intent, SELECT_DEVICE);
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if (requestCode == SELECT_DEVICE && resultCode == RESULT_OK) {
//            String address = data.getStringExtra("deviceAddress");
//            chatutil.connect(bluetoothAdapter.getRemoteDevice(address));
//            Toast.makeText(this, "Address" + address, Toast.LENGTH_SHORT).show();
//
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == 1) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Location Permission Granted", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(ListBluetooth.this, pairing_list.class);
//                startActivity(intent);
//
//            } else {
//                Toast.makeText(this, "Location Permission Diened", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (chatutil != null) {
//            chatutil.stop();
//            Log.e(TAG, "stop");
//        }
//    }
//}
package com.example.sample1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class ListBluetooth extends AppCompatActivity {
    Button listen, send, listDevices, btnencrypt, btndecrypt;
    ListView listView, listaviavble;
    TextView messageBox, status;
    EditText writeMessage, key;

    BluetoothDevice[] bluetoothDevices;
    SendReceive sendReceive;

    // ArrayAdapter<String> arrayAdapter;
    //ArrayList<String> stringArrayList = new ArrayList<String>();
    static final int STATE_LISTENING = 1;
    static final int STATE_CONNECTING = 2;
    static final int STATE_CONNECTED = 3;
    static final int STATE_CONNECTION_FAILED = 4;
    static final int STATE_MESSAGE_RECEIVED = 5;
    int REQUEST_ENABLE_BLUETOOTH = 1;
    private static final String APP_NAME = "BluetoothChatApp";
    private static final UUID MY_UUID = UUID.fromString("c413e31a-3766-48f9-8a20-e9f5b7b77b99");
    private static final String TAG = "Bluetooth";
    BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_bluetooth);
        listen = findViewById(R.id.listen);
        send = findViewById(R.id.send);
        key = findViewById(R.id.key);
        listDevices = findViewById(R.id.listDevices);
        listView = findViewById(R.id.listview);
        listaviavble = findViewById(R.id.listaviavble);
        status = findViewById(R.id.status);
        messageBox = findViewById(R.id.msg);
        writeMessage = findViewById(R.id.writemsg);
        btnencrypt = findViewById(R.id.Encrypt);
        btndecrypt = findViewById(R.id.Decrypt);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        implementListeners();
        set_up_action_and_status_bar();
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            if (ActivityCompat.checkSelfPermission(ListBluetooth.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            }
            startActivityForResult(enableIntent, REQUEST_ENABLE_BLUETOOTH);
        }
    }

    private void implementListeners() {
        listDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(ListBluetooth.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
                    String[] strings = new String[devices.size()];
                    bluetoothDevices = new BluetoothDevice[devices.size()];
                    int index = 0;
                    if (devices.size() > 0) {
                        for (BluetoothDevice device : devices) {
                            bluetoothDevices[index] = device;
                            strings[index] = device.getName();
                            index++;
                        }
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, strings);
                        listView.setAdapter(arrayAdapter);
                    }
                }
            }
        });
        listen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServerClass serverClass = new ServerClass();
                serverClass.start();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                ClientClass clientClass = new ClientClass(bluetoothDevices[i]);
                clientClass.start();
                status.setText("Connecting");
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = writeMessage.getText().toString();
                //     String message = etsendmsg.getText().toString();
                sendReceive.write(string.getBytes());
                Log.e(TAG, "setting up server" + string);

            }
        });
        btnencrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String encrypt = com.scottyab.aescrypt.AESCrypt.encrypt(key.getText().toString(), writeMessage.getText().toString());
                    writeMessage.setText(encrypt);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
        btndecrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string = writeMessage.getText().toString();
                try {
                    String decrypt = com.scottyab.aescrypt.AESCrypt.decrypt(key.getText().toString(), messageBox.getText().toString());
                    messageBox.setText(decrypt);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            if (ActivityCompat.checkSelfPermission(ListBluetooth.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                switch (msg.what) {
                    case STATE_LISTENING:
                        status.setText("Listening");
                        break;
                    case STATE_CONNECTING:
                        status.setText("Connecting");
                        break;
                    case STATE_CONNECTED:
                        status.setText("Connected");
                        Log.e(TAG, "Connected");
                        break;
                    case STATE_CONNECTION_FAILED:
                        status.setText("Connection Failed");
                        break;
                    case STATE_MESSAGE_RECEIVED:
                        byte[] readBuffer = (byte[]) msg.obj;
                        String tempMessage = new String(readBuffer, 0, msg.arg1);
                        Log.e(TAG, " message received " + tempMessage);
                        messageBox.setText(tempMessage);
                        break;
                }
            }
            return true;
        }
    });

    private class ServerClass extends Thread {
        private BluetoothServerSocket serverSocket;

        public ServerClass() {
            try {
                if (ActivityCompat.checkSelfPermission(ListBluetooth.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    serverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(APP_NAME, MY_UUID);
                    Log.e(TAG, "server socket" + serverSocket);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            BluetoothSocket socket = null;
            while (true) {
                try {
                    Message message = Message.obtain();
                    message.what = STATE_CONNECTING;
                    handler.sendMessage(message);
                    socket = serverSocket.accept();
                    Log.e(TAG, "Socket accepted" + socket);
                } catch (IOException e) {
                    e.printStackTrace();
                    Message message = Message.obtain();
                    message.what = STATE_CONNECTION_FAILED;
                    handler.sendMessage(message);
                }
                if (socket != null) {
                    Message message = Message.obtain();
                    message.what = STATE_CONNECTED;
                    handler.sendMessage(message);
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
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    socket = device.createRfcommSocketToServiceRecord(MY_UUID);
                    Log.e(TAG, "setting up server" + device);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                if (ActivityCompat.checkSelfPermission(ListBluetooth.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    socket.connect();
                    Message message = Message.obtain();
                    message.what = STATE_CONNECTED;
                    handler.sendMessage(message);
                    sendReceive = new SendReceive(socket);
                    sendReceive.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Message message = Message.obtain();
                message.what = STATE_CONNECTION_FAILED;
                handler.sendMessage(message);
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
                // Log.e(TAG, "write bytes" + bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

}