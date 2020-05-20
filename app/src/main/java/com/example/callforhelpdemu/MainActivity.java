
//////////////////////////////////*****THIS ACTIVITY IS LOGIN ACTIVITY*****//////////////////////////////////////////////////////

package com.example.callforhelpdemu;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class MainActivity extends AppCompatActivity implements
        View.OnClickListener  {


    //CREATE KEY FOR SEND DATA FROM ONE ACTIVITY TO ANOTHER ACTIVITY//
    public static final String Extra ="com.example.callforhelpdemu.Extra";


    //CREATE A FILE NAME//
    public static final String Stat_File = "sta.txt";
    public static final String user_File = "user.txt";
    public static final String name_File = "name.txt";
    public static final String phone_File = "phone.txt";
    public static final String email_File = "email.txt";
    public static final String DOB_File = "DOB.txt";
    public static final String BLD_File = "blood.txt";


    //CREATE A OBJECT//
    ImageView image1,image3;
    EditText textUsername,textpassword;
    TextView Login,User,ForgetMessage,message,register;
    Button signin;


    //CREATE A DATABASEREFEREBCE OBJECT//
     DatabaseReference userRefLogin;

    public void onBackPressed() {
        final AlertDialog.Builder exit = new AlertDialog.Builder(MainActivity.this);
        exit.setTitle("Alert");
        exit.setMessage("Are you sure to exit this app?");
        exit.setIcon(R.drawable.warn_icon);
        exit.setCancelable(true);
        exit.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        exit.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog alert = exit.create();
        alert.show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //////////////////////////no internet connection function call
        if(!isConnected(MainActivity.this))
            buildDialog(MainActivity.this).show();


        //FIND THE DATABASEREFERENCE OBJECT//

         userRefLogin = FirebaseDatabase.getInstance().getReference().child("Information");







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
        image3=findViewById(R.id.image3);


        //THIS PART IS GET THE STRING FROM REGISTRATION JAVA//

        Intent intent = getIntent();
        String text1 = intent.getStringExtra(registration.Extra);
        String text2 = intent.getStringExtra(registration.Extra1);

        textUsername.setText(text1);
        textpassword.setText(text2);


        signin.setOnClickListener(this);
        register.setOnClickListener(this);
        ForgetMessage.setOnClickListener(this);



    }





    @Override
    public void onClick(View v) {

        final String userId=textUsername.getText().toString();
        final String passId= textpassword.getText().toString().trim();

            switch (v.getId()) {
            case R.id.SigninButton:


                //CHECKING WITH FIREBASE //
                Log.d("Atanu", "onDataChange0: ");

                if(isConnected(MainActivity.this))
                {
                    //FIND THE DATA FROM DATABASE//
                    userRefLogin.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            boolean tree = false;

                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                                Log.d("Atanu", "onDataChange1: ");


                                Information informetion1 = dataSnapshot1.getValue(Information.class);


                                Log.d("Atanu", informetion1.getUserId() + informetion1.getPass());


                                if (userId.equals(informetion1.getUserId()) && passId.equals(informetion1.getPass())) {
                                    tree = true;

                                    Log.d("Atanu", "onDataChange2: ");

                                    Intent intent = new Intent(MainActivity.this, Home.class);
                                    intent.putExtra(Extra, userId);
                                    startActivity(intent);
                                    finish();


                                    Log.d("Atanu", "onDataChange3:");


                                    //CALL THE FUNCTION AND WRITE THE FILE //

                                    saveLoginInfoToFile(informetion1.getFullname(), informetion1.getUserId(), informetion1.getPhoneno(), informetion1.getEmail(), informetion1.getDate(), informetion1.getSpinner());

                                    //WRITE THE FILE ONLY FOR Stat file//
                                    FileOutputStream fos0 = null;
                                    try {
                                        fos0 = openFileOutput(Stat_File, MODE_PRIVATE);
                                        fos0.write("Signed In".getBytes());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                            }
                            if (tree == false) {

                                Toast.makeText(MainActivity.this, "Invalid User Id or Password", Toast.LENGTH_LONG).show();
                            }

                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {


                        }
                    });
                }
                else
                {
                    buildDialog(MainActivity.this).show();
                }




               if(userId.isEmpty())
                {
                    textUsername.setError("Please Enter User Id");
                    textUsername.requestFocus();
                }

                else if(passId.isEmpty())
                {
                    textpassword.setError("Please Enter Password");
                    textpassword.requestFocus();
                }
                else
                    return;

                break;

           case R.id.register:
               if(isConnected(MainActivity.this))
               {
                   Intent intent2 = new Intent(this, registration.class);
                   startActivity(intent2);
                   finish();
                   break;
               }
               else
               {
                   buildDialog(MainActivity.this).show();
               }

            case R.id.Forget:
                if(isConnected(MainActivity.this))
                {

                       Intent intent3 = new Intent(this, ForgetPass.class);
                       startActivity(intent3);
                       finish();

                       break;
               }
                else
                {
                    buildDialog(MainActivity.this).show();
                }

        }
    }

    //THIS FUNCTION WRITE THE FILE//
    public void saveLoginInfoToFile( String name,String user,String phone,String email,String Dob,String Bld)
    {

        FileOutputStream fos1 = null;
        try {
            fos1 = openFileOutput(name_File, MODE_PRIVATE);
            fos1.write(name.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        FileOutputStream fos2 = null;
        try {
            fos2 = openFileOutput(user_File, MODE_PRIVATE);
            fos2.write(user.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        FileOutputStream fos3 = null;
        try {
            fos3 = openFileOutput(phone_File, MODE_PRIVATE);
            fos3.write(phone.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        FileOutputStream fos4 = null;
        try {
            fos4 = openFileOutput(email_File, MODE_PRIVATE);
            fos4.write(email.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        FileOutputStream fos5 = null;
        try {
            fos5 = openFileOutput(DOB_File, MODE_PRIVATE);
            fos5.write(Dob.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        FileOutputStream fos6 = null;
        try {
            fos6 = openFileOutput(BLD_File, MODE_PRIVATE);
            fos6.write(Bld.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //THIS  FUNCTION ONLY READ THE FILE//
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

    ///////////////// no internet  connection

    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
            else
                return false;
        } else
            return false;
    }

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setIcon(R.drawable.warn_icon);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or Wi-Fi to access this.");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        return builder;
    }



}