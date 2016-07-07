package com.sini4ka.mytodomanager.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.sini4ka.mytodomanager.R;
import com.sini4ka.mytodomanager.data.DatabaseHelper;
import com.sini4ka.mytodomanager.model.Todo;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by sini4ka on 24.6.16.
 */
public class AddTodoActivity extends AbstractAppCompatActivity{
    @Override
    protected int getOptionsMenuResourceId() {
        return R.menu.action_bar_todo_add_menu;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_save_new_todo:
                this.save();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_add_todo);

        // Configure action bar
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void save() {
        String content = ((EditText) findViewById(R.id.add_todo_content_edit_text)).getText().toString();
        String title = ((EditText) findViewById(R.id.add_todo_title_edit_text)).getText().toString();;
        Timestamp date = new Timestamp(new Date().getTime());
        Todo todo = new Todo(title, content, date);

        DatabaseHelper dbHelper = new DatabaseHelper(this.getApplicationContext());
        dbHelper.addTodo(todo);
        dbHelper.close();

        this.finish();
    }
}
