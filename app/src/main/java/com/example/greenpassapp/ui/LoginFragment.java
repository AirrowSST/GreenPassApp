package com.example.greenpassapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.greenpassapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

public class LoginFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        System.out.println("login");

        // stuff in the login fragment
        Button button = view.findViewById(R.id.login_button);
        TextInputLayout usernameLayout = view.findViewById(R.id.layout_username);
        TextInputLayout passwordLayout = view.findViewById(R.id.layout_password);
        TextInputEditText usernameInput = view.findViewById(R.id.input_username);
        TextInputEditText passwordInput = view.findViewById(R.id.input_password);


    }

}