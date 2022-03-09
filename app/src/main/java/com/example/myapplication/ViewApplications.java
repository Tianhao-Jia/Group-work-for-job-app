package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ViewApplications extends AppCompatActivity {

    private FirebaseDatabase firebaseDB;
    private DatabaseReference firebaseDBRef;

    DatabaseReference applications = firebaseDB.getReference().child("applications");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.applications_page);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            applications.child(extras.getString("User Hash")).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (extras != null) {
                        if (snapshot.child(extras.getString("User Hash")).getChildrenCount() > 0) {
                            // Meaningless statement to be place holder until I figure it out
                            System.out.println("Idk");
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }
}
