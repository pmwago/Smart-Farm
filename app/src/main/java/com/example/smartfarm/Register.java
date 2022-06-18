package com.example.smartfarm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {
    private EditText textEmail,pass,pwd,textRole,textLivestock;
    private Button register;
    private FirebaseAuth mAuth;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        textEmail=findViewById(R.id.email);
        pass=findViewById(R.id.pwd);
        pwd=findViewById(R.id.confPwd);
        textRole=findViewById(R.id.role);
        textLivestock=findViewById(R.id.livestock);
        register=findViewById(R.id.register);

        mAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                register();
            }
        });


    }
    public void  register() {
        String email = textEmail.getText().toString().trim();
        String password = pass.getText().toString().trim();
        String confirmPassword = pwd.getText().toString().trim();
        String role = textRole.getText().toString().trim();
        String livestock = textLivestock.getText().toString().trim();

        if (email.isEmpty()) {
            textEmail.setError("Provide email");
            textEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            pass.setError("Please provide password");
            pass.requestFocus();
            return;
        }
        if (confirmPassword.isEmpty()) {
            pwd.setError("Please confirm password");
            pwd.requestFocus();
            return;
        }
        if (role.isEmpty()) {
            textRole.setError("Please your age");
            textRole.requestFocus();
            return;
        }
        if (password.length() < 6) {
            pass.setError("Password should be more than 6 characters long");
            pass.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textEmail.setError("Provide a valid email");
            textEmail.requestFocus();
            return;
        }
        if (!password.equals(confirmPassword)) {
            pass.setError("Passwords must match");
            pass.requestFocus();
            return;
        } else {
            ref=database.getReference().child("Users");
            ref.orderByChild("email").equalTo(email).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        Toast.makeText(getApplicationContext(), "Email already exists,use another email", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String id=ref.push().getKey();
                        UserModel model=new UserModel(email,password,role,livestock);
                        ref.child(id).setValue(model);
                        Toast.makeText(getApplicationContext(), "User registered", Toast.LENGTH_SHORT).show();
                        Intent log = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(log);
                        textEmail.setText("");
                        textLivestock.setText("");
                        textRole.setText("");
                        pass.setText("");
                        pwd.setText("");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }
}