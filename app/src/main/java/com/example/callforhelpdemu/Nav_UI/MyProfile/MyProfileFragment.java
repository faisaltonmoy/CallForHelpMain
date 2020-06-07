package com.example.callforhelpdemu.Nav_UI.MyProfile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.callforhelpdemu.Home;
//import com.example.callforhelpdemu.Informetion;
import com.example.callforhelpdemu.R;
import com.google.firebase.database.DatabaseReference;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MyProfileFragment extends Fragment{
    //*********************** CREATE KEY FOR SEND DATA FROM ONE ACTIVITY TO ANOTHER ACTIVITY ********************//
    public static final String Extra ="com.example.callforhelpdemu.Extra";


    //**************** CREATE A OBJECT *******************//
    private MyProfileViewModel myProfileViewModel;
    private TextView name,phone,email,dob,bld;

    DatabaseReference userRef;
    Home home = new Home();

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myProfileViewModel =
                ViewModelProviders.of(this).get(MyProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_my_profile, container, false);

        //****************** FIND THE OBJECT ***********************//
        name = root.findViewById(R.id.pro_name);
        phone = root.findViewById(R.id.pro_phn);
        email = root.findViewById(R.id.pro_email);
        dob = root.findViewById(R.id.pro_Dob);
        bld = root.findViewById(R.id.pro_bld);


        name.setText(check("name.txt"));
        phone.setText(check("phone.txt"));
        email.setText(check("email.txt"));
        dob.setText(check("DOB.txt"));
        bld.setText(check("blood.txt"));

        return root;

    }

    private String check(String File_Name){

        String st = null;
        FileInputStream fis0 = null;
        try {
            fis0 =getActivity().openFileInput(File_Name);
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
        return st;      //returns string//

    }

}