package com.example.cliz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
    Integer tot;
    Integer corr;
    Integer incorr;
    TextView tvCorr,tvIncorr,tvTot,tvAns,tvSelect;
    ArrayList<String> arrCorrect = new ArrayList<>();
    ArrayList<String> arrSelect = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        tvCorr = findViewById(R.id.tvCorr);
        tvIncorr = findViewById(R.id.tvIncorr);
        tvTot = findViewById(R.id.tvTot);
        tvAns = findViewById(R.id.tvAnswer);
        tvSelect = findViewById(R.id.tvSelect);

        if (getIntent().getExtras()!= null){
            corr = getIntent().getExtras().getInt("Correct");
            tot = getIntent().getExtras().getInt("Total");
            arrCorrect = (ArrayList<String>) getIntent().getExtras().get("Answer");
            arrSelect = (ArrayList<String>) getIntent().getExtras().get("Select");
            Toast.makeText(this, corr.toString(), Toast.LENGTH_LONG).show();
            incorr = tot-corr;
        }
        float d = (float)corr/((float)(corr+incorr) );
        d*=100;
        tvCorr.setText(String.format("Correct:%d", corr));
        tvIncorr.setText(String.format("Incorrect:%d", incorr));
        tvTot.setText("Score: "+d+"%");
        tvAns.setText("Answer Key:"+arrCorrect.toString());
        tvSelect.setText("Selected Choices:"+arrSelect.toString());
//        tvTot.setText(String.format("Score:%f", d));
    }
}