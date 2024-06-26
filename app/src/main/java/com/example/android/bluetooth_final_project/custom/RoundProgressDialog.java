package com.example.android.bluetooth_final_project.custom;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.android.bluetooth_final_project.R;
public class RoundProgressDialog extends Dialog {
    private RoundImageProgressBar progressBar;
    private TextView progressText;

    public RoundProgressDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.round_progress_dialog);

        progressBar = findViewById(R.id.progress_bar);
        progressText = findViewById(R.id.progress_text);
        Log.e("image", "here");
    }

    public void setImage(Bitmap image) {
        Log.e("image", image.toString());
        progressBar.setImage(image);
    }

    public void setProgressText(String text) {
        progressText.setText(text);
    }
}
