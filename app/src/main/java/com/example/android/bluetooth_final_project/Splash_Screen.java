package com.example.android.bluetooth_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;

public class Splash_Screen extends AppCompatActivity {

    PreferenceHelper preferenceHelper;

    private ImageView imageView;
    private int currentImageIndex = 0;
    private final int[] imageResources = {R.drawable.logo_removebg22, R.drawable.logo_removebg3, R.drawable.logo_removebg};
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable runnable = this::toggleImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        LottieAnimationView animationView = findViewById(R.id.animationView);
        animationView.setAnimation(R.raw.animationsplash); // Replace with the actual animation file name
        animationView.playAnimation();

        imageView = findViewById(R.id.logo_id);
        startImageToggle();
        preferenceHelper = new PreferenceHelper(Splash_Screen.this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //This method will be executed once the timer is over
                // Start your app main activity
                Intent i;
                if (preferenceHelper.getBluename(Splash_Screen.this).isEmpty() || preferenceHelper.getBluename(Splash_Screen.this).equals("")){
                    i = new Intent(getApplicationContext(), MainActivity.class);

                }else{
                    i = new Intent(getApplicationContext(), Home.class);


                }
                startActivity(i);
                finish();


            }
        }, 7000);
    }

    private void startImageToggle() {
        handler.postDelayed(runnable, 2000); // Start the image toggle after 2 seconds
    }

    private void toggleImage() {
        imageView.setImageResource(imageResources[currentImageIndex]);
        currentImageIndex = (currentImageIndex + 1) % imageResources.length;
        handler.postDelayed(runnable, 500); // Schedule the next toggle in 2 seconds
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable); // Stop the image toggle when the activity is destroyed
    }
}