package com.futuremedicalassistance.fma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText nameEditText, lastNameEditText, idNumberEditText, phoneEditText, birthPlaceEditText, emailEditText, passwordEditText;
    Button registerButton;

    FirebaseAuth firebaseAuth;
    DatabaseReference myReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameEditText = findViewById(R.id.nameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        idNumberEditText = findViewById(R.id.idNumberEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        birthPlaceEditText = findViewById(R.id.birthPlaceEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString().trim();
                String lastName = lastNameEditText.getText().toString().trim();
                String idNumber = idNumberEditText.getText().toString().trim();
                String phone = phoneEditText.getText().toString().trim();
                String birthPlace = birthPlaceEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if(name.isEmpty()){
                    nameEditText.setError("Ingresar nombre");
                    nameEditText.requestFocus();
                    return;

                }

                if (lastName.isEmpty()){
                    lastNameEditText.setError("Ingresar Apellido");
                    lastNameEditText.requestFocus();
                    return;
                }

                if(idNumber.isEmpty()){
                    idNumberEditText.setError("Ingresar Numero de identificacion");
                    idNumberEditText.requestFocus();
                    return;
                }

                if(phone.isEmpty()){
                    phoneEditText.setError("Ingresar numero telefonico");
                    phoneEditText.requestFocus();
                    return;

                }

                if(!Patterns.PHONE.matcher(phone).matches()){
                    phoneEditText.setError("Ingresar telefono valido");
                    phoneEditText.requestFocus();
                    return;

                }

                if(birthPlace.isEmpty()){
                    birthPlaceEditText.setError("Ingresar fecha de nacimiento");
                    birthPlaceEditText.requestFocus();
                    return;

                }

                if(email.isEmpty()){
                    emailEditText.setError("Ingresar Email");
                    emailEditText.requestFocus();
                    return;

                }

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    emailEditText.setError("Ingresar email valido");
                    emailEditText.requestFocus();
                    return;

                }

                if(password.isEmpty()){
                    passwordEditText.setError("Ingresar contrasena");
                    passwordEditText.requestFocus();
                    return;

                }

                if(password.length() < 6){
                    passwordEditText.setError("Contrasena muy corta");
                    passwordEditText.requestFocus();
                    return;

                }

                SignIn( name,lastName, idNumber, phone, birthPlace, email, password);

            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

    }

    private void SignIn (final String name, String lastName, String idNumber, String phone, String birthPlace, String email, String password){
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    String userId = firebaseUser.getUid();
                    myReference = FirebaseDatabase.getInstance().getReference("Users").child(userId);

                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("id", userId);
                    hashMap.put("name",name);
                    hashMap.put("lastName", lastName);
                    hashMap.put("idNumber", idNumber);
                    hashMap.put("phone", phone);
                    hashMap.put("birthPlace", birthPlace);
                    hashMap.put("imageURL", "default");
                    hashMap.put("userType", "default");

                    myReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                finish();

                            }
                        }
                    });

                }else{
                    Toast.makeText(RegisterActivity.this, "Email o contrasena incorrecta", Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}