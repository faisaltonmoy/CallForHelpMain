package com.example.callforhelpdemu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView image1,image3;
    EditText textUsername,textpassword;
    TextView Login,User,ForgetMessage,message,register;
    Button signin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TEXT VIEW FIND//
        Login=findViewById(R.id.Login);
        User=findViewById(R.id.User);
        ForgetMessage=findViewById(R.id.Forget);
        message=findViewById(R.id.message);
        register=findViewById(R.id.register);



        // EDIT TEXT FIND//
        textUsername=findViewById(R.id.USERNAMEEDIT);
        textpassword=findViewById(R.id.PasswordEdit);


        //BUTTON FIND//

        signin=findViewById(R.id.SigninButton);




        //IMAGE FIND//
        image1=findViewById(R.id.image1);
        image3=findViewById(R.id.image3);


        //THIS PART IS GET THE STRING FROM REGISTRATION JAVA//

        Intent intent = getIntent();
        String text1 = intent.getStringExtra(registration.Extra);
        String text2 = intent.getStringExtra(registration.Extra1);
        textUsername.setText(text1);
        textpassword.setText(text2);




        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RegistrationActivity();

            }
        });


    }


    public void RegistrationActivity(){

        Intent intent = new Intent(this,registration.class);
        startActivity(intent);
        finish();

    }
}
