package com.example.domin.tamz_ukol_4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Audience extends Activity {
    protected int spravna_odpoved;
    Button okey;
    ImageView Asloupec, Bsloupec, Csloupec, Dsloupec;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audience);
        okey = findViewById(R.id.ok);
        Asloupec = findViewById(R.id.Asloupec);
        Bsloupec= findViewById(R.id.Bsloupec);
        Csloupec = findViewById(R.id.Csloupec);
        Dsloupec = findViewById(R.id.Dsloupec);

        okey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                setResult(330, intent);
                finish();
            }

        });
        Intent intent = getIntent();
        spravna_odpoved = intent.getIntExtra("spravna", 0);
        if(spravna_odpoved==1){
            Asloupec.setImageResource(R.drawable.spravnysloupec);
            Bsloupec.setImageResource(R.drawable.sloupec1);
            Csloupec.setImageResource(R.drawable.sloupec3);
            Dsloupec.setImageResource(R.drawable.sloupec2);
        }
        else if(spravna_odpoved==2){
            Asloupec.setImageResource(R.drawable.sloupec2);
            Bsloupec.setImageResource(R.drawable.spravnysloupec);
            Csloupec.setImageResource(R.drawable.sloupec3);
            Dsloupec.setImageResource(R.drawable.sloupec1);
        }
        else if(spravna_odpoved==3){
            Asloupec.setImageResource(R.drawable.sloupec3);
            Bsloupec.setImageResource(R.drawable.sloupec2);
            Csloupec.setImageResource(R.drawable.spravnysloupec);
            Dsloupec.setImageResource(R.drawable.sloupec1);
        }
        else if(spravna_odpoved==4){
            Asloupec.setImageResource(R.drawable.sloupec3);
            Bsloupec.setImageResource(R.drawable.sloupec2);
            Csloupec.setImageResource(R.drawable.sloupec1);
            Dsloupec.setImageResource(R.drawable.spravnysloupec);
        }
    }



    }

