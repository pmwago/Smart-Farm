package com.example.smartfarm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Forgot extends AppCompatActivity {

    private EditText textEmail, pass, pwd;
    private TextView login;
    private Button reset;
    private FirebaseAuth mAuth;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        textEmail = findViewById(R.id.email);
        pass = findViewById(R.id.pwd);
        pwd = findViewById(R.id.confPwd);
        reset = findViewById(R.id.reset);
        login = findViewById(R.id.login);


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resetCredentials();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent log = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(log);
            }
        });

    }

    public void resetCredentials() {
        String password = pwd.getText().toString().trim();
        String confPassword = pass.getText().toString().trim();
        email = textEmail.getText().toString().trim();

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
        if (confPassword.isEmpty()) {
            pwd.setError("Please confirm password");
            pwd.requestFocus();
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
        if (!password.equals(confPassword)) {
            pass.setError("Passwords must match");
            pass.requestFocus();
            return;
        } else {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        String thisEmail = data.child("email").getValue().toString();
                        if (email.equals(thisEmail)) {
                            String keyId = data.getKey();
                            ref.child(keyId).child("password").setValue(password);
                            Toast.makeText(getApplicationContext(), "Password reset successfully", Toast.LENGTH_SHORT).show();
                            Intent log = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(log);
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
