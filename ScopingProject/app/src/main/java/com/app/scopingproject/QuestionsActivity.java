package com.app.scopingproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class QuestionsActivity extends AppCompatActivity {


    int i = 0;
    TextView question_text;
    String groupName;
    Button submit, next;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;

    String[] ALL_QUESTIONS= {"Question 1", "Question 2","Question 3","Question 4", "Question 5"};
    static final int NUMBER_OF_QUESTIONS = 5;
    RadioGroup choose_answer;
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
        if(i<ALL_QUESTIONS.length)
            question_text.setText(ALL_QUESTIONS[i]);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton r= findViewById(choose_answer.getCheckedRadioButtonId());
                checkNumber( r.getText());
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myRef.addValueEventListener(new ValueEventListener() {

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
                });

//                Intent intent = new Intent(QuestionsActivity.this, ScanGroupCodeActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                finish();
            }
        });
    }

    private void setScore(int avg){
        myRef.child(groupName).child(user.getUid()).child("score").setValue(avg);
        myRef.child(groupName).child(user.getUid()).child("evaluated").setValue(true);
    }

    public void checkNumber( CharSequence answer){
        Log.d("Value of i", String.valueOf(i));
        if(i>=NUMBER_OF_QUESTIONS-1){
            next.setClickable(false);
            submit.setClickable(true);
        }
        else{
                Toast.makeText(getApplicationContext(), "Your choice "+ answer,Toast.LENGTH_LONG).show();
                myRef.child(groupName).child(user.getUid()).child("Questions").child(ALL_QUESTIONS[i]).setValue(answer);
                if(i<ALL_QUESTIONS.length)
                    question_text.setText(ALL_QUESTIONS[++i]);


        }

    }
}
