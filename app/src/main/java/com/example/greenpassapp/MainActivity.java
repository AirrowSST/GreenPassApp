package com.example.greenpassapp;

import android.os.Bundle;
import android.view.View;

import com.example.greenpassapp.model.NRICModel;
import com.example.greenpassapp.model.PasswordCreator;
import com.example.greenpassapp.ui.KeyboardManager;
import com.example.greenpassapp.ui.greenPass.GreenPassFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private @Nullable BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // General stuff
        // layout
        setContentView(R.layout.activity_main);
        // hide keyboard on touch
        View root = findViewById(R.id.activityMainRoot);
        root.setOnClickListener(arg -> KeyboardManager.hideKeyboard(getBaseContext(), root));


        // Navigation
        // Passing each menu ID as a set of IDs because each menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_login, R.id.navigation_check, R.id.navigation_settings
        ).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        // for the bottom navigation
        bottomNavigationView = findViewById(R.id.nav_view);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        bottomNavigationView.getMenu().findItem(R.id.navigation_home).setEnabled(false);

        // model-related things below
        // be a Yusof Ishak and get his password (if the password creator is working, it should print "yusofishak")
        System.out.println(PasswordCreator.create("S0000001I"));
        // initialize the database ("vaccine.txt")
        NRICModel.initFiles(this);
    }

    public BottomNavigationView getBottomNavigation() {
        return bottomNavigationView;
    }

//    public void openGreenPass(View view){
//        GreenPassFragment gpf = new GreenPassFragment();
//        gpf.showDialog();
//    }
//
//    public void loginClick(View view){
//        GreenPassFragment gpf = new GreenPassFragment();
//        gpf.showDialog();
//    }
}