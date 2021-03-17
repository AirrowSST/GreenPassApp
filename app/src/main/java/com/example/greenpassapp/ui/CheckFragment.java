package com.example.greenpassapp.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.greenpassapp.R;
import com.example.greenpassapp.model.Account;
import com.example.greenpassapp.model.NRICModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CheckFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_check, container, false);
    }

    @Override
    public void onViewCreated(@NotNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);

//        System.out.println("check fragment entered");

        // stuff in the check fragment
        Button checkButton = root.findViewById(R.id.check_button);
        TextInputLayout nricLayout = root.findViewById(R.id.layout_nric);
        TextInputEditText nricInput = root.findViewById(R.id.input_nric);
        TextView resultText = root.findViewById(R.id.check_result);

        // on username text changed (the same as login)
        TextWatcher usernameTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // hopefully don't need
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString();
                // if the text is a valid NRIC
                if (NRICModel.checkIC(text)) {
                    // show a tick ✔
                    nricLayout.setHelperText("✔");
                    // and enable the check button
                    checkButton.setEnabled(true);
                } else {
                    // disable the button due to invalid input
                    checkButton.setEnabled(true);
                    // do stuff based on the length of the input
                    int len = text.length();
                    if (len != 9) {
                        // invalid ic length
                        if (len > 9) {
                            // too many characters
                            if (len > 20) {
                                nricLayout.setError(getString(R.string.error_spam));
                            } else {
                                nricLayout.setError(getString(R.string.error_long_nric));
                            }
                        } else {
                            // too few characters
                            nricLayout.setHelperText("...");
                        }
                    } else {
                        // correct number of characters, but still invalid
                        nricLayout.setError(getString(R.string.error_invalid_nric));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // these methods are sort of useless
            }
        };
        nricInput.addTextChangedListener(usernameTextWatcher);

        checkButton.setOnClickListener(view -> {

            // hide the keyboard
            KeyboardManager.hideKeyboard(requireContext(), view);

            resultText.setText(
                getString(R.string.text_check_result,
                    (Account.isUserPassed(Objects.requireNonNull(nricInput.getText()).toString()))
                        ? getString(R.string.text_check_result_true) // true, has GREEN PASS
                        : getString(R.string.text_check_result_false) // false, bad
                )
            );

        });
    }

}