package com.example.sample1;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;


public class chatFragment extends Fragment {
//    private ListView lispairedevice;
//    private ArrayAdapter<String> adapterpaired, adapteraivalable;
//    private ProgressBar progresscandevice;
    private BluetoothAdapter bluetoothAdapter;
    BluetoothDevice[] bluetoothDevices;
    RecyclerView recyclerView;
   chatadapter adapter;
    ArrayList<String> stringList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        ListView listView = (ListView) view.findViewById(R.id.list_paired_device);
//        adapterpaired = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
//        listView.setAdapter(adapterpaired);
//        recyclerView = getActivity().findViewById(R.id.recview);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recview);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new chatadapter(getActivity(), stringList);


        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
//            Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
//            String[] strings = new String[devices.size()];
//            ArrayList<String> stringlist= new ArrayList<>(Arrays.asList(strings));
//            bluetoothDevices = new BluetoothDevice[devices.size()];
//            int index = 0;
//            if (devices.size() > 0) {
//                for (BluetoothDevice device : devices) {
//                    bluetoothDevices[index] = device;
//                    strings[index] = device.getName();
//                    index++;
//                }
//                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strings);
//                   listView.setAdapter(arrayAdapter);
//                adapter = new recycleadapter(getActivity(), stringlist);
//                recyclerView.setAdapter(adapter);
//            }
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
            if (pairedDevices != null && pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {
                    stringList.add(device.getName() + "\n" + device.getAddress());
                    Log.e("BT","device" + device.getName());
                }
//                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
//                 listView.setAdapter(arrayAdapter
                recyclerView.setAdapter(adapter);
            }
            adapter.notifyDataSetChanged();
        }

    }
}

