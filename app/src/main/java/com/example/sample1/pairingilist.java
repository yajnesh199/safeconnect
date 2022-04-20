package com.example.sample1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Set;

public class pairingilist extends AppCompatActivity {
    private ListView listViewpaired;
    private Context context;
    private ArrayAdapter<String> adapterpaired;
    private BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pairingilist);
        context = this;
        init();

    }

    private void init() {
        listViewpaired = findViewById(R.id.list_paired);
        adapterpaired = new ArrayAdapter<String>(context, R.layout.devicelist);
        listViewpaired.setAdapter(adapterpaired);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
        }
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if(pairedDevices!=null && pairedDevices.size()>0){
            for(BluetoothDevice device:pairedDevices){
                adapterpaired.add(device.getName()+"\n"+device.getAddress());
            }
        }
    }
}
