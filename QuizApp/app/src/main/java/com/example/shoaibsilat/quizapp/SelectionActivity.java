package com.example.shoaibsilat.quizapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectionActivity extends AppCompatActivity {

    Button tableQuiz;
    Button generalKnowledge;
    static String tableName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        tableQuiz=(Button)findViewById(R.id.btntablequiz);
        generalKnowledge=(Button)findViewById(R.id.btngeneralknowledge);
        tableQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tableName="tablequiz";
                Intent intent=new Intent(SelectionActivity.this,QuizActivity.class);
                startActivityForResult(intent,3);
            }
        });
        generalKnowledge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tableName="genknow";
                Intent intent=new Intent(SelectionActivity.this,QuizActivity.class);
                startActivityForResult(intent,3);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK&&requestCode==3){
            setResult(RESULT_OK);
            finish();
        }
        else if(resultCode==RESULT_CANCELED&&requestCode==3){
            setResult(RESULT_CANCELED);
            finish();
        }
    }
}
