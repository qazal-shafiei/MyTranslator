package com.example.mytranslator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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
    private String fromLanguage = "", toLanguage = "";
    private EditText editText;
    private List<String> iconSpinnerItems = new ArrayList<>();
    private ImageView change;
    private ConstraintLayout bg;


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
        change = findViewById(R.id.change);
        bg = findViewById(R.id.bg);

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
                    if (fromLanguage.equals("Persian") && toLanguage.equals("English")){
                        makeToast("dehkhoda");
                    }
                });

        change.setOnClickListener(view -> {
            powerSpinnerView1.dismiss();
            powerSpinnerView2.dismiss();

            String temp;
            temp = fromLanguage;
            fromLanguage = toLanguage;
            toLanguage = temp;

            System.out.println(fromLanguage +"__1111111__"+toLanguage);

       //     powerSpinnerView1.setPreferenceName(fromLanguage);
       //     powerSpinnerView2.setPreferenceName(toLanguage);
            powerSpinnerView1.notifyItemSelected(iconSpinnerItems.indexOf(fromLanguage) , fromLanguage);
            powerSpinnerView2.notifyItemSelected(iconSpinnerItems.indexOf(toLanguage) , toLanguage);

            System.out.println(fromLanguage +"__2222222__"+toLanguage);
        });

        editText.setOnClickListener(view -> {
            powerSpinnerView1.dismiss();
            powerSpinnerView2.dismiss();
        });

        bg.setOnClickListener(view -> {
            powerSpinnerView1.dismiss();
            powerSpinnerView2.dismiss();
        });

        submitButton.setOnClickListener(view -> {
            if (fromLanguage == null || toLanguage == null || editText.getText().toString().trim().equals("")) {
                makeToast("fill the blanks");
                return;
            }


            Intent showWordsDetail = new Intent(HomeActivity.this,
                    WordDetailActivity.class);
            getDataObservable().enqueue(new Callback<Model>() {
                @Override
                public void onResponse(Call<Model> call, Response<Model> response) {
                    int code = response.body().getResponse().getCode();
                    if (code == 200 && response.body().getData().getResults() != null && response.body().getData().getResults().size() != 0) {
                        String text = response.body().getData().getResults().get(0).getText();
                        String titleEn = response.body().getData().getResults().get(0).getTitle_en();
                        String source = response.body().getData().getResults().get(0).getSource();
                        showWordsDetail.putExtra("text", text);
                        showWordsDetail.putExtra("title", editText.getText().toString());
                        showWordsDetail.putExtra("titleEn", titleEn);
                        showWordsDetail.putExtra("source", source);
                        startActivity(showWordsDetail);
                    } else {
                        makeToast("wrong request");
                    }
                }

                @Override
                public void onFailure(Call<Model> call, Throwable t) {
                    makeToast("wrong request");
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
                return from+"2fa";
            }
            case "Arabic": {
                return from + "2ar";
            }
            case "English": {
                return  "dehkhoda";
            }
        }
        return null;
    }

    private Call<Model> getDataObservable() {
        return ServiceGenerator.getRequestApi().getTranslation("68311.owiMM2FtLg8QuuiPU8UQdCOGi3Pqch7hQ0RL3br7", editText.getText().toString().trim(), "exact", findDB());
    }

    private void makeToast(String st) {
        Toast.makeText(this, st, Toast.LENGTH_SHORT).show();
    }
}



