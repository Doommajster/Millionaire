package com.example.domin.tamz_ukol_4;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SkoreAdapter extends BaseAdapter {
        Activity activity;
        LayoutInflater inflater;
        public SkoreAdapter(Activity activity, ArrayList<PolozkyDb> scores) {
            this.activity = activity;
            this.scores = scores;
            inflater=LayoutInflater.from(activity.getApplicationContext());
        }

        ArrayList<PolozkyDb> scores;
        @Override
        public int getCount() {
            return scores.size();
        }

    @Override
        public Object getItem(int i) {
            return scores.get(i);
        }

    @Override
        public long getItemId(int i) {
            return i;
        }

    @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View v=inflater.inflate(R.layout.rowscore,null);
            TextView tvscore=(TextView)v.findViewById(R.id.score);
            TextView tvdatum=(TextView)v.findViewById(R.id.datum);

            tvscore.setText(""+scores.get(i).getScore()+" " + "Kč");
            tvdatum.setText(""+scores.get(i).getDatum());

            return v;
        }
}
