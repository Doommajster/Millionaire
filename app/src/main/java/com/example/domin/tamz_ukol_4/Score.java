package com.example.domin.tamz_ukol_4;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

public class Score extends Activity {
    protected int score;
    Button b500,b1000,b2000,b5000,b10000,b25000,b50000,b100000,b250000,b500000,b750000,b1000000;
    Button penize;
    MediaPlayer player;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.money_how_much);

        b500=findViewById(R.id.b500);
        b1000=findViewById(R.id.b1_000);
        b2000=findViewById(R.id.b2_000);
        b5000=findViewById(R.id.b5_000);
        b10000=findViewById(R.id.b10_000);
        b25000=findViewById(R.id.b25_000);
        b50000=findViewById(R.id.b50_000);
        b100000=findViewById(R.id.b100_000);
        b250000=findViewById(R.id.b250_000);
        b500000=findViewById(R.id.b500_000);
        b750000=findViewById(R.id.b750_000);
        b1000000=findViewById(R.id.b1_000_000);
        penize=findViewById(R.id.penize);
        penize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent now=getIntent();
                Intent intent = new Intent(v.getContext(), end.class);
                intent.putExtra("score",now.getIntExtra("score",0));
                startActivity(intent);

            }
        });

        Intent intent = getIntent();
        score=intent.getIntExtra("score",0);
        if(score==500){
            b1000.setBackground(getDrawable(R.drawable.orangepressed));
            b1000.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    ZvukaOtevreni();
                }

            });
        }
        if(score==1000){
            b2000.setBackground(getDrawable(R.drawable.orangepressed));
            b2000.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    ZvukaOtevreni();
                }

            });
        }
        if(score==2000){
            b5000.setBackground(getDrawable(R.drawable.orangepressed));
            b5000.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    ZvukaOtevreni();
                }

            });
        }
        if(score==5000){
            b10000.setBackground(getDrawable(R.drawable.orangepressed));
            b10000.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    ZvukaOtevreni();
                }

            });
        }
        if(score==10000){
            b25000.setBackground(getDrawable(R.drawable.orangepressed));
            b25000.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    ZvukaOtevreni();
                }

            });
        }
        if(score==25000){
            b50000.setBackground(getDrawable(R.drawable.orangepressed));
            b50000.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    ZvukaOtevreni();
                }

            });
        }
        if(score==50000){
            b100000.setBackground(getDrawable(R.drawable.orangepressed));
            b100000.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    ZvukaOtevreni();
                }

            });
        }
        if(score==100000){
            b250000.setBackground(getDrawable(R.drawable.orangepressed));
            b250000.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    ZvukaOtevreni();
                }

            });
        }
        if(score==250000){
            b500000.setBackground(getDrawable(R.drawable.orangepressed));
            b500000.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    ZvukaOtevreni();
                }

            });
        }
        if(score==500000){
            b750000.setBackground(getDrawable(R.drawable.orangepressed));
            b750000.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    ZvukaOtevreni();
                }

            });
        }
        if(score==750000){
            b1000000.setBackground(getDrawable(R.drawable.orangepressed));
            b1000000.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    ZvukaOtevreni();
                }

            });
        }

    }

    public void ZvukaOtevreni(){
        SharedPreferences sharedPreferences=getSharedPreferences("zvuk", Context.MODE_PRIVATE);
        if (sharedPreferences.getString("zvuk", "").equals("ok"))
        {

            player = MediaPlayer.create(getBaseContext(), R.raw.starthry);
            player.start();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    player.stop();
                    Intent intent = getIntent();
                    setResult(333, intent);
                    finish();

              }
            }, 2500);
        }
        else{
            Intent intent = getIntent();
            setResult(333, intent);
            finish();
        }
    }


}
