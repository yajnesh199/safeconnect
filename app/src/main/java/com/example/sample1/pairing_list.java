package com.example.sample1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Set;

public class pairing_list extends AppCompatActivity {
    private ListView listViewpaired;
    private Context context;
    private ArrayAdapter<String> adapterpaired;
    private BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pairing_list);
        context = this;
        init();
        IntentFilter filter =new IntentFilter(BluetoothDevice.ACTION_FOUND);
       registerReceiver(receiver,filter);
    }
private final BroadcastReceiver receiver=new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action=intent.getAction();
        if(BluetoothDevice.ACTION_FOUND.equals(action)){
            BluetoothDevice device=intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        String deviceAdress=device.getAddress();
        }
    }
};
    protected  void onDestroy(){
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private void init() {
        listViewpaired = findViewById(R.id.list_paired);
        adapterpaired = new ArrayAdapter<String>(context, R.layout.devicelist);
        listViewpaired.setAdapter(adapterpaired);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
            Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
            if (pairedDevices != null && pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {
                    adapterpaired.add(device.getName() + "\n" + device.getAddress());
                }
            }
        }

    }
}
