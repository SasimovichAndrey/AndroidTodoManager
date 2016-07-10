package com.sini4ka.mytodomanager.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.sini4ka.mytodomanager.R;
import com.sini4ka.mytodomanager.data.DatabaseHelper;
import com.sini4ka.mytodomanager.helpers.Constants;
import com.sini4ka.mytodomanager.model.Todo;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;


public class MainActivity extends AbstractAppCompatActivity {
    private TodoListItemCursorAdapter adapter;
    private DatabaseHelper dbHelper;

    @Override
    protected int getOptionsMenuResourceId() {
        return R.menu.actionbar_menu;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_add_todo:
                this.createNewTodo();
                break;
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.dbHelper = new DatabaseHelper(this.getApplicationContext());

        // Set adapter for ListView
        ListView todoListView = (ListView)findViewById(R.id.todoList);
        Cursor cursor = this.dbHelper.getAllTodosCursor();
        this.adapter = new TodoListItemCursorAdapter(this, cursor);
        todoListView.setAdapter(this.adapter);

        // Set click event listener on ListView items
        todoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Create new Intent for starting new activity
                Intent intent = new Intent(MainActivity.this, ViewEditTodoActivity.class);

                // Get clicked item Todo from Cursor object
                Cursor cursor = (Cursor)adapterView.getItemAtPosition(i);
                Todo todo = DatabaseHelper.getTodoFromCursor(cursor);

                // Attach Todo object to Intent
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.TODO_OBJECT, todo);
                intent.putExtras(bundle);

                // Staty AddTodoActivity
                startActivity(intent);
            }
        });


        // Set delete action
        todoListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int todoListIndex, long l) {

                Cursor cursor = (Cursor)adapterView.getItemAtPosition(todoListIndex);
                final Todo todo = DatabaseHelper.getTodoFromCursor(cursor);
                CharSequence[] items = {"Delete"};
                new AlertDialog.Builder(view.getContext()).setItems(items, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i == 0){
                            MainActivity.this.dbHelper.deleteTodoByid(todo.getId());
                            MainActivity.this.dbHelper.close();
                            MainActivity.this.refresh();
                        }
                    }
                }).show();

                return true;
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        this.refresh();
    }

    public void createNewTodo(){
        Intent intent = new Intent(this, AddTodoActivity.class);
        this.startActivity(intent);
    }

    public void refresh(){
        Cursor cursor = this.dbHelper.getAllTodosCursor();
        this.adapter.swapCursor(cursor);
    }

    private class TodoListItemCursorAdapter extends CursorAdapter{

        public TodoListItemCursorAdapter(Context context, Cursor c) {
            super(context, c, 0);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.todo_list_item, parent, false); // have no idea what this means :)
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView titleTextView = (TextView)view.findViewById(R.id.todo_list_item_title);
            String todoTitle = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            String todoContent = cursor.getString(cursor.getColumnIndexOrThrow("content"));

            String resultTitle = null;
            if(!(todoTitle == null || todoTitle.trim().isEmpty())){
                resultTitle = todoTitle;
            }
            else{
                String todoContentFirstLine = todoContent.split("\n", 2)[0];
                if(todoContentFirstLine.length() <= 20){
                    resultTitle = todoContentFirstLine;
                }
                else{
                    resultTitle = todoContentFirstLine.substring(0, 19) + "...";
                }
            }

            titleTextView.setText(resultTitle);
        }
    }
}
