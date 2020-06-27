package com.example.checkmyschedule;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MakeSchedule extends AppCompatActivity {

    int y=0, m=0, d=0;
    private EditText title;
    private DatePickerDialog.OnDateSetListener callbackMethod;
    private Button selectDate;
    private Button save;
    private TextView dateStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_schedule_layout);

        selectDate = (Button) findViewById(R.id.selectDateButton);
        save = (Button) findViewById(R.id.saveButton);
        title = (EditText)findViewById(R.id.titleStr);
        dateStr = (TextView) findViewById(R.id.selectedDateStr);

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDate();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(title.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "내용을 입력해 주세요", Toast.LENGTH_SHORT).show();
                }
                else if(y == 0) {
                    Toast.makeText(getApplicationContext(), "날짜를 선택해 주세요", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(MakeSchedule.this, MainActivity.class);
                    intent.putExtra("title", title.getText().toString());
                    Log.i("전달한 title : ", title.getText().toString());
                    intent.putExtra("date", (y + " " + m + " " + d));
                    Log.i("전달한 date : ", y + " " + m + " " + d);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    private void showDate() {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());

        String year = yearFormat.format(currentTime);
        String month = monthFormat.format(currentTime);
        String day = dayFormat.format(currentTime);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                y = year;
                m = month+1;
                d = dayOfMonth;
                dateStr.setText(y + " " + m + " " + d);

            }
        },Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));

        datePickerDialog.setMessage("메시지");
        datePickerDialog.show();
    }

}
