package com.sini4ka.mytodomanager.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.sini4ka.mytodomanager.R;
import com.sini4ka.mytodomanager.data.DatabaseHelper;
import com.sini4ka.mytodomanager.helpers.Constants;
import com.sini4ka.mytodomanager.model.Todo;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by sini4ka on 30.6.16.
 */
public class ViewEditTodoActivity extends AbstractAppCompatActivity {
    private Todo todo;

    @Override
    protected int getOptionsMenuResourceId() {
        return R.menu.action_bar_todo_view_menu;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.action_update_todo:
                this.update();
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_todo_view_edit);

        // Configure action bar
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        Todo todo = (Todo) this.getIntent().getExtras().getSerializable(Constants.TODO_OBJECT);
        this.todo = todo;

        // Set text views
        TextView titleTextView = (TextView)findViewById(R.id.todoTitleEditText);
        titleTextView.setText(todo.getTitle());
        TextView dateTextView = (TextView)findViewById(R.id.todoDateTextView);
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        dateTextView.setText(dateFormat.format(todo.getDate()));
        TextView contentTextView = (TextView)findViewById(R.id.todoContentEditText);
        contentTextView.setText(todo.getContent());
    }

    private void update() {
        Todo todo = this.todo;
        String titleFromEditText = ((EditText)findViewById(R.id.todoTitleEditText)).getText().toString();
        String contentFromEditText = ((EditText)findViewById(R.id.todoContentEditText)).getText().toString();
        todo.setTitle(titleFromEditText);
        todo.setContent(contentFromEditText);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.updateTodo(todo);
        this.finish();
    }
}
