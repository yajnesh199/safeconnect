package com.example.sample1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

public class chatutils {
    private final UUID APP_UUID = UUID.fromString("c413e31a-3766-48f9-8a20-e9f5b7b77b99");
    private final String APP_NAME = "Bluetooth";
    private static final String TAG = "Bluetooth";
    private ConnectThread connectThread;
    private ConnectedThread connectedThread;
    private BluetoothAdapter bluetoothAdapter;
    private Context context;
    private final Handler handler;
    private AcceptThread acceptThread;
    public static final int STATE_NONE = 0;
    public static final int STATE_LISTEN = 1;
    public static final int STATE_CONNECTING = 2;
    public static final int STATE_CONNECTED = 3;
    private int state;

    public chatutils(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
        state = STATE_NONE;
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public synchronized int getState() {
        return state;
    }

    public synchronized void setState(int state) {
        this.state = state;
        handler.obtainMessage(ListBluetooth.MESSAGE_STATE_CHANGED, state, -1).sendToTarget();
    }

    private synchronized void start() {
        Log.e(TAG, "start");
        if (connectThread != null) {
            connectThread.cancel();
            connectThread = null;
        }
        if (acceptThread == null) {
            acceptThread = new AcceptThread();
            acceptThread.start();
        }
        if (connectedThread != null) {
            connectedThread.cancel();
            connectedThread = null;
        }
        setState(STATE_LISTEN);
    }

    public synchronized void stop() {
        Log.e(TAG, "stop");
        if (connectThread != null) {
            connectThread.cancel();
            connectThread = null;
        }
        if (acceptThread != null) {
            acceptThread.cancel();
            acceptThread = null;
        }
        if (connectedThread != null) {
            connectedThread.cancel();
            connectedThread = null;
        }
        setState(STATE_NONE);
    }

    public void connect(BluetoothDevice device) {

        // // Cancel any thread attempting to make a connection
        if (state == STATE_CONNECTING) {
            connectThread.cancel();
            connectThread = null;
        }

        // Cancel any thread currently running a connection
        if (connectedThread != null) {
            connectedThread.cancel();
            connectedThread = null;
        }
        setState(STATE_CONNECTING);
        //connect with the given device
        connectThread = new ConnectThread(device);
        connectThread.start();
        Log.e(TAG, "connect to: " + device);

    }

    //inistiate all sending messages
    public void write(byte[] buffer) {
        ConnectedThread connThread;
        synchronized (this) {
            if (state != STATE_CONNECTED) {
                return;
            }
            connThread = connectedThread;
        }
        connThread.write(buffer);
    }

    // thread which accepts the  incoming connections
    private class AcceptThread extends Thread {
        private BluetoothServerSocket serversocket;
        public AcceptThread() {
            BluetoothServerSocket tmp = null;
          //  mSocketType = secure ? "Secure" : "Insecure";
            try {
//                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
//                    //       tmp = bluetoothAdapter.listenUsingRfcommWithServiceRecord(APP_NAME, APP_UUID);
//                    //    Log.e(TAG, "setting up server" + APP_UUID);
//                    return;
//                }
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    tmp = bluetoothAdapter.listenUsingRfcommWithServiceRecord(APP_NAME, APP_UUID);
                    Log.e(TAG, "setting up server" + APP_UUID);
                    return ;
                }

            } catch (IOException e) {
                Log.e("Acept Constructor", e.toString());
            }
            serversocket = tmp;
        }
        //connecting the seversocket
        public void run() {
            Log.e(TAG, "BEGIN mAcceptThread" + this);
            BluetoothSocket socket = null;
            Log.e(TAG, "run accept thread");
            try {
                Log.e(TAG, "run server socket start");
                socket = serversocket.accept(3);
                Log.e(TAG, "run server socket accepted");
            } catch (IOException e) {
                Log.e("Acept Run", e.toString());
                try {
                    serversocket.close();
                } catch (IOException e1) {
                    Log.e("Acept and close", e.toString());
                }
            }
            if (socket != null) {
                switch (state) {
                    case STATE_LISTEN:
                    case STATE_CONNECTING:
                        connected(socket, socket.getRemoteDevice());
                        //    Log.e(TAG, "c" +serversocket);
                        break;
                    case STATE_NONE:
                    case STATE_CONNECTED:
                        try {
                            socket.close();
                        } catch (IOException e) {
                            Log.e("Close Socket", String.valueOf(e));
                        }
                        break;
                }
            }
            //   }
        }

        public void cancel() {
            try {
                serversocket.close();
            } catch (IOException e) {
                Log.e("close serversocket", e.toString());
            }
        }
    }

    //thread which makes  outgoing connections with device
    private class ConnectThread extends Thread {
        private BluetoothSocket socket;
        private BluetoothDevice device;

        public ConnectThread(BluetoothDevice device) {
            this.device = device;
            BluetoothSocket tmp = null;
            try {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    tmp = device.createRfcommSocketToServiceRecord(APP_UUID);
                    Log.e(TAG, "run server  " + tmp);
                }
            } catch (IOException e) {
                Log.e("connect Constructor", e.toString());
            }
            socket = tmp;
            state = STATE_CONNECTING;
            Log.e(TAG, "run temp " + socket);
        }

        public void run() {
//            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
//                return;
//            }
//            bluetoothAdapter.cancelDiscovery();
            try {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    try {
                        socket = (BluetoothSocket) device.getClass().getMethod("createRfcommSocket", new Class[]{int.class}).invoke(device, 2);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    socket.connect();
                    //  socket.connect();
                    Log.e(TAG, "run server socket " + socket);
                }

            } catch (IOException e) {
                Log.e("connect Run", e.toString());
                try {
                    socket.close();
                } catch (IOException el) {
                    Log.e("connect close socket", e.toString());
                }
                connectionfailed();
                return;
            }
            // Reset the ConnectThread
            synchronized (chatutils.this) {
                connectThread = null;
            }
            // Start the connected thread
            connected(socket, device);
            Log.e(TAG, "SD" + socket + device);
        }

        public void cancel() {
            try {
                socket.close();
            } catch (IOException e) {
                Log.e("cancel", e.toString());
            }
        }
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket socket;
        private final InputStream inputStream;
        private final OutputStream outputStream;


        public ConnectedThread(BluetoothSocket socket) {
            this.socket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {

            }
            inputStream = tmpIn;
            outputStream = tmpOut;
        }

        //listen to incomming stream
        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;
         //   while (true) {
                try {
                    bytes = inputStream.read(buffer);
                    Log.e(TAG, " read bytes " + bytes);
                    handler.obtainMessage(ListBluetooth.MESSAGE_READ, bytes, -1, buffer).sendToTarget();
                } catch (IOException e) {
                    Log.e(TAG, "Input stream was disconnected");
                    connectionLost();
                    //  e.printStackTrace();
                }
           // }
        }
        //brings in buffer sent to device
        public void write(byte[] buffer) {
            try {
                outputStream.write(buffer);
                Log.e(TAG," write buffer " + buffer);
                handler.obtainMessage(ListBluetooth.MESSAGE_WRITE, -1, -1, buffer).sendToTarget();
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }

        public void cancel() {
            try {
                socket.close();
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }
    }

    private void connectionLost() {
        Message message = handler.obtainMessage(ListBluetooth.MESSAGE_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString(ListBluetooth.TOAST, "Connection lost");
        message.setData(bundle);
        handler.sendMessage(message);
        chatutils.this.start();
    }

    private synchronized void connectionfailed() {
        Message message = handler.obtainMessage(ListBluetooth.MESSAGE_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString(ListBluetooth.TOAST, "cant connect to device");
        message.setData(bundle);
        handler.sendMessage(message);
        chatutils.this.start();
    }
    private synchronized void connected(BluetoothSocket socket, BluetoothDevice device) {
        Log.e(TAG, "connected");
        if (connectThread != null) {
            connectThread.cancel();
            connectThread = null;
        }
        if (connectedThread != null) {
            connectedThread.cancel();
            connectedThread = null;
        }
        connectedThread = new ConnectedThread(socket);
        connectedThread.start();

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

        }
        Message message = handler.obtainMessage(ListBluetooth.MESSAGE_DEVICE_NAME);
        Bundle bundle = new Bundle();
        bundle.putString(ListBluetooth.DEVICE_NAME, device.getName());
        message.setData(bundle);
        handler.sendMessage(message);
        setState(STATE_CONNECTED);
    }

}
