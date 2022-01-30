package com.example.crud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddTodo extends AppCompatActivity {

    private EditText title, des;
    private Button add;
    private DBHandler dbHandler;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        title = findViewById(R.id.editTextTitle);
        des = findViewById(R.id.editTextDescription);
        add = findViewById(R.id.btnadd);
        context = this;

        dbHandler = new DBHandler(context); //creating DBhandler method to call create method

        add.setOnClickListener(new View.OnClickListener() { //button eka click karaddi mokada wenna ona
            @Override
            public void onClick(View view) {

                //assigning user inputs to local variables
                String usertitle = title.getText().toString(); //object eka string karanawa
                String userdescription = des.getText().toString();
                long started = System.currentTimeMillis(); //todo patan gaththa time from system

                //creating a model class object
                //passing inputs to class variables
                Todo todo = new Todo(usertitle, userdescription, started, 0);
                dbHandler.createTodo(todo);

                startActivity(new Intent(context, MainActivity.class));
            }
        });

    }
}