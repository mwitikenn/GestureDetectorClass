package com.watiwat.iknowyourtouch;

import android.graphics.Color;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button button1, button2;
    TextToSpeech myTts;
    TextView text;
    private GestureDetectorCompat detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = findViewById(R.id.button1);
        text = findViewById(R.id.textview);
        detector= new GestureDetectorCompat(this, new MyGestureListener());
        button1.setOnTouchListener(touchListener);
        myTts =new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i ==TextToSpeech.SUCCESS){
                    int result = myTts.setLanguage(Locale.ENGLISH);
                    if (result == TextToSpeech.LANG_NOT_SUPPORTED || result ==TextToSpeech.LANG_AVAILABLE){

                        Toast.makeText(MainActivity.this, "No message to display / Language not supported", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(MainActivity.this, "Failed to initialize text", Toast.LENGTH_SHORT).show();
                }
            }
        });


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }

        });
    }
    private void speak(String message){
        myTts.setSpeechRate(1f);
        myTts.setPitch(1f);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            myTts.speak(message,TextToSpeech.QUEUE_FLUSH,null,null);
        } else {
            myTts.speak(message, TextToSpeech.QUEUE_FLUSH, null);
        }


    }
    View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // pass the events to the gesture detector
            // a return value of true means the detector is handling it
            // a return value of false means the detector didn't
            // recognize the event
            return detector.onTouchEvent(event);

        }
    };

    @Override
    protected void onDestroy() {
        if (myTts != null) {
            myTts.stop();
            myTts.shutdown();
        }

        super.onDestroy();
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent event) {
            return true;
        }


        @Override
        public void onLongPress(MotionEvent e) {
            String message ="You long pressed me!!";
            text.setText(message);
            text.setTextColor(Color.parseColor("#B026DB"));
            speak(message);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            String message ="You double tapped me!!";
            text.setText(message);
            text.setTextColor(Color.parseColor("#F76300"));
            speak(message);
            return true;
        }

    }
}
