package com.example.greenpassapp.ui.login;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.greenpassapp.R;
import com.example.greenpassapp.model.NRICModel;
import com.example.greenpassapp.model.PasswordCreator;

public class LoginViewModel extends ViewModel {

    public MutableLiveData<String> usernameInput = new MutableLiveData<>();
    public MutableLiveData<String> usernameError = new MutableLiveData<>();
    public MutableLiveData<String> usernameHelperText = new MutableLiveData<>();

    public MutableLiveData<String> passwordInput = new MutableLiveData<>();
    public MutableLiveData<String> passwordError = new MutableLiveData<>();
    public MutableLiveData<String> passwordHelperText = new MutableLiveData<>();

    public MutableLiveData<Boolean> loginButtonEnabled = new MutableLiveData<>();

    public LoginViewModel() {
        setUsernameInput("");
        setUsernameError("");
        setUsernameHelperText("");

        setPasswordInput("");
        setPasswordError("");
        setPasswordHelperText("");

        setLoginButtonEnabled(false);
    }

    private void setUsernameInput(String text) {
        this.usernameInput.setValue(text);
    }

    private void setUsernameError(String text) {
        this.usernameError.setValue(text);
    }

    private void setUsernameHelperText(String text) {
        this.usernameHelperText.setValue(text);
    }

    private void setPasswordInput(String text) {
        this.passwordInput.setValue(text);
    }

    private void setPasswordError(String text) {
        this.passwordError.setValue(text);
    }

    private void setPasswordHelperText(String text) {
        this.passwordHelperText.setValue(text);
    }

    private void setLoginButtonEnabled(boolean enabled) {
        this.loginButtonEnabled.setValue(enabled);
    }

    protected String getUsernameInput() {
        return usernameInput.getValue();
    }

    protected String getPasswordInput() {
        return passwordInput.getValue();
    }

    protected void onUsernameTextChange(Fragment fragment) {
        updateUsernameAndPasswordUI();
        updateUsernameSpecificUI(fragment);
    }

    protected void onPasswordTextChange(Fragment fragment) {
        updateUsernameAndPasswordUI();
        updatePasswordSpecificUI(fragment);
    }

    private void updateUsernameAndPasswordUI() {
        String username = getUsernameInput();
        String password = getPasswordInput();
        if (NRICModel.checkIC(username)) {
            String correctPassword = PasswordCreator.create(username);
            setUsernameHelperText("✔");
            if (password.equals(correctPassword)) {
                setLoginButtonEnabled(true);
                setPasswordHelperText("✔");
            } else {
                setLoginButtonEnabled(false);
                setPasswordHelperText("");
            }
        } else {
            setLoginButtonEnabled(false);
            setUsernameHelperText("");
            setPasswordHelperText("");
        }
    }

    private void updateUsernameSpecificUI(Fragment fragment) {
        String username = getUsernameInput();
        if (!NRICModel.checkIC(username)) {
            int len = username.length();
            if (len != 9) {
                if (len > 9) {
                    if (len > 20) {
                        setUsernameError(fragment.getString(R.string.error_spam));
                    } else {
                        setUsernameError(fragment.getString(R.string.error_long_nric));
                    }
                } else {
                    setUsernameHelperText("");
                }
            } else {
                setUsernameError(fragment.getString(R.string.error_invalid_nric));
            }
        }
    }

    private void updatePasswordSpecificUI(Fragment fragment) {
        String username = getUsernameInput();
        String password = getPasswordInput();
        if (password.length() > 20) {
            setPasswordError(fragment.getString(R.string.error_spam));
        } else {
            if (NRICModel.checkIC(username)) {
                setPasswordError(fragment.getString(R.string.error_invalid_password));
            } else if (!username.equals("")) {
                setPasswordError(fragment.getString(R.string.error_cannot_password));
            }
        }
    }
}
