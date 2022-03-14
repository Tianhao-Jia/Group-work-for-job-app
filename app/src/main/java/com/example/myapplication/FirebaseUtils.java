package com.example.myapplication;

import com.google.firebase.database.FirebaseDatabase;

//Class to reference the correct FireBase nodes
public class FirebaseUtils {
    public FirebaseUtils(){};

    public static final String FIREBASE_URL = "https://quick-cash-55715-default-rtdb.firebaseio.com/";
    public static final String USERS = "users";
    public static final String JOBS = "jobs";


    public static FirebaseDatabase connectFirebase(){
        return FirebaseDatabase.getInstance(FirebaseUtils.FIREBASE_URL);
    }
}
