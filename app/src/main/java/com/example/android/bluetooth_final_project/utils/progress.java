package com.example.android.bluetooth_final_project.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.bluetooth_final_project.R;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class progress {

    private static Dialog dialog;
    private static CustomCircularProgressView ivProgressBar;

    public static final String TAG = "Utils";






    public static void hideCustomProgressDialog() {
        try {
            if (dialog != null && ivProgressBar != null) {

                dialog.dismiss();


            }
        } catch (Exception e) {
            AppLog.handleException(TAG, e);
        }
    }

    public static void showCustomProgressDialog(Context context, boolean isCancel) {
//        if (dialog != null && dialog.isShowing()) {
//            return;
//        }
        Log.e("image", "here");
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.circuler_progerss_bar);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ivProgressBar = (CustomCircularProgressView) dialog.findViewById(R.id.ivProgressBarTwo);
        ivProgressBar.startAnimation();
        dialog.setCancelable(isCancel);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setDimAmount(0);
        dialog.show();
//        if (!((AppCompatActivity) context).isFinishing()) {
//
//        }


    }

    public static String getTimeZoneName() {
        String name = TimeZone.getDefault().getID();
        AppLog.Log("TimeZoneName", name);
        return name;
    }

}

