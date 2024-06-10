package com.example.android.bluetooth_final_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.RadarChart;

import java.util.Random;

public class Home extends AppCompatActivity {



    private FrameLayout radarChartContainer;
    AppCompatButton chart1,chart2;
    ImageView imageView,bluetooth;
    TextView batterypresentage,messagetext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
            messagetext.setText(gestures);
        }
        else if (percent <= 80 && percent >= 61){
            System.out.println("Percentage is between 61% and 80%.");
            imageView.setImageDrawable(null);
            imageView.setBackgroundResource(R.drawable.battery80);
            batterypresentage.setText(String.valueOf(percent));
            messagetext.setText(gestures);
        }
        else if (percent <= 60 && percent >= 51){
            System.out.println("Percentage is between 51% and 60%.");
            imageView.setImageDrawable(null);
            imageView.setBackgroundResource(R.drawable.battery60);
            batterypresentage.setText(String.valueOf(percent));
            messagetext.setText(gestures);
        }
        else if (percent <= 50 && percent >= 36){
            System.out.println("Percentage is between 36% and 50%.");
            imageView.setImageDrawable(null);
            imageView.setBackgroundResource(R.drawable.battery50);
            batterypresentage.setText(String.valueOf(percent));
            messagetext.setText(gestures);
        }
        else if (percent <= 35 && percent >= 16){
            System.out.println("Percentage is between 16% and 35%.");
            imageView.setImageDrawable(null);
            imageView.setBackgroundResource(R.drawable.battery35);
            batterypresentage.setText(String.valueOf(percent));
            messagetext.setText(gestures);
        }
        else if (percent <= 15 && percent >= 6){
            System.out.println("Percentage is between 6% and 15%.");
            imageView.setImageDrawable(null);
            imageView.setBackgroundResource(R.drawable.battery15);
            batterypresentage.setText(String.valueOf(percent));
            messagetext.setText(gestures);
        }
        else if (percent <= 5 && percent >= 1){
            System.out.println("Percentage is between 1% and 5%.");
            imageView.setImageDrawable(null);
            imageView.setBackgroundResource(R.drawable.battery5);
            batterypresentage.setText(String.valueOf(percent));
            messagetext.setText(gestures);
        }
        else if (percent <= 0){
            System.out.println("Percentage is 0%.");
            imageView.setImageDrawable(null);
            imageView.setBackgroundResource(R.drawable.battery0);
            batterypresentage.setText(String.valueOf(percent));
            messagetext.setText(gestures);
        }
        else{
            System.out.println("Invalid percentage range.");
        }
    }
}

