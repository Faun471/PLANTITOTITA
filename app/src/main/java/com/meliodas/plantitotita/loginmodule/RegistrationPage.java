package com.meliodas.plantitotita.loginmodule;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.meliodas.plantitotita.R;
import com.meliodas.plantitotita.mainmodule.HomePage;

public class RegistrationPage extends AppCompatActivity {

    private EditText editTextName, editTextEmailAddress, editTextMobileNumber, editTextPassword, editTextConfirmPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        mAuth = FirebaseAuth.getInstance();
        editTextName = findViewById(R.id.editTxtName);
        editTextEmailAddress = findViewById(R.id.editTxtEmailAddress);
        editTextMobileNumber = findViewById(R.id.editTxtMobileNumber);
        editTextPassword = findViewById(R.id.editTxtPassword);
        editTextConfirmPassword = findViewById(R.id.editTxtConfirmPassword);
    }

    public void onClickCreateAccount(View v) {
        if (editTextName.getText() == null || editTextName.getText().toString().isEmpty()){
            editTextName.setError("Name can't be blank.");
            editTextName.requestFocus();
            return;
        }

        if (editTextEmailAddress.getText() == null || editTextEmailAddress.getText().toString().isEmpty()){
            editTextEmailAddress.setError("Email can't be blank.");
            editTextEmailAddress.requestFocus();
            return;
        }

        if (editTextMobileNumber.getText() == null || editTextMobileNumber.getText().toString().isEmpty()){
            editTextMobileNumber.setError("Mobile Number can't be blank.");
            editTextMobileNumber.requestFocus();
            return;
        }

        if (editTextPassword.getText() == null || editTextPassword.getText().toString().isEmpty()){
            editTextPassword.setError("Password can't be blank.");
            editTextPassword.requestFocus();
            return;
        }

        if (editTextConfirmPassword.getText() == null || editTextConfirmPassword.getText().toString().isEmpty()){
            editTextConfirmPassword.setError("Confirm Password can't be blank.");
            editTextConfirmPassword.requestFocus();
            return;
        }

        if (editTextName.getText().length() < 2){
            editTextName.setError("Name should be at least 2 characters.");
            editTextName.requestFocus();
            return;
        }

        if (!editTextEmailAddress.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            editTextEmailAddress.setError("Invalid email address Ex.abcdefg123@gmail.com");
            editTextEmailAddress.requestFocus();
            return;
        }

        /*mAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        boolean isNewUser = task.getResult().getSignInMethods().isEmpty();
                        if (isNewUser) {
                            // return true
                        } else {
                            // return false
                        }
                    }
                });*/

        if (editTextMobileNumber.length() > 11 || editTextMobileNumber.length() < 11){
            editTextMobileNumber.setError("You can only enter 11 numbers");
            editTextMobileNumber.requestFocus();
            return;
        }

        if (!editTextMobileNumber.getText().toString().matches("^(09)\\d{9}")){
            editTextMobileNumber.setError("Invalid mobile number format");
            editTextMobileNumber.requestFocus();
            return;
        }

        if (editTextPassword.length() < 8) {
            editTextPassword.setError("Your Password is too short. Use at least 8 or more characters");
            editTextPassword.requestFocus();
            return;
        }

        if (!editTextPassword.getText().toString().matches("[A-Za-z0-9]+")){
            editTextPassword.setError("You may only enter Alphanumeric Characters");
            editTextPassword.requestFocus();
            return;
        }

        if (!editTextConfirmPassword.getText().toString().equals(editTextPassword.getText().toString())){
            editTextConfirmPassword.setError("Those passwords didn’t match. Try again.");
            editTextConfirmPassword.requestFocus();
            return;
        }

        String name = editTextName.getText().toString();
        String email = editTextEmailAddress.getText().toString();
        String password = editTextPassword.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        showDialog(EnumLayout.SUCCESS);
                    } else {
                        // If sign in fails, display a message to the user.
                        showDialog(EnumLayout.ERROR);
                    }
                });
    }

    private void showDialog(EnumLayout layout) {
        View view = LayoutInflater.from(this).inflate(
        switch (layout) {
            case SUCCESS -> R.layout.custom_alert_dialog_success;
            case ERROR -> R.layout.custom_alert_dialog_error;
        },  null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);

        final AlertDialog alertDialog = builder.create();

        Button continueButton = view.findViewById(R.id.dialogContinueButton);

        continueButton.setOnClickListener(view1 -> {
            switch (layout) {
                case SUCCESS -> {
                    startActivity(new Intent(getApplicationContext(), HomePage.class));
                    finish();
                }
                case ERROR -> alertDialog.dismiss();
            }
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();
    }

    public void onClickReturn(View v) {
        startActivity(new Intent(this, WelcomePage.class));
        finish();
    }

    public void onClickSignIn1(View v) {
        startActivity(new Intent(this, LoginPage.class));
        finish();
    }
}