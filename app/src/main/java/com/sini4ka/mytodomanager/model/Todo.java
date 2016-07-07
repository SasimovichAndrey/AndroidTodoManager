package com.sini4ka.mytodomanager.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by sini4ka on 24.6.16.
 */
public class Todo implements Serializable{
    private int id;
    private String title;
    private String content;
    private Timestamp date;

    public Todo(String title, String content, Timestamp date) {
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public Todo(int id, String title, String content, Timestamp date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
