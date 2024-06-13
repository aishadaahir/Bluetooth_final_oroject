package com.example.android.bluetooth_final_project;

import static android.provider.MediaStore.MediaColumns.TITLE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.github.mikephil.charting.data.RadarEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PreferenceHelper {
    private static final String PREF_FILE = "app_preferences";
    private static final String PREF_ENTRIES1 = "entries1";
    private static final String PREF_ENTRIES2 = "entries2";

    private Context context;

    public PreferenceHelper(Context context) {
        this.context = context;
    }

    public void saveEntries1(List<RadarEntry> entries1) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        // Convert the RadarEntry list to a string format that can be stored in the preferences
        StringBuilder sb = new StringBuilder();
        for (RadarEntry entry : entries1) {
            sb.append(entry.toString()).append("|");
        }
        editor.putString(PREF_ENTRIES1, sb.toString());
        editor.apply();
    }

//    public List<RadarEntry> getEntries1() {
//        SharedPreferences preferences = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
//        String entries1String = preferences.getString(PREF_ENTRIES1, "");
//
//        List<RadarEntry> entries1 = new ArrayList<>();
//        if (!entries1String.isEmpty()) {
//            String[] entryStrings = entries1String.split("\\|");
//            for (String entryString : entryStrings) {
//                RadarEntry entry = createRadarEntryFromString(entryString);
//                if (entry != null) {
//                    entries1.add(entry);
//                }
//            }
//        }
//        return entries1;
//    }
    public ArrayList<RadarEntry> getEntries1() {
        SharedPreferences preferences = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        String entries1String = preferences.getString(PREF_ENTRIES1, "");

        ArrayList<RadarEntry> entries1 = new ArrayList<>();
        if (!entries1String.isEmpty()) {
            String[] entryStrings = entries1String.split("\\|");
            for (String entryString : entryStrings) {
                RadarEntry entry = createRadarEntryFromString(entryString);
                Log.e("string", String.valueOf(entry));
                if (entry != null) {
                    entries1.add(entry);
                }
            }
        }
        return entries1;
    }
//    public ArrayList<RadarEntry> getEntries1() {
//        SharedPreferences preferences = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
//        String entries1String = preferences.getString(PREF_ENTRIES1, "");
//
//        ArrayList<RadarEntry> entries1 = new ArrayList<>();
//        Log.d("PreferenceHelper", "entries1String: " + entries1String);
//
//        if (!entries1String.isEmpty()) {
//            String[] entryStrings = entries1String.split("\\|");
//            Log.d("PreferenceHelper", "entryStrings length: " + entryStrings.length);
//            Log.e("PreferenceHelperstring", Arrays.toString(entryStrings));
//            for (String entryString : entryStrings) {
//                Log.e("PreferenceHelperstring", entryString);
//                RadarEntry entry = createRadarEntryFromString(entryString);
//                Log.d("entry", String.valueOf(entry));
//////                if (entry != null) {
//////                    entries1.add(entry);
//////                }
////                if (entry != null) {
////                    Log.d("PreferenceHelper", "RadarEntry: " + entry.toString());
////                    entries1.add(entry);
////                } else {
////                    Log.e("PreferenceHelper", "Failed to create RadarEntry from stored string");
////                }
//            }
//        }
////        String entryString = preferences.getString(PREF_ENTRIES1, "");
////        RadarEntry entry = createRadarEntryFromString(entryString);
////        if (entry != null) {
////            Log.d("PreferenceHelper", "RadarEntry: " + entry.toString());
////        } else {
////            Log.e("PreferenceHelper", "Failed to create RadarEntry from stored string");
////        }
//        return entries1;
//    }

    public void saveEntries2(List<RadarEntry> entries2) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        // Convert the RadarEntry list to a string format that can be stored in the preferences
        StringBuilder sb = new StringBuilder();
        for (RadarEntry entry : entries2) {
            sb.append(entry.toString()).append("|");
        }
        editor.putString(PREF_ENTRIES2, sb.toString());
        editor.apply();
    }

//    public List<RadarEntry> getEntries2() {
//        SharedPreferences preferences = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
//        String entries2String = preferences.getString(PREF_ENTRIES2, "");
//
//        List<RadarEntry> entries2 = new ArrayList<>();
//        if (!entries2String.isEmpty()) {
//            String[] entryStrings = entries2String.split("\\|");
//            for (String entryString : entryStrings) {
//                RadarEntry entry = createRadarEntryFromString(entryString);
//                if (entry != null) {
//                    entries2.add(entry);
//                }
//            }
//        }
//        return entries2;
//    }

    public ArrayList<RadarEntry> getEntries2() {
        SharedPreferences preferences = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        String entries2String = preferences.getString(PREF_ENTRIES2, "");

        ArrayList<RadarEntry> entries2 = new ArrayList<>();
        if (!entries2String.isEmpty()) {
            String[] entryStrings = entries2String.split("\\|");
            for (String entryString : entryStrings) {
                RadarEntry entry = createRadarEntryFromString(entryString);
                Log.e("string", String.valueOf(entry));
                if (entry != null) {
                    entries2.add(entry);
                }
            }
        }
        return entries2;
    }



//    private RadarEntry createRadarEntryFromString(String entryString) {
//        try {
//            // Assuming the RadarEntry class has a constructor that takes a string representation
//            return new RadarEntry(entryString);
//        } catch (Exception e) {
//            // Handle any issues with creating the RadarEntry object
//            return null;
//        }
//    }


    private RadarEntry createRadarEntryFromString(String entryString) {
        try {
            String[] parts = entryString.split(",");
            if (parts.length == 2) {
                String entryLabel = parts[0].trim();
                String entryData = parts[1].trim();

                // Parse the entry data
                String[] dataPoints = entryData.split(" ");
//                Log.e("length", String.valueOf(dataPoints.length));
//                Log.e("string", dataPoints[0]);
//                Log.e("string", dataPoints[1]);
//                Log.e("string", dataPoints[2]);
//                Log.e("string", dataPoints[3]);
                if (dataPoints.length == 4 && Objects.equals(dataPoints[0], "x:") && Objects.equals(dataPoints[2], "y:")) {
//                    Log.e("string", "innnnn");
                    float x = Float.parseFloat(dataPoints[1]);
                    float y = Float.parseFloat(dataPoints[3]);
                    if(x != 0){
                        return new RadarEntry(x);
                    }
                    else{
                        return new RadarEntry(y);
                    }
//                    Log.e("x", String.valueOf(x));
//                    Log.e("y", String.valueOf(y));
//                    Log.e("y", String.valueOf(new RadarEntry(x, y)));
//                    return new RadarEntry(x, y);
                } else {
                    Log.e("PreferenceHelper", "Unexpected format in entry data: " + entryData);
                }
            } else {
                Log.e("PreferenceHelper", "Unexpected format in entry string: " + entryString);
            }
        } catch (NumberFormatException e) {
            Log.e("PreferenceHelper", "Error parsing float values: " + e.getMessage());
        } catch (Exception e) {
            Log.e("PreferenceHelper", "Error creating RadarEntry: " + e.getMessage());
        }
        return null;
    }

//    private RadarEntry createRadarEntryFromString(String entryString) {
//        Log.e("entry ", entryString);
//        try {
//            String[] parts = entryString.split(",");
//            if (parts.length == 2) {
//                // Extract the x and y values
//                float x = Float.parseFloat(parts[1].trim().split("x: ")[1]);
//                float y = Float.parseFloat(parts[1].trim().split("y: ")[1]);
//                Log.e("x", String.valueOf(x));
//                Log.e("y", String.valueOf(y));
//                Log.e("r", String.valueOf(new RadarEntry(x, y)));
//                return new RadarEntry(x, y);
//            } else {
//                Log.e("PreferenceHelper", "Unexpected format in entry string: " + entryString);
//            }
////        if (parts.length == 2) {
////            String entryLabel = parts[0].trim();
////            String entryData = parts[1].trim();
////
////            // Parse the entry data
////            float x = 0.0f;
////            float y = 0.0f;
////            String[] dataPoints = entryData.split(" ");
////            for (String dataPoint : dataPoints) {
////                Log.e("entry ", dataPoint);
////                if (dataPoint.startsWith("x:")) {
////                    x = Float.parseFloat(dataPoint.substring(3).trim());
////                } else if (dataPoint.startsWith("y:")) {
////                    y = Float.parseFloat(dataPoint.substring(3).trim());
////                }
////            }
////
////            Log.e("x", String.valueOf(x));
////            Log.e("y", String.valueOf(y));
////            Log.e("RadarEntry", String.valueOf(new RadarEntry(x, y)));
////            return new RadarEntry(x, y);
////        } else {
////            Log.e("PreferenceHelper", "Unexpected format in entry string: " + entryString);
////        }
//        } catch (NumberFormatException e) {
//            Log.e("PreferenceHelper", "Error parsing float values: " + e.getMessage());
//        } catch (Exception e) {
//            Log.e("PreferenceHelper", "Error creating RadarEntry: " + e.getMessage());
//        }
//        return null;
//    }

}