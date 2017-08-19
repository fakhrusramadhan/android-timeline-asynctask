package com.fakhrus.bootcamppariwisata;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText et_username, et_password;
    private TextInputLayout til_password;
    private String username, password;
    private Button bt_login;

    private String user = "admin";
    private String pass = "admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        setViews();
    }

    private void setViews() {

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginExecute();
            }
        });

    }

    private void loginExecute() {
        username = et_username.getText().toString();
        password = et_password.getText().toString();

        if (validation()){

            if (username.equals(user) && password.equals(pass)){
                loginSuccess();
            }else {
                loginError();
            }

        }
    }

    private void initViews() {

        et_username = (EditText) findViewById(R.id.et_username_login);
        et_password = (EditText) findViewById(R.id.et_password_login);
        til_password = (TextInputLayout) findViewById(R.id.til_password_login);
        bt_login = (Button) findViewById(R.id.bt_login_login);
    }

    private boolean validation() {
        boolean valid = true;
        if (username.isEmpty()) {
            et_username.setError("Masukkan Username Anda");
            valid = false;
        }
        if (password.isEmpty()) {
            til_password.setError("Masukkan Password Anda");
            valid = false;
        }
        return valid;
    }

    private void loginSuccess(){
        Intent toSplashScreen = new Intent(LoginActivity.this, SplashScreenActivity.class);
        startActivity(toSplashScreen);
    }

    private void loginError(){
        Toast.makeText(LoginActivity.this, "Informasi yang anda input salah", Toast.LENGTH_SHORT).show();
    }
}
