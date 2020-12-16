package com.example.android.magshop.Buyers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.magshop.Model.Users;
import com.example.android.magshop.Prevalent.Prevalent;
import com.example.android.magshop.R;
import com.example.android.magshop.Sellers.SellerHomeActivity;
import com.example.android.magshop.Sellers.SellerRegistrationActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;


    public class MainActivity extends AppCompatActivity {

        private Button joinButton, loginButton;
        private ProgressDialog loadingBar;
        private TextView sellerBegin;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            joinButton = (Button) findViewById(R.id.main_join_btn);
            loginButton = (Button) findViewById(R.id.main_login_btn);
            sellerBegin = (TextView) findViewById(R.id.seller_begin);
            loadingBar = new ProgressDialog(this);

            Paper.init(this);

            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                }
            });

            sellerBegin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent loginIntent = new Intent(MainActivity.this, SellerRegistrationActivity.class);
                    startActivity(loginIntent);
                }
            });

            joinButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                    startActivity(registerIntent);
                }
            });

            String UserPhoneKey = Paper.book().read(Prevalent.UserPhoneKey);
            String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);

            if(UserPhoneKey != "" && UserPasswordKey != ""){
                if(!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(UserPasswordKey)) {
                    ValidateUser(UserPhoneKey, UserPasswordKey);

                    loadingBar.setTitle("Вход в приложение");
                    loadingBar.setMessage("Пожалуйста, подождите...");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();
                }
            }
        }

        private void ValidateUser(String phone, String password) {
            final DatabaseReference RootRef;
            RootRef = FirebaseDatabase.getInstance().getReference();

            RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child("Users").child(phone).exists()) {
                        Users usersData = dataSnapshot.child("Users").child(phone).getValue(Users.class);

                        if (usersData.getPhone().equals(phone))
                        {
                            if (usersData.getPassword().equals(password)) {
                                loadingBar.dismiss();
                                Toast.makeText(MainActivity.this, " Успешный вход!", Toast.LENGTH_SHORT).show();

                                Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
                                Prevalent.currentOnlineUser = usersData;
                                startActivity(homeIntent);
                            } else {
                                loadingBar.dismiss();
                            }

                        } else {
                            loadingBar.dismiss();
                            Toast.makeText(MainActivity.this, "Аккаунт с номером " + phone + "не существует", Toast.LENGTH_SHORT).show();

                            Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                            startActivity(registerIntent);
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        }
