package com.example.sample1;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Set;


public class pairedFragment extends Fragment {
    private ListView lispairedevice, listavialable;
    private ArrayAdapter<String> adapterpaired, adapteraivalable;
    private BluetoothAdapter bluetoothAdapter;
    private ProgressBar progresscandevice;
    BluetoothDevice[] bluetoothDevices;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_chat, container, false);

        View view = inflater.inflate(R.layout.fragment_paired, container, false);
        return view;
    }
//    public void init() {
//        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
//            Set<BluetoothDevice> pairedDevice = bluetoothAdapter.getBondedDevices();
//            if (pairedDevice != null && pairedDevice.size() > 0) {
//                for (BluetoothDevice device : pairedDevice) {
//                    adapterpaired.add(device.getName() + "\n" + device.getAddress());
//                    Log.e("tag", "name" + device.getName());
//                }
//            }
//            IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
//            registerReceiver(bluetoothdevicelistner, intentFilter);
//            IntentFilter intentFilter1 = new IntentFilter(bluetoothAdapter.ACTION_DISCOVERY_FINISHED);
//            registerReceiver(bluetoothdevicelistner, intentFilter1);
//        }
//    }
//    private BroadcastReceiver bluetoothdevicelistner = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
//                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
//
//                    if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
//                        adapteraivalable.add(device.getName() + "\n" + device.getAddress());
//                    }
//                } else if (bluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
//                    progresscandevice.setVisibility(View.GONE);
//                    if (adapteraivalable.getCount() == 0) {
//                        Toast.makeText(context, "No device found", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        }
//    };
//    private void scan() {
//        progresscandevice.setVisibility(View.VISIBLE);
//        adapteraivalable.clear();
//
//        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
//            if (bluetoothAdapter.isDiscovering()) {
//                bluetoothAdapter.cancelDiscovery();
//            }
//            bluetoothAdapter.startDiscovery();
//            return;
//        }
//    }
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (bluetoothdevicelistner != null) {
//            unregisterReciever(bluetoothdevicelistner);
//        }
//    }

}