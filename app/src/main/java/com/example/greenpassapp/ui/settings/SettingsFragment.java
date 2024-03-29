package com.example.greenpassapp.ui.settings;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.greenpassapp.MainActivity;
import com.example.greenpassapp.R;
import com.example.greenpassapp.ui.PassDialog;
import com.example.greenpassapp.ui.scan.ScanFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class SettingsFragment extends PreferenceFragmentCompat {

    private int counter = 10;
    private boolean isAdmin = false;
    private Toast toast;

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
        resetAdminCount();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings_xml, rootKey);

        if (isAdmin) {
            Preference preference = Objects.requireNonNull(findPreference("admin"));
            preference.setSummary("Click here to open the admin dialog!");
        }
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        MainActivity mainActivity = Objects.requireNonNull((MainActivity) getActivity());
        BottomNavigationView bottomNavigationView = Objects.requireNonNull(mainActivity.getBottomNavigation());
        switch (preference.getKey()) {
            case "admin":
                adminClicked();
                break;
            case "tab_1":
                bottomNavigationView.setSelectedItemId(R.id.navigation_home);
                break;
            case "tab_2":
                bottomNavigationView.setSelectedItemId(R.id.navigation_login);
                break;
            case "tab_3":
                bottomNavigationView.setSelectedItemId(R.id.navigation_check);
                break;
            case "pass":
                PassDialog.showDialog(requireActivity().getSupportFragmentManager());
                break;
            case "scan":
                ScanFragment.startScan(requireActivity().getSupportFragmentManager());
                break;
            default:
                // no
                break;
        }
        return super.onPreferenceTreeClick(preference);
    }

    private void resetAdminCount() {
        if (isAdmin) {
            counter = 1;
        } else {
            counter = 20;
        }
    }

    private void adminClicked() {
        counter--;
        String toastText;
        if (counter == 0) {
            toastText = (isAdmin)
                ? getString(R.string.admin_open_toast)
                : getString(R.string.admin_open_toast_first_time);
            if (toast != null)
                toast.cancel();
            toast = Toast.makeText(getActivity(), toastText, Toast.LENGTH_SHORT);
            toast.show();
            resetAdminCount();
            showAdmin();
        } else if (counter <= 5) {
            if (counter == 1) {
                toastText = getString(R.string.admin_hack_toast_one);
            } else {
                toastText = getString(R.string.admin_hack_toast, counter);
            }
            if (toast != null)
                toast.cancel();
            toast = Toast.makeText(getActivity(), toastText, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void showAdmin() {
        isAdmin = true;

        // change setting text
        Preference preference = Objects.requireNonNull(findPreference("admin"));
        preference.setSummary("Click here to open the admin dialog!");

        // show admin fragment
        AdminFragment.showDialog(requireActivity().getSupportFragmentManager());
    }
}
