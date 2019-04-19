package com.example.healthapp.util;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;

public interface Constants {
    String dateFormat = "dd-mm-yyyy";
    SimpleDateFormat simpleDateFormamt = new SimpleDateFormat(dateFormat);
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

}
