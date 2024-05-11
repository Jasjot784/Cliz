package com.example.cliz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etTopic,etNumber;
    Button btSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        etTopic = findViewById(R.id.etTopic);
        etNumber = findViewById(R.id.etNumber);
        btSubmit = findViewById(R.id.btSubmit);

        btSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(QuizActivity.this,QuestionsActivity.class);
        intent.putExtra("topic",etTopic.getText().toString());
        intent.putExtra("num",etNumber.getText().toString());
        startActivity(intent);
    }
}