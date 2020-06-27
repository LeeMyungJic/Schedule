package com.example.checkmyschedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ScheduleAdapter extends ArrayAdapter implements View.OnClickListener{

    public interface ListBtnClickListener {
        void onListBtnClick(int position);
    }

    int resourceId ;
    private ListBtnClickListener listBtnClickListener ;
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<Schedule> sample;

    ScheduleAdapter(Context context, int resource, ArrayList<Schedule> data, ListBtnClickListener clickListener) {
        super(context, resource, data);

        this.resourceId = resource;
        this.listBtnClickListener = clickListener;

        mContext = context;
        sample = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return sample.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Schedule getItem(int position) {
        return sample.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position ;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(this.resourceId/*R.layout.listview_btn_item*/, parent, false);
        }

        View view = mLayoutInflater.inflate(R.layout.schedule_list, null);

        TextView title = (TextView)view.findViewById(R.id.scheduleStr);
        TextView date = (TextView)view.findViewById(R.id.dateStr);

        title.setText(sample.get(position).getTitle());
        date.setText(sample.get(position).getDate());

        Button delete = (Button) view.findViewById(R.id.deleteButton);
        delete.setTag(position);
        return view;
    }

    public void onClick(View v) {
        // ListBtnClickListener(MainActivity)의 onListBtnClick() 함수 호출.
        if (this.listBtnClickListener != null) {
            this.listBtnClickListener.onListBtnClick((int)v.getTag()) ;
        }
    }
}