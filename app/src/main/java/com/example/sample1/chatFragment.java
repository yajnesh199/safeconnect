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

import android.os.Message;
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

import com.airbnb.lottie.L;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;


public class chatFragment extends Fragment implements RecyclerClickListener {
    //    private ListView lispairedevice;
//    private ArrayAdapter<String> adapterpaired, adapteraivalable;
//    private ProgressBar progresscandevice;
    BluetoothAdapter bluetoothAdapter;
    BluetoothDevice[] bluetoothDevices;
    RecyclerView recyclerView;
    chatadapter adapter;
    Button btnBT;
    String Snd_Msg;
   // SendReceive sendReceive;
    BluetoothDevice device;
    ArrayList<String> stringList = new ArrayList<>();
    SharedPreferences sharedPreferences;
    private static final String APP_NAME = "BluetoothChatApp";
    private static final UUID MY_UUID = UUID.fromString("b159fafe-e55e-11ec-8fea-0242ac120002");
    private static final String TAG = "Bluetooth";

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
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new chatadapter(stringList, this);
//        SharedPreferences sh = getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
//        Snd_Msg = sh.getString("name", " ");
        //ServerClass1 serverClass = new ServerClass1();
        //serverClass.start();

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
            //  bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
            bluetoothDevices = new BluetoothDevice[pairedDevices.size()];
            int index = 0;
            if (pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {
//                    sharedPreferences = getActivity().getSharedPreferences("My", MODE_PRIVATE);
//                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
//                    myEdit.putInt(bluetoothDevices +"_size", bluetoothDevices.length);
                    bluetoothDevices[index] = device;
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
//        ServerClass1 serverClass1=new ServerClass1();
//        serverClass1.start();
//        ClientClass clientClass = new ClientClass(bluetoothDevices[pos]);
//        clientClass.start();
        Intent intent=new Intent(getActivity(),chatActivity.class);
        intent.putExtra("bluetoothdevice", bluetoothDevices[pos]);
        startActivity(intent);
        Log.e("tag", "pos" + pos);


    }
    private class ServerClass1 extends Thread {
        private BluetoothServerSocket serverSocket;

        public ServerClass1() {
            try {

                if (ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
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
                if (socket != null) {
//                    sendReceive = new SendReceive(socket);
//                    sendReceive.start();
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
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    socket = device.createRfcommSocketToServiceRecord(MY_UUID);
                    Log.e(TAG, "setting up " + device);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    socket.connect();
                    Log.e("tag", "connect" + socket);
//                    sendReceive = new SendReceive(socket);
//                    sendReceive.start();
                }
            } catch (IOException e) {
                e.printStackTrace();

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
            Log.e("tag", "h" + inputStream);
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
                    // handler.obtainMessage(STATE_MESSAGE_RECEIVED, bytes, -1, buffer).sendToTarget();
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



}

