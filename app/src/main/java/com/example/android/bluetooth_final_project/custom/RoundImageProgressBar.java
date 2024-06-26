package com.example.android.bluetooth_final_project.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class RoundImageProgressBar extends ProgressBar {
    private Bitmap image;

    public RoundImageProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setImage(Bitmap bitmap) {
        image = bitmap;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (image != null) {
            int width = getWidth();
            int height = getHeight();
            int size = Math.min(width, height);
            int x = (width - size) / 2;
            int y = (height - size) / 2;

            // Draw the image inside the progress bar
            canvas.drawBitmap(image, null, new Rect(x, y, x + size, y + size), null);
        }
    }
}