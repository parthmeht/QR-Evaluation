package com.app.scopingproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class QuestionsActivity extends AppCompatActivity {


    int i = 0;
    int answer = 0;
    TextView question_text;
    Button submit, next;

    String[] ALL_QUESTIONS= {"Question 1", "Question 2","Question 3","Question 4", "Question 5"};
    static final int NUMBER_OF_QUESTIONS = 5;
    RadioGroup choose_answer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        question_text = findViewById(R.id.question_text);
        submit = findViewById(R.id.questions_submit_button);
        next = findViewById(R.id.button_next);
        choose_answer = findViewById(R.id.answer_choice_radio);
        if(i<ALL_QUESTIONS.length)
            question_text.setText(ALL_QUESTIONS[i]);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton r= findViewById(choose_answer.getCheckedRadioButtonId());
                checkNumber(i, r.getText().toString());
            }
        });
    }

    public void checkNumber(int d, String answer){
        if(d>=Questions.NUMBER_OF_QUESTIONS-1){
            next.setClickable(false);
            submit.setClickable(true);

        }
        else{
            Toast.makeText(getApplicationContext(), "Your choice "+ answer,Toast.LENGTH_LONG).show();
            if(i<ALL_QUESTIONS.length)
                question_text.setText(ALL_QUESTIONS[++i]);
        }

    }
}
