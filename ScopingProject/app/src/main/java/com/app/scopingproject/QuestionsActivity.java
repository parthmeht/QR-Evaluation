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
    String[] ALL_QUESTIONS = {"Poster content is of professional quality and indicates a master of the project subject matter. *",
            "The presentation is organised, engaging and includes a thorough description of the design and the implementation of the design. *",
            "All team members are suitably attired, are polite, demonstrate full knowledge of material, and can answer all relevant questions. *",
            "The work product(model, prototype, documentation set or computer simulation) is of professional quality in all respects *",
            "The team implemented novel approaches and/or solutions in the development of the project. *",
            "The project has the potential to enhance the reputation of the Innovative Computing Project and/or CCI/DSI. *",
            "The team successfully explained the scope and results of their project in no more than 5 minutes. *"};
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
    int countNumber =0;

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
        hm = new HashMap<>();
        if (i < ALL_QUESTIONS.length)
            question_text.setText(ALL_QUESTIONS[i]);
        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(groupName).hasChild(user.getUid())){
                    if(countNumber == 0)
                        Toast.makeText(getApplicationContext(),"You already evaluated this group!",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(QuestionsActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
                countNumber = 1;
                RadioButton radioButton = findViewById(choose_answer.getCheckedRadioButtonId());
                hm.put(String.valueOf(i+1), Integer.parseInt(radioButton.getText().toString()));
                int sum = 0;
                for (int val : hm.values()) {
                    sum += val;
                }
                questions = new Questions();
                questions.setHm(hm);
                questions.setScore(sum);
                myRef.child(groupName).child(user.getUid()).setValue(questions);
                Intent intent = new Intent(QuestionsActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
                i = 0;
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
