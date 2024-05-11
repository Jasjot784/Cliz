package com.example.cliz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class QuestionsActivity extends AppCompatActivity implements View.OnClickListener {
    Integer num;
    private DatabaseReference mDatabase;
    ArrayList<Integer> quesnum  = new ArrayList<>();
    ArrayList<String> ques = new ArrayList<>();
    ArrayList<String> opt1 = new ArrayList<>();
    ArrayList<String> opt2 = new ArrayList<>();
    ArrayList<String> opt3 = new ArrayList<>();
    ArrayList<String> opt4 = new ArrayList<>();
    ArrayList<String> optionnum = new ArrayList<>();
    Integer i = 1;
    String topic;
    TextView tvQues;
    EditText etQues,etA,etB,etC,etD,etCorrect;
    Button btNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        tvQues = findViewById(R.id.tvQues);
        etQues = findViewById(R.id.etQues);
        etA = findViewById(R.id.etA);
        etB = findViewById(R.id.etB);
        etC = findViewById(R.id.etC);
        etD = findViewById(R.id.etD);
        etCorrect = findViewById(R.id.etCorrect);
        btNext = findViewById(R.id.btNext);
        btNext.setOnClickListener(this);

        if (getIntent().getExtras()!= null){
            num = Integer.parseInt((String) getIntent().getExtras().get("num"));
            topic = (String) getIntent().getExtras().get("topic");
            Toast.makeText(this, topic, Toast.LENGTH_LONG).show();
        }



    }

    @Override
    public void onClick(View view) {
        quesnum.add(i);
        optionnum.add(etCorrect.getText().toString());
        ques.add(etQues.getText().toString());
        opt1.add(etA.getText().toString());
        opt2.add(etB.getText().toString());
        opt3.add(etC.getText().toString());
        opt4.add(etD.getText().toString());
        i++;
        String s = "Question " + i;
        tvQues.setText(s);

        if(i>num){
            saveQuizData();
            Intent intent = new Intent(QuestionsActivity.this,MainActivity.class);
            intent.putStringArrayListExtra("opnum",optionnum);
            startActivity(intent);
        }else {
            etQues.setText("");
            etA.setText("");
            etB.setText("");
            etC.setText("");
            etD.setText("");
            etCorrect.setText("");
        }
    }
    public void saveQuizData(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ArrayList<ArrayList<String>> arrayList = new ArrayList<>();
        arrayList.add(optionnum);
        arrayList.add(ques);
        arrayList.add(opt1);
        arrayList.add(opt2);
        arrayList.add(opt3);
        arrayList.add(opt4);
        mDatabase.child("quizzes").child(topic).setValue(arrayList);
    }
}