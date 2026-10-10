package com.example.report5;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class BmiActivity extends AppCompatActivity {

    private SeekBar seekWeight;
    private SeekBar seekHeight;
    private TextView textWeight;
    private TextView textHeight;
    private TextView textBmiResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bmi);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main), (v, insets) -> {
                    Insets bars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars());

                    v.setPadding(
                            bars.left, bars.top,
                            bars.right, bars.bottom);

                    return insets;
                });

        seekWeight = findViewById(R.id.seekWeight);
        seekHeight = findViewById(R.id.seekHeight);
        textWeight = findViewById(R.id.textWeight);
        textHeight = findViewById(R.id.textHeight);
        textBmiResult = findViewById(R.id.textBmiResult);

        SeekBar.OnSeekBarChangeListener listener =
                new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onProgressChanged(
                            SeekBar seekBar, int progress,
                            boolean fromUser) {
                        updateBmi();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                };

        seekWeight.setOnSeekBarChangeListener(listener);
        seekHeight.setOnSeekBarChangeListener(listener);
    }

    private void updateBmi() {
        double weight = 35.0 + seekWeight.getProgress() * 0.5;
        double heightCm = 90.0 + seekHeight.getProgress() * 0.5;
        double heightM = heightCm / 100.0;

        double bmi = weight / (heightM * heightM);

        textWeight.setText(
                String.format(Locale.US, "%.1f Kg", weight));

        textHeight.setText(
                String.format(Locale.US, "%.1f Cm", heightCm));

        String category;

        if (bmi < 18.5) {
            category = "저체중";
        } else if (bmi < 23.0) {
            category = "정상 체중";
        } else if (bmi < 25.0) {
            category = "과체중";
        } else {
            category = "비만";
        }

        textBmiResult.setText(
                String.format(
                        Locale.US,
                        "BMI : %.2f\n당신은 %s 입니다!",
                        bmi,
                        category));
    }
}