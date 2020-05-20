package com.example.callforhelpdemu.Nav_UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.callforhelpdemu.R;
import com.example.callforhelpdemu.Nav_UI.Instruction.InstructionViewModel;
import com.example.callforhelpdemu.Nav_UI.tools.ToolsViewModel;

public class Signout extends Fragment {

    private InstructionViewModel instructionViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

                ViewModelProviders.of(this).get(ToolsViewModel.class);
       return inflater.inflate(R.layout.fragment_signout, container, false);
        //return root;
        };

    }


