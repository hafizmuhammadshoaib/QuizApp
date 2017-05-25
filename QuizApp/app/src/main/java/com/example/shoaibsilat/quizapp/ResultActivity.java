package com.example.shoaibsilat.quizapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    TextView result;
    Button exitButton;
    Button tryAgain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        result=(TextView)findViewById(R.id.resultview);
        tryAgain=(Button)findViewById(R.id.tryagain);
        exitButton=(Button)findViewById(R.id.exit);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent=getIntent();
                result.setText("Your Score is: "+intent.getStringExtra("score"));
            }
        });


    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Quit")
                .setMessage("Do you really want to Quit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       setResult(RESULT_OK);
                        finish();
                    }
                })
                .setNegativeButton("No",null)
                .setCancelable(false);
        AlertDialog alert=builder.create();
        alert.show();
    }

    public void onClickExit(View v){
        setResult(RESULT_OK);
        finish();
    }
    public  void onClickTryAgain(View v){
        setResult(RESULT_CANCELED);
        finish();
    }
}
