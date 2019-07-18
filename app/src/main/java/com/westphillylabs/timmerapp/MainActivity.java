package com.westphillylabs.timmerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar timmerSeekBar;
    TextView timeTextView;
    Button stopBttn;
    Button startBttn;
    CountDownTimer countDownTimer;
    MediaPlayer alram;
    Boolean countingDown = true;


    public void startTimmer(View view){
        if (countingDown && (timmerSeekBar.getProgress() > 0)) {
            startBttn.setVisibility(View.GONE);
            stopBttn.setVisibility(View.VISIBLE);
            timmerSeekBar.setVisibility(View.INVISIBLE);
            startCountdown(timmerSeekBar.getProgress());
        }

    }

    public void updateTimmer(int timeLeft){

        int min = timeLeft/60;
        int sec = timeLeft - (min*60);

        String doublMin = Integer.toString(min);

        if (doublMin.length() < 2){
            doublMin = "0" + doublMin;
        }

        String doublSec = Integer.toString(sec);

        if (doublSec.length() < 2){
            doublSec = "0" + doublSec;
        }


        timeTextView.setText(doublMin + ":" + doublSec );
    }


    //
    public void stopTimmer(View view){
        startBttn.setVisibility(View.VISIBLE);
        stopBttn.setVisibility(View.INVISIBLE);
        timmerSeekBar.setVisibility(View.VISIBLE);
        timmerSeekBar.setProgress(0);
        countingDown = false;
//        countDownTimer.start();
        countDownTimer.cancel();
        startCountdown(0);
        alram.stop();
    }


    public void startCountdown(int timmerSeekBarGetProgress){

            countDownTimer = new CountDownTimer(timmerSeekBarGetProgress * 1000, 1000) {
                @Override
                public void onTick(long l) {
                    updateTimmer((int) l / 1000);
                }

                @Override
                public void onFinish() {
                    alram = MediaPlayer.create(getApplicationContext(), R.raw.oldbell);
                    startBttn.setVisibility(View.VISIBLE);
                    stopBttn.setVisibility(View.INVISIBLE);
                    timmerSeekBar.setVisibility(View.VISIBLE);
                    timmerSeekBar.setProgress(0);
                    countingDown = true;
                    alram.start();
                }
            }.start();
        }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //
        timmerSeekBar = findViewById(R.id.timmerSeekBar);
        timeTextView = findViewById(R.id.timeTextView);
        stopBttn = findViewById(R.id.stopBttn);
        startBttn = findViewById(R.id.startBttn);

        timmerSeekBar.setMax(6000);
        timmerSeekBar.setProgress(30);


        timmerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimmer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

}
