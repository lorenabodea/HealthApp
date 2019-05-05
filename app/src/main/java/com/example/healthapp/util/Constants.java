package com.example.healthapp.util;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public interface Constants {
    String dateFormat = "dd-mm-yyyy";
    SimpleDateFormat simpleDateFormamt = new SimpleDateFormat(dateFormat);
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

    DateFormat format = new SimpleDateFormat("YYYYMMdd");
    String date = format.format(new Date());
    long d = System.currentTimeMillis();
    String dateToInsert = format.format(new Date(d));


}
