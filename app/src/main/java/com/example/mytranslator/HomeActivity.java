package com.example.mytranslator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.skydoves.powerspinner.PowerSpinnerView;

public class HomeActivity extends AppCompatActivity {

    private PowerSpinnerView powerSpinnerView;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        init(this);
        setListeners();
    }

    private void init(Context context) {
        powerSpinnerView = new PowerSpinnerView(context);
        submitButton = findViewById(R.id.submit);

    }

    private void setListeners() {
        powerSpinnerView.setOnSpinnerItemSelectedListener(
                (OnSpinnerItemSelectedListener<String>) (oldIndex, oldItem, newIndex, newItem) -> {
                });

        submitButton.setOnClickListener(view -> {
            Intent showWordsDetail = new Intent(HomeActivity.this,
                    WordDetailActivity.class);
            startActivity(showWordsDetail);
        });
    }


}


