package com.example.crud;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button add;
    private ListView listView;
    private TextView count;
    private Context context;
    private DBHandler dbHandler;
    private List<Todo> LTodo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHandler = new DBHandler(this);
        add = findViewById(R.id.add);
        listView = findViewById(R.id.listView);
        count = findViewById(R.id.todocount);
        context = this;
        LTodo = new ArrayList<>();

        LTodo = dbHandler.getAllTodos(); //this method can retrive all data in database

        ToDoAdapter adapter = new ToDoAdapter(context, R.layout.single_todo, LTodo);

        listView.setAdapter(adapter);

        int counttodo = dbHandler.countTodo(); //dbhandler method
        count.setText("You have " + counttodo + " todos");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddTodo.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { //list view eka athule eka item ekak, eka row ekak click kalahama mokada wenna ona
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                final Todo todo = LTodo.get(i);

                AlertDialog.Builder builder = new AlertDialog.Builder(context); //creating a dialogbox
                builder.setTitle(todo.getTitle()); //dialog box ekata test kiyala namak
                builder.setMessage(todo.getDescription());

                builder.setPositiveButton("finished", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        todo.setFinished(System.currentTimeMillis());
                        dbHandler.updateString(todo);
                        startActivity(new Intent(context, MainActivity.class));


                    }
                });

                builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dbHandler.DeleteTodo(todo.getId());
                        startActivity(new Intent(context, MainActivity.class));
                    }
                });

                builder.setNeutralButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(context, UpdateTODO.class);
                        intent.putExtra("id", String.valueOf(todo.getId()));
                        startActivity(intent);
                    }
                });
                builder.show();
            }
        });
    }
}