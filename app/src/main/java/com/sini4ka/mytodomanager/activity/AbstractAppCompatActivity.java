package com.sini4ka.mytodomanager.activity;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;

import com.sini4ka.mytodomanager.R;

/**
 * Created by sini4ka on 7.7.16.
 */
public abstract class AbstractAppCompatActivity extends AppCompatActivity {
    protected abstract int getOptionsMenuResourceId();

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = this.getMenuInflater();
        menuInflater.inflate(this.getOptionsMenuResourceId(), menu);
        return true;
    }
}
