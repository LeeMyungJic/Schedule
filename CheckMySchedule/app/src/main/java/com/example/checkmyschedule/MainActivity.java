package com.example.checkmyschedule;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ScheduleAdapter.ListBtnClickListener {

    Button goMakeSchedule;
    ArrayList<Schedule> scheduleArrayList;
    ScheduleAdapter scheduleAdapter;
    static final int REQ_ADD_CONTACT = 1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.InitializeScheduleList();

        ListView listView = (ListView) findViewById(R.id.scheduleList);
        scheduleAdapter = new ScheduleAdapter(this, scheduleArrayList);
        listView.setAdapter(scheduleAdapter);


        goMakeSchedule = findViewById(R.id.addButton);
        goMakeSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MakeSchedule.class);
                startActivityForResult(intent, REQ_ADD_CONTACT);

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // TODO : item click
            }
        }) ;

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

                scheduleArrayList.add(new Schedule(getTitle, getDate));
                scheduleAdapter.notifyDataSetChanged();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, intent);
        }
    }

    @Override
    public void onListBntClick(int position) {

    }

    public void InitializeScheduleList() {
        scheduleArrayList = new ArrayList<Schedule>();
    }
}

