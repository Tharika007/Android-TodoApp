package com.example.crud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UpdateTODO extends AppCompatActivity {

    private Button updatebtn; //creating references
    private EditText uptitle, updes;
    private DBHandler dbHandler;
    private Context context;
    private Long Updateddate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_t_o_d_o);

        updatebtn = findViewById(R.id.Updatetodo);
        uptitle = findViewById(R.id.updatetitle);
        updes = findViewById(R.id.updatedescription);
        context = this;
        dbHandler = new DBHandler(context);


        final String id = getIntent().getStringExtra("id");
        Todo todo = dbHandler.getsingletodo(Integer.parseInt(id)); //single to method eka call kranawa dbhandler claa eke

        uptitle.setText(todo.getTitle());
        updes.setText(todo.getDescription());

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = uptitle.getText().toString();
                String desc = updes.getText().toString();
                Updateddate = System.currentTimeMillis();

                Todo todo = new Todo(Integer.parseInt(id),title,desc,Updateddate,0);
                int Status = dbHandler.updateString(todo);
                System.out.println(Status);
                startActivity(new Intent(context, MainActivity.class));


            }
        });

    }
}