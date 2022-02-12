package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private static final String FIREBASE_DATABASE_URL = "https://quick-cash-55715-default-rtdb.firebaseio.com/";
    private FirebaseDatabase firebaseDB;
    private DatabaseReference firebaseDBRef;
    private TextView textView;
    static Boolean found = false;

    EditText id,password;
    Button login,signup;
    TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        id = findViewById(R.id.loginUsernameET);
        password = findViewById(R.id.loginPasswordET);
        signup = findViewById(R.id.loginToSignupButton);
        login = findViewById(R.id.loginButton);
        status = findViewById(R.id.loginStatus);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = id.getText().toString();
                String password = LoginActivity.this.password.getText().toString();
                if (TextUtils.isEmpty(username)){
                    status.setText(getString(R.string.name_empty));
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    status.setText(getString(R.string.pwd_empty));
                    return;
                }
                status.setText("");
                loginEvent();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterUser.class));
            }
        });

        connectFirebase();

    }

    private void loginEvent() {
        connectFirebase();
        firebaseDBRef.getDatabase();

        firebaseDBRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot: snapshot.getChildren()){

                    String email = dataSnapshot.child("email").getValue(String.class);
                    String password = dataSnapshot.child("password").getValue(String.class);
                    String passwordCompare = LoginActivity.this.password.getText().toString();
                    String employType = dataSnapshot.child("userType").getValue(String.class);


                    boolean passwordNotNull = password!=null;
                    boolean emailNotNull = email!=null;
                    boolean passwordIsSame = password.equals(passwordCompare);
                    boolean emailIsSame = email.equals(id.getText().toString());

                    if(passwordNotNull && emailNotNull && passwordIsSame && emailIsSame){
                        found = true;

                        if (employType.equalsIgnoreCase(Employee.EMPLOYEE)) {
                            Intent intent = new Intent(LoginActivity.this, EmployeeActivity.class);

                            intent.putExtra("User Hash", dataSnapshot.getRef().getKey());
                            intent.putExtra("Login Email", email);
                            intent.putExtra("Login Password", password);
                            intent.putExtra("User Type", Employee.EMPLOYEE);

                            dataSnapshot.child("loginState").getRef().setValue(true);
                            startActivity(intent);

                        }
                        else if (employType.equalsIgnoreCase(Employer.EMPLOYER)) {
                            Intent intent = new Intent(LoginActivity.this, EmployerActivity.class);

                            intent.putExtra("User Hash", dataSnapshot.getRef().getKey());
                            intent.putExtra("Login Email", email);
                            intent.putExtra("Login Password", password);
                            intent.putExtra("User Type", Employer.EMPLOYER);

                            dataSnapshot.child("loginState").getRef().setValue(true);
                            startActivity(intent);
                        }
                        else {
                            Log.e("Error", "User Type is neither Employee or Employer!");
                            System.exit(-1);
                        }
                    }
                }

                if (!found) {
                    Toast.makeText(LoginActivity.this, "login failed", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void connectFirebase(){
        firebaseDB = FirebaseDatabase.getInstance(FIREBASE_DATABASE_URL);
        firebaseDBRef = firebaseDB.getReference("users");

    }

    private void listenToDataChanges(){
        firebaseDBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String valueRead = snapshot.getValue(String.class);
                textView.setText("Success: "+ valueRead);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                final String errorRead = error.getMessage();
                textView.setText("Error: "+ errorRead);

            }
        });
    }

}
