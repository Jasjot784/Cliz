package com.example.cliz;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class QuizDataManager {
    private final DatabaseReference mDatabase;

    public QuizDataManager(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public Task<List<ArrayList<ArrayList<String>>>> retreiveAllQuizData(){
        DatabaseReference quizzesRef = mDatabase.child("quizzes");
        return quizzesRef.get().continueWithTask(task -> {
            List<Task<ArrayList<ArrayList<String>>>> tasks = new ArrayList<>();
            if (task.isSuccessful()){
                DataSnapshot dataSnapshot = task.getResult();
                if (dataSnapshot.exists()){
                    for (DataSnapshot topicSnapshot:dataSnapshot.getChildren()){
                        String topic = topicSnapshot.getKey();
                        tasks.add(retreiveQuizData(topic));
                    }
                }
            }
            return Tasks.whenAllSuccess(tasks);
        });
    }

    private Task<ArrayList<ArrayList<String>>> retreiveQuizData(String topic){
        DatabaseReference quizRef = mDatabase.child("quizzes").child(topic);
        return quizRef.get().continueWith(task -> {
            ArrayList<ArrayList<String>> quizData = new ArrayList<>();
            if (task.isSuccessful()){
                DataSnapshot dataSnapshot = task.getResult();
                if (dataSnapshot.exists()){
                    for (DataSnapshot quesSnapshot:dataSnapshot.getChildren()){
                        ArrayList<String> quesData = new ArrayList<>();
                        for(DataSnapshot childSnapshot : quesSnapshot.getChildren()){
                            quesData.add(childSnapshot.getValue(String.class));
                        }
                        quizData.add(quesData);
                    }
                }
            }    return  quizData;
        });
    }
}
