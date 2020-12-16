package com.example.android.magshop.Buyers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.magshop.Admin.AdminHomeActivity;
import com.example.android.magshop.Sellers.SellerProductCategoryActivity;
import com.example.android.magshop.Model.Users;
import com.example.android.magshop.Prevalent.Prevalent;
import com.example.android.magshop.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;


import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    private Button loginBtn;
    private EditText phoneInput, passwordInput;
    private ProgressDialog loadingBar;
    private TextView AdminLink, NotAdminLink, ForgetPasswordLink;

    private String parentDbName = "Users";
    private CheckBox checkBoxRememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = (Button) findViewById(R.id.login_btn);
        passwordInput = (EditText) findViewById(R.id.login_password_input);
        phoneInput = (EditText) findViewById(R.id.login_phone_input);
        AdminLink = (TextView) findViewById(R.id.admin_panel_link);
        NotAdminLink = (TextView) findViewById(R.id.not_admin_panel_link);
        ForgetPasswordLink = findViewById(R.id.forget_password);
        loadingBar = new ProgressDialog(this);


        checkBoxRememberMe = (CheckBox) findViewById(R.id.login_checkbox);
        Paper.init(this);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.this.loginUser();
            }
        });

        ForgetPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                intent.putExtra("check", "login");
                startActivity(intent);
            }
        });

        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                loginBtn.setText("Вход для админа");
                AdminLink.setVisibility(View.INVISIBLE);
                NotAdminLink.setVisibility(View.VISIBLE);
                parentDbName = "Admins";
            }
        });

        NotAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                loginBtn.setText("Войти");
                AdminLink.setVisibility(View.VISIBLE);
                NotAdminLink.setVisibility(View.INVISIBLE);
                parentDbName = "Users";
            }
        });
    }

    private void loginUser() {
        String phone = phoneInput.getText().toString();
        String password = passwordInput.getText().toString();

        if(TextUtils.isEmpty(phone))
        {
            Toast.makeText(this,"Введите номер", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"Введите пароль", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Вход в приложение");
            loadingBar.setMessage("Пожалуйста, подождите...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidateUser(phone, password);
        }
    }

    private void ValidateUser(final String phone, final String password) {

        if(checkBoxRememberMe.isChecked()) {
            Paper.book().write(Prevalent.UserPhoneKey, phone);
            Paper.book().write(Prevalent.UserPasswordKey, password);

        }

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(parentDbName).child(phone).exists()) {
                    Users usersData = dataSnapshot.child(parentDbName).child(phone).getValue(Users.class);

                    if (usersData.getPhone().equals(phone)) {
                        if (usersData.getPassword().equals(password)) {
                            if (parentDbName.equals("Admins")) {
                                Toast.makeText(LoginActivity.this, "Успешный вход", Toast.LENGTH_SHORT).show();

                                loadingBar.dismiss();

                                Intent homeIntent = new Intent(LoginActivity.this, AdminHomeActivity.class);
                                startActivity(homeIntent);
                            } else if (parentDbName.equals("Users")) {
                                Toast.makeText(LoginActivity.this, "Успешный вход!", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
                                Prevalent.currentOnlineUser = usersData;
                                startActivity(homeIntent);
                            }
                        } else {
                            loadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, "Неверный пароль", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                    else
                        {
                        Toast.makeText(LoginActivity.this, "Аккаунт с номером " + phone + "не существует", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();

                    }
                }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}