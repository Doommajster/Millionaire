package com.example.domin.tamz_ukol_4;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

public class end extends Activity {

    TextView win;
    Button menu;
    int score=0;
    MediaPlayer player;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.endwindow);
        final SharedPreferences sharedPreferences=getSharedPreferences("zvuk", Context.MODE_PRIVATE);

        win=findViewById(R.id.vyhra);
        menu=findViewById(R.id.menu);

        player = MediaPlayer.create(getBaseContext(), R.raw.spravna);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (sharedPreferences.getString("zvuk", "").equals("ok")) {
                    player.stop();
                }
                Intent intent = new Intent(v.getContext(), Menu.class);
                startActivity(intent);
            }
        });

        Intent i = getIntent();
        score=i.getIntExtra("score",0);
        if(score==-1){
            win.setText("Bohužel to tentokrát nevyšlo, ale můžeš to zkusit znovu.");
        }
        else if(score != 0 && score!=1000000){
            win.setText("Gratuluji vyhrál si: " +score+ " Kč");
            Sqldatabase SQL = new Sqldatabase(this);
            SQL.addScore(score,""+ Calendar.getInstance().getTime());
            if (sharedPreferences.getString("zvuk", "").equals("ok")) {

                player.start();
            }
        }
        else if(score==1000000){
            win.setText("Stáváš se novým milionářem!!");
            Sqldatabase SQL = new Sqldatabase(this);
            SQL.addScore(score,""+ Calendar.getInstance().getTime());
            if (sharedPreferences.getString("zvuk", "").equals("ok")) {

                player.start();
            }
        }
        else
        {
            win.setText("Chyba ve výpočtu skóre!!!");
        }

    }
    @Override
    public void onBackPressed(){
        final SharedPreferences sharedPreferences=getSharedPreferences("zvuk", Context.MODE_PRIVATE);
        if (sharedPreferences.getString("zvuk", "").equals("ok")) {
              player.stop();
        }
    }
}
