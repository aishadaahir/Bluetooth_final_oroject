package com.example.android.bluetooth_final_project;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class DataReceiveActivity extends AppCompatActivity {
    private static final String TAG = "DataReceiveActivity";
    private static final int BUFFER_SIZE = 1024;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mBluetoothDevice;
    private BluetoothSocket mBluetoothSocket;
    private InputStream mInputStream;
    private OutputStream mOutputStream;

    private TextView textViewReceivedData;
    private TextView textViewDataDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_receive);

        textViewReceivedData = findViewById(R.id.textViewReceivedData);
        textViewDataDisplay = findViewById(R.id.textViewDataDisplay);

        // Get the default Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // Get the paired Bluetooth device (assuming it's already paired)
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        for (BluetoothDevice device : pairedDevices) {
            if (device.getName().equals("HC-06")) {
                mBluetoothDevice = device;
                break;
            }
        }

        // Connect to the Bluetooth device
        try {
            mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(MY_UUID);
            mBluetoothSocket.connect();
            mInputStream = mBluetoothSocket.getInputStream();
            mOutputStream = mBluetoothSocket.getOutputStream();
        } catch (IOException e) {
            Log.e(TAG, "Error connecting to Bluetooth device: " + e.getMessage());
            return;
        }

        // Start a new thread to receive data
        new DataReceiveThread().start();
    }

    private class DataReceiveThread extends Thread {
        @Override
        public void run() {
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytes;

            while (true) {
                try {
                    // Read data from the input stream
                    bytes = mInputStream.read(buffer);
                    if (bytes > 0) {
                        // Process the received data
                        String data = new String(buffer, 0, bytes);
                        Log.d(TAG, "Received data: " + data);

                        // Update the UI thread with the received data
                        new Handler(Looper.getMainLooper()).post(() -> {
                            textViewDataDisplay.append(data + "\n");
                        });
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Error reading from Bluetooth socket: " + e.getMessage());
                    break;
                }
            }
        }
    }
}

