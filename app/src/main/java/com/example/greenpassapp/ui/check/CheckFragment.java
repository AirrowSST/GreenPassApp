package com.example.greenpassapp.ui.check;

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
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.greenpassapp.BR;
import com.example.greenpassapp.R;
import com.example.greenpassapp.databinding.FragmentCheckBinding;
import com.example.greenpassapp.databinding.FragmentLoginBinding;
import com.example.greenpassapp.model.Account;
import com.example.greenpassapp.model.NRICModel;
import com.example.greenpassapp.ui.KeyboardManager;
import com.example.greenpassapp.ui.login.LoginViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CheckFragment extends Fragment {

    private CheckViewModel mViewModel;
    private FragmentCheckBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_check, container, false);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CheckViewModel.class);
        binding.setVariable(BR.checkViewModel, mViewModel);

        mViewModel.nric.observe(getViewLifecycleOwner(), string -> mViewModel.onNRICChanged(this));

        binding.checkButton.setOnClickListener(view -> {
            KeyboardManager.hideKeyboard(requireContext(), view);
            mViewModel.setResult(
                getString(R.string.text_check_result,
                    (Account.isUserPassed(mViewModel.getNric()))
                        ? getString(R.string.text_check_result_true) // true, has GREEN PASS
                        : getString(R.string.text_check_result_false) // false, bad
                ));
        });
    }

}