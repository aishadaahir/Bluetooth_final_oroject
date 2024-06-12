package com.example.android.bluetooth_final_project;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class DataReceiveActivity extends AppCompatActivity {
    private static final String TAG = "DataReceiveActivity";
    private static final int BUFFER_SIZE = 1024;
    static final UUID mUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mBluetoothDevice;
    private BluetoothSocket mBluetoothSocket;
    private InputStream mInputStream;
    private OutputStream mOutputStream;

    private TextView textViewReceivedData;
    private TextView textViewDataDisplay;
    private static final int REQUEST_BLUETOOTH_CONNECT = 1;
    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_receive);
        textViewDataDisplay=findViewById(R.id.textViewDataDisplay);
        Log.e("devicename","innn");

//        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
//        if (ActivityCompat.checkSelfPermission(DataReceiveActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
////            ActivityCompat.requestPermissions(DataReceiveActivity.this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, REQUEST_BLUETOOTH_CONNECT);
//        }
////        if (androidx.core.app.ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
////            // TODO: Consider calling
////            //    ActivityCompat#requestPermissions
////            // here to request the missing permissions, and then overriding
////            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
////            //                                          int[] grantResults)
////            // to handle the case where the user grants the permission. See the documentation
////            // for ActivityCompat#requestPermissions for more details.
////            Log.e("devicename","return");
////            return;
////        }
//        System.out.println(btAdapter.getBondedDevices());
//        Log.e("devicename", btAdapter.getBondedDevices().toString());
//
//        BluetoothDevice hc05 = btAdapter.getRemoteDevice("00:23:02:34:B8:9D");
//        System.out.println(hc05.getName());
//        Log.e("devicename",hc05.getName());
//
//        BluetoothSocket btSocket = null;
//        int counter = 0;
//        do {
//            Log.e("devicename", String.valueOf(counter));
//            try {
//                btSocket = hc05.createRfcommSocketToServiceRecord(mUUID);
//                Log.e("devicename", String.valueOf(btSocket));
//                btSocket.connect();
//                Log.e("devicename", String.valueOf(btSocket.isConnected()));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            counter++;
//        } while (!Objects.requireNonNull(btSocket).isConnected() && counter < 3);
//
//        InputStream inputStream = null;
//        try {
//            inputStream = btSocket.getInputStream();
//            inputStream.skip(inputStream.available());
//            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                textViewDataDisplay.append(line + "\n");
////                Log.e("data", line);
//                String[] data = line.split(":");
////                Log.e("datain", Arrays.toString(data));
////                Log.e("datain33", data[0]);
//                if(Objects.equals(data[0], "currentGesture")){
//                    Log.e("datain33", data[1]);
//                }
//
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }




        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (ActivityCompat.checkSelfPermission(DataReceiveActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
        }
        Log.e("devicename", mBluetoothAdapter.getBondedDevices().toString());

//        BluetoothDevice hc05 = btAdapter.getRemoteDevice("00:21:13:02:B6:5B");
//        System.out.println(hc05.getName());


        textViewReceivedData = findViewById(R.id.textViewReceivedData);
        textViewDataDisplay = findViewById(R.id.textViewDataDisplay);

        // Get the default Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        for (BluetoothDevice device : pairedDevices) {
            Log.e("devicename",device.getName());
            if (device.getName().equals("HC-05")) {
                mBluetoothDevice = device;
                Log.e("devicename",device.getAddress());
                Log.e("devicename",device.getName());
                break;
            }
        }

        // Connect to the Bluetooth device
        int counter = 0;
        do {
            try {
                mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(mUUID);
                mBluetoothSocket.connect();
                mInputStream = mBluetoothSocket.getInputStream();
                mInputStream.skip(mInputStream.available());
            } catch (IOException e) {
                Log.e(TAG, "Error connecting to Bluetooth device: " + e.getMessage());
                Log.e("devicename", "Error connecting to Bluetooth device: " + e.getMessage());
                return;
            }
            counter++;
        } while (!mBluetoothSocket.isConnected() && counter < 3);



        // Start a new thread to receive data
        new DataReceiveThread().start();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_BLUETOOTH_CONNECT) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with your Bluetooth-related code
                // ...
            } else {
                Log.e("devicename", "denied");

            }
        }
    }

        private class DataReceiveThread extends Thread {
        @Override
        public void run() {
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytes;
            Log.e("buffer", Arrays.toString(buffer));
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(mInputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    // Update the UI thread with the received data
                    String finalLine = line;
//                    new Handler(Looper.getMainLooper()).post(() -> {
//                        textViewDataDisplay.append(finalLine + "\n");
//                    });

//                    Log.e("data", line);
                    String[] data = line.split(":");
//                Log.e("datain", Arrays.toString(data));
//                Log.e("datain33", data[0]);
                    if(Objects.equals(data[0], "currentGesture")){
                        Log.e("datain33", data[1]);
                        new Handler(Looper.getMainLooper()).post(() -> {
                            textViewDataDisplay.append(data[1] + "\n");
                        });
                    }

                }
//                textViewDataDisplay.append("----------------------" + "\n");

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
//
//            while (true) {
//                try {
//                    // Read data from the input stream
//                    bytes = mInputStream.read(buffer);
//                    if (bytes > 0) {
//                        // Process the received data
//                        String data = new String(buffer, 0, bytes);
//                        Log.d(TAG, "Received data: " + data);
//
//                        // Update the UI thread with the received data
//                        new Handler(Looper.getMainLooper()).post(() -> {
//                            textViewDataDisplay.append(data + "\n");
//                        });
//                    }
//                } catch (IOException e) {
//                    Log.e(TAG, "Error reading from Bluetooth socket: " + e.getMessage());
//                    break;
//                }
//            }
        }
    }
}

