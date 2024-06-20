package com.example.android.bluetooth_final_project;

import static java.lang.Math.round;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class SensorChart extends Fragment implements SeekBar.OnSeekBarChangeListener {

    private LineChart chart;
    private SeekBar seekBarX;
    private TextView tvX;
    protected Typeface tfRegular;
    protected Typeface tfLight;
    private List<String> xvalue = new ArrayList<>();;
    private List<Double> datavalue = new ArrayList<>();
    XAxis xAxis;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sensor_chart, container, false);

        tfRegular = Typeface.createFromAsset(requireContext().getAssets(), "OpenSans-Regular.ttf");
        tfLight = Typeface.createFromAsset(requireContext().getAssets(), "OpenSans-Light.ttf");
        tvX = view.findViewById(R.id.tvXMax);
        seekBarX = view.findViewById(R.id.seekBar1);
        seekBarX.setOnSeekBarChangeListener(this);

        chart = view.findViewById(R.id.chart1);

        // no description text
        chart.getDescription().setEnabled(false);

        // enable touch gestures
        chart.setTouchEnabled(true);

        chart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setDrawGridBackground(false);
        chart.setHighlightPerDragEnabled(true);

        // set an alternative background color
        chart.setBackgroundColor(Color.WHITE);
        chart.setViewPortOffsets(0f, 0f, 0f, 0f);

        // add data
//        seekBarX.setProgress(10);

//        datavalue = Arrays.asList(12.0,5.0,27.0);
//        datavalue.addAll(Arrays.asList(0.0));
        datavalue = PreferenceHelper.getDataValue(requireContext());
        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();
        l.setEnabled(false);

        int count=datavalue.size();
        for (int i = 1; i <= count; i++) {
            xvalue.add(String.valueOf(i));
        }
        Log.e("xvaluestring", xvalue.toString());
//        xvalue = Arrays.asList("1","2","3","4","5","6","7","8","9","10");

        xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
        xAxis.setTypeface(tfLight);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(true);
        xAxis.setTextColor(Color.rgb(255, 192, 56));
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(1f); // one hour
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xvalue));

//        xAxis.setValueFormatter(new IAxisValueFormatter() {
//
//            private final SimpleDateFormat mFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
//
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//
//                long millis = TimeUnit.HOURS.toMillis((long) value);
//                return mFormat.format(new Date(millis));
//            }
//        });


        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        leftAxis.setTypeface(tfLight);
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(80);
        leftAxis.setYOffset(-9f);
        leftAxis.setTextColor(Color.rgb(120, 30, 56));

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);


        firstsetData(datavalue.size(), 51);

        return view;
    }

    private void firstsetData(int count, float range) {

        ArrayList<Entry> values = new ArrayList<>();


        for (float x = 0; x < count; x++) {
            float y =datavalue.get((int) x).floatValue();
            values.add(new Entry(x+1, y));
        }
        Log.e("sensordata", String.valueOf(values));

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(values, "DataSet 1");
        set1.setAxisDependency(AxisDependency.LEFT);
        set1.setColor(Color.rgb(130, 91, 234));
        set1.setValueTextColor(Color.rgb(130, 91, 234));
        set1.setLineWidth(1.5f);
        set1.setDrawCircles(false);
        set1.setDrawValues(false);
        set1.setFillAlpha(65);
        set1.setFillColor(ColorTemplate.getHoloBlue());
        set1.setHighLightColor(Color.rgb(130, 91, 234));
        set1.setDrawCircleHole(false);

        // create a data object with the data sets
        LineData data = new LineData(set1);
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(9f);

        // set data
        chart.setData(data);
    }


    private void setData(int count, float range) {

        ArrayList<Entry> values = new ArrayList<>();


        // increment by 1 hour
        for (float x = 0; x < count; x++) {

            float y = round((Math.random() * range) + 1);
//            float y = round(getRandom(5, 50));
            values.add(new Entry(x, y)); // add one entry per hour
        }
        Log.e("sensordata", String.valueOf(values));

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(values, "DataSet 1");
        set1.setAxisDependency(AxisDependency.LEFT);
        set1.setColor(Color.rgb(130, 91, 234));
        set1.setValueTextColor(Color.rgb(130, 91, 234));
        set1.setLineWidth(1.5f);
        set1.setDrawCircles(false);
        set1.setDrawValues(false);
        set1.setFillAlpha(65);
        set1.setFillColor(ColorTemplate.getHoloBlue());
        set1.setHighLightColor(Color.rgb(130, 91, 234));
        set1.setDrawCircleHole(false);

        // create a data object with the data sets
        LineData data = new LineData(set1);
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(9f);

        // set data
        chart.setData(data);
    }

    protected float getRandom(float range, float start) {
        return (float) (Math.random() * range) + start;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        tvX.setText(String.valueOf(seekBarX.getProgress()));
        Double y = (double) round((Math.random() * 51) + 1);
        Log.e("ydouble", String.valueOf(y));
        if (datavalue.size() >= 20) {
            datavalue.remove(0);
        }
        datavalue.add(y);
        PreferenceHelper.saveDataValue(requireContext(), datavalue);

        xvalue.clear();
        int count=datavalue.size();
        for (int i = 1; i <= count; i++) {
            xvalue.add(String.valueOf(i));
        }
        Log.e("xvaluestring", xvalue.toString());

        xAxis.setValueFormatter(new IndexAxisValueFormatter(xvalue));

        firstsetData(datavalue.size(), 51);

//        setData(30, 51);

        // redraw
        chart.invalidate();
    }


    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}
}