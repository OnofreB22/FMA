package com.futuremedicalassistance.fma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogInActivity extends AppCompatActivity {
    EditText emailEditText, passwordEditText;
    Button logInButton;
    TextView registerTextView, forgotPasswordTextView;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser != null){
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
            String userId = firebaseUser.getUid();

            reference.child(userId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Users users = snapshot.getValue(Users.class);

                    if(users.getUserType().equals("doctor")){
                        startActivity(new Intent(LogInActivity.this, MainDoctorActivity.class));
                    }else{
                        startActivity(new Intent(LogInActivity.this, MainActivity.class));
                    }
                    finish();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        logInButton = findViewById(R.id.logInButton);

        registerTextView = findViewById(R.id.registerTextView);
        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this, RegisterActivity.class));
            }
        });

        forgotPasswordTextView = findViewById(R.id.forgotPasswordTextView);
        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this, ForgotPasswordActivity.class));
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(LogInActivity.this, "completa los campos", Toast.LENGTH_SHORT).show();

                }else{
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                                if(firebaseUser != null){
                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                                    String userId = firebaseUser.getUid();

                                    reference.child(userId).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            Users users = snapshot.getValue(Users.class);

                                            if(users.getUserType().equals("doctor")){
                                                startActivity(new Intent(LogInActivity.this, MainDoctorActivity.class));
                                            }else{
                                                startActivity(new Intent(LogInActivity.this, MainActivity.class));
                                            }
                                            finish();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                            }else{
                                Toast.makeText(LogInActivity.this, "email o contrasena incorrecta", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });
    }
}