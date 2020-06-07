


package com.example.callforhelpdemu;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class registration extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //*************** CREATE KEY FOR SEND DATA FROM ONE ACTIVITY TO ANOTHER ACTIVITY ****************//
    public static final String Extra ="com.example.callforhelpdemu.Extra";
    public static final String Extra1 ="com.example.callforhelpdemu.Extra1";



    //******************* OBJECT CREATE **************************//

    TextView fullnameid,emailid_id,phoneno_id,date_id,bloodgrp_id,userid_id,password_id,cpassword_id;

    EditText date,nameid,emailid,phoneid,dob_id,userid,passid,cpassid;

    DatePickerDialog datePickerDialog;

    private Spinner spinner;

    Information information;

    Button signup_id;



    //*************** Database reference object ***********************//
    DatabaseReference  databaseReference,regisdatabase;



    //****************** Fire base Database object *********************//
    private  FirebaseDatabase firebaseDatabase;



    //****************** ValueEventListener object ********************//
    ValueEventListener valueEventListener;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        //************* DATE FIND ****************//
        date = findViewById(R.id.dob_id);

        //************* SPINNER FIND *************//
        spinner = findViewById(R.id.spin_id);


        //************** TEXT VIEW FIND *************//
        fullnameid=findViewById(R.id.fullnameid);
        emailid_id=findViewById(R.id.emailid_id);
        phoneno_id=findViewById(R.id.phoneno_id);
        date_id=findViewById(R.id.date_id);
        bloodgrp_id=findViewById(R.id.bloodgrp_id);
        userid_id=findViewById(R.id.userid_id);
        password_id=findViewById(R.id.password_id);
        cpassword_id=findViewById(R.id.cpassword_id);



        //************* EditText FIND ***************//
        nameid=findViewById(R.id.name_id);
        emailid=findViewById(R.id.emailid);
        phoneid=findViewById(R.id.phoneid);
        dob_id=findViewById(R.id.dob_id);
        userid=findViewById(R.id.userid);
        passid=findViewById(R.id.passid);
        cpassid=findViewById(R.id.cpassid);


        //************** BUTTON FIND ***************//

        signup_id=findViewById(R.id.signup_id);



        //************* DATABASE REFERENCE FIND **************//

        databaseReference= FirebaseDatabase.getInstance().getReference("Information");
        regisdatabase=FirebaseDatabase.getInstance().getReference("Information");





        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.bloodGrp_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);



        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int month = calendar.get(Calendar.MONTH);
                final int year = calendar.get(Calendar.YEAR);


                datePickerDialog = new DatePickerDialog(registration.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                        date.setText(i2+"/"+(i1+1)+"/"+i);

                    }
                },day,month,year);
                datePickerDialog.show();


            }
        });




        //********** CREATE ACTION LISTENER FOR SIGN UP BUTTON *****************//

        signup_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String fullname=nameid.getText().toString();//GET STRING FROM FULL NAME ID//
                final String Emailid=emailid.getText().toString().trim();//GET STRING FROM EMAIL ID//
                final String phoneno=phoneid.getText().toString().trim() ;//GET INTEGER FROM PHONE NUMBER//
                //int phoneno1=Integer.parseInt(phoneno_id.getText().toString().trim()) ;//GET INTEGER FROM PHONE NUMBER//
                String date=date_id.getText().toString();//GET INTEGER FROM DATE//
                final String UserId=userid.getText().toString(); //GET STRING FROM USERID//
                final String PassId=passid.getText().toString().trim(); //GET STRING FROM PASSID//
                String ConfermPass=cpassid.getText().toString().trim();//GET STRING FROM CONFERM PASSWORD//
                final String Spinner = spinner.getSelectedItem().toString();//GET STRING FROM Spinner//
                final String Date = dob_id.getText().toString();//GET STRING FROM datePicker//


            if(isConnected(registration.this))
            {

            //************ CHECK THE ALL CONDITION FOR THE ALL EDIT TEXT ***********************//

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

                    regisdatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            boolean tree=false;

                            for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                            {

                                Information informetion1=dataSnapshot1.getValue(Information.class);


                                    if(phoneno.equals(informetion1.getPhoneno()))
                                    {
                                        tree=true;
                                        phoneid.setError("Please Enter Another PhoneNO");
                                        phoneid.requestFocus();

                                    }

                                    if(UserId.equals(informetion1.getUserId()))
                                    {
                                        tree=true;
                                        userid.setError("Please Enter Another User Id");
                                        userid.requestFocus();


                                    }





                            }
                            if(tree==false)
                            {
                                //****************** IF EVERY THING IS RIGHT WHEN INTENT THE REGISTRATION ACTIVITY TO MAIN ACTIVITY AND RELOAD THE DATA ****************//

                                Intent intent=new Intent(registration.this,MainActivity.class);
                                intent.putExtra(Extra,UserId);
                                intent.putExtra(Extra1,PassId);
                                startActivity(intent);

                                finish();

                                Toast.makeText(registration.this, "Registration Completed", Toast.LENGTH_SHORT).show();


                                //**************** CREATE A REALTIME DATABASE ************************//

                                Information information=new Information(fullname,phoneno,UserId,Spinner,Date,PassId,Emailid);
                                String key=UserId;
                                databaseReference.child(key).setValue(information);


                            }



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });




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


            }
            else
            {
                buildDialog(registration.this).show();
            }


            }



        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
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

