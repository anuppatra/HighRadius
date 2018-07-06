package com.example.android.highradius;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class VerificationCheck extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verification_check);
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();

        assert user != null;
        if (user.isEmailVerified()) {
            Toast.makeText(VerificationCheck.this, "Verified Email", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(VerificationCheck.this, UserProfile.class));
        } else {
            Toast.makeText(VerificationCheck.this, "Please verify your Email first", Toast.LENGTH_SHORT).show();
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(VerificationCheck.this, "Verification Email Sent", Toast.LENGTH_SHORT).show();
                }
            });

            Button refresh;
            refresh = findViewById(R.id.refresh);
            refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    assert user != null;
                    Boolean msg;
                    Toast.makeText(VerificationCheck.this,user.toString(),Toast.LENGTH_LONG).show();


                    user.reload();

                    /*user.addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {

                        }

                        @override
                        public void onSuccess(Void aVoid) {
                            user = mAuth.getCurrentUser();
                            boolean useremailveri = user.isEmailVerified();
                            String useremailuid = user.getUid();
                        }
                    });*/

                    msg=user.isEmailVerified();
                    if (user.isEmailVerified()) {

                        Toast.makeText(VerificationCheck.this, "Verified Email", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(VerificationCheck.this, UserProfile.class));
                    } else {
                        Toast.makeText(VerificationCheck.this, msg.toString(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(VerificationCheck.this, "Please verify your Email first", Toast.LENGTH_SHORT).show();
                    }

                }

            });

        }
    }}

