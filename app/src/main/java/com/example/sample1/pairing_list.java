package com.example.sample1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class pairing_list extends AppCompatActivity {
    private ListView lispairedevice, listavialable;
    private ArrayAdapter<String> adapterpaired, adapteraivalable;
    private BluetoothAdapter bluetoothAdapter;
    private ProgressBar progresscandevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pairing_list);
        init();
    }

    public void init() {
        lispairedevice = findViewById(R.id.list_paired_device);
        listavialable = findViewById(R.id.list_available_device);
        progresscandevice = findViewById(R.id.scan_progress);

        adapteraivalable = new ArrayAdapter<String>(pairing_list.this, android.R.layout.simple_list_item_1);
        adapterpaired = new ArrayAdapter<String>(pairing_list.this, android.R.layout.simple_list_item_1);

        lispairedevice.setAdapter(adapterpaired);
        listavialable.setAdapter(adapteraivalable);

        lispairedevice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String info=((TextView) view).getText().toString();
                String address=info.substring(info.length()-17);
                Intent intent=new Intent();
                intent.putExtra("deviceAddress", address);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        listavialable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                if (ActivityCompat.checkSelfPermission(pairing_list.this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
//                 bluetoothAdapter.cancelDiscovery();
//                    return;
//                }

                String info = ((TextView) view).getText().toString();
                String address = info.substring(info.length() - 17);

                Intent intent = new Intent();
                intent.putExtra("deviceAddress", address);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (ActivityCompat.checkSelfPermission(pairing_list.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            Set<BluetoothDevice> pairedDevice = bluetoothAdapter.getBondedDevices();
            if (pairedDevice != null && pairedDevice.size() > 0) {
                for (BluetoothDevice device : pairedDevice) {
                    adapterpaired.add(device.getName() + "\n" + device.getAddress());
                    Log.e("tag", "name" + device.getName());
                }
            }
            IntentFilter intentFilter=new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(bluetoothdevicelistner,intentFilter);
            IntentFilter intentFilter1=new IntentFilter(bluetoothAdapter.ACTION_DISCOVERY_FINISHED);
            registerReceiver(bluetoothdevicelistner,intentFilter1);
        }
    }

    private BroadcastReceiver bluetoothdevicelistner = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (ActivityCompat.checkSelfPermission(pairing_list.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

                    if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                        adapteraivalable.add(device.getName() + "\n" + device.getAddress());
                    }
                }else if(bluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
                    progresscandevice.setVisibility(View.GONE);
                    if(adapteraivalable.getCount()==0){
                        Toast.makeText(context, "No device found", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    }
};
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scan, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_scan:
                //Toast.makeText(this, "Scan devices clicked", Toast.LENGTH_SHORT).show();
                    scan();
                    return  true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void scan() {
        progresscandevice.setVisibility(View.VISIBLE);
        adapteraivalable.clear();
        Toast.makeText(this, "Scan Started", Toast.LENGTH_SHORT).show();
        if (ActivityCompat.checkSelfPermission(pairing_list.this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            if (bluetoothAdapter.isDiscovering()) {
                bluetoothAdapter.cancelDiscovery();
            }
            bluetoothAdapter.startDiscovery();
            return;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bluetoothdevicelistner  != null) {
            unregisterReceiver(bluetoothdevicelistner );
        }
    }
}


