package com.example.sample1;

import android.Manifest;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class ListBluetooth extends AppCompatActivity {

    private chatutils chatutil;
    private BluetoothAdapter bluetoothAdapter;
    private final int SELECT_DEVICE = 102;
    private static final String TAG = "BluetoothActivity";
    public static final int MESSAGE_STATE_CHANGED = 0;
    public static final int MESSAGE_READ = 1;
    public static final int MESSAGE_WRITE = 2;
    public static final int MESSAGE_DEVICE_NAME = 3;
    public static final int MESSAGE_TOAST = 4;
    private String connectedDevice;
    public static final String DEVICE_NAME = "deviceName";
    public static final String TOAST = "toast";


    private  ListView listView;
    private EditText etsendmsg;
    private Button btnsendmsg;
    private  ArrayAdapter<String> sndmessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_bluetooth);
        //set_up_action_and_status_bar();

        initBluetooth();
        init();
        chatutil = new chatutils(ListBluetooth.this, handler);
    }
     private  void init(){
         listView=findViewById(R.id.l1);
         etsendmsg=findViewById(R.id.et_message);
         btnsendmsg=findViewById(R.id.btnsndmsg);
         sndmessage=new ArrayAdapter<String>(ListBluetooth.this,android.R.layout.simple_list_item_1);
         listView.setAdapter(sndmessage);
         btnsendmsg.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String message=etsendmsg.getText().toString();
                 if(!message.isEmpty()){
                     etsendmsg.setText("");
                    chatutil.write(message.getBytes());
                 }
             }
         });
     }
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            switch (message.what) {
                case MESSAGE_STATE_CHANGED:
                    switch (message.arg1) {
                        case chatutils.STATE_NONE:
                            setState("Not connected");
                            break;
                        case chatutils.STATE_LISTEN:
                            setState("Nott connect");
                            break;
                        case chatutils.STATE_CONNECTING:
                            setState("connecting..");
                            //    Log.e(TAG, "connect " + getMessage());
                            Log.e(TAG, "connecting ");
                            break;
                        case chatutils.STATE_CONNECTED:
                            setState("Connected :" + connectedDevice);
                            Log.e(TAG, "connected " +connectedDevice);
                            break;
                    }
                    break;
                case MESSAGE_WRITE:
                    byte[] buffer1=(byte[]) message.obj;
                    String outputbuffer=new String(buffer1);
                    sndmessage.add("Me : " + outputbuffer);
                    Log.e(TAG,"write msg " + outputbuffer);
                    break;
                case MESSAGE_READ:
                    byte[] buffer2=(byte[]) message.obj;
                    String inputbuffer=new String(buffer2,0,message.arg1);
                    sndmessage.add(connectedDevice + " : " + inputbuffer);
                    Log.e(TAG,"read msg " + inputbuffer);
                    break;
                case MESSAGE_DEVICE_NAME:
                    connectedDevice = message.getData().getString(DEVICE_NAME);
                    Toast.makeText(ListBluetooth.this, connectedDevice, Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_TOAST:
                    Toast.makeText(ListBluetooth.this, message.getData().getString(TOAST), Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        }
    });

    private void setState(CharSequence subTitle) {
        getSupportActionBar().setSubtitle(subTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_search:
                Toast.makeText(this, "search clicked", Toast.LENGTH_SHORT).show();
                checkpermissions();
                return true;
            case R.id.menu_item_enable:
                enableBluetooth();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void initBluetooth() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(ListBluetooth.this, "No Bluetooth found", Toast.LENGTH_SHORT).show();
        }
    }
    private void enableBluetooth() {
        if (!bluetoothAdapter.isEnabled()) {
            if (ActivityCompat.checkSelfPermission(ListBluetooth.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                bluetoothAdapter.enable();
                return;
            }
        }
        if (bluetoothAdapter.getScanMode() != bluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
                Intent discoveryIntent = new Intent(bluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                discoveryIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                startActivity(discoveryIntent);
            }
    }
    private void  checkpermissions() {
        if (ContextCompat.checkSelfPermission(ListBluetooth.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ListBluetooth.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            Intent intent = new Intent(ListBluetooth.this, pairing_list.class);
            startActivityForResult(intent,SELECT_DEVICE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == SELECT_DEVICE && resultCode == RESULT_OK) {
            String address = data.getStringExtra("deviceAddress");
            chatutil.connect(bluetoothAdapter.getRemoteDevice(address));
            Toast.makeText(this, "Address" + address, Toast.LENGTH_SHORT).show();

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Location Permission Granted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ListBluetooth.this, pairing_list.class);
                startActivity(intent);

            } else {
                Toast.makeText(this, "Location Permission Diened", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (chatutil != null) {
            chatutil.stop();
            Log.e(TAG, "stop");
        }
    }
}