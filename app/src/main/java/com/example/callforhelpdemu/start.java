package com.example.callforhelpdemu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class start extends AppCompatActivity {



    //CREATE KEY FOR SEND DATA FROM ONE ACTIVITY TO ANOTHER ACTIVITY//
   //public static final String Extra ="com.example.callforhelpdemu.Extra";


    //create a object //

    private static int time = 2500;
    ImageView image;
    Animation bganim;


    //THIS FUNCTION WRITE THE sta FILE //

    private String check(String File_Name){
        String st = null;
        FileInputStream fis0 = null;
        try {
            fis0 =openFileInput(File_Name);
            InputStreamReader isr = new InputStreamReader(fis0);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while( (text = br.readLine()) != null ){
                sb.append(text).append("\n");
            }

            st = sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(fis0 != null) {
                try {
                    fis0.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return st;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        //Find the the image//
        image=findViewById(R.id.starting_id);



        //Create the animation and set the image of splash screen//

        bganim= AnimationUtils.loadAnimation(this,R.anim.bganim);

        image.animate().translationY(-2660).setDuration(1800).setStartDelay(500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                 activity();  //Call the function//
                 finish();

            }
        },time);


    }


    //This function decide our project is login or not login//

    private void activity()
    {

        try{
            String comp = check("sta.txt").trim();
            if(comp.equals("Signed In".trim()))
            {
                Intent intent = new Intent(start.this, Home.class);
                startActivity(intent);

            }
            else if(comp.equals("Signed Out".trim()))
            {
                Intent intent = new Intent(start.this, MainActivity.class);
                startActivity(intent);
            }
        }catch(Exception e)
        {
            Intent intent = new Intent(start.this, MainActivity.class);
            startActivity(intent);
        }
    }


}





