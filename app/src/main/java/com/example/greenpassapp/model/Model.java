package com.example.greenpassapp.model;

import android.content.Context;

import java.io.IOException;

public class Model {

    // validate IC
    // should I put this in a separate class?

    private static final int[] ic_multipliers = {2, 7, 6, 5, 4, 3, 2};
    private static final String ic_start = "FGST";
    private static final String F_or_G = "XWUTRQPNMLK";
    private static final String S_or_T = "JZIHGFEDCBA";

    // ok https://ivantay2003.medium.com/creation-of-singapore-identity-number-nric-24fc3b446145
    public static boolean checkIC(String ic) {
        if (ic.length() != 9)
            return false;
        char start = ic.charAt(0),
             end = ic.charAt(8);
        if (!ic_start.contains(String.valueOf(start)))
            return false;
        int sum = 0, i;
        try {
            for (i = 0; i < 7; i++) sum += ic_multipliers[i] * Integer.parseInt(String.valueOf(ic.charAt(i + 1)));
        } catch (NumberFormatException e) {
            return false;
        }
        if (start == 'T' || start == 'G') sum += 4;
        int checksum = sum % 11;
        String letters;
        if (start == 'F' || start == 'G')
            letters = F_or_G;
        else letters = S_or_T;
        return (letters.charAt(checksum) == end);
    }

    // initialize the "fake online database"
    public static void initFiles(Context context) {
        try {

            if (!File.createFile("vaccine.txt", context)) {
                // read from the file
                Account.load(context);
            } else {
                // write empty string if file doesn't exist
                File.writeToFile("vaccine.txt", context, "");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}