package com.example.greenpassapp.model;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordCreator {

    public static final int MIN_LENGTH = 1;
    public static final int MAX_LENGTH = 39;
    public static final int PASSWORD_LENGTH = 10;

    /**
     * @param ic the nric
     * @return a unique password based on the input
     */
    public static String create(String ic) {
        if (!Model.checkIC(ic)) return "error";
        String ans = "error";
        try {
            byte[] bites = convert(ic); // can throw exception here
            BigInteger bi = new BigInteger(bites);
            ans = bi.toString(36).subSequence(0, PASSWORD_LENGTH).toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace(); // yay, an error!
        }
        return ans;
    }

    private static byte[] convert(String rope) throws NoSuchAlgorithmException {
        return MessageDigest.getInstance("SHA-256").digest(rope.getBytes(StandardCharsets.UTF_8));
    }

}
