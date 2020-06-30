package com.example.checkmyschedule;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    Button goMakeSchedule;
    ArrayList<Schedule> scheduleArrayList;
    ScheduleAdapter scheduleAdapter;
    static final int REQ_ADD_CONTACT = 1 ;
    private long startTime = 0;
    private static final String SETTINGS_PLAYER_JSON = "settings_item_json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.InitializeScheduleList();

        scheduleArrayList = getStringArrayPref(getApplicationContext(), SETTINGS_PLAYER_JSON);

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

    private void setStringArrayPref(Context context, String key, ArrayList<Schedule> values) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();

        Gson gson = new Gson();
        try {
            JSONArray jsonArray = new JSONArray();
            String json = "";
            JSONObject jsonObject;
            for(int i = 0; i < values.size(); i++){
                jsonObject = new JSONObject();
                jsonObject.put("title", values.get(i).getTitle());
                jsonObject.put("data", values.get(i).getDate());
                jsonArray.put(jsonObject);
            }
            if (!values.isEmpty()) {
                editor.putString(key, jsonArray.toString());
            }
            else {
                editor.putString(key, null);
            }
            editor.apply();
        }
        catch (Exception e) {
            e.getMessage();
        }
    }

    private ArrayList<Schedule> getStringArrayPref(Context context, String key) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String json = prefs.getString(key, null);

        ArrayList<Schedule> arrayList = new ArrayList();

        if (json != null) {
            try {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String title = jsonObject.optString("title");
                    String data = jsonObject.optString("data");
                    arrayList.add(new Schedule(i, title, data));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return arrayList;
    }

    @Override
    public void onStop() {
        super.onStop();
        setStringArrayPref(getApplicationContext(), SETTINGS_PLAYER_JSON, scheduleArrayList);
        Log.d(TAG, "Put json");

    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - startTime >= 2000) {
            startTime = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(), "한번 더 누르면 종료합니다!", Toast.LENGTH_SHORT).show();
        } else if (System.currentTimeMillis() - startTime < 2000) {
            finish();
        }
    }
}

