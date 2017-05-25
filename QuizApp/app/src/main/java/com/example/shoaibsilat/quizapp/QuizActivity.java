package com.example.shoaibsilat.quizapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.CountDownTimer;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class QuizActivity extends AppCompatActivity {

    int qCount;
    int count;
    List <Integer> id;
    Button nextButton;
    RadioGroup rdBtnGrp;
    RadioButton rBtn1;
    RadioButton rBtn2;
    RadioButton rBtn3;
    RadioButton rBtn4;
    String [] defaultAns;
    TextView textView;
    TextView time;
    long startTime=50000;
    long timeInterval=1000;
    long currentTime;
    DatabaseAccess databaseAccess;
    String uAns[];
    MyCounter countDownTimer;
    int rdBtnCount=0;
    TextView correctAnsView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_quiz);



        count=0;
        qCount=1;
        id=new ArrayList<>();
        for(int i=0;i<=4;i++){
            id.add(i);
        }
        Collections.shuffle(id);
        rdBtnGrp=(RadioGroup)findViewById(R.id.rgrpbtn);
        rBtn1=(RadioButton)findViewById(R.id.rdbtn1);
        rBtn2=(RadioButton)findViewById(R.id.rdbtn2);
        rBtn3=(RadioButton)findViewById(R.id.rdbtn3);
        rBtn4=(RadioButton)findViewById(R.id.rdbtn4);
        textView=(TextView)findViewById(R.id.quesView);
        nextButton=(Button)findViewById(R.id.nxtbtn);
        time=(TextView)findViewById(R.id.timer);
        correctAnsView=(TextView)findViewById(R.id.correctAnsView);
        uAns=new String[5];
        defaultAns=new String[5];
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                accessMethod() ;
                countDownTimer=new MyCounter(startTime,timeInterval);
                countDownTimer.start();

            }
        });



    }
    public void accessMethod(){
        databaseAccess=DatabaseAccess.getInstance(this);
        databaseAccess.open();
        textView.setText("Q"+qCount+":"+databaseAccess.getQues(id.get(count)));
        List <String> options=databaseAccess.getOptions(id.get(count));
        rBtn1.setText(options.get(0));
        rBtn2.setText(options.get(1));
        rBtn3.setText(options.get(2));
        rBtn4.setText(options.get(3));
        defaultAns[id.get(count)]=databaseAccess.getAns(id.get(count));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK&&requestCode==2){
            setResult(RESULT_OK);
            finish();
        }
        else if(resultCode==RESULT_CANCELED&&requestCode==2){
            setResult(RESULT_CANCELED);
            finish();
        }
    }

    public void onClickNext(View v){

        if(rBtn1.isChecked()==false&&rBtn2.isChecked()==false&&rBtn3.isChecked()==false&&rBtn4.isChecked()==false){
            Toast.makeText(this, "Please select any option", Toast.LENGTH_SHORT).show();
        }
       else if(count<=3){
            qCount++;
            count++;
            rdBtnCount=0;
           rdBtnGrp.clearCheck();
            correctAnsView.setText("");
            accessMethod();
        }
        else{
           /* for (String ans:uAns) {

                System.out.println("user ans: "+ans);
            }
            for (String ans1:defaultAns) {
                System.out.println("default ans:"+ ans1);

            }*/



           Thread thread=new Thread(new Runnable() {
               @Override
               public void run() {
                   databaseAccess.close();
                   countDownTimer.cancel();
                   Intent oIntent=new Intent(QuizActivity.this,ResultActivity.class);
                   oIntent.putExtra("score",correctAns());

                   startActivityForResult(oIntent,2);
               }
           });
            thread.start();


        }

    }
    public void onClickRadioButton(View v){
        boolean checked=((RadioButton)v).isChecked();
        if(rdBtnCount==0) {
            switch (v.getId()) {
                case R.id.rdbtn1:
                    uAns[id.get(count)] = rBtn1.getText().toString();
                    rdBtnCount++;
                    if(rBtn1.getText().toString().equals(defaultAns[id.get(count)]))
                        Toast.makeText(this, "CORRECT", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(this, "WRONG", Toast.LENGTH_SHORT).show();
                    correctAnsView.setText("Correct Ans is: "+defaultAns[id.get(count)]);
                    break;
                case R.id.rdbtn2:
                    uAns[id.get(count)] = rBtn2.getText().toString();
                    rdBtnCount++;
                    if(rBtn2.getText().toString().equals(defaultAns[id.get(count)]))
                        Toast.makeText(this, "CORRECT", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(this, "WRONG", Toast.LENGTH_SHORT).show();
                    correctAnsView.setText("Correct Ans is: "+defaultAns[id.get(count)]);
                    break;
                case R.id.rdbtn3:
                    uAns[id.get(count)] = rBtn3.getText().toString();
                    rdBtnCount++;
                    if(rBtn3.getText().toString().equals(defaultAns[id.get(count)]))
                        Toast.makeText(this, "CORRECT", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(this, "WRONG", Toast.LENGTH_SHORT).show();
                    correctAnsView.setText("Correct Ans is: "+defaultAns[id.get(count)]);
                    break;
                case R.id.rdbtn4:
                    uAns[id.get(count)] = rBtn4.getText().toString();
                    rdBtnCount++;
                    if(rBtn4.getText().toString().equals(defaultAns[id.get(count)]))
                        Toast.makeText(this, "CORRECT", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(this, "WRONG", Toast.LENGTH_SHORT).show();
                    correctAnsView.setText("Correct Ans is: "+defaultAns[id.get(count)]);
                    break;
            }
        }


    }
    public String correctAns(){
        int noOfCorrectAns=0;
    for (int i=0;i<defaultAns.length;i++){
       try {
           if (uAns[i].equals(defaultAns[i])) {
               noOfCorrectAns++;

           }
       }catch (NullPointerException e){

       }
    }

        return String.valueOf(10*noOfCorrectAns);
    }
    class MyCounter extends CountDownTimer{
public MyCounter(long startTime,long timeInterval){
    super(startTime,timeInterval);
}
        @Override
        public void onTick(long l) {
            currentTime=l;
            if((currentTime/timeInterval)<10)
                time.setText("0:0"+(currentTime/timeInterval));
            else
            {
                time.setText("0:"+(currentTime/timeInterval));
            }
        }

        @Override
        public void onFinish() {
            time.setText("0:00");
            Toast.makeText(QuizActivity.this, "Times Up!!!!!", Toast.LENGTH_SHORT).show();
            databaseAccess.close();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Intent oIntent=new Intent(QuizActivity.this,ResultActivity.class);
                    oIntent.putExtra("score",correctAns());

                    startActivityForResult(oIntent,2);

                }
            });

        }
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
       countDownTimer.cancel();
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Quit")
                .setMessage("Do you really want to quit this Quiz?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       // QuizActivity.super.onBackPressed();
                        setResult(RESULT_CANCELED);
                        countDownTimer.cancel();
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       new MyCounter(currentTime,timeInterval).start();

                    }
                })
                .setCancelable(false);
        AlertDialog alert=builder.create();
        alert.show();
    }

}
