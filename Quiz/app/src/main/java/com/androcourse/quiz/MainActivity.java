package com.androcourse.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button yesButton;
    Button noButton;
    Button showAnswer;
    Toast lastToast;
    TextView questionTextView;
    int currentQuestion = 0;
    boolean needReset = true;

    Question[] questions = {
            new Question(R.string.question, false),
            new Question(R.string.question1, true),
            new Question(R.string.question2, true),
            new Question(R.string.question3, false),
            new Question(R.string.question4, false),
    };
    Bundle savedInstanceState;
    String[] answers;
    int answersGood = 0;

    void
    respond(boolean givenYes) {
        Question q = questions[currentQuestion];
        int message = R.string.messageIncorrect;
        int answer = givenYes ? R.string.answerYes : R.string.answerNo;
        int answerGoodness = R.string.answerIncorrect;
        if (q.isAnswer() == givenYes) {
            answersGood++;
            message = R.string.messageCorrect;
            answerGoodness = R.string.answerCorrect;
        }
        answers[currentQuestion] = getResources().getString(q.getQuestion()) +
                getResources().getString(R.string.joinString) +
                getResources().getString(answer) +
                getResources().getString(R.string.joinString) +
                getResources().getString(answerGoodness);

        if (lastToast != null) {
            lastToast.cancel();
            lastToast = null;
        }
        if (currentQuestion + 1 < questions.length) {
            needReset = false;
            currentQuestion++;
            Toast toast = Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT);
            lastToast = toast;
            toast.show();
            updateOnscreenQuestion();
        } else {
            needReset = true;
            Intent intent = new Intent(MainActivity.this, AnswerActivity.class);
            intent.putExtra("answers", answers);
            intent.putExtra("answersGood", answersGood);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            currentQuestion = savedInstanceState.getInt("qn");
        }

        answers = new String[questions.length];

        questionTextView = findViewById(R.id.questionTextView);
        updateOnscreenQuestion();

        yesButton = findViewById(R.id.yesButton);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                respond(true);
            }
        });

        noButton = findViewById(R.id.noButton);
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                respond(false);
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

    void updateOnscreenQuestion() {
        assert (currentQuestion < questions.length);
        questionTextView.setText(questions[currentQuestion].getQuestion());
    }
}