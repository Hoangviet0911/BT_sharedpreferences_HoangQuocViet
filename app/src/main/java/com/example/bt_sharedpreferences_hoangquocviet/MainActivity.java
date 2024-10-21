package com.example.bt_sharedpreferences_hoangquocviet;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText guessEditText;
    private Button guessButton;
    private TextView resultTextView;
    private int randomNumber;
    private int score = 0;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        guessEditText = findViewById(R.id.edittext_doanso);
        guessButton = findViewById(R.id.guessButton);
        resultTextView = findViewById(R.id.resultTextView);
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        score = sharedPreferences.getInt("score", 0);
        updateScoreTextView();
        randomNumber = generateRandomNumber();

        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String guessString = guessEditText.getText().toString();
                if (guessString.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập số dự đoán", Toast.LENGTH_SHORT).show();
                    return;
                }
                int guessedNumber = Integer.parseInt(guessString);
                if (guessedNumber == randomNumber) {
                    score += 10;
                    Toast.makeText(MainActivity.this, "Chúc mừng! Bạn đã đoán đúng.", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("score", score);
                    editor.apply();

                    randomNumber = generateRandomNumber();
                } else {
                    Toast.makeText(MainActivity.this, "Sai rồi! Thử lại.", Toast.LENGTH_SHORT).show();
                }
                updateScoreTextView();
                guessEditText.setText("");
            }
        });
    }

    private int generateRandomNumber() {
        Random random = new Random();
        return random.nextInt(10) + 1;
    }

    private void updateScoreTextView() {
        resultTextView.setText("Điểm hiện tại: " + score);
    }
}
