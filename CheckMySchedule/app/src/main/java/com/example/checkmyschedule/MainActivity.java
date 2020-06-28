package com.example.checkmyschedule;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button goMakeSchedule;
    ArrayList<Schedule> scheduleArrayList;
    ScheduleAdapter scheduleAdapter;
    ArrayAdapter<Schedule> arrayAdapter;
    static final int REQ_ADD_CONTACT = 1 ;
    private static final String SETTINGS_PLAYER_JSON = "settings_item_json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.InitializeScheduleList();

        ListView listView = (ListView) findViewById(R.id.scheduleList);
        scheduleAdapter = new ScheduleAdapter(this, 0, scheduleArrayList);
        listView.setAdapter(scheduleAdapter);

        goMakeSchedule = findViewById(R.id.addButton);
        goMakeSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MakeSchedule.class);
                startActivityForResult(intent, REQ_ADD_CONTACT);

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        if (requestCode == REQ_ADD_CONTACT) {
            if (resultCode == RESULT_OK) {
                String getTitle = intent.getStringExtra("title");
                if (getTitle != null) {
                    Log.i("받아온 값", getTitle);
                }

                String getDate = intent.getStringExtra("date") ;
                if (getDate != null) {
                    Log.i("받아온 값", getDate);
                }

                scheduleArrayList.add(new Schedule(scheduleArrayList.size(), getTitle, getDate));
                scheduleAdapter.notifyDataSetChanged();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, intent);
        }
    }



    public void InitializeScheduleList() {
        scheduleArrayList = new ArrayList<Schedule>();
    }
}

