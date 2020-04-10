package com.example.callforhelpdemu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView image1,image2,image3;
    EditText textUsername,textpassword;
    TextView Login,User,ForgetMessage,message,regester;
    Button signin;
    Animation bganim;//Animition image object//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TEXT VIEW FIND//
        Login=findViewById(R.id.Login);
        User=findViewById(R.id.User);
        ForgetMessage=findViewById(R.id.Forget);
        message=findViewById(R.id.message);
        regester=findViewById(R.id.regester);



        // EDIT TEXT FIND//
        textUsername=findViewById(R.id.USERNAMEEDIT);
        textpassword=findViewById(R.id.PasswordEdit);


        //BUTTON FIND//

        signin=findViewById(R.id.SigninButton);




        //IMAGE FIND//
        image1=findViewById(R.id.image1);
        image2=findViewById(R.id.image2);
        image3=findViewById(R.id.image3);


        //ANIMATION FIND//
        bganim= AnimationUtils.loadAnimation(this,R.anim.bganim);



        //ANIMATION WORK//
        image2.animate().translationY(-2660).setDuration(1800).setStartDelay(500);

        image1.animate().translationY(0).setDuration(1800).setStartDelay(500);
        image3.animate().translationY(25).setDuration(3700).setStartDelay(500);

    }
}
