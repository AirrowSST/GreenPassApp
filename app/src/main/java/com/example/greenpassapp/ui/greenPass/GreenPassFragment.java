package com.example.greenpassapp.ui.greenPass;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.greenpassapp.MainActivity;
import com.example.greenpassapp.R;
import com.example.greenpassapp.ui.PassDialog;
import com.example.greenpassapp.ui.scan.ScanFragment;

import org.jetbrains.annotations.NotNull;

public class GreenPassFragment extends Fragment {

    private GreenPassViewModel greenPassViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        greenPassViewModel =
                new ViewModelProvider(this).get(GreenPassViewModel.class);
        View root = inflater.inflate(R.layout.fragment_green_pass, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
        greenPassViewModel.getText().observe(getViewLifecycleOwner(), s -> {
//            textView.setText(s);
        });

        return root;
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void showDialog() {
        PassDialog.showDialog(requireActivity().getSupportFragmentManager());
    }

    public void showScanner() {
        ScanFragment.startScan(requireActivity().getSupportFragmentManager());
    }

    public void openGreenPass(View view){

//        PassDialog pd = new PassDialog();
//        FragmentTransaction ft = new FragmentManager() {
//            @NonNull
//            @Override
//            public FragmentTransaction beginTransaction() {
//                return super.beginTransaction();
//            }
//        }.beginTransaction();
//        ft.add(R.id.constraintLayout,pd);
//        ft.commit();
    }
}