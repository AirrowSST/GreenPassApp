package com.example.greenpassapp.ui.check;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.greenpassapp.R;
import com.example.greenpassapp.model.NRICModel;

public class CheckViewModel extends ViewModel {

    public MutableLiveData<String> result = new MutableLiveData<>();
    public MutableLiveData<String> nric = new MutableLiveData<>();
    public MutableLiveData<String> nricHelperText = new MutableLiveData<>();
    public MutableLiveData<String> nricError = new MutableLiveData<>();
    public MutableLiveData<Boolean> checkButtonEnabled = new MutableLiveData<>();

    public CheckViewModel() {
        setResult("");
        setNRICHelperText("");
        setNRICError("");
        setCheckButtonEnabled(false);
    }

    protected String getNric() {
        return nric.getValue();
    }

    protected void setResult(String text) {
        this.result.setValue(text);
    }
    private void setNRICHelperText(String text) {
        this.nricHelperText.setValue(text);
    }
    private void setNRICError(String text) {
        this.nricError.setValue(text);
    }
    private void setCheckButtonEnabled(boolean enabled) {
        this.checkButtonEnabled.setValue(enabled);
    }

    protected void onNRICChanged(Fragment fragment) {
        updateNRICSpecificUI(fragment);
    }
    private void updateNRICSpecificUI(Fragment fragment) {
        String nric = getNric();
        if (!NRICModel.checkIC(nric)) {
            setCheckButtonEnabled(false);
            int len = nric.length();
            if (len != 9) {
                if (len > 9) {
                    if (len > 20) {
                        setNRICError(fragment.getString(R.string.error_spam));
                    } else {
                        setNRICError(fragment.getString(R.string.error_long_nric));
                    }
                } else {
                    setNRICHelperText("");
                }
            } else {
                setNRICError(fragment.getString(R.string.error_invalid_nric));
            }
        } else {
            setNRICHelperText("✔");
            setCheckButtonEnabled(true);
        }
    }

}
