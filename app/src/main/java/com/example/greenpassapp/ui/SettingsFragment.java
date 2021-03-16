package com.example.greenpassapp.ui;

import android.app.FragmentBreadCrumbs;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceFragmentCompat;

import com.example.greenpassapp.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // no
//        requireActivity().getActionBar().hide();
        // no 2
//        ((FragmentBreadCrumbs)(requireActivity().findViewById(android.R.id.title))).setVisibility(View.GONE);
        // no 3
//        requireActivity().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings_xml, rootKey);
    }
}
