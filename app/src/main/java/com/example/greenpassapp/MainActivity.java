package com.example.greenpassapp;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;

import com.example.greenpassapp.model.FakeController;
import com.example.greenpassapp.model.PasswordCreator;
import com.example.greenpassapp.ui.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setSupportActionBar(findViewById(R.id.toolbar));

        // todo remove weird back button on login and settings pages done
        // doesn't work
//        Objects.requireNonNull(this.getSupportActionBar()).setDisplayHomeAsUpEnabled(false);

        // layout
        setContentView(R.layout.activity_main);

        // Passing each menu ID as a set of IDs because each menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_login, R.id.navigation_home, R.id.navigation_settings
        ).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // for the bottom navigation
        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavigationUI.setupWithNavController(navView, navController);

        // be a Yusof Ishak and get his password (if the password creator is working, it should print "yusofishak"
        System.out.println(PasswordCreator.create("S0000001I"));
        // fake
        FakeController.main(new String[]{""});

    }

    // does nothing at all
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                NavUtils.navigateUpFromSameTask(this);
//                System.out.println("back button pressed");
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

//    @Override
//    public boolean onContextItemSelected(@NonNull MenuItem item) {
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.settings_container, new SettingsFragment())
//                .commit();
//        return super.onContextItemSelected(item);
//    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }
}