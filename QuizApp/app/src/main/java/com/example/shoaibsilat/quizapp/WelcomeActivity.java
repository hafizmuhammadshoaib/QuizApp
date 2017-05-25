package com.example.shoaibsilat.quizapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {

    Button startButton;
    Button quitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startButton=(Button)findViewById(R.id.startbtn);
                quitButton=(Button)findViewById(R.id.quitbtn);
                quitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
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
                        WelcomeActivity.super.onBackPressed();
                        //finish();
                    }
                })
                .setNegativeButton("No",null)
        .setCancelable(false);
        AlertDialog alert=builder.create();
        alert.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK&& requestCode==1){
//            System.out.println("result_ok");
            finish();
        }
        else if(resultCode==RESULT_CANCELED&&requestCode==1){
  //          System.out.println("result_canceled");
        }

    }

    public void clickStartButton(View v){
        Intent oIntent=new Intent(this,SelectionActivity.class);
        startActivityForResult(oIntent,1);
    }
}
