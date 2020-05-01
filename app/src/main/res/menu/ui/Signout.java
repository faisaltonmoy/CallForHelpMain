package com.example.callforhelpdemu.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.callforhelpdemu.R;
import com.example.callforhelpdemu.ui.send.SendViewModel;
import com.example.callforhelpdemu.ui.tools.ToolsViewModel;

public class Signout extends Fragment {

    private SendViewModel sendViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

                ViewModelProviders.of(this).get(ToolsViewModel.class);
       return inflater.inflate(R.layout.fragment_signout, container, false);
        //return root;
        };

    }


