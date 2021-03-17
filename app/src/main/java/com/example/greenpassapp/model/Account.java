package com.example.greenpassapp.model;

import android.content.Context;

import androidx.annotation.Nullable;

import com.example.greenpassapp.model.file.File;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TreeMap;

/**
 * Account class (more like a namespace)
 * Contains static methods and static fields storing NRICs and corresponding data.
 * Also keeps track of current user.
 */
public class Account {

//     is this good practice?

    // if user == "", the user is logged out
    // if user == some ic, the user is logged in
    // if user == "admin", the user is an administrator
    private static String user = "";
    // whether the user has passed vaccination (has access to the GREEN PASS)
    // this needs a password (use PasswordCreator to get the password in the administrator account)
    private static boolean passed = false;

    private static final TreeMap<String, Boolean> map = new TreeMap<>();
    private static final TreeMap<String, String> dateMap = new TreeMap<>();

    /**
     * Getter for the current user.
     * @return the string data associated with the user. (NRIC, "admin" or "" if there is no current user)
     */
    public static String getUser() {
        return user;
    }
    /**
     * Sets the current user's string data. (NRIC, "admin" or "" if there is no current user)
     * @param user the string data.
     */
    public static void setUser(String user) {
        Account.user = user;
    }

    /**
     * Checks if the current user is vaccinated.
     * @return whether the current user is vaccinated.
     */
    public static boolean isUserPassed() {
        return isUserPassed(user);
    }
    /**
     * Gets the date of vaccination of the current user.
     * @return a string with the date of vaccination.
     */
    public static String getUserPassDate() {
        return getUserPassDate(user);
    }

    /**
     * Overloading method - Checks if a given user is vaccinated.
     * @return whether a given user is vaccinated.
     */
    public static boolean isUserPassed(String username) {
        if (username.equals("") || username.equals("admin")) return false;
        Boolean b = map.get(username);
        return b != null && b;
    }
    /**
     * Overloading method - Gets the date of vaccination of a given user.
     * @return a string with the date of vaccination.
     */
    public static String getUserPassDate(String username) {
        if (username.equals("")) return "";
        String s = dateMap.get(username);
        return (s != null) ? s : "";
    }

    /**
     * Setter for whether the current user has been vaccinated.
     * @param passed whether the current user has been vaccinated.
     * @param context the context of the application (used to get the external files)
     */
    public static void setUserPassed(boolean passed, @Nullable Context context) {
        Account.passed = passed;

        // if logging out, there is no need to update the map or database
        if (user.equals("")) return;

        map.put(user, passed);

        // also update the database
        try {
            String date = (new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)).format(new Date());
            // 3 fields to store in database:
            // 1. username NRIC (String)
            // 2. whether the user has GREEN PASS (boolean)
            // 3. display date of vaccination (String)
            String string = user + "," + passed + "," + date;
            File.overwriteLine("vaccine.txt", context, string, user, ",");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to load data from a the external files.
     * @param context the context of the application (used to get the external files)
     * @throws IOException if dies.
     */
    public static void load(Context context) throws IOException {
        // read the file
        String raw = File.readFile("vaccine.txt", context);

        // clear the map and load contents of file into the map
        map.clear();
        for (String line : raw.split("\n")) {
            if (line.equals("")) {
                continue;
            }
            String[] data = line.split(",");
            if (data[0].equals("") || data.length != 3) {
                continue;
            }
            map.put(data[0], Boolean.getBoolean(data[1]));
            dateMap.put(data[0], data[2]);
        }
    }
}
