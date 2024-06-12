package com.example.android.bluetooth_final_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.RadarChart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class Home extends AppCompatActivity {
    private static final String TAG = "Home";
    static final UUID mUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private BluetoothDevice mBluetoothDevice;
    private BluetoothSocket mBluetoothSocket;
    private InputStream mInputStream;

    private final static int REQUEST_ENABLE_BT = 1;
    private FrameLayout radarChartContainer;
    AppCompatButton chart1,chart2;
    ImageView imageView,bluetooth;
    TextView batterypresentage,messagetext;
    BluetoothAdapter mBluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Log.e("devicename","innn");

        radarChartContainer = findViewById(R.id.chart_container);
        chart1 = findViewById(R.id.ch1);
        chart2 = findViewById(R.id.ch2);

        messagetext = findViewById(R.id.messagetext);
        batterypresentage = findViewById(R.id.batterypresentage);
        imageView = findViewById(R.id.imageView);
        bluetooth = findViewById(R.id.bluetooth);

        refreshdata();


        // Create an instance of the RadarChartFragment
        RedarChart radarChart = new RedarChart();

        // Create an instance of the SensorChartFragment
        SensorChart sensorChart = new SensorChart();

        // Replace the content of the FrameLayout with the RadarChartActivity
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.chart_container, radarChart)
                .commit();

        chart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.chart_container, radarChart)
                        .commit();
                refreshdata();
            }
        });
        chart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.chart_container, sensorChart)
                        .commit();
                refreshdata();
            }
        });

        bluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });


        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        //turn on bluetooth if turned off
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            if (androidx.core.app.ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

            }
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            Toast.makeText(getApplicationContext(), getString(R.string.sBTturON), Toast.LENGTH_SHORT).show();

        }


        connecttoarduino();




    }

    public void connecttoarduino(){
        if (ActivityCompat.checkSelfPermission(Home.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
        }
        Log.e("devicename", mBluetoothAdapter.getBondedDevices().toString());


        // Get the default Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        for (BluetoothDevice device : pairedDevices) {
//            Log.e("devicename",device.getName());
            if (device.getName().equals("HC-05")) {
                mBluetoothDevice = device;
                Log.e("devicewhole", String.valueOf(device));
//                Log.e("devicename",device.getName());
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


    private class DataReceiveThread extends Thread {
        @Override
        public void run() {
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
                            messagetext.setText(data[1] );
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

    public void refreshdata(){
        Random rand = new Random();

        int percent = rand.nextInt(101); // Generate a random integer between 0 and 100 (inclusive)
        String[] handGestures = {"reverse peace sign", "peace sign", "fist", "thumbs up", "costume"};

        String gestures= handGestures[rand.nextInt(handGestures.length)];


        System.out.println("Random percentage: " + percent);

        if(percent <= 100 && percent >= 81){
            System.out.println("Percentage is between 81% and 100%.");
            imageView.setImageDrawable(null);
            imageView.setBackgroundResource(R.drawable.battery100);
            batterypresentage.setText(String.valueOf(percent));
//            messagetext.setText(gestures);
        }
        else if (percent <= 80 && percent >= 61){
            System.out.println("Percentage is between 61% and 80%.");
            imageView.setImageDrawable(null);
            imageView.setBackgroundResource(R.drawable.battery80);
            batterypresentage.setText(String.valueOf(percent));
//            messagetext.setText(gestures);
        }
        else if (percent <= 60 && percent >= 51){
            System.out.println("Percentage is between 51% and 60%.");
            imageView.setImageDrawable(null);
            imageView.setBackgroundResource(R.drawable.battery60);
            batterypresentage.setText(String.valueOf(percent));
//            messagetext.setText(gestures);
        }
        else if (percent <= 50 && percent >= 36){
            System.out.println("Percentage is between 36% and 50%.");
            imageView.setImageDrawable(null);
            imageView.setBackgroundResource(R.drawable.battery50);
            batterypresentage.setText(String.valueOf(percent));
//            messagetext.setText(gestures);
        }
        else if (percent <= 35 && percent >= 16){
            System.out.println("Percentage is between 16% and 35%.");
            imageView.setImageDrawable(null);
            imageView.setBackgroundResource(R.drawable.battery35);
            batterypresentage.setText(String.valueOf(percent));
//            messagetext.setText(gestures);
        }
        else if (percent <= 15 && percent >= 6){
            System.out.println("Percentage is between 6% and 15%.");
            imageView.setImageDrawable(null);
            imageView.setBackgroundResource(R.drawable.battery15);
            batterypresentage.setText(String.valueOf(percent));
//            messagetext.setText(gestures);
        }
        else if (percent <= 5 && percent >= 1){
            System.out.println("Percentage is between 1% and 5%.");
            imageView.setImageDrawable(null);
            imageView.setBackgroundResource(R.drawable.battery5);
            batterypresentage.setText(String.valueOf(percent));
//            messagetext.setText(gestures);
        }
        else if (percent <= 0){
            System.out.println("Percentage is 0%.");
            imageView.setImageDrawable(null);
            imageView.setBackgroundResource(R.drawable.battery0);
            batterypresentage.setText(String.valueOf(percent));
//            messagetext.setText(gestures);
        }
        else{
            System.out.println("Invalid percentage range.");
        }
    }
}

