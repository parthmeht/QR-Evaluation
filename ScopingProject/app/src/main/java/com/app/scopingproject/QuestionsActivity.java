package com.app.scopingproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class QuestionsActivity extends AppCompatActivity {


    static final int NUMBER_OF_QUESTIONS = 7;
    private static int i = 0;
    String[] ALL_QUESTIONS = {"Question_1", "Question_2", "Question_3", "Question_4", "Question_5", "Question_6", "Question_7"};
    RadioGroup choose_answer;
    private TextView question_text;
    private String groupName;
    private Button submit, next;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;
    private Questions questions;
    private HashMap<String, Integer> hm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        firebaseDatabase = FirebaseDatabase.getInstance();
        groupName = getIntent().getStringExtra("GROUP");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        myRef = firebaseDatabase.getReference("groups");
        question_text = findViewById(R.id.question_text);
        submit = findViewById(R.id.questions_submit_button);
        next = findViewById(R.id.button_next);
        choose_answer = findViewById(R.id.answer_choice_radio);
        questions = new Questions();


        hm = new HashMap<>();
        if (i < ALL_QUESTIONS.length)
            question_text.setText(ALL_QUESTIONS[i]);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton radioButton = findViewById(choose_answer.getCheckedRadioButtonId());
                checkNumber(radioButton.getText().toString());
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton radioButton = findViewById(choose_answer.getCheckedRadioButtonId());
                hm.put(String.valueOf(i+1), Integer.parseInt(radioButton.getText().toString()));
                int sum = 0;
                for (int val : hm.values()) {
                    sum += val;
                }
                questions.setHm(hm);
                questions.setScore(sum);
                myRef.child(groupName).child(user.getUid()).setValue(questions);
                Intent intent = new Intent(QuestionsActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
                /*myRef.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int avg = 0;
                        for(DataSnapshot child: dataSnapshot.child(groupName).child("groups").child(user.getUid()).child("Questions").getChildren()){
                            avg += (int) child.getValue();
                        }
                        setScore(avg);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });*/
            }
        });
    }

    private void setScore(int avg) {
        myRef.child(groupName).child(user.getUid()).child("score").setValue(avg);
        myRef.child(groupName).child(user.getUid()).child("evaluated").setValue(true);
    }

    public void checkNumber(String answer) {
        Log.d("Value of i", String.valueOf(i));
        Toast.makeText(getApplicationContext(), "Your choice " + answer, Toast.LENGTH_LONG).show();
        hm.put(String.valueOf(i+1), Integer.parseInt(answer));
        //myRef.child(groupName).child(user.getUid()).child("Questions").child(ALL_QUESTIONS[i]).setValue(answer);
        if (i < ALL_QUESTIONS.length)
            question_text.setText(ALL_QUESTIONS[++i]);
        if (i==ALL_QUESTIONS.length-1) {
            next.setVisibility(View.INVISIBLE);
            submit.setVisibility(View.VISIBLE);
        }
    }
}
