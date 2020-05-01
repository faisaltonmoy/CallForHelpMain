package com.example.callforhelpdemu.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.callforhelpdemu.Home2;
import com.example.callforhelpdemu.MainActivity;
import com.example.callforhelpdemu.R;

import static android.widget.Toast.LENGTH_SHORT;
import static com.example.callforhelpdemu.R.string.Hospital;

public class HomeFragment extends Fragment implements View.OnClickListener{


    //CREATE A OBJECT //
    private HomeViewModel homeViewModel;
    private CardView HospitalCardView,PoliceStationCardView,FireServiceCardView,AmbulanceCardView;

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


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.HospitalCardView)
        {
           Toast.makeText(getActivity(), "Hospital", Toast.LENGTH_SHORT).show();
         //  startActivity(new Intent(getActivity(),MainActivity.class));//


        }
        else if(v.getId()==R.id.PoliceStationCardView)
        {
            Toast.makeText(getActivity(), "PoliceStation", Toast.LENGTH_SHORT).show();

        }
        else if(v.getId()==R.id.FireServiceCardView)
        {
            Toast.makeText(getActivity(), "FireService", Toast.LENGTH_SHORT).show();

        }
        else if(v.getId()==R.id.AmbulanceCardView)
        {
            Toast.makeText(getActivity(), "Ambulance", Toast.LENGTH_SHORT).show();

        }

    }
}