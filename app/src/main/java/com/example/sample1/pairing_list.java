package com.example.sample1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Set;

public class pairing_list extends AppCompatActivity {
    private static final int REQUEST_ENABLE_BT = 12;
    private TextView out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pairing_list);
        out = findViewById(R.id.tvBluetoothInfo);
        setBluetoothData();
        if (Connection.blueTooth()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        out.setText("");
        setBluetoothData();
    }

    @SuppressLint("HardwareIds")
    private void setBluetoothData() {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
            out.append("\nAdapter: " + adapter.toString() + "\n Name: " + adapter.getName() + "\n Address: " + adapter.getAddress());
            return;
        }

        if (adapter == null) {
            Toast.makeText(this, "Bluetooth NOT supported. Aborting.",
                    Toast.LENGTH_LONG).show();
        }

        out.append("\n Starting discovery...");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED) {
            assert adapter != null;
            adapter.startDiscovery();
            out.append("\nDone with discovery...\n");
            out.append("\nDevices Paired:");
            Set<BluetoothDevice> devices = adapter.getBondedDevices();
            for (BluetoothDevice device : devices) {
                out.append("\nFound device: " + device.getName() + " Add: " + device.getAddress());
            }
        }

    }
}


