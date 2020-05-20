package com.example.callforhelpdemu.Nav_UI.Home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.callforhelpdemu.Ambulance;
import com.example.callforhelpdemu.MapsActivity;
import com.example.callforhelpdemu.R;

import java.io.FileOutputStream;


public class HomeFragment extends Fragment implements View.OnClickListener{


    //CREATE A OBJECT //
    private HomeViewModel homeViewModel;
    private CardView HospitalCardView,PoliceStationCardView,FireServiceCardView,AmbulanceCardView;

    public static final String search_txt = "search.txt";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        //FIND THE CARD VIEW//
        HospitalCardView=root.findViewById(R.id.HospitalCardView);
        PoliceStationCardView=root.findViewById(R.id.PoliceStationCardView);
        FireServiceCardView=root.findViewById(R.id.FireServiceCardView);
        AmbulanceCardView=root.findViewById(R.id.AmbulanceCardView);


         HospitalCardView.setOnClickListener(this);
         PoliceStationCardView.setOnClickListener(this);
         FireServiceCardView.setOnClickListener(this);
         AmbulanceCardView.setOnClickListener(this);


        return root;



    }


    //INTENT TO MAPSACTIVITY AND SAVE FILE WITH RESPECTIVE CARD VIEW//
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.HospitalCardView)
        {
            Toast.makeText(getActivity(), "Click 'Hospital' show Nearby Hospitals", Toast.LENGTH_SHORT).show();
            saveFileBtn("Hospital");
            startActivity(new Intent(getActivity(),MapsActivity.class));

        }
        else if(v.getId()==R.id.PoliceStationCardView)
        {
            Toast.makeText(getActivity(), "Click 'Police Station' show Nearby Police Station", Toast.LENGTH_SHORT).show();
            saveFileBtn("Police Station");
            startActivity(new Intent(getActivity(),MapsActivity.class));

        }
        else if(v.getId()==R.id.FireServiceCardView)
        {
            Toast.makeText(getActivity(), "Click 'Fire Service' show Nearby Fire Station", Toast.LENGTH_SHORT).show();
            saveFileBtn("Fire Service");
            startActivity(new Intent(getActivity(),MapsActivity.class));

        }
        else if(v.getId()==R.id.AmbulanceCardView)
        {
            startActivity(new Intent(getActivity(),Ambulance.class));
            Toast.makeText(getActivity(), "Ambulance", Toast.LENGTH_SHORT).show();

        }

    }


    public void saveFileBtn(String file)
    {
        FileOutputStream fos1 = null;
        try {
            fos1 = getActivity().openFileOutput(search_txt, Context.MODE_PRIVATE);
            fos1.write(file.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}