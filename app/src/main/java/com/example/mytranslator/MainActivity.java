package com.example.mytranslator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//your splash activity can cover the entire screen.
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(() -> {
            Intent splash = new Intent(MainActivity.this,
                    HomeActivity.class);
            startActivity(splash);
            finish();
        }, SPLASH_SCREEN_TIME_OUT);
    }
}
