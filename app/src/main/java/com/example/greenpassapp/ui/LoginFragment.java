package com.example.greenpassapp.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.greenpassapp.R;
import com.example.greenpassapp.model.Account;
import com.example.greenpassapp.model.Model;
import com.example.greenpassapp.model.PasswordCreator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class LoginFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        System.out.println("login fragment entered");

        // stuff in the login fragment
        Button button = view.findViewById(R.id.login_button);
        TextInputLayout usernameLayout = view.findViewById(R.id.layout_username);
        TextInputLayout passwordLayout = view.findViewById(R.id.layout_password);
        TextInputEditText usernameInput = view.findViewById(R.id.input_username);
        TextInputEditText passwordInput = view.findViewById(R.id.input_password);

        // on password text changed
        TextWatcher passwordTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ?
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString();
                String ic = Objects.requireNonNull(usernameInput.getText()).toString();
                String correct = "password"; // adds bypass for now
                if (Model.checkIC(ic)) {
                    correct = PasswordCreator.create(ic);
                }
                if (correct.equals(text)) {
                    // password is correct!
                    // turn on login button
                    button.setEnabled(true);
                    // show a tick ✔
                    passwordLayout.setHelperText("✔");
                } else {
                    // password is not correct
                    // turn off login button
                    button.setEnabled(false);
                    // show error(s)
                    passwordLayout.setErrorIconDrawable(null);
                    if (text.length() > 20) {
                        passwordLayout.setError(getString(R.string.error_spam));
                    } else {
                        if (Model.checkIC(usernameInput.getText().toString())) {
                            passwordLayout.setError(getString(R.string.error_invalid_password));
                        } else {
                            passwordLayout.setError(getString(R.string.error_cannot_password));
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // ??
            }
        };
        passwordInput.addTextChangedListener(passwordTextWatcher);

        // on username text changed (is this ok?)
        TextWatcher usernameTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ???
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString();
                // if the text is a valid NRIC
                if (Model.checkIC(text)) {
                    // show a tick ✔
                    usernameLayout.setHelperText("✔");
                } else {
                    // do stuff based on the length of the input
                    int len = text.length();
                    if (len != 9) {
                        // invalid ic length
                        if (len > 9) {
                            // too many characters
                            if (len > 20) {
                                usernameLayout.setError(getString(R.string.error_spam));
                            } else {
                                usernameLayout.setError(getString(R.string.error_long_nric));
                            }
                        } else {
                            // too few characters
                            usernameLayout.setHelperText("...");
                        }
                    } else {
                        // correct number of characters, but still invalid
                        usernameLayout.setError(getString(R.string.error_invalid_nric));
                    }
                }
                // update the password text listener too, in case the password matches the new username
                passwordTextWatcher.onTextChanged(Objects.requireNonNull(passwordInput.getText()).toString(), 0, 0, 0);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // ????
            }
        };
        usernameInput.addTextChangedListener(usernameTextWatcher);

        button.setOnClickListener(view1 -> {
            login(view1, Objects.requireNonNull(usernameInput.getText()).toString());
        });
    }

    // does nothing at all
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                NavUtils.navigateUpFromSameTask(this);
                System.out.println("login: back button pressed");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This method is run when user clicks on the login button (it must be enabled)
     */
    public void login(View view, String username) {
//        System.out.println("login button pressed");
//        PassDialog.showDialog(requireActivity().getSupportFragmentManager());
        ScanFragment.startScan(requireActivity().getSupportFragmentManager());
        Account.setUser(username);
    }

    public void logout(View view) {
//        System.out.println("logout button pressed");
//        PassDialog.showDialog(requireActivity().getSupportFragmentManager());
        Account.setUser("");
        Account.setUserPassed(false, requireContext());
    }

}