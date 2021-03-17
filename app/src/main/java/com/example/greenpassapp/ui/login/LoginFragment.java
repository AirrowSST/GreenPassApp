package com.example.greenpassapp.ui.login;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.greenpassapp.BR;
import com.example.greenpassapp.MainActivity;
import com.example.greenpassapp.R;
import com.example.greenpassapp.model.Account;
import com.example.greenpassapp.databinding.FragmentLoginBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

import static android.content.Context.NOTIFICATION_SERVICE;

public class LoginFragment extends Fragment {

    private LoginViewModel mViewModel;
    private FragmentLoginBinding binding;

    private NotificationCompat.Builder notificationBuilder;
    private int notificationId = 1;

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            String CHANNEL_ID = "channel";
            String name = getString(R.string.notification_channel_name);
            String descriptionText = getString(R.string.notification_channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(descriptionText);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = (NotificationManager) requireActivity().getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(mChannel);

            notificationBuilder = new NotificationCompat.Builder(requireContext(), CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Green Pass App")
                    .setContentText("Successfully logged in!")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);
        } else {
            notificationBuilder = new NotificationCompat.Builder(requireContext())
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Green Pass App")
                    .setContentText("Successfully logged in!")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);
        }
    }

    // does nothing?
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

        // notify the user about login
        NotificationManagerCompat.from(requireActivity()).notify(notificationId, notificationBuilder.build());
        notificationId++;
        //open popup for green pass

        BottomNavigationView bottomNavigationView = Objects.requireNonNull(Objects.requireNonNull((MainActivity) requireActivity()).getBottomNavigation());
        bottomNavigationView.getMenu().findItem(R.id.navigation_login).setEnabled(false);
        bottomNavigationView.getMenu().findItem(R.id.navigation_home).setEnabled(true);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
    }

    // can show the dialog
//    PassDialog.showDialog(requireActivity().getSupportFragmentManager());
    // or start scan
//    ScanFragment.startScan(requireActivity().getSupportFragmentManager());

}