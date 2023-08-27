package com.example.autentificare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.autentificare.model.Common;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {


    DatabaseReference database = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fooddeliverydatabase-5ac23-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText name = findViewById(R.id.name);
        final EditText phone = findViewById(R.id.phone);
        final EditText password = findViewById(R.id.password);
        final EditText confirmPassword = findViewById(R.id.confirmPassword);

        final Button registerButton = findViewById(R.id.registerButton);
        final TextView loginButton = findViewById(R.id.loginButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String numeTxt = name.getText().toString();
                final String passwordTxt = password.getText().toString();
                final String phoneTxt = phone.getText().toString();
                final String confirmPasswordTxt = confirmPassword.getText().toString();

                if(Common.isConnectedToInterner(getBaseContext())) {

                    if (numeTxt.isEmpty() || passwordTxt.isEmpty() || phoneTxt.isEmpty() || confirmPasswordTxt.isEmpty()) {
                        Toast.makeText(Register.this, "Te rugam sa completezi toate campurile", Toast.LENGTH_SHORT).show();
                    } else if (!passwordTxt.equals(confirmPasswordTxt)) {
                        Toast.makeText(Register.this, "Parolele nu sunt la fel", Toast.LENGTH_SHORT).show();
                    } else {

                        database.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.hasChild(phoneTxt)) {
                                    Toast.makeText(Register.this, "Numarul de telefon deja exista!", Toast.LENGTH_SHORT).show();
                                } else {
                                    database.child("User").child(phoneTxt).child("name").setValue(numeTxt);
                                    database.child("User").child(phoneTxt).child("password").setValue(passwordTxt);

                                    Toast.makeText(Register.this, "Utilizator inregistrat cu succes!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                }
                else
                {
                    Toast.makeText(Register.this, "Te rugam sa te conecteze le internet!!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}