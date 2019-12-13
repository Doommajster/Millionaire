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
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

public class gameCore extends Activity {

    public String[] otazky_easy=otazky.otazky.getOtazky_easy();
    public Integer[] odpovedi_easy=otazky.otazky.getOdpovedi_easy();
    public String[] otazky_medium=otazky.otazky.getOtazky_medium();
    public Integer[] odpovedi_medium=otazky.otazky.getOdpovedi_medium();
    public String[] otazky_hard=otazky.otazky.getOtazky_hard();
    public Integer[] odpovedi_hard=otazky.otazky.getOdpovedi_hard();
    public Integer[] money={500,1000,2000,5000,10000,25000,50000,100000,250000,500000,750000,1000000};

    private Button a,b,c,d;
    private CountDownTimer CDT;
    private TextView otazka,time;
    public int score=0,casVMS=60000;
    public int life=1,progress2=20;
    public boolean publikumB=false,telefonB=false,padesatNaPadesatB=false,timmerRunning=false;
    public int randomCislo=0,spravnaOdpoved=0,increment=0;
    public ImageView padesatNaPadesat;
    public ImageView telefon;
    public ImageView publikum;
    public MediaPlayer player;
    public ProgressBar circle;

    int maxVolume = 50,currVolume=50,cekej=1000;
    float log1=(float)(Math.log(maxVolume-currVolume)/Math.log(maxVolume));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamecore);
        player = MediaPlayer.create(this, R.raw.starthry);
        circle = findViewById(R.id.progressBar);
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
        circle.setProgress(100);
        nastaveniTlacitek();

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
        int progress =100-2*(60-seconds);
         Log.d("progress",""+progress);
        if(progress<=20){
            circle.setProgress(progress2--);

        }
        else
            circle.setProgress(progress);

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
                if(score >=5000 && score <100000) i.putExtra("score", 5000);
                else if (score >=100000 && score <=1000000) i.putExtra("score", 100000);
                else i.putExtra("score",-1);
                startActivity(i);
            }
        }
    }

    public void startGame(){

        a.setBackground(getDrawable(R.drawable.standartbutton));
        b.setBackground(getDrawable(R.drawable.standartbutton));
        c.setBackground(getDrawable(R.drawable.standartbutton));
        d.setBackground(getDrawable(R.drawable.standartbutton));

           publikum.setOnTouchListener(new View.OnTouchListener() {
                                            @Override
                                            public boolean onTouch(View v, MotionEvent event) {
                                                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                                    if(publikumB) return false;
                                                    else{

                                                        Intent intent2=new Intent(v.getContext(),Audience.class);
                                                        intent2.putExtra("spravna",spravnaOdpoved);
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
                                                    intent3.putExtra("spravna",spravnaOdpoved);
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
                                                    if(spravnaOdpoved==1){
                                                        b.setText("");
                                                        c.setText("");
                                                    }
                                                   else if(spravnaOdpoved==2){
                                                       a.setText("");
                                                       c.setText("");
                                                   }
                                                   else if(spravnaOdpoved==3){
                                                       b.setText("");
                                                       d.setText("");
                                                   }
                                                   else if(spravnaOdpoved==4){
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
        nastaveniTlacitek();
        casVMS=60000;
        String[] otazky={};
        Random r = new Random();
        Log.d("score:",""+score);
        if(score<=5000){
            otazky=otazky_easy;
        }
        else if (score>5000&&score<=100000){
            otazky=otazky_medium;
        }
        else{
            otazky=otazky_hard;
        }
        int tmp = r.nextInt(otazky.length);

        int zbytek = tmp%5;
        randomCislo = tmp-zbytek;

        if(score<=5000){
            spravnaOdpoved=odpovedi_easy[randomCislo/5];
        }
        else if (score>5000&&score<=100000){
            spravnaOdpoved=odpovedi_medium[randomCislo/5];
        }
        else{
            spravnaOdpoved=odpovedi_hard[randomCislo/5];
        }


        otazka.setText(otazky[randomCislo]);


        a.setText("(a) "+otazky[randomCislo+1]);
        b.setText("(b) "+otazky[randomCislo+2]);
        c.setText("(c) "+otazky[randomCislo+3]);
        d.setText("(d) "+otazky[randomCislo+4]);


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
    public void nastaveniTlacitek(){
        final SharedPreferences sharedPreferences=getSharedPreferences("zvuk", Context.MODE_PRIVATE);
        a.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View v){
                b.setOnClickListener(null);
                c.setOnClickListener(null);
                d.setOnClickListener(null);
                Handler handler = new Handler();
                if(spravnaOdpoved==1){
                    if(score==1000000){
                        a.setBackground(getDrawable(R.drawable.correctanswerbutton));

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
                        a.setBackground(getDrawable(R.drawable.correctanswerbutton));
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
                    a.setBackground(getDrawable(R.drawable.badanswerbutton));
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
                                if(score >=5000 && score <100000) i.putExtra("score", 5000);
                                else if (score >=100000 && score <=1000000) i.putExtra("score", 100000);
                                else i.putExtra("score",-1);
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
                a.setOnClickListener(null);
                c.setOnClickListener(null);
                d.setOnClickListener(null);
                Handler handler = new Handler();
                if(spravnaOdpoved==2){
                    if(score==1000000){
                        b.setBackground(getDrawable(R.drawable.correctanswerbutton));

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
                        b.setBackground(getDrawable(R.drawable.correctanswerbutton));

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
                    b.setBackground(getDrawable(R.drawable.badanswerbutton));
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
                                if(score >=5000 && score <100000) i.putExtra("score", 5000);
                                else if (score >=100000 && score <=1000000) i.putExtra("score", 100000);
                                else i.putExtra("score",-1);
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
                b.setOnClickListener(null);
                a.setOnClickListener(null);
                d.setOnClickListener(null);
                Handler handler = new Handler();
                if(spravnaOdpoved==3){
                    if(score==1000000){
                        c.setBackground(getDrawable(R.drawable.correctanswerbutton));

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
                        c.setBackground(getDrawable(R.drawable.correctanswerbutton));

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
                    c.setBackground(getDrawable(R.drawable.badanswerbutton));
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
                                if(score >=5000 && score <100000) i.putExtra("score", 5000);
                                else if (score >=100000 && score <=1000000) i.putExtra("score", 100000);
                                else i.putExtra("score",-1);
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
                b.setOnClickListener(null);
                c.setOnClickListener(null);
                a.setOnClickListener(null);
                Handler handler = new Handler();
                if(spravnaOdpoved==4){
                    if(score==1000000){
                        d.setBackground(getDrawable(R.drawable.correctanswerbutton));

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
                        d.setBackground(getDrawable(R.drawable.correctanswerbutton));

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
                    d.setBackground(getDrawable(R.drawable.badanswerbutton));
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
                                if(score >=5000 && score <100000) i.putExtra("score", 5000);
                                else if (score >=100000 && score <=1000000) i.putExtra("score", 100000);
                                else i.putExtra("score",-1);
                                startActivity(i);
                            }
                        }
                    },cekej);
                }


            }
        });
    }


}

