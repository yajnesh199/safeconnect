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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;


public class pairedFragment extends Fragment {
    private ListView lispairedevice, listavialable;
    private ArrayAdapter<String> adapterpaired, adapteraivalable;
    private BluetoothAdapter bluetoothAdapter;
    RecyclerView recyclerView;
    recycleadapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //  return inflater.inflate(R.layout.fragment_chat, container, false);

        final View view = inflater.inflate(R.layout.fragment_paired, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        scan();
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recviewpaired);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        ListView listView = (ListView) getActivity().findViewById(R.id.list_available_device);
//        adapteraivalable = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
//        listView.setAdapter(adapteraivalable);


//        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
//            Set<BluetoothDevice> pairedDevice = bluetoothAdapter.getBondedDevices();
//            if (pairedDevice != null && pairedDevice.size() > 0) {
//                for (BluetoothDevice device : pairedDevice) {
//                    adapteraivalable.add(device.getName() + "\n" + device.getAddress());
//                    Log.e("tag", "name" + device.getName());
//                }
//
//            }

        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        getActivity().registerReceiver(bluetoothdevicelistner, intentFilter);
        IntentFilter intentFilter1 = new IntentFilter(bluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        getActivity().registerReceiver(bluetoothdevicelistner, intentFilter1);
    }

    private BroadcastReceiver bluetoothdevicelistner = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

                    if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                        ArrayList<String> stringList = new ArrayList<>();
                        stringList.add(device.getName() + "\n" + device.getAddress());
                        Log.e("BT", "device" + device.getName());
                        Log.e("BT", "address" + device.getAddress());
                        Log.e("BT", "Stringlist" + stringList);
                        adapter = new recycleadapter(getActivity(), stringList);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }

                } else if (bluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                    if (adapteraivalable.getCount() == 0) {
                        Toast.makeText(context, "No device found", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    };

//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        getActivity().getMenuInflater().inflate(R.menu.menu_scan, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_scan:
//                //Toast.makeText(this, "Scan devices clicked", Toast.LENGTH_SHORT).show();
//                scan();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bluetoothdevicelistner != null) {
            getActivity().unregisterReceiver(bluetoothdevicelistner);
        }
    }

    public void scan() {

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            if (bluetoothAdapter.isDiscovering()) {
                bluetoothAdapter.cancelDiscovery();
                Log.e("h", "scan");
            }
            bluetoothAdapter.startDiscovery();
            Log.e("h", "scan");
            return;
        }
    }


}