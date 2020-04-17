


package com.example.callforhelpdemu;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class registration extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //CREATE KEY FOR SEND DATA FROM ONE ACTIVITY TO ANOTHER ACTIVITY//
    public static final String Extra ="com.example.callforhelpdemu.Extra";
    public static final String Extra1 ="com.example.callforhelpdemu.Extra1";



    //OBJECT CREATE//

    TextView fullnameid,emailid_id,phoneno_id,date_id,bloodgrp_id,userid_id,password_id,cpassword_id;

    EditText date,nameid,emailid,phoneid,dob_id,userid,passid,cpassid;

    DatePickerDialog datePickerDialog;

    private Spinner spinner;

    Informetion informetion;

    Button signup_id;

    //Database reference object//
    DatabaseReference  databaseReference;


    //ValueEventListener object//
    ValueEventListener valueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        //DATE FIND//
        date = findViewById(R.id.dob_id);

        //SPINNER FIND//
        spinner = findViewById(R.id.spin_id);


        //TEXTVIEW FIND//
        fullnameid=findViewById(R.id.fullnameid);
        emailid_id=findViewById(R.id.emailid_id);
        phoneno_id=findViewById(R.id.phoneno_id);
        date_id=findViewById(R.id.date_id);
        bloodgrp_id=findViewById(R.id.bloodgrp_id);
        userid_id=findViewById(R.id.userid_id);
        password_id=findViewById(R.id.password_id);
        cpassword_id=findViewById(R.id.cpassword_id);



        //EditText FIND//
        nameid=findViewById(R.id.name_id);
        emailid=findViewById(R.id.emailid);
        phoneid=findViewById(R.id.phoneid);
        dob_id=findViewById(R.id.dob_id);
        userid=findViewById(R.id.userid);
        passid=findViewById(R.id.passid);
        cpassid=findViewById(R.id.cpassid);


        //BUTTON FIND//

        signup_id=findViewById(R.id.signup_id);



        //DATABASE REFERENCE FIND//

        databaseReference= FirebaseDatabase.getInstance().getReference("Information");





        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.bloodGrp_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);



        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);


                datePickerDialog = new DatePickerDialog(registration.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                        date.setText(i2+"/"+(i1+1)+"/"+i);

                    }
                },day,month,year);
                datePickerDialog.show();


            }
        });




        //CREATE ACTION LISTENER FOR SIGNUP BUTTON//

        signup_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String fullname=nameid.getText().toString();//GET STRING FROM FULL NAME ID//
                String Emailid=emailid.getText().toString().trim();//GET STRING FROM EMAIL ID//
                String phoneno=phoneid.getText().toString().trim() ;//GET INTEGER FROM PHONE NUMBER//
                //int phoneno1=Integer.parseInt(phoneno_id.getText().toString().trim()) ;//GET INTEGER FROM PHONE NUMBER//
                String date=date_id.getText().toString();//GET INTEGER FROM DATE//
                String UserId=userid.getText().toString(); //GET STRING FROM USERID//
                String PassId=passid.getText().toString().trim(); //GET STRING FROM PASSID//
                String ConfermPass=cpassid.getText().toString().trim();//GET STRING FROM CONFERM PASSWORD//
                String Spinner = spinner.getSelectedItem().toString();//GET STRING FROM Spinner//
                String Date = dob_id.getText().toString();//GET STRING FROM datePicker//





            //CHECK THE ALL CONDITION FOR THE ALL EDITTEXT//

                if(!fullname.isEmpty() && !Emailid.isEmpty() && !UserId.isEmpty() && !PassId.isEmpty() && !ConfermPass.isEmpty() &&  !phoneno.isEmpty() && !date.isEmpty())
                {

                    if(!PassId.equals(ConfermPass))
                {
                    cpassid.setError("Your Confirm Password Doesn't match ");
                    cpassid.requestFocus();
                    return;

                }

                    if(!Patterns.EMAIL_ADDRESS.matcher(Emailid).matches())
                    {
                        emailid.setError("Enter a valid email address");
                        emailid.requestFocus();
                        return;

                    }

                    if(PassId.length()<8)
                    {
                        passid.setError("Minimum length of password should be 8");
                        passid.requestFocus();
                        return;

                    }
                   if(phoneid.length()<11)
                    {
                        phoneno_id.setError("Minimum length of password should be 11");
                        phoneno_id.requestFocus();
                        return;

                    }



                    //IF EVERY THING IS RIGHT WHEN INTENT THE REGISTRATION ACTIVITY TO MAIN ACTIVITY AND RELOAD THE DATA//

                    Intent intent=new Intent(registration.this,MainActivity.class);
                    intent.putExtra(Extra,UserId);
                    intent.putExtra(Extra1,PassId);
                    startActivity(intent);
                }



                 else if(fullname.isEmpty())
                {
                    nameid.setError("Please Enter Name");
                    nameid.requestFocus();
                }


                else if(Emailid.isEmpty())
                {
                    emailid.setError("Please Enter Email Id");
                    emailid.requestFocus();
                }
                else if(UserId.isEmpty())
                {
                    userid.setError("Please Enter User Id");
                    userid.requestFocus();
                }
                else if(phoneno.isEmpty())
                {
                    phoneid.setError("Please Enter PhoneNO");
                    phoneid.requestFocus();
                }
                else if(PassId.isEmpty())
                {
                    passid.setError("Please Enter Password");
                    passid.requestFocus();
                }
                else if(ConfermPass.isEmpty())
                {
                    cpassid.setError("Please Enter Confirm Password");
                    cpassid.requestFocus();
                }



                else

                    return;





        //FIND THE DATA BASE KEY & PASS THE OBJECT FROM REGISTRATION  to information java class//

        String key=databaseReference.push().getKey();

            Informetion informetion=new Informetion(fullname,phoneno,UserId,Spinner,Date);


            //create a child and pass the key and set the value in child//

            databaseReference.child(key).setValue(informetion);
            Toast.makeText(getApplicationContext(),"Your Information is addes",Toast.LENGTH_SHORT).show();


            }




        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

