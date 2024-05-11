package com.example.cliz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    FloatingActionButton fab;
    public static final String TAG = MainActivity.class.getSimpleName();

    private DatabaseReference mDatabase;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> keyList = new ArrayList<>();
    List<ArrayList<ArrayList<String>>> allQuizData = new ArrayList<>();
    private QuizDataManager quizDataManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listview);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,keyList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedKey = keyList.get(i);
                ArrayList<ArrayList<String>> arrayList = allQuizData.get(i);
                Intent intent = new Intent(MainActivity.this,AnswerActivity.class);
                intent.putExtra("topic",selectedKey);
                intent.putStringArrayListExtra("correct",arrayList.get(0));
                intent.putStringArrayListExtra("ques",arrayList.get(1));
                intent.putStringArrayListExtra("opt1",arrayList.get(2));
                intent.putStringArrayListExtra("opt2",arrayList.get(3));
                intent.putStringArrayListExtra("opt3",arrayList.get(4));
                intent.putStringArrayListExtra("opt4",arrayList.get(5));
                startActivity(intent);
            }
        });

        fetchdata();
//        Log.d(TAG, "onCreate2: "+allQuizData);
    }
    public void fetchdata(){
        mDatabase = FirebaseDatabase.getInstance().getReference("quizzes");

        mDatabase.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                DataSnapshot dataSnapshot = task.getResult();
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        String quizKey = snapshot.getKey();
                        keyList.add(quizKey);
                        ArrayList<ArrayList<String>> quizValue = (ArrayList<ArrayList<String>>) snapshot.getValue();
                        Log.d(TAG, "fetchdata: "+quizValue);
                        allQuizData.add(quizValue);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
    public void fetch(){
        quizDataManager = new QuizDataManager();
        quizDataManager.retreiveAllQuizData().addOnCompleteListener(new OnCompleteListener<List<ArrayList<ArrayList<String>>>>() {
            @Override
            public void onComplete(@NonNull Task<List<ArrayList<ArrayList<String>>>> task) {
                if (task.isSuccessful()){
                    allQuizData = task.getResult();
                }
            }
        });
    }



    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this,QuizActivity.class);
        startActivity(intent);
    }
}