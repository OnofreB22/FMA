package com.futuremedicalassistance.fma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText emailEditText;
    Button resetPasswordEditText;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailEditText = findViewById(R.id.emailEditText);

        resetPasswordEditText = findViewById(R.id.resetPasswordButton);
        resetPasswordEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetPassword();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void ResetPassword() {
        String email = emailEditText.getText().toString().trim();

        if(email.isEmpty()){
            emailEditText.setError("Ingresar email");
            emailEditText.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Ingresar email valido");
            emailEditText.requestFocus();
            return;
        }

        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPasswordActivity.this, "Correo de recuperacion enviado correctamente", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(ForgotPasswordActivity.this, "Algo salio mal", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}