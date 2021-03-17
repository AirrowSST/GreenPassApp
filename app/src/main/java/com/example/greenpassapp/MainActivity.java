package com.example.greenpassapp;

import android.os.Bundle;

import com.example.greenpassapp.model.NRICModel;
import com.example.greenpassapp.model.PasswordCreator;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // layout
        setContentView(R.layout.activity_main);

        // Passing each menu ID as a set of IDs because each menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_login, R.id.navigation_check, R.id.navigation_settings
        ).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // for the bottom navigation
        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavigationUI.setupWithNavController(navView, navController);

        /* model-related things below */

        // be a Yusof Ishak and get his password (if the password creator is working, it should print "yusofishak")
        System.out.println(PasswordCreator.create("S0000001I"));

        // initialize the database ("vaccine.txt")
        NRICModel.initFiles(this);

    }

}