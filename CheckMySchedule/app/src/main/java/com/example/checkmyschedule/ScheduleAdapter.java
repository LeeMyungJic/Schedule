package com.example.checkmyschedule;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ScheduleAdapter extends ArrayAdapter<Schedule> {

    private Context context;
    private ArrayList<Schedule> scheduleList;

    ScheduleAdapter(Context context, int textViewResourceId, ArrayList<Schedule> data) {
        super(context, textViewResourceId, data);
        this.context = context;
        this.scheduleList = data;
    }

    /*
    @Override
    public int getCount() {
        return scheduleList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Schedule getItem(int position) {
        return scheduleList.get(position);
    }
     */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

       View view = convertView;

       if(null == view) {
           LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           view = vi.inflate(R.layout.schedule_list, null);

           TextView title = (TextView) view.findViewById(R.id.scheduleStr);
           TextView date = (TextView) view.findViewById(R.id.dateStr);

           title.setText(scheduleList.get(position).getTitle());
           date.setText(scheduleList.get(position).getDate());

           Button delete = (Button) view.findViewById(R.id.deleteButton);
           delete.setTag(position);
           delete.setOnClickListener(mOnClickListener);
       }

        return view;
    }

    // 리스트뷰 내 버튼 클릭으로 아이템 동적 삭제
    Button.OnClickListener mOnClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.i("선택된 아이템", view.getTag().toString());
            int position = Integer.parseInt((view.getTag().toString()));
            scheduleList.remove(position);
            notifyDataSetChanged();
        }
    };
}