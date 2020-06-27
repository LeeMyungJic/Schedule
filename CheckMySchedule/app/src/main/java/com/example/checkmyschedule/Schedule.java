package com.example.checkmyschedule;

import android.widget.Button;

public class Schedule {
    private String date;
    private String title;
    public Schedule(String title, String date) {
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
