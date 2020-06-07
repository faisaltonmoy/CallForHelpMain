package com.example.callforhelpdemu.Nav_UI.AboutUs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;


import static com.example.callforhelpdemu.R.*;

public class AboutUsFragment extends Fragment implements View.OnClickListener {
    AboutUsViewModel aboutUsViewModel;


    //********************* CREATE THE OBJECT ***********************//
    ImageView image1 , image2 , image3;





    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
         aboutUsViewModel = ViewModelProviders.of(this).get(AboutUsViewModel.class);
        View root = inflater.inflate(layout.fragment_about_us, container, false);

        //*************** FIND THE OBJECT ********************//
         image1 =root.findViewById(id.atanu);
         image2 =root.findViewById(id.mahin);
         image3 =root.findViewById(id.tonmoy);

         image1.setOnClickListener(this);
         image2.setOnClickListener(this);
         image3.setOnClickListener(this);




        return root;
    }


    @Override
    public void onClick(View v) {

        if(v.getId()== id.atanu)
        {
            image1.setVisibility(v.GONE);
            image2.setVisibility(v.VISIBLE);
        }
        if(v.getId()== id.mahin)
        {
            image2.setVisibility(v.GONE);
            image3.setVisibility(v.VISIBLE);
        }if(v.getId()== id.tonmoy)
        {
            image3.setVisibility(v.GONE);
            image1.setVisibility(v.VISIBLE);
        }

    }
}
