package com.example.greenpassapp.model;

import android.content.Context;

import java.io.IOException;
import java.util.TreeMap;

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

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        Account.user = user;
    }

    public static boolean isUserPassed() {
        return isUserPassed(user);
    }

    // overriding default above
    public static boolean isUserPassed(String username) {
        Boolean b = map.get(username);
        return b != null && b;
    }

    public static void setUserPassed(boolean passed, Context context) {
        Account.passed = passed;
        map.put(user, passed);

        // also update the database?
        try {
            File.overwriteLine("vaccine.txt", context, user + "," + passed, user, ",");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
            map.put(data[0], Boolean.getBoolean(data[1]));
        }
    }
}
