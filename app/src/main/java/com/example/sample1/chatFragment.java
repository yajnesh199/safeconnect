package com.example.sample1;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;


public class chatFragment extends Fragment implements RecyclerClickListener {
    //    private ListView lispairedevice;
//    private ArrayAdapter<String> adapterpaired, adapteraivalable;
//    private ProgressBar progresscandevice;
    private BluetoothAdapter bluetoothAdapter;
    BluetoothDevice[] bluetoothDevices;
    RecyclerView recyclerView;
    chatadapter adapter;
    Button btnBT;
    BluetoothDevice device;
    ArrayList<String> stringList = new ArrayList<>();
    SharedPreferences sharedPreferences;
    private static final String APP_NAME = "BluetoothChatApp";
    private static final UUID MY_UUID = UUID.fromString("b159fafe-e55e-11ec-8fea-0242ac120002");
    private static final String TAG = "Bluetooth";
//    BluetoothAdapter bluetoothAdapter;
//    BluetoothDevice[] bluetoothDevices;

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
        // btnBT=view.findViewById(R.id.btnBT);
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new chatadapter(stringList, this);


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
//                adapter = new chatadapter(getActivity(), stringList);
//                recyclerView.setAdapter(adapter);
//            }
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
            bluetoothDevices = new BluetoothDevice[pairedDevices.size()];
            if (pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {
//                    sharedPreferences = getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
//                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
//                    myEdit.commit();
                    //  myEdit.putString("bluetooth",bluetoothDevices);
                    //     myEdit.putInt(bluetoothDevices +"_size", bluetoothDevices.length);
                    stringList.add(device.getName() + "\n" + device.getAddress());
                    //  stringList.add(device.getAddress());
                    Log.e("BT", "device" + bluetoothDevices);

                }
//                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
//                 listView.setAdapter(arrayAdapter)
                recyclerView.setAdapter(adapter);

            }
            adapter.notifyDataSetChanged();


        }

//        btnBT.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ServerClass serverClass=new ServerClass();
//                serverClass.start();
//            }
//        });
    }

    @Override
    public void onItemClick(int pos) {
        ServerClass1 serverClass = new ServerClass1();
        serverClass.start();
        ClientClass clientClass = new ClientClass(bluetoothDevices[pos]);
        clientClass.start();
        Log.e("tag", "pos" + pos);


    }


    private class ServerClass1 extends Thread {
        private BluetoothServerSocket serverSocket;

        public ServerClass1() {
            try {

                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
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
                break;
            }
        }
    }

    private class ClientClass extends Thread {
        private BluetoothDevice device;
        private BluetoothSocket socket;

        public ClientClass(BluetoothDevice device1) {
            device = device1;
            try {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    if (device != null) {
                        socket = device.createRfcommSocketToServiceRecord(MY_UUID);
                        Log.e(TAG, "setting up " + device);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

                    socket.connect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

