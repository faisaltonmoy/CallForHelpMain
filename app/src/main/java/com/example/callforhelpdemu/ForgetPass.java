package com.example.callforhelpdemu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class ForgetPass extends AppCompatActivity implements View.OnClickListener {

    //CREATE KEY FOR SEND DATA FROM ONE ACTIVITY TO ANOTHER ACTIVITY//
    public static final String Extra3 ="com.example.callforhelpdemu.Extra3";
    public static final String Extra4 ="com.example.callforhelpdemu.Extra4";

    //CREATE THE OBJECT//
    EditText email, phn;
    TextView user, pass, textBtn;
    Button send;

    //CREATE THE FILE NAME//
    public static final String ForgetuserId_txt = "forget_user.txt";
    public static final String Forgetpass_txt = "forget_pass.txt";

    //CREATE THE DATABASE REFERENCE OBJECT//
    DatabaseReference Forgetpass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);

        //FIND THE DATABASE REFERENCE//
        Forgetpass = FirebaseDatabase.getInstance().getReference().child("Information");

        //FIND THE OBJECT//
        email = findViewById(R.id.emailid);
        phn = findViewById(R.id.phnid);
        user = findViewById(R.id.userid);
        pass = findViewById(R.id.passid);
        textBtn = findViewById(R.id.textid);
        send = findViewById(R.id.send_id);

        send.setOnClickListener(this);
        textBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        //GET THE INFORMATION FROM USER//
        final String emailId = email.getText().toString().trim();
        final String phnId = phn.getText().toString().trim();

        switch (v.getId()) {

            case R.id.send_id:

                Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();

                 //DATA RETRIVE FROM DATABASE//

                Forgetpass.addListenerForSingleValueEvent(new ValueEventListener() {

                    boolean tree = false;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                            Information forget = dataSnapshot1.getValue(Information.class);

                            if (emailId.equals(forget.getEmail()) && phnId.equals(forget.getPhoneno())) {
                                tree = true;
                                Toast.makeText(ForgetPass.this, "Correct Information", Toast.LENGTH_SHORT).show();
                                saveData(forget.getUserId(), forget.getPass());
                                Toast.makeText(ForgetPass.this, "Click on the text below", Toast.LENGTH_SHORT).show();

                                user.setText(check("forget_user.txt"));
                                pass.setText(check("forget_pass.txt"));
                            }
                        }
                        if(tree==false) {
                            Toast.makeText(ForgetPass.this, "Wrong Information", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                if(emailId.isEmpty())
                {
                    email.setError("Please Enter Email Id");
                    email.requestFocus();
                }

                else if(phnId.isEmpty())
                {
                    phn.setError("Please Enter Phone no");
                    phn.requestFocus();
                }
                else
                    return;

                break;
            case R.id.textid:

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();

                break;


        }

    }

    //THIS FUNCTION IS WRITE FILE//

    public void saveData(String user, String pass)
    {
        FileOutputStream fos1 = null;
        try {
            fos1 = openFileOutput(ForgetuserId_txt, MODE_PRIVATE);
            fos1.write(user.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        FileOutputStream fos2 = null;
        try {
            fos2 = openFileOutput(Forgetpass_txt, MODE_PRIVATE);
            fos2.write(pass.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //THIS FUNCTION IS READ FILE//
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
}
