package com.example.callforhelpdemu.Nav_UI.Feedback;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.callforhelpdemu.Feedback;
import com.example.callforhelpdemu.Home;
import com.example.callforhelpdemu.MainActivity;
import com.example.callforhelpdemu.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FeedbackFragment extends Fragment implements
        AdapterView.OnItemSelectedListener,
        View.OnClickListener{

    private FeedbackViewModel feedbackViewModel;
    private Spinner rate;
    EditText feedback;
    Button submit;
    DatabaseReference dbref,feed_msg;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        feedbackViewModel =
                ViewModelProviders.of(this).get(FeedbackViewModel.class);
        View root = inflater.inflate(R.layout.fragment_feedback, container, false);

        //************** ID FIND **********************//
        rate = root.findViewById(R.id.rate_id);
        feedback = root.findViewById(R.id.feedback_id);
        submit = root.findViewById(R.id.submit_btn_id);

        //************ SPINNER VALUE ADD *********************//
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.rate_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rate.setAdapter(adapter);

        //****************** DATABASE REFERENCE *******************//
        dbref = FirebaseDatabase.getInstance().getReference("FeedBack");
        feed_msg=FirebaseDatabase.getInstance().getReference("FeedBack");

        submit.setOnClickListener(this);



        return root;
    }

    @Override
    public void onClick(View v) {
        if(isConnected(this.getActivity()))
        {
        final String Rate = rate.getSelectedItem().toString();//GET STRING FROM Spinner//
        final String Msg = feedback.getText().toString();//GET FEEDBACK FROM EDIT TEXT//

        //***************** GET FEEDBACK FROM USER AND STORE IN DATABASE *********************//

        feed_msg.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                startActivity(new Intent(getActivity(),Home.class));
                Toast.makeText(getActivity(), "Feedback Submitted", Toast.LENGTH_SHORT).show();
                getActivity().finish();

                String key=dbref.push().getKey();
                Feedback feedback = new Feedback(Msg,Rate);
                dbref.child(key).setValue(feedback);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        }
        else
        {
            buildDialog(this.getActivity()).show();
        }
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