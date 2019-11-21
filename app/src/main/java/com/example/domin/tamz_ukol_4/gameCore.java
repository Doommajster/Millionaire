package com.example.domin.tamz_ukol_4;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class gameCore extends Activity {

    public String[] otazky_easy=new otazky().getOtazky_easy();
    public String[] otazky_medium=new otazky().getOtazky_medium();
    public String[] otazky_hard=new otazky().getOtazky_hard();
    public Integer[] money={500,1000,2000,5000,10000,25000,50000,100000,250000,500000,750000,1000000};

    private Button a,b,c,d;
    private CountDownTimer CDT;
    private TextView otazka,time;
    public int score=0,casVMS=60000;
    public int life=1;
    public boolean publikumB=false,telefonB=false,padesatNaPadesatB=false,timmerRunning=false;
    public int randomCislo=0,spravnaOdpoved=0,increment=0;
    public ImageView padesatNaPadesat;
    public ImageView telefon;
    public ImageView publikum;
    public MediaPlayer player;

    int maxVolume = 50,currVolume=50,cekej=1000;
    float log1=(float)(Math.log(maxVolume-currVolume)/Math.log(maxVolume));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamecore);
        player = MediaPlayer.create(this, R.raw.starthry);
        a=findViewById(R.id.button1);
        b=findViewById(R.id.button2);
        c=findViewById(R.id.button3);
        d=findViewById(R.id.button4);
        otazka=findViewById(R.id.textView3);
        padesatNaPadesat = findViewById(R.id.padesatnapadesat);
        telefon = findViewById(R.id.telefon);
        publikum = findViewById(R.id.publikum);
        time=findViewById(R.id.time);
        final SharedPreferences sharedPreferences=getSharedPreferences("zvuk", Context.MODE_PRIVATE);
        a.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View v){
                Handler handler = new Handler();
                if(spravnaOdpoved==randomCislo+1){
                    if(score==1000000){
                        a.setBackground(getDrawable(R.drawable.greenbackground));

                        CDT.cancel();

                        if(sharedPreferences.getString("zvuk","").equals("ok"))
                        {
                            player.stop();
                            player=MediaPlayer.create(getBaseContext(),R.raw.spravna);
                            player.start();
                        }

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                player.stop();
                                Intent intent1=new Intent(v.getContext(),end.class);
                                intent1.putExtra("score",score);
                                startActivity(intent1);

                            }
                        },cekej);
                    }
                    else{
                        score=money[increment];
                        increment++;
                        a.setBackground(getDrawable(R.drawable.greenbackground));
                        CDT.cancel();
                        if(sharedPreferences.getString("zvuk","").equals("ok"))
                        {
                            player.stop();
                            player=MediaPlayer.create(getBaseContext(),R.raw.spravna);
                            player.start();
                        }
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                player.stop();
                                Intent intent1=new Intent(v.getContext(),Score.class);
                                intent1.putExtra("score",score);
                                startActivityForResult(intent1,333);

                            }
                        },cekej);
                    }

                }
                else{
                    a.setBackground(getDrawable(R.drawable.red_background));
                    life-=1;
                    if(sharedPreferences.getString("zvuk","").equals("ok"))
                    {
                        player.stop();
                        player=MediaPlayer.create(getBaseContext(),R.raw.spatna);
                        player.start();
                    }
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (life == 0) {
                                player.stop();
                                CDT.cancel();
                                Intent i = new Intent(v.getContext(), end.class);
                                i.putExtra("score", -1);
                                startActivity(i);
                            }
                        }
                        },cekej);
                }

            }

        });

        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View v){
                Handler handler = new Handler();
                if(spravnaOdpoved==randomCislo+2){
                    if(score==1000000){
                        b.setBackground(getDrawable(R.drawable.greenbackground));

                        CDT.cancel();
                        if(sharedPreferences.getString("zvuk","").equals("ok"))
                        {
                            player.stop();
                            player=MediaPlayer.create(getBaseContext(),R.raw.spravna);
                            player.start();
                        }
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                player.stop();
                                Intent intent1=new Intent(v.getContext(),end.class);
                                intent1.putExtra("score",score);
                                startActivity(intent1);

                            }
                        },cekej);
                    }
                    else{
                        score=money[increment];
                        increment++;
                        b.setBackground(getDrawable(R.drawable.greenbackground));

                        CDT.cancel();
                        if(sharedPreferences.getString("zvuk","").equals("ok"))
                        {
                            player.stop();
                            player=MediaPlayer.create(getBaseContext(),R.raw.spravna);
                            player.start();
                        }
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                player.stop();
                                Intent intent1=new Intent(v.getContext(),Score.class);
                                intent1.putExtra("score",score);
                                startActivityForResult(intent1,333);

                            }
                        },cekej);
                    }
                }
                else{
                    b.setBackground(getDrawable(R.drawable.red_background));
                    life-=1;
                    if(sharedPreferences.getString("zvuk","").equals("ok"))
                    {
                        player.stop();
                        player=MediaPlayer.create(getBaseContext(),R.raw.spatna);
                        player.start();
                    }
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (life == 0) {
                                player.stop();
                                CDT.cancel();
                                Intent i = new Intent(v.getContext(), end.class);
                                i.putExtra("score", -1);
                                startActivity(i);
                            }
                        }
                    },cekej);
                }

            }
        });

        c.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View v){
                Handler handler = new Handler();
                if(spravnaOdpoved==randomCislo+3){
                    if(score==1000000){
                        c.setBackground(getDrawable(R.drawable.greenbackground));

                        CDT.cancel();
                        if(sharedPreferences.getString("zvuk","").equals("ok"))
                        {
                            player.stop();
                            player=MediaPlayer.create(getBaseContext(),R.raw.spravna);
                            player.start();
                        }
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                player.stop();
                                Intent intent1=new Intent(v.getContext(),end.class);
                                intent1.putExtra("score",score);
                                startActivity(intent1);

                            }
                        },cekej);
                    }
                    else{
                        score=money[increment];
                        increment++;
                        c.setBackground(getDrawable(R.drawable.greenbackground));

                        CDT.cancel();
                        if(sharedPreferences.getString("zvuk","").equals("ok"))
                        {
                            player.stop();
                            player=MediaPlayer.create(getBaseContext(),R.raw.spravna);
                            player.start();
                        }
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                player.stop();
                                Intent intent1=new Intent(v.getContext(),Score.class);
                                intent1.putExtra("score",score);
                                startActivityForResult(intent1,333);

                            }
                        },cekej);
                    }
                }
                else{
                    c.setBackground(getDrawable(R.drawable.red_background));
                    life-=1;

                    if(sharedPreferences.getString("zvuk","").equals("ok"))
                    {
                        player.stop();
                        player=MediaPlayer.create(getBaseContext(),R.raw.spatna);
                        player.start();
                    }
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (life == 0) {
                                player.stop();
                                CDT.cancel();
                                Intent i = new Intent(v.getContext(), end.class);
                                i.putExtra("score", -1);
                                startActivity(i);
                            }
                        }
                    },cekej);
                }

            }
        });

        d.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View v){
                Handler handler = new Handler();
                if(spravnaOdpoved==randomCislo+4){
                    if(score==1000000){
                        d.setBackground(getDrawable(R.drawable.greenbackground));

                        CDT.cancel();
                        if(sharedPreferences.getString("zvuk","").equals("ok"))
                        {

                            player.stop();
                            player=MediaPlayer.create(getBaseContext(),R.raw.spravna);
                            player.start();
                        }
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                player.stop();
                                Intent intent1=new Intent(v.getContext(),end.class);
                                intent1.putExtra("score",score);
                                startActivity(intent1);

                            }
                        },cekej);
                    }
                    else{
                        score=money[increment];
                        increment++;
                        d.setBackground(getDrawable(R.drawable.greenbackground));

                        CDT.cancel();
                        if(sharedPreferences.getString("zvuk","").equals("ok"))
                        {
                            player.stop();
                            player=MediaPlayer.create(getBaseContext(),R.raw.spravna);
                            player.start();
                        }
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                player.stop();
                                Intent intent1=new Intent(v.getContext(),Score.class);
                                intent1.putExtra("score",score);
                                startActivityForResult(intent1,333);

                            }
                        },cekej);
                    }

                }
                else{
                    d.setBackground(getDrawable(R.drawable.red_background));
                    life-=1;
                    if(sharedPreferences.getString("zvuk","").equals("ok"))
                    {
                        player.stop();
                        player=MediaPlayer.create(getBaseContext(),R.raw.spatna);
                        player.start();
                    }
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (life == 0) {
                                player.stop();
                                CDT.cancel();
                                Intent i = new Intent(v.getContext(), end.class);
                                i.putExtra("score", -1);
                                startActivity(i);
                            }
                        }
                    },cekej);
                }


            }
        });

        if (sharedPreferences.getString("zvuk", "").equals("ok"))
        {
                cekej=3500;
                 player.start();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        player.stop();
                        player = MediaPlayer.create(getBaseContext(), R.raw.otazka);
                        player.start();
                        player.setLooping(true);
                        player.setVolume(log1, log1);
                    }
                }, 2500);
        }



        startGame();

    }

    public void startTimer(){
        timmerRunning=true;
        CDT = new CountDownTimer(casVMS,1000) {
            @Override
            public void onTick(long l) {
                 casVMS-=1000;
                 updateTimer();
            }

            @Override
            public void onFinish() {
                life-=1;
            }
        }.start();
    }

    public void updateTimer(){

        int minutes= (int) casVMS/60000;
        int seconds = (int) casVMS % 60000 / 1000;
        //Log.d("zidan",""+casVMS);
        String timeLeftText;
        timeLeftText= "" + minutes;
        timeLeftText += ":";
        if(seconds <10) timeLeftText+="0";
        timeLeftText+=seconds;
        //Log.d("zidan",""+timeLeftText);
        time.setText(timeLeftText);
        if(minutes==0&&seconds==0){
            life-=1;
            if(life==0) {
                CDT.cancel();
                Context context = this;
                player.stop();
                Intent i = new Intent(context,end.class);
                i.putExtra("score", -1);
                startActivity(i);
            }
        }
    }

    public void startGame(){

        a.setBackground(getDrawable(R.drawable.button_background_main));
        b.setBackground(getDrawable(R.drawable.button_background_main));
        c.setBackground(getDrawable(R.drawable.button_background_main));
        d.setBackground(getDrawable(R.drawable.button_background_main));

           publikum.setOnTouchListener(new View.OnTouchListener() {
                                            @Override
                                            public boolean onTouch(View v, MotionEvent event) {
                                                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                                    if(publikumB) return false;
                                                    else{

                                                        Intent intent2=new Intent(v.getContext(),Audience.class);
                                                        intent2.putExtra("spravna",spravnaOdpoved%6);
                                                        publikum.setImageResource(R.drawable.nopublikum);
                                                        startActivityForResult(intent2,330);
                                                        publikumB=true;
                                                        return true;
                                                    }

                                                }
                                                return false;
                                            }

                                        }
            );
        telefon.setOnTouchListener(new View.OnTouchListener() {
                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                                if(telefonB) return false;
                                                else{
                                                    SharedPreferences sharedPreferences=getSharedPreferences("zvuk", Context.MODE_PRIVATE);
                                                    if(sharedPreferences.getString("zvuk","").equals("ok"))
                                                      player.stop();


                                                    Intent intent3=new Intent(v.getContext(),Telefon.class);
                                                    intent3.putExtra("spravna",spravnaOdpoved%6);
                                                    telefon.setImageResource(R.drawable.notelefon);
                                                    startActivityForResult(intent3,331);
                                                    telefonB=true;
                                                    return true;
                                                }

                                            }
                                            return false;
                                        }

                                    }
        );
        padesatNaPadesat.setOnTouchListener(new View.OnTouchListener() {
                                       @Override
                                       public boolean onTouch(View v, MotionEvent event) {
                                           if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                               if(padesatNaPadesatB) return false;
                                               else{
                                                    if(spravnaOdpoved%6==1){
                                                        b.setText("");
                                                        c.setText("");
                                                    }
                                                   else if(spravnaOdpoved%6==2){
                                                       a.setText("");
                                                       c.setText("");
                                                   }
                                                   else if(spravnaOdpoved%6==3){
                                                       b.setText("");
                                                       d.setText("");
                                                   }
                                                   else if(spravnaOdpoved%6==4){
                                                       b.setText("");
                                                       c.setText("");
                                                   }
                                                   padesatNaPadesat.setImageResource(R.drawable.nopadesatnapadesat);
                                                   padesatNaPadesatB=true;
                                                   return true;
                                               }

                                           }
                                           return false;
                                       }

                                   }
        );


        startTimer();
        dalsiOtazka();

    }

    public void dalsiOtazka(){
        casVMS=60000;
        String[] otazky={};
        Random r = new Random();
        Log.d("score:",""+score);
        if(score<=10000){
            otazky=otazky_easy;
        }
        else if (score>10000&&score<=250000){
            otazky=otazky_medium;
        }
        else{
            otazky=otazky_hard;
        }
        int tmp = r.nextInt(otazky.length);


        int zbytek = tmp%6;
        randomCislo = tmp-zbytek;

        for(int i=randomCislo;i<randomCislo+6;i++){
           if(otazky[i]=="-") {
               spravnaOdpoved=i-1;
           }
        }

        otazka.setText(otazky[randomCislo]);

        if(otazky[randomCislo+2]=="-"){
            a.setText("(a) "+otazky[randomCislo+1]);
            b.setText("(b) "+otazky[randomCislo+3]);
            c.setText("(c) "+otazky[randomCislo+4]);
            d.setText("(d) "+otazky[randomCislo+5]);
        }
        else if(otazky[randomCislo+3]=="-"){
            a.setText("(a) "+otazky[randomCislo+1]);
            b.setText("(b) "+otazky[randomCislo+2]);
            c.setText("(c) "+otazky[randomCislo+4]);
            d.setText("(d) "+otazky[randomCislo+5]);
        }
        else if(otazky[randomCislo+4]=="-"){
            a.setText("(a) "+otazky[randomCislo+1]);
            b.setText("(b) "+otazky[randomCislo+2]);
            c.setText("(c) "+otazky[randomCislo+3]);
            d.setText("(d) "+otazky[randomCislo+5]);
        }
        else{
            a.setText("(a) "+otazky[randomCislo+1]);
            b.setText("(b) "+otazky[randomCislo+2]);
            c.setText("(c) "+otazky[randomCislo+3]);
            d.setText("(d) "+otazky[randomCislo+4]);
        }

    }
    @Override
    public void onBackPressed() {
        CDT.cancel();
        final SharedPreferences sharedPreferences = this.getSharedPreferences("zvuk", Context.MODE_PRIVATE);
        if (sharedPreferences.getString("zvuk", "").equals("ok"))
        player.stop();
        super.onBackPressed();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        final SharedPreferences sharedPreferences = this.getSharedPreferences("zvuk", Context.MODE_PRIVATE);

        if (requestCode == 333) {
            // Make sure the request was successful
            life=1;
            if (sharedPreferences.getString("zvuk", "").equals("ok"))
            {

                if(!player.isPlaying()){

                    player.start();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            player.stop();
                            player = MediaPlayer.create(getBaseContext(), R.raw.otazka);
                            player.start();
                            player.setLooping(true);
                            player.setVolume(log1, log1);
                        }
                    }, 2500);
                }

            }

            casVMS=60000;
            startGame();

        }
        if(requestCode ==331){


            if (sharedPreferences.getString("zvuk", "").equals("ok"))
            {

                if(!player.isPlaying()){

                    player.start();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            player.stop();
                            player = MediaPlayer.create(getBaseContext(), R.raw.otazka);
                            player.start();
                            player.setLooping(true);
                            player.setVolume(log1, log1);
                        }
                    }, 2500);
                }

            }
        }
    }


}
