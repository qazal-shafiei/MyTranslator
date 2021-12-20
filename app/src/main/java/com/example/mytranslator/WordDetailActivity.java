package com.example.mytranslator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class WordDetailActivity extends AppCompatActivity {

    private TextView word , description , textSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_detail);

        init();

    }

    private void init(){
        word = findViewById(R.id.word);
        description = findViewById(R.id.description);
        textSource = findViewById(R.id.source);
        String title =getIntent().getStringExtra("title");
        String text =getIntent().getStringExtra("text");
        String titleEn =getIntent().getStringExtra("titleEn");
        String source =getIntent().getStringExtra("source");
        word.setText(title);
        description.setText(titleEn +"\n"+text);
        textSource.setText(source);
    }
}