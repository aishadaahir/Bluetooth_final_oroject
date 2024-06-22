package com.example.android.bluetooth_final_project;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


import com.example.android.bluetooth_final_project.custom.RadarMarkerView;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;

import com.github.mikephil.charting.formatter.IAxisValueFormatter;


public class RedarChart extends Fragment {

    private RadarChart chart;

    private static final int PERMISSION_STORAGE = 0;

    protected Typeface tfRegular;
    protected Typeface tfLight;

    PreferenceHelper preferenceHelper;
    Timer timer;
    ArrayList<RadarEntry> entries1;
    ArrayList<RadarEntry> entries2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public void setData(ArrayList<RadarEntry> entries1,ArrayList<RadarEntry> entries2) {
//    public void setData() {

//        float mul = 70;
//        float min = 30;
//        int cnt = 5;
//
//        ArrayList<RadarEntry> entries1 = new ArrayList<>();
//        ArrayList<RadarEntry> entries2 = new ArrayList<>();
//
//        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
//        // the chart.
//        for (int i = 0; i < cnt; i++) {
//            float val1 = (float) (Math.random() * mul) + min;
//            entries1.add(new RadarEntry(val1));
//
//        }
//
//        for (int i = 0; i < cnt; i++) {
//            float val2 = (float) (Math.random() * mul) + min;
//            entries2.add(new RadarEntry(val2));
//        }

        Log.e("arrays", String.valueOf(entries1));
        Log.e("arrays", String.valueOf(entries2));
        RadarDataSet set1 = new RadarDataSet(entries1, "Fingers");
        set1.setColor(Color.rgb(198, 190, 229));
        set1.setFillColor(Color.rgb(198, 190, 229));
        set1.setDrawFilled(true);
        set1.setFillAlpha(180);
        set1.setLineWidth(2f);
        set1.setDrawHighlightCircleEnabled(true);
        set1.setDrawHighlightIndicators(false);

        RadarDataSet set2 = new RadarDataSet(entries2, "wrist");
        set2.setColor(Color.rgb(146, 243, 252));
        set2.setFillColor(Color.rgb(146, 243, 252));
        set2.setDrawFilled(true);
        set2.setFillAlpha(180);
        set2.setLineWidth(2f);
        set2.setDrawHighlightCircleEnabled(true);
        set2.setDrawHighlightIndicators(false);

        ArrayList<IRadarDataSet> sets = new ArrayList<>();
        sets.add(set1);
        sets.add(set2);

        RadarData data = new RadarData(sets);
        data.setValueTypeface(tfLight);
        data.setValueTextSize(8f);
        data.setDrawValues(false);
        data.setValueTextColor(Color.WHITE);

        chart.setData(data);
        chart.invalidate();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_redar_chart, container, false);

        View view = inflater.inflate(R.layout.fragment_redar_chart, container, false);

        preferenceHelper = new PreferenceHelper(getContext());

        tfRegular = Typeface.createFromAsset(requireContext().getAssets(), "OpenSans-Regular.ttf");
        tfLight = Typeface.createFromAsset(requireContext().getAssets(), "OpenSans-Light.ttf");

        chart = view.findViewById(R.id.chart1);
//        chart.setBackgroundColor(Color.rgb(201, 174, 226));
        chart.setBackgroundColor(Color.WHITE);

        chart.getDescription().setEnabled(false);

        chart.setWebLineWidth(1f);
        chart.setWebColor(Color.rgb(130, 91, 234));
        chart.setWebLineWidthInner(1f);
        chart.setWebColorInner(Color.rgb(130, 91, 234));
        chart.setWebAlpha(100);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MarkerView mv = new RadarMarkerView(getContext(), R.layout.radar_markerview);
        mv.setChartView(chart); // For bounds control
        chart.setMarker(mv); // Set the marker to the chart

        entries1 = preferenceHelper.getEntries1();
        entries2 = preferenceHelper.getEntries2();
        Log.e("entries", String.valueOf(preferenceHelper.getEntries1()));
        Log.e("entries1", String.valueOf(entries1));
        Log.e("entries2", String.valueOf(entries2));
        setData(entries1,entries2);
//        setData();

//        chart.animateXY(1400, 1400, Easing.EaseInOutQuad);
        chart.animateXY(1400, 1400);

        XAxis xAxis = chart.getXAxis();
        xAxis.setTypeface(tfLight);
        xAxis.setTextSize(9f);
        xAxis.setYOffset(0f);
        xAxis.setXOffset(0f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {

//            private final String[] mActivities = new String[]{"little finger", "pinkie", "ring finger", "middle finger", "index finger"};
            private final String[] mActivities = new String[]{"little finger","index finger", "middle finger", "ring finger", "pinkie" };

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mActivities[(int) value % mActivities.length];
            }
        });
        xAxis.setTextColor(Color.rgb(130, 91, 234));

        YAxis yAxis = chart.getYAxis();
        yAxis.setTypeface(tfLight);
        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(9f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(1f);
        yAxis.setDrawLabels(false);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setTypeface(tfLight);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
        l.setTextColor(Color.rgb(130, 91, 234));

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Fetch new data and update the chart
                updateChartData();
            }
        }, 0, 1000); // Run every 2 seconds
        return view;
    }

    private void updateChartData(){

        entries1 = preferenceHelper.getEntries1();
        entries2 = preferenceHelper.getEntries2();
        Log.e("entries", String.valueOf(preferenceHelper.getEntries1()));
        Log.e("entries1", String.valueOf(entries1));
        Log.e("entries2", String.valueOf(entries2));
        setData(entries1,entries2);

        chart.invalidate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @Override
    public void onPause() {
        super.onPause();
        timer.cancel();
    }


}