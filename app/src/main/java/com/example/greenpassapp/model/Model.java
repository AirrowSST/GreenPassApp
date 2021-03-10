package com.example.greenpassapp.model;

public class Model {
    String i_want_to_die = "i want to die";

    public String gay(String s){
        return s;
    }

    public void yawTia(String s){
        System.out.println("old"+s);
    }

    /**
     * Method 'hi()' recurses infinitely, and can only end by throwing an exception
     *     - Android Studio
     */
    private String hi(Model model) {
        return model.i_want_to_die + model.hi(this);
    }
}
