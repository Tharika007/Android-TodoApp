package com.example.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class DBHandler extends SQLiteOpenHelper {



    private static final int VERSION = 1;
    private static final String DB_NAME = "todo";
    private static final String TABLE_NAME = "todo";

    //colums names
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String STARTED = "started";
    private static final String FINISHED = "finished";

    public DBHandler(@Nullable Context context) {

        super(context, DB_NAME, null, VERSION);
    }

/*

    @Override


        String TABLE_CREATE_QUERY = "CREATE TABLE "+TABLE_NAME+" " +
                "("
                +ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +TITLE + " TEXT,"
                +DESCRIPTION + " TEXT,"
                +STARTED+ " TEXT,"
                +FINISHED+" TEXT" +
                ");";

        db.execSQL(TABLE_CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS "+ TABLE_NAME;
        db.execSQL(DROP_TABLE_QUERY);


        onCreate(db);
    }
*/

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String TABLE_CREATE_QUERY = "CREATE TABLE "+TABLE_NAME+" " +
                "("
                +ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +TITLE + " TEXT,"
                +DESCRIPTION + " TEXT,"
                +STARTED+ " TEXT,"
                +FINISHED+" TEXT" +
                ");";
        sqLiteDatabase.execSQL(TABLE_CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String TABLE_CREATE_QUERY = "DROP TABLE IF EXISTS "+ TABLE_NAME;
        // Drop older table if existed
        sqLiteDatabase.execSQL(TABLE_CREATE_QUERY);

        onCreate(sqLiteDatabase);
    }

    public void createTodo(Todo todo){ //passing todo object
        SQLiteDatabase sqLiteDatabase = getWritableDatabase(); //ready to write data in database

        ContentValues contentValues = new ContentValues(); //structuring and delivering the data

        contentValues.put(TITLE,todo.getTitle()); //getting saved data from class
        contentValues.put(DESCRIPTION,todo.getDescription());
        contentValues.put(STARTED,todo.getStarted());
        contentValues.put(FINISHED,todo.getFinished());

        //save data to table
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
    }


    //count todo records(table records)
    public int countTodo(){
        SQLiteDatabase db = getReadableDatabase(); //read data
        String query = "SELECT * FROM "+ TABLE_NAME;

        Cursor cursor = db.rawQuery(query, null);
        return cursor.getCount();
    }

    //get all todos from database
    public List<Todo> getAllTodos() {

        List<Todo> toDos = new ArrayList(); //creating array list
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) { //here check weather the database is having any data or not
            do {
                //
                Todo todo = new Todo(); //creating todo object and getting data to it

                todo.setId(cursor.getInt(0));
                todo.setTitle(cursor.getString(1));
                todo.setDescription(cursor.getString(2));
                todo.setStarted(cursor.getLong(3));
                todo.setFinished(cursor.getLong(3));

                toDos.add(todo); //
            } while (cursor.moveToNext());
        }
            return toDos; //return arraylist
        }

        //delete items
    public void DeleteTodo (int id) {
        SQLiteDatabase database = getWritableDatabase();
        //deleting the row according to int id
        database.delete(TABLE_NAME,ID+" =?",new String[]{String.valueOf(id)});
        database.close();
    }

    public Todo getsingletodo(int id) {
        SQLiteDatabase database = getWritableDatabase();
        //
        Cursor cursor = database.query(TABLE_NAME, new String[]{ID, TITLE, DESCRIPTION, STARTED, FINISHED},
                ID + "= ?", new String[]{String.valueOf(id)},
                null, null, null);

        Todo todo; //Todo class object
        if (cursor != null) {
            cursor.moveToFirst();

            todo = new Todo(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getLong(3),
                    cursor.getLong(4)
            );

            return todo;
        }
            return null;
    }

    //
    public int updateString(Todo todo){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(TITLE,todo.getTitle());
        contentValues.put(DESCRIPTION,todo.getDescription());
        contentValues.put(STARTED,todo.getStarted());
        contentValues.put(FINISHED,todo.getFinished());
        int Status = sqLiteDatabase.update(TABLE_NAME,contentValues, ID +" =?",
                new String[]{String.valueOf(todo.getId())});
        sqLiteDatabase.close();
        return Status;
    }
    }
