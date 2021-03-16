package com.example.greenpassapp.model;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.TreeMap;

public class PasswordCreator {

    public static final int MIN_LENGTH = 1;
    public static final int MAX_LENGTH = 39;
    public static final int PASSWORD_LENGTH = 10;
    public static final int[] PASSWORD_SHIFT = {-7, 6, 14, 8, -17, 4, -11, -3, -14, -8}; // length == PASSWORD_LENGTH
    public static final String STRING = "0123456789abcdefghijklmnopqrstuvwxyz";
    public static final int RADIX = STRING.length();

    public static final TreeMap<String, String> memo = new TreeMap<>();

    /**
     * creates a 10-letter password for the user with the nric
     * specifically outputs "yusofishak" for a certain user
     * @param ic the nric
     * @return a unique password based on the input
     */
    public static String create(String ic) {
        if (memo.containsKey(ic)) return memo.get(ic);
        if (!Model.checkIC(ic)) return "error";
        StringBuilder ans = new StringBuilder("error");
        try {
            byte[] bites = convert(ic); // can throw exception here
            BigInteger bi = new BigInteger(bites);
            String normal = bi.toString(RADIX).subSequence(0, PASSWORD_LENGTH).toString();
            ans = new StringBuilder();
            for (int i = 0; i < PASSWORD_LENGTH; i++) {
                ans.append(STRING.charAt((STRING.indexOf(normal.charAt(i)) + PASSWORD_SHIFT[i] + RADIX) % RADIX));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace(); // yay, an error!
        }
        if (!ans.toString().equals("error")) {
            memo.put(ic, ans.toString());
        }
        return ans.toString();
    }

    private static byte[] convert(String rope) throws NoSuchAlgorithmException {
        return MessageDigest.getInstance("SHA-256").digest(rope.getBytes(StandardCharsets.UTF_8));
    }

}