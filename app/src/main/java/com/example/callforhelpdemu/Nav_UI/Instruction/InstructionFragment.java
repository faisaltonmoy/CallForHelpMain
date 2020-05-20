package com.example.callforhelpdemu.Nav_UI.Instruction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.callforhelpdemu.R;

public class InstructionFragment extends Fragment {

    private InstructionViewModel instructionViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        instructionViewModel =
                ViewModelProviders.of(this).get(InstructionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_instruction, container, false);

        return root;
    }
}