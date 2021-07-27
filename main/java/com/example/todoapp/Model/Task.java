package com.example.todoapp.Model;

import java.io.Serializable;
import java.util.UUID;

public class Task implements Serializable {

    private String _uid;
    private String _name;
    private int _status;


    public Task() {
        _uid = UUID.randomUUID().toString();
        set_name("");
        set_status(0);


    }

    public Task(String uid) {
        _uid = uid;
        set_name("");
        set_status(0);
    }

    public String get_uid() { return _uid; }

    public String get_name() {
        return _name;
    }

    public void set_name(String name) {
        _name= name;
    }

    public int get_status() {
        return _status;
    }

    public void set_status(int status) {
        _status = status;
    }

}
