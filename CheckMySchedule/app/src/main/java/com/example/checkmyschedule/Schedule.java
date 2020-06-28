package com.example.checkmyschedule;

import android.widget.Button;

public class Schedule {

    private int index;
    private String date;
    private String title;
    public Schedule(int index, String title, String date) {
        this.index = index;
        this.title = title;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }
}
