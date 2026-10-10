package com.example.report5;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class TemperatureActivity extends AppCompatActivity {

    private SeekBar seekCelsius;
    private SeekBar seekFahrenheit;
    private TextView textCelsius;
    private TextView textFahrenheit;
    private TextView textTemperatureResult;
    private RadioGroup radioGroupConversion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_temperature);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main), (v, insets) -> {
                    Insets bars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars());

                    v.setPadding(
                            bars.left, bars.top,
                            bars.right, bars.bottom);

                    return insets;
                });

        seekCelsius = findViewById(R.id.seekCelsius);
        seekFahrenheit = findViewById(R.id.seekFahrenheit);
        textCelsius = findViewById(R.id.textCelsius);
        textFahrenheit = findViewById(R.id.textFahrenheit);
        textTemperatureResult =
                findViewById(R.id.textTemperatureResult);
        radioGroupConversion =
                findViewById(R.id.radioGroupConversion);

        Button buttonConvert = findViewById(R.id.buttonConvert);
        Button buttonReset = findViewById(R.id.buttonReset);

        SeekBar.OnSeekBarChangeListener listener =
                new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onProgressChanged(
                            SeekBar seekBar, int progress,
                            boolean fromUser) {
                        updateLabels();
                        textTemperatureResult.setText("");
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                };

        seekCelsius.setOnSeekBarChangeListener(listener);
        seekFahrenheit.setOnSeekBarChangeListener(listener);

        radioGroupConversion.setOnCheckedChangeListener(
                (group, checkedId) -> {
                    updateEnabledSeekBars();
                    textTemperatureResult.setText("");
                });

        buttonConvert.setOnClickListener(v -> convertTemperature());

        buttonReset.setOnClickListener(v -> {
            radioGroupConversion.clearCheck();
            seekCelsius.setProgress(100);
            seekFahrenheit.setProgress(100);
            updateLabels();
            updateEnabledSeekBars();
            textTemperatureResult.setText("");
        });

        updateLabels();
        updateEnabledSeekBars();
    }

    private void updateLabels() {
        double celsius = seekCelsius.getProgress() - 100;
        double fahrenheit = seekFahrenheit.getProgress() - 100;

        textCelsius.setText(String.format(
                Locale.US, "섭씨온도(%.1f ℃)", celsius));

        textFahrenheit.setText(String.format(
                Locale.US, "화씨온도(%.1f ℉)", fahrenheit));
    }

    private void updateEnabledSeekBars() {
        int selectedId =
                radioGroupConversion.getCheckedRadioButtonId();

        // Before choosing a direction, both bars are enabled
        // to match the initial screen in the example.
        boolean noSelection = selectedId == -1;

        seekCelsius.setEnabled(
                noSelection
                        || selectedId == R.id.radioCelsiusToFahrenheit);

        seekFahrenheit.setEnabled(
                noSelection
                        || selectedId == R.id.radioFahrenheitToCelsius);
    }

    private void convertTemperature() {
        int selectedId =
                radioGroupConversion.getCheckedRadioButtonId();

        double celsius;
        double fahrenheit;

        if (selectedId == R.id.radioCelsiusToFahrenheit) {
            celsius = seekCelsius.getProgress() - 100;
            fahrenheit = celsius * 1.8 + 32;

        } else if (selectedId == R.id.radioFahrenheitToCelsius) {
            fahrenheit = seekFahrenheit.getProgress() - 100;
            celsius = (fahrenheit - 32) / 1.8;

        } else {
            Toast.makeText(
                    this, "선택해주세요", Toast.LENGTH_SHORT).show();
            return;
        }

        textTemperatureResult.setText(String.format(
                Locale.US,
                "섭씨온도 : %.2f ℃, 화씨온도 : %.2f ℉",
                celsius, fahrenheit));
    }
}