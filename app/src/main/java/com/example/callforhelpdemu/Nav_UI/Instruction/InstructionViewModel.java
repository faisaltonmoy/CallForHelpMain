package com.example.callforhelpdemu.Nav_UI.Instruction;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InstructionViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public InstructionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is send fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}