package com.androcourse.quiz;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class AnswerActivity extends AppCompatActivity {
    private TextView answerTextView;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("===", "answerActivity onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        TextView caption = findViewById(R.id.answerCaptionTextView);
        caption.setText(R.string.answersCaptionLabel);

        answerTextView = findViewById(R.id.answerTextView);
        String[] answers = getIntent().getStringArrayExtra("answers");
        int answersGood = getIntent().getIntExtra("answersGood", 0);
        String text = String.join("\n", answers) + "\n\n" + "Верно: " + answersGood + " из " + answers.length;
        answerTextView.setText(text);
    }
}