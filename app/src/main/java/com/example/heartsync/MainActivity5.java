package com.example.heartsync;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity5 extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;
    private DBHelper db;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        editTextUsername = findViewById(R.id.editTextText4);
        editTextPassword = findViewById(R.id.editTextTextPassword2);
        buttonLogin = findViewById(R.id.button5);
        db = new DBHelper(this);
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        String savedUsername = sharedPreferences.getString("username", "");
        editTextUsername.setText(savedUsername);
        String savedPassword = sharedPreferences.getString("password","");
        editTextPassword.setText(savedPassword);

        // Set a click listener for the login button
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = editTextUsername.getText().toString();
                String pass = editTextPassword.getText().toString();
                if(user.equals("")||pass.equals(""))
                    Toast.makeText(MainActivity5.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                else {
                    Boolean checkuserpass = db.checkusernamepassword(user, pass);
                    if(checkuserpass==true){
                        Toast.makeText(MainActivity5.this, "Login Successful", Toast.LENGTH_SHORT).show();

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", user);
                        editor.putString("password",pass);
                        editor.apply();

                        Intent intent = new Intent(MainActivity5.this, MainActivity2.class);
                        intent.putExtra("key_username", user);
                        startActivity(intent);
                        Log.d("Username ", "Username: "+ user);
                    }else {
                        Toast.makeText(MainActivity5.this, "Login failed!! Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
