package com.example.sample1;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class chatadapter extends RecyclerView.Adapter<chatadapter.myviewholder> {
    ArrayList<String> stringList;
    RecyclerClickListener onItemClickRecycler;
    Context context;
    private static final String APP_NAME = "BluetoothChatApp";
    private static final UUID MY_UUID = UUID.fromString("b159fafe-e55e-11ec-8fea-0242ac120002");
    private static final String TAG = "Bluetooth";
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    BluetoothDevice[] devices;

    public chatadapter(ArrayList<String> stringList, RecyclerClickListener onItemClickRecycler) {
        this.stringList = stringList;
        this.onItemClickRecycler = onItemClickRecycler;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerchatrows, parent, false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, @SuppressLint("RecyclerView") int position) {
        //String data = stringList.get(position);
        holder.tvv_paired.setText(stringList.get(position).toString());

        // onItemClickRecycler.onItemClick(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("M", "n" + position);
                onItemClickRecycler.onItemClick(position);
//                  String id=tvv_paired.getText().toString();
//                  Log.e("bt_id","he " + id);
//                Intent intent = new Intent(view.getContext(), chatActivity.class);
//                intent.putExtra("key", " " + stringList.get(position));
//                view.getContext().startActivity(intent);
//                       ClientClass clientClass = new ClientClass(devices);
//                     clientClass.start();

            }
        });

    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    class myviewholder extends RecyclerView.ViewHolder {
        TextView tvv_paired;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            tvv_paired = itemView.findViewById(R.id.tvv_paired);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(view.getContext(), chatActivity.class);
//                    intent.putExtra("key", "value ");
//                    view.getContext().startActivity(intent);
//
//                }
//            });
        }
    }

    public class ServerClass extends Thread {
        private BluetoothServerSocket serverSocket;

        @SuppressLint("MissingPermission")
        public ServerClass() {
            try {

                //  if (ContextCompat.checkSelfPermission(context,Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                //     if(ContextCompat.checkSelfPermission(context,Manifest.permission.BLUETOOTH_CONNECT)!=PackageManager.PERMISSION_GRANTED){
                serverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(APP_NAME, MY_UUID);
                Log.e(TAG, "server socket" + serverSocket);
                //   }

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
                if (ActivityCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
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
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

                    socket.connect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}