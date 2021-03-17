package com.example.greenpassapp.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.greenpassapp.BR;
import com.example.greenpassapp.R;
import com.example.greenpassapp.model.Account;
import com.example.greenpassapp.model.NRICModel;
import com.example.greenpassapp.model.PasswordCreator;
import com.example.greenpassapp.databinding.FragmentLoginBinding;

import java.util.Objects;

public class LoginFragment extends Fragment {

    private LoginViewModel mViewModel;
    private FragmentLoginBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding.setVariable(BR.loginViewModel, mViewModel);

        mViewModel.usernameInput.observe(getViewLifecycleOwner(), string -> {
            onUsernameTextChanged();
            onPasswordTextChanged();
        });
        mViewModel.passwordInput.observe(getViewLifecycleOwner(), string -> {
            onUsernameTextChanged();
            onPasswordTextChanged();
        });

        binding.loginButton.setOnClickListener(view -> {
            login(view, Objects.requireNonNull(binding.inputUsername.getText()).toString());
        });
    }

    private void onUsernameTextChanged() {
        String text = mViewModel.getUsernameInput();
        // if the text is a valid NRIC
        if (NRICModel.checkIC(text)) {
            // show a tick ✔
            binding.layoutUsername.setHelperText("✔");
        } else {
            // do stuff based on the length of the input
            int len = text.length();
            if (len != 9) {
                // invalid ic length
                if (len > 9) {
                    // too many characters
                    if (len > 20) {
                        binding.layoutUsername.setError(getString(R.string.error_spam));
                    } else {
                        binding.layoutUsername.setError(getString(R.string.error_long_nric));
                    }
                } else {
                    // too few characters
                    binding.layoutUsername.setHelperText("...");
                }
            } else {
                // correct number of characters, but still invalid
                binding.layoutUsername.setError(getString(R.string.error_invalid_nric));
            }
        }
    }

    private void onPasswordTextChanged() {
        String text = mViewModel.getPasswordInput();
        String ic = mViewModel.getPasswordInput();
        String correct = "password"; // adds bypass for now
        if (NRICModel.checkIC(ic)) {
            correct = PasswordCreator.create(ic);
        }
        binding.layoutPassword.setEnabled(true);
        if (correct.equals(text)) {
            // password is correct!
            // turn on login button
            binding.loginButton.setEnabled(true);
            // show a tick ✔
            binding.layoutPassword.setHelperText("✔");
        } else {
            // password is not correct
            // turn off login button
            binding.loginButton.setEnabled(false);
            // show error(s)
            binding.layoutPassword.setErrorIconDrawable(null);
            if (text.length() > 20) {
                binding.layoutPassword.setError(getString(R.string.error_spam));
            } else {
                if (NRICModel.checkIC(mViewModel.getUsernameInput())) {
                    if (text.length() > 0) {
                        binding.layoutPassword.setError(getString(R.string.error_invalid_password));
                    } else {
                        binding.layoutPassword.setHelperText("...");
                    }
                } else {
                    binding.layoutPassword.setEnabled(false);
                    binding.layoutPassword.setError(getString(R.string.error_cannot_password));
                }
            }
        }
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

        // log in
        Account.setUser(username);

        // Just a test.
        // The user should not have to provide a password on login but rather using something else (probably on a profile page).
        // The line below should be deleted.
        Account.setUserPassed(true, requireContext());
    }

    public void logout(View view) {
//        System.out.println("logout button pressed");

        // log out
        Account.setUser("");

        // user passed yay
        Account.setUserPassed(false, requireContext());
    }

}