package com.androcourse.quiz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private Button yesBtn;
    private Button noBtn;
    private TextView questionTextView;
    private int currentQuestion = 0;

    Question[] questions = {
            new Question(R.string.question, false),
            new Question(R.string.question1, true),
            new Question(R.string.question2, true),
            new Question(R.string.question3, false),
            new Question(R.string.question4, false),
    };
    private Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            currentQuestion = savedInstanceState.getInt("qn");
            Log.d("===", "onCreate non-null " + currentQuestion);
        } else {
            Log.d("===", "onCreate null");
        }

        questionTextView = findViewById(R.id.questionTextView);
        updateText();

        yesBtn = findViewById(R.id.yesBtn);
        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, produceResponse(true), Toast.LENGTH_SHORT).show();
                nextQuestion();
            }
        });

        noBtn = findViewById(R.id.noBtn);
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, produceResponse(false), Toast.LENGTH_SHORT).show();
                nextQuestion();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("===", "onDestroy");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.d("===", "onSaveInstanceState saving " + currentQuestion);
        savedInstanceState.putInt("qn", currentQuestion);
    }

    void nextQuestion() {
        rollQuestion(1);
        updateText();
    }

    int produceResponse(boolean givenYes) {
        return questions[currentQuestion].isAnswer() == givenYes ? R.string.correct : R.string.incorrect;
    }

    void rollQuestion(int delta) {
        assert (questions.length > 0);
        currentQuestion = (currentQuestion + delta) % questions.length;
    }

    void updateText() {
        rollQuestion(0);
        questionTextView.setText(questions[currentQuestion].getQuestion());
    }
}