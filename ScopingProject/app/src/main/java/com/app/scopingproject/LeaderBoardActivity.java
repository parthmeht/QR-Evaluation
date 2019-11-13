package com.app.scopingproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeaderBoardActivity extends AppCompatActivity {
    private static final String TAG = "LeaderBoardActivity";
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private List<Group> groups;
    private RecyclerView recyclerView;
    private GroupAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        groups = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("groups");
        recyclerView = findViewById(R.id.recycler_view);
        adapter = new GroupAdapter(groups);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                groups.clear();
                for (DataSnapshot child: dataSnapshot.getChildren()){
                    Group group = new Group();
                    group.setName(child.getKey());
                    for (DataSnapshot eval: child.getChildren()){
                        group.setNoOfEvaluators(group.getNoOfEvaluators()+1);
                        int score = eval.child("score").getValue(Integer.class);
                        group.setScore(group.getScore()+score);
                    }
                    group.setAvgScore(group.getScore()/group.getNoOfEvaluators());
                    Log.v(TAG,group.toString());
                    groups.add(group);
                }
                Collections.sort(groups);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
