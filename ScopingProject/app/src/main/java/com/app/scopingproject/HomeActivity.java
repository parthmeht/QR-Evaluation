package com.app.scopingproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        name = findViewById(R.id.txtName);
        Intent intent = getIntent();
        if(intent.getExtras()!=null){
            name.setText("Hello, "+ intent.getStringExtra("data"));
        }
    }
}
