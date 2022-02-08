package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText loginUsernameET;
    private EditText loginPasswordET;
    private Button loginBtn;
    private static final String FIREBASE_DATABASE_URL = "https://quick-cash-55715-default-rtdb.firebaseio.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        this.loginUsernameET=findViewById(R.id.loginUsernameET);
        this.loginPasswordET=findViewById(R.id.loginPasswordET);
        this.loginBtn=findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginEvent();
            }
        });
    }

    private void loginEvent() {
        DatabaseReference firebaseDBRef = FirebaseDatabase.getInstance().getReference("users");
        firebaseDBRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean hasFoundUser = false;

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String emailToCompare = dataSnapshot.child("email").getValue(String.class);
                    String passwordToCompare = dataSnapshot.child("password").getValue(String.class);
                    String inputtedPassword = loginPasswordET.getText().toString();

                    if (passwordToCompare.equals(inputtedPassword) && emailToCompare.equals(loginUsernameET.getText().toString())) {
                        hasFoundUser = true;
                    }

                }

                if(hasFoundUser){
                    Intent newIntent = new Intent(LoginActivity.this, EmployeeActivity.class);
                    startActivity(newIntent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
