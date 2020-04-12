package com.example.callforhelpdemu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class start extends AppCompatActivity {

    private static int time = 2500;
    ImageView image;
    Animation bganim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        image=findViewById(R.id.starting_id);

        bganim= AnimationUtils.loadAnimation(this,R.anim.bganim);

        image.animate().translationY(-2660).setDuration(1800).setStartDelay(500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(start.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        },time);


    }

}
