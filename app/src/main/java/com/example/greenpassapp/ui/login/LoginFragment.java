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
import com.example.greenpassapp.databinding.FragmentLoginBinding;

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

        mViewModel.usernameInput.observe(getViewLifecycleOwner(), string -> mViewModel.onUsernameTextChange(this));
        mViewModel.passwordInput.observe(getViewLifecycleOwner(), string -> mViewModel.onPasswordTextChange(this));

        binding.loginButton.setOnClickListener(view -> login(view, mViewModel.getUsernameInput()));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
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
        Account.setUser(username);
        Account.setUserPassed(true, requireContext());
    }

    // can show the dialog
//    PassDialog.showDialog(requireActivity().getSupportFragmentManager());
    // or start scan
//    ScanFragment.startScan(requireActivity().getSupportFragmentManager());

    public void logout(View view) {
        Account.setUser("");
        Account.setUserPassed(false, requireContext());
    }

}