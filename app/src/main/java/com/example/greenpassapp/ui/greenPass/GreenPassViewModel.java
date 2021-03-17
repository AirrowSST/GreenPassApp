package com.example.greenpassapp.ui.greenPass;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GreenPassViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GreenPassViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}