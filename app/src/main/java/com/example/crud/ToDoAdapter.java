package com.example.crud;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ToDoAdapter extends ArrayAdapter<Todo> { //defining the type of data using
    private Context context;
    private int resource;
    List<Todo> LTodo;

    public ToDoAdapter(@NonNull Context context, int resource, @NonNull List<Todo> LTodo) { //importing LTodo
        super(context, resource, LTodo);

        this.context = context;
        this.resource = resource;
        this.LTodo = LTodo;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context); //single todo activity eka convert karanawa java walata
        View row = inflater.inflate(resource, parent, false);

        TextView title = row.findViewById(R.id.title);
        TextView description = row.findViewById(R.id.description);
        ImageView imageView = row.findViewById(R.id.imageView2);

        Todo Adaptodo = LTodo.get(position); //arraylist data retrieving position
        title.setText(Adaptodo.getTitle());
        description.setText(Adaptodo.getDescription());
        imageView.setVisibility(row.INVISIBLE);

        if (Adaptodo.getFinished() > 0) {
            imageView.setVisibility(View.VISIBLE);
        }

        return row;
    }
}


