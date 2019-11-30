package com.example.domin.tamz_ukol_4;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class SqlScore extends Activity {

    ListView listSkore;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scorelist);
        listSkore = findViewById(R.id.skorlistview);

        Sqldatabase SQL = new Sqldatabase(this);
        SkoreAdapter adapter = new SkoreAdapter(this,SQL.getAllScoresHighest());
        listSkore.setAdapter(adapter);


    }

    public void skore(View view) {
        Sqldatabase SQL = new Sqldatabase(this);
        SkoreAdapter adapter = new SkoreAdapter(this, SQL.getAllScoresHighest());
        listSkore.setAdapter(adapter);
    }

    public void datum(View view) {
        Sqldatabase SQL = new Sqldatabase(this);
        ArrayList<PolozkyDb> list= SQL.getAllScores();
        Collections.reverse(list);
        SkoreAdapter adapter = new SkoreAdapter(this,list);
        listSkore.setAdapter(adapter);
    }

    public void deleteAll(View view) {
        Sqldatabase SQL = new Sqldatabase(this);
        SQL.deleteAllScores();
        SkoreAdapter adapter = new SkoreAdapter(this, SQL.getAllScoresHighest());
        listSkore.setAdapter(adapter);
    }

}
