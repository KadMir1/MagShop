package com.example.android.magshop.Buyers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.magshop.Prevalent.Prevalent;
import com.example.android.magshop.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ResetPasswordActivity extends AppCompatActivity
{
    private String check = "";
    private TextView pageTitle, titleQuestions;
    private EditText phoneNumber, question1, question2;
    private Button verifyButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);


        check = getIntent().getStringExtra("check");


        pageTitle = findViewById(R.id.page_title);
        titleQuestions = findViewById(R.id.title_questions);
        phoneNumber = findViewById(R.id.find_phone_number);
        question1 = findViewById(R.id.question_1);
        question2 = findViewById(R.id.question_2);
        verifyButton = findViewById(R.id.verify_btn);

    }


    @Override
    protected void onStart()
    {
        super.onStart();

        phoneNumber.setVisibility(View.GONE);

        if (check.equals("settings"))
        {
            pageTitle.setText("Задайте вопросы");
            titleQuestions.setText("Установите секретный вопрос?");
            verifyButton.setText("Установить");

            displayPreviousAnswers();


            verifyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    setAnswer();
                }
            });
        }
        else if (check.equals("login"))
        {
            phoneNumber.setVisibility(View.VISIBLE);

            verifyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    verifyUser();
                }
            });
        }
    }

    private void setAnswer()
    {
        String answer1 = question1.getText().toString().toLowerCase();
        String answer2 = question2.getText().toString().toLowerCase();

        if (question1.equals("") && question2.equals(""))
        {
            Toast.makeText(ResetPasswordActivity.this, "Пожалуйста, ответьте на оба вопроса.", Toast.LENGTH_SHORT).show();


        }
        else
        {
            DatabaseReference ref = FirebaseDatabase.getInstance()
                    .getReference()
                    .child("Users")
                    .child(Prevalent.currentOnlineUser.getPhone());

            HashMap<String, Object> userDataMap = new HashMap<>();
            userDataMap.put("answer1", answer1);
            userDataMap.put("answer2", answer2);

            ref.child("Security Questions").updateChildren(userDataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task)
                {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(ResetPasswordActivity.this, "Ваш секретный вопрос успешно установлен.", Toast.LENGTH_SHORT).show();

                        Intent homeIntent = new Intent(ResetPasswordActivity.this, HomeActivity.class);
                        startActivity(homeIntent);
                    }
                }
            });
        }
    }


    private void displayPreviousAnswers()
    {
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference()
                .child("Users")
                .child(Prevalent.currentOnlineUser.getPhone());

        ref.child("Security Questions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    String ans1 = dataSnapshot.child("answer1").getValue().toString();
                    String ans2 = dataSnapshot.child("answer2").getValue().toString();

                    question1.setText(ans1);
                    question2.setText(ans2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }



    private void verifyUser()
    {
       final String phone = phoneNumber.getText().toString();
       final String answer1 = question1.getText().toString().toLowerCase();
       final String answer2 = question2.getText().toString().toLowerCase();

       if (!phone.equals("") && !answer1.equals("") && !answer2.equals(""))
       {
           final DatabaseReference ref = FirebaseDatabase.getInstance()
                   .getReference()
                   .child("Users")
                   .child(phone);

           ref.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot)
               {
                   if (dataSnapshot.exists())
                   {
                       String mPhone = dataSnapshot.child("phone").getValue().toString();

                       if (dataSnapshot.hasChild("Security Questions"))
                       {
                           String ans1 = dataSnapshot.child("Security Questions").child("answer1").getValue().toString();
                           String ans2 = dataSnapshot.child("Security Questions").child("answer2").getValue().toString();

                           if (!ans1.equals(answer1))
                           {
                               Toast.makeText(ResetPasswordActivity.this, "Ответ на первый вопрос неверен.", Toast.LENGTH_SHORT).show();
                           }
                           else if (!ans2.equals(answer2))
                           {
                               Toast.makeText(ResetPasswordActivity.this, "Ответ на второй вопрос неверен.", Toast.LENGTH_SHORT).show();
                           }
                           else
                           {
                               AlertDialog.Builder builder = new AlertDialog.Builder(ResetPasswordActivity.this);
                               builder.setTitle("Новый пароль");

                               final EditText newPassword = new EditText(ResetPasswordActivity.this);
                               newPassword.setHint("Напишите пароль здесь...");
                               builder.setView(newPassword);

                               builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialog, int which)
                                   {
                                       if (!newPassword.getText().toString().equals(""))
                                       {
                                           ref.child("password")
                                                   .setValue(newPassword.getText().toString())
                                                   .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                       @Override
                                                       public void onComplete(@NonNull Task<Void> task)
                                                       {
                                                           if (task.isSuccessful())
                                                           {
                                                               Toast.makeText(ResetPasswordActivity.this, "Пароль успешно изменён.", Toast.LENGTH_SHORT).show();

                                                               Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                                                               startActivity(intent);
                                                           }

                                                       }
                                                   });
                                       }

                                   }
                               });
                               builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialogInterface, int i)
                                   {
                                        dialogInterface.cancel();
                                   }
                               });

                               builder.show();
                           }
                       }
                       else
                       {
                           Toast.makeText(ResetPasswordActivity.this, "у вас не установлена защита вопросом.", Toast.LENGTH_SHORT).show();
                       }
                   }
                   else
                   {
                       Toast.makeText(ResetPasswordActivity.this, "этого номера телефона не существует.", Toast.LENGTH_SHORT).show();
                   }
               }

               @Override
               public void onCancelled(@NonNull DatabaseError error) {

               }
           });
       }
       else
       {
           Toast.makeText(this, "пожалуйста, заполните форму.", Toast.LENGTH_SHORT).show();
       }
    }
}