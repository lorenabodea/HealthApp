package com.example.healthapp.util;

public class Validations {

    public static boolean isEmptyField(String text){
        if(text.trim().isEmpty()||text==null){
            return true;
        }
        return false;
    }
}
