package com.example.greenpassapp.model;

import java.util.TreeMap;

public class Account {

    // is this good practice?

    // if user == "", the user is logged out
    // if user == some ic, the user is logged in
    // if user == "admin", the user is an administrator
    private static String user = "";
    // whether the user has passed vaccination (has access to the GREEN PASS)
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

    public static void setUserPassed(boolean passed) {
        Account.passed = passed;
        map.put(user, passed);
    }
}
