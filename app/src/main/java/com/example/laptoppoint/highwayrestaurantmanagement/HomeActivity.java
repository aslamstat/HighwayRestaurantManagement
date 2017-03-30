package com.example.laptoppoint.highwayrestaurantmanagement;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.laptoppoint.highwayrestaurantmanagement.mainly.MainActivity;
import com.example.laptoppoint.highwayrestaurantmanagement.mainly.SignUPActivity;

public class HomeActivity extends AppCompatActivity {
    Button btnSignIn, btnSignUp;
    LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        btnSignIn = (Button) findViewById(R.id.buttonSignIN);
        btnSignUp = (Button) findViewById(R.id.buttonSignUP);
      btnSignUp.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intentSignUp=new Intent(getApplicationContext(),SignUPActivity.class);
              startActivity(intentSignUp);

          }
      });
    }

    public void signIn(View V) {
        final Dialog dialog = new Dialog(HomeActivity.this);
        dialog.setContentView(R.layout.login);
        dialog.setTitle("Login");
        final EditText editTextUserName = (EditText) dialog
                .findViewById(R.id.editTextUserNameToLogin);
        final EditText editTextPassword = (EditText) dialog
                .findViewById(R.id.editTextPasswordToLogin);

        Button btnSignIn = (Button) dialog.findViewById(R.id.buttonSignIn);
btnSignIn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String userName=editTextUserName.getText().toString();
        String password=editTextPassword.getText().toString();
        String storedPassword=loginDataBaseAdapter.getSinlgeEntry(userName);



                if (password.equals(storedPassword)) {
                    Toast.makeText(HomeActivity.this,
                            "Congrats: Login Successfull", Toast.LENGTH_LONG)
                            .show();
                    dialog.dismiss();
                    Intent main = new Intent(HomeActivity.this,MainActivity.class);
                    startActivity(main);
                } else {
                    Toast.makeText(HomeActivity.this,
                            "User Name or Password does not match",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginDataBaseAdapter.close();
    }
}