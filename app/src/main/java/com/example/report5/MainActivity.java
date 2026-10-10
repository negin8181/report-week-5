package com.example.report5;

import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private boolean selected1 = false;
    private boolean selected2 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main), (v, insets) -> {
                    Insets bars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars());

                    v.setPadding(
                            bars.left, bars.top,
                            bars.right, bars.bottom);

                    return insets;
                });

        TextView textNumber1 = findViewById(R.id.textNumber1);
        TextView textNumber2 = findViewById(R.id.textNumber2);
        TextView textResult = findViewById(R.id.textResult);
        SeekBar seekNumber1 = findViewById(R.id.seekNumber1);
        SeekBar seekNumber2 = findViewById(R.id.seekNumber2);
        Button buttonCalculate = findViewById(R.id.buttonCalculate);

        if (savedInstanceState != null) {
            selected1 = savedInstanceState.getBoolean("selected1");
            selected2 = savedInstanceState.getBoolean("selected2");
        }

        SeekBar.OnSeekBarChangeListener listener =
                new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onProgressChanged(
                            SeekBar seekBar, int progress,
                            boolean fromUser) {

                        int number = progress + 1;

                        if (seekBar.getId() == R.id.seekNumber1) {
                            textNumber1.setText(
                                    "자연수 입력(" + number + ")");
                            if (fromUser) {
                                selected1 = true;
                            }
                        } else {
                            textNumber2.setText(
                                    "자연수 입력(" + number + ")");
                            if (fromUser) {
                                selected2 = true;
                            }
                        }

                        textResult.setText("결과");
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        if (seekBar.getId() == R.id.seekNumber1) {
                            selected1 = true;
                        } else {
                            selected2 = true;
                        }
                        textResult.setText("결과");
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                };

        seekNumber1.setOnSeekBarChangeListener(listener);
        seekNumber2.setOnSeekBarChangeListener(listener);

        buttonCalculate.setOnClickListener(v -> {
            if (!selected1 || !selected2) {
                Toast.makeText(
                        this, "입력 바람", Toast.LENGTH_SHORT).show();
                return;
            }

            int number1 = seekNumber1.getProgress() + 1;
            int number2 = seekNumber2.getProgress() + 1;

            int gcd = calculateGcd(number1, number2);
            int lcm = number1 * number2 / gcd;

            textResult.setText(
                    number1 + ", " + number2
                            + "의 GCD(최대공약수)는 "
                            + gcd + "입니다.\n"
                            + number1 + ", " + number2
                            + "의 LCM(최소공배수)는 "
                            + lcm + "입니다.");
        });
    }

    private int calculateGcd(int a, int b) {
        while (b != 0) {
            int remainder = a % b;
            a = b;
            b = remainder;
        }
        return a;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("selected1", selected1);
        outState.putBoolean("selected2", selected2);
        super.onSaveInstanceState(outState);
    }
}