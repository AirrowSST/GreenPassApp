package com.example.greenpassapp.ui.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {

    public MutableLiveData<String> usernameInput = new MutableLiveData<>();
    public MutableLiveData<String> passwordInput = new MutableLiveData<>();

    public LoginViewModel() {
        usernameInput.setValue("");
        passwordInput.setValue("");
    }

    protected String getUsernameInput() {
        return usernameInput.getValue();
    }
    protected String getPasswordInput() {
        return passwordInput.getValue();
    }

}
