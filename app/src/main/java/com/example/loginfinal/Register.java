package com.example.loginfinal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.loginfinal.R;
import com.example.loginfinal.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Register extends AppCompatActivity {
    EditText id,name,passward;
    ImageView profile;
    Button btn;
    Uri imageUri;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.activity_register));
        id=findViewById(R.id.id);
        passward=findViewById(R.id.password);
        name=findViewById(R.id.name);
        btn=findViewById(R.id.signup);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });




    }




    private void signup() {

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(id.getText().toString(),passward.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                    StorageReference storageReference= FirebaseStorage.getInstance().getReference().child("users").child(uid);
                    storageReference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful()){
                                storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        String imageurl = task.toString();
                                        UserModel userModel = new UserModel();
                                        userModel.name=name.getText().toString();
                                        userModel.uid=uid;
                                        userModel.imageurl=imageurl;
                                        FirebaseDatabase.getInstance().getReference().child("users").child(uid).setValue(userModel);
                                    }
                                });
                            }
                        }
                    });
                }else{
                    Toast.makeText(Register.this,"Failed to create",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }



}
