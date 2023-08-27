package com.example.autentificare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.autentificare.model.Common;
import com.example.autentificare.model.User;
import com.example.autentificare.model.Common;
import com.example.autentificare.model.User;
import com.facebook.FacebookSdk;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.paperdb.Paper;
import com.facebook.FacebookSdk;


public class Login extends AppCompatActivity {

    DatabaseReference database = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fooddeliverydatabase-5ac23-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FacebookSdk.sdkInitialize(getApplicationContext());

        printKeyHash();
        
        final EditText phone = findViewById(R.id.phone);
        final EditText password = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.loginButton);
        final TextView registerButton = findViewById(R.id.registerButton);
        CheckBox ckbRemember = (CheckBox) findViewById(R.id.ckbRemember);

        Paper.init(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String phoneTxt = phone.getText().toString();
                final String passwordTxt = password.getText().toString();

                if(Common.isConnectedToInterner(getBaseContext())) {

                    if (ckbRemember.isChecked()) {
                        Paper.book().write(Common.USER_KEY, phoneTxt);
                        Paper.book().write(Common.PWD_KEY, passwordTxt);

                    }
                    if (phoneTxt.isEmpty() || passwordTxt.isEmpty()) {
                        Toast.makeText(Login.this, "Te rugam introduce numarul de teelfon sau parola!", Toast.LENGTH_SHORT).show();
                    } else {
                        database.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.hasChild(phoneTxt)) {
                                    User user = snapshot.child(phoneTxt).getValue(User.class);
                                    final String getPassword = snapshot.child(phoneTxt).child("password").getValue(String.class);
                                    //final String getPhone = snapshot.child(phoneTxt).getValue(String.class);
                                    if (getPassword.equals(passwordTxt)) {
                                        //Toast.makeText(Login.this, "Succes logare!!", Toast.LENGTH_SHORT).show();
                                       // Log.i("userrrr", phone.getText().toString());
                                        user.setPhone(phone.getText().toString());
                                        Intent homeIntent = new Intent(Login.this, Home.class);
                                        Common.currentUser = user;
                                        Log.i("userrrr", Common.currentUser.toString());
                                        startActivity(homeIntent);
                                        finish();
                                    } else {
                                        Toast.makeText(Login.this, "Parola gresita", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(Login.this, "Acest user nu exista in baza de date!", Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(Login.this, "Te rugam sa te conecteze le internet!!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });

        String user = Paper.book().read(Common.USER_KEY);
        String pwd = Paper.book().read(Common.PWD_KEY);
        if(user != null && pwd != null)
        {
            if(!user.isEmpty() && !pwd.isEmpty())
                intra(user,pwd);
        }
    }

    private void printKeyHash() {
        try{
            PackageInfo info = getPackageManager().getPackageInfo("com.example.autentificare",
            PackageManager.GET_SIGNATURES);
            for(Signature signature: info.signatures)
            {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(),Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private void intra(String phonee, String pwdd) {
        /*FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference table_user = database.getReference("User");*/

        Log.i("asdasd", phonee);
        Log.i("asdasdasd", pwdd);

        if(Common.isConnectedToInterner(getBaseContext())) {


            if (phonee.isEmpty() || pwdd.isEmpty()) {
                Toast.makeText(Login.this, "Te rugam introduce numarul de teelfon sau parola!", Toast.LENGTH_SHORT).show();
            }
            else {
                database.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(phonee)) {
                            User user = snapshot.child(phonee).getValue(User.class);
                            final String getPassword = snapshot.child(phonee).child("password").getValue(String.class);
                            //final String getPhone = snapshot.child(phoneTxt).getValue(String.class);
                            if (getPassword.equals(pwdd)) {
                                //Toast.makeText(Login.this, "Succes logare!!", Toast.LENGTH_SHORT).show();
                                // Log.i("userrrr", phone.getText().toString());
                                user.setPhone(phonee);
                                Intent homeIntent = new Intent(Login.this, Home.class);
                                Common.currentUser = user;
                                Log.i("userrrr", Common.currentUser.toString());
                                startActivity(homeIntent);
                                finish();
                            } else {
                                Toast.makeText(Login.this, "Parola gresita", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Login.this, "Acest user nu exista in baza de date!", Toast.LENGTH_SHORT).show();

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
            Toast.makeText(Login.this, "Te rugam sa te conecteze le internet!!", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}