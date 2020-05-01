package com.example.callforhelpdemu.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.callforhelpdemu.Home2;
import com.example.callforhelpdemu.Informetion;
import com.example.callforhelpdemu.MainActivity;
import com.example.callforhelpdemu.R;
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

public class GalleryFragment extends Fragment{
    //CREATE KEY FOR SEND DATA FROM ONE ACTIVITY TO ANOTHER ACTIVITY//
    public static final String Extra ="com.example.callforhelpdemu.Extra";


    //CREATE A OBJECT//
    private GalleryViewModel galleryViewModel;
    private TextView name,phone,email,dob,bld;

    DatabaseReference userRef;
    Home2 home = new Home2();

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        //FIND THE OBJECT//
        name = root.findViewById(R.id.pro_name);
        phone = root.findViewById(R.id.pro_phn);
        email = root.findViewById(R.id.pro_email);
        dob = root.findViewById(R.id.pro_Dob);
        bld = root.findViewById(R.id.pro_bld);

       /* final String userId = home.check("user.txt");

        userRef= FirebaseDatabase.getInstance().getReference("Information");

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnap:dataSnapshot.getChildren())
                {

                    Log.d("Atanu", "onDataChange1: ");


                    Informetion info=dataSnap.getValue(Informetion.class);


                   // Log.d("Atanu", home.check("user.txt") );


                    if(userId.equals(info.getUserId()))
                    {
                        show(info.getFullname(),info.getPhoneno(),info.getEmail(),info.getDate(),info.getSpinner());
                    }

                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });*/
        return root;

    }

   /* private void show(String fullname, String phoneno, String Email, String date, String spinner) {
        name.setText(fullname);
        phone.setText(phoneno);
        email.setText(Email);
        dob.setText(date);
        bld.setText(spinner);
    }*/

}