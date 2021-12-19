package com.example.mytranslator;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import com.example.mytranslator.model.Model;
import com.example.mytranslator.request.ServiceGenerator;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.skydoves.powerspinner.PowerSpinnerView;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private PowerSpinnerView powerSpinnerView1;
    private PowerSpinnerView powerSpinnerView2;
    private Button submitButton;
    private String fromLanguage, toLanguage;
    private EditText editText;
    private List<String> iconSpinnerItems = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        init();
        setListeners();
    }

    private void init() {
        iconSpinnerItems.add("English");
        iconSpinnerItems.add("Arabic");
        iconSpinnerItems.add("Persian");
        powerSpinnerView1 = findViewById(R.id.spinner1);
        powerSpinnerView2 = findViewById(R.id.spinner2);
        editText = findViewById(R.id.edit_text);

        powerSpinnerView1.setItems(iconSpinnerItems);
        powerSpinnerView2.setItems(iconSpinnerItems);
        submitButton = findViewById(R.id.submit);

    }

    private void setListeners() {
        powerSpinnerView1.setOnClickListener(view -> powerSpinnerView1.show());
        powerSpinnerView2.setOnClickListener(view -> powerSpinnerView2.show());
        powerSpinnerView1.setOnSpinnerItemSelectedListener(
                (OnSpinnerItemSelectedListener<String>) (oldIndex, oldItem, newIndex, newItem) -> {
                    fromLanguage = newItem;

                });
        powerSpinnerView2.setOnSpinnerItemSelectedListener(
                (OnSpinnerItemSelectedListener<String>) (oldIndex, oldItem, newIndex, newItem) -> {
                    toLanguage = newItem;
                });


        submitButton.setOnClickListener(view -> {
            Intent showWordsDetail = new Intent(HomeActivity.this,
                    WordDetailActivity.class);
            getDataObservable().enqueue(new Callback<Model>() {
                @Override
                public void onResponse(Call<Model> call, Response<Model> response) {
                    System.out.println(response.body().getResponse().getCode());
                    System.out.println(response.body().getData().getResults().get(0).getText());

                    //      startActivity(showWordsDetail);
                }

                @Override
                public void onFailure(Call<Model> call, Throwable t) {
                    call.cancel();
                }
            });
        });
    }

    private String findDB() {
        switch (fromLanguage) {
            case "Persian": {
                return setToLanguage("fa");
            }
            case "Arabic": {
                return setToLanguage("ar");
            }
            case "English": {
                return setToLanguage("en");
            }
        }
        return "";
    }

    private String setToLanguage(String from) {
        switch (toLanguage) {
            case "Persian": {
                return from + "2fa";
            }
            case "Arabic": {
                return from + "2ar";
            }
            case "English": {
                return from + "2en";
            }
        }
        return null;
    }

    private Call<Model> getDataObservable() {
        return ServiceGenerator.getRequestApi().getTranslation("68311.owiMM2FtLg8QuuiPU8UQdCOGi3Pqch7hQ0RL3br7", editText.getText().toString().trim(), "exact", findDB());
    }
}



