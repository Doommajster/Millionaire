package com.example.domin.tamz_ukol_4;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class Telefon extends Activity {
    protected int spravnaOdpoved;
    Button diky;
    TextView telefon_odpoved;
    MediaPlayer player;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.telefon);
        diky = findViewById(R.id.dikyB);
        telefon_odpoved =findViewById(R.id.telefon_odpoved);

        final SharedPreferences sharedPreferences = this.getSharedPreferences("zvuk", Context.MODE_PRIVATE);
        if (sharedPreferences.getString("zvuk", "").equals("ok")) {
            player = MediaPlayer.create(getBaseContext(), R.raw.phoneafriedn);
            player.start();
        }

        diky.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (sharedPreferences.getString("zvuk", "").equals("ok"))
                     player.stop();

                Intent intent = getIntent();
                setResult(331, intent);
                finish();
            }

        });

        Intent intent = getIntent();
        spravnaOdpoved= intent.getIntExtra("spravna",9);
        Log.d("spravna odpoved",""+spravnaOdpoved);
        char pismeno;
        if (spravnaOdpoved == 1) pismeno = 'A';
        else if (spravnaOdpoved == 2) pismeno = 'B';
        else if (spravnaOdpoved == 3) pismeno = 'C';
        else pismeno = 'D';

        Random random = new Random();
        int i = random.nextInt(5);
        switch (i) {

            case 0:
                telefon_odpoved.setText("Neznám odpověď bohužel kámo :(. Hodně štěstí.");
                break;

            case 1:
                telefon_odpoved.setText("Našel jsem to na Googlu je to " + pismeno);
                break;

            case 2:
                telefon_odpoved.setText("Nejsem si jistý, ale asi to bude  " + pismeno + ".");
                break;

            case 3:
                telefon_odpoved.setText("Kdybych byl tebou tak bych dal " + pismeno + ".");
                break;

            case 4:
                telefon_odpoved.setText("Čau kámo hodně stěstí, myslím si, že to je  " + pismeno + ".");
                break;
        }


    }
    @Override
    public void onBackPressed() {
        final SharedPreferences sharedPreferences = this.getSharedPreferences("zvuk", Context.MODE_PRIVATE);
        if (sharedPreferences.getString("zvuk", "").equals("ok"))
            player.stop();
        super.onBackPressed();
    }
}