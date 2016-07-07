package com.sini4ka.mytodomanager.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sini4ka.mytodomanager.model.Todo;

import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by sini4ka on 24.6.16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "todoManager";
    private static final String TODOS_TABLE_NAME = "todos";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTablesScript = "CREATE TABLE " + TODOS_TABLE_NAME + "("
                + "_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                + "title VARCHAR NOT NULL, "
                + "content VARCHAR NOT NULL, "
                + "date DATETIME NOT NULL"
                + ")";
        sqLiteDatabase.execSQL(createTablesScript);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        String dropTablesScript = "DROP TABLE " + TODOS_TABLE_NAME;
        sqLiteDatabase.execSQL(dropTablesScript);
        this.onCreate(sqLiteDatabase);
    }

    /**/

    public void addTodo(Todo todo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put("title", todo.getTitle());
        newValues.put("content", todo.getContent());

        String todoStringDate = this.getSqlLiteStringDate(todo.getDate());
        newValues.put("date", todoStringDate);

        db.insert(TODOS_TABLE_NAME, null, newValues);
        db.close();
    }

    public ArrayList<Todo> getAllTodos(){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectAllScript = "SELECT * FROM " + TODOS_TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAllScript, null);

        ArrayList<Todo> todoArrayList = new ArrayList<Todo>();
        if(cursor.moveToFirst()){
            do{
                Todo todo = getTodoFromCursor(cursor);
                todoArrayList.add(todo);
            }
            while (cursor.moveToNext());
        }

        db.close();
        return todoArrayList;
    }

    public static Todo getTodoFromCursor(Cursor cursor){
        int id = Integer.parseInt(cursor.getString(0));
        String title = cursor.getString(1);
        String content = cursor.getString(2);

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Timestamp date = new Timestamp(dateFormat.parse(cursor.getString(3), new ParsePosition(0)).getTime());

        Todo todo = new Todo(id, title, content, date);

        return todo;
    }

    public Cursor getAllTodosCursor(){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectAllScript = "SELECT * FROM " + TODOS_TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAllScript, null);
        cursor.moveToFirst();

        return cursor;
    }

    public void updateTodo(Todo todo){
        if(todo.getId() <= 0){
            throw new Error("Cannot update Todo with id=0");
        }

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues updatedValues = new ContentValues();
        updatedValues.put("title", todo.getTitle());
        updatedValues.put("date", this.getSqlLiteStringDate(todo.getDate()));
        updatedValues.put("content", todo.getContent());
        String where = "_id=" + todo.getId();
        db.update(TODOS_TABLE_NAME, updatedValues, where, null);
        db.close();
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectAllScript = "DELETE FROM " + TODOS_TABLE_NAME + " WHERE _id>0";
        Cursor cursor = db.rawQuery(selectAllScript, null);
    }

    public void deleteTodoByid(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectAllScript = "DELETE FROM " + TODOS_TABLE_NAME + " WHERE _id=" + id;
        Cursor cursor = db.rawQuery(selectAllScript, null);
        cursor.moveToFirst();
    }

    private String getSqlLiteStringDate(Timestamp date){
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String todoStringDate =  dateFormat.format(date);

        return todoStringDate;
    }
}
