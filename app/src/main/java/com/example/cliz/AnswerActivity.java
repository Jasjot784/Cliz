package com.example.cliz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Objects;

public class AnswerActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayList<String> correct = new ArrayList<>();
    ArrayList<String> ques = new ArrayList<>();
    ArrayList<String> opt1 = new ArrayList<>();
    ArrayList<String> opt2 = new ArrayList<>();
    ArrayList<String> opt3 = new ArrayList<>();
    ArrayList<String> opt4 = new ArrayList<>();
    ArrayList<String> arrSelect = new ArrayList<>();
    Integer i = 0;
    Integer corr = 0;
    Integer num;
    public static final String TAG = AnswerActivity.class.getSimpleName();
    TextView tvQues;
    RadioButton rb1,rb2,rb3,rb4;
    RadioGroup rg;
    Button buttonNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        tvQues = findViewById(R.id.tvQues);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        rb4 = findViewById(R.id.rb4);
        rg = findViewById(R.id.radio);
        buttonNext = findViewById(R.id.btNext);
        buttonNext.setOnClickListener(this);

        if (getIntent().getExtras()!= null){
            correct = (ArrayList<String>) getIntent().getExtras().get("correct");
            ques = (ArrayList<String>) getIntent().getExtras().get("ques");
            opt1 = (ArrayList<String>) getIntent().getExtras().get("opt1");
            opt2 = (ArrayList<String>) getIntent().getExtras().get("opt2");
            opt3 = (ArrayList<String>) getIntent().getExtras().get("opt3");
            opt4 = (ArrayList<String>) getIntent().getExtras().get("opt4");
            num = correct.size();

            Log.d(TAG, "onCreate: "+correct+ques+opt1+opt2+opt3+opt4);
        }
        tvQues.setText(String.format("Q1:%s", ques.get(0)));
        rb1.setText(String.format("A:%s", opt1.get(0)));
        rb2.setText(String.format("B:%s", opt2.get(0)));
        rb3.setText(String.format("C:%s", opt3.get(0)));
        rb4.setText(String.format("D:%s", opt4.get(0)));
    }

    @Override
    public void onClick(View view) {
        if (Objects.equals(correct.get(i), "A") && rb1.isChecked())corr++;
        if (Objects.equals(correct.get(i), "B") && rb2.isChecked())corr++;
        if (Objects.equals(correct.get(i), "C") && rb3.isChecked())corr++;
        if (Objects.equals(correct.get(i), "D") && rb4.isChecked())corr++;

        int a = rg.getCheckedRadioButtonId();
        RadioButton rb = findViewById(a);
        arrSelect.add(rb.getText().toString());
        i++;
        if (i.equals(num)){
            Intent intent = new Intent(AnswerActivity.this,ResultActivity.class);
            intent.putExtra("Correct",corr);
            intent.putExtra("Total",num);
            intent.putExtra("Answer",correct);
            intent.putExtra("Select",arrSelect);
            startActivity(intent);
        }else{
            tvQues.setText(String.format("Q%d:%s",i+1, ques.get(i)));
            rb1.setText(String.format("A:%s", opt1.get(i)));
            rb2.setText(String.format("B:%s", opt2.get(i)));
            rb3.setText(String.format("C:%s", opt3.get(i)));
            rb4.setText(String.format("D:%s", opt4.get(i)));
            rg.clearCheck();
        }
    }
}