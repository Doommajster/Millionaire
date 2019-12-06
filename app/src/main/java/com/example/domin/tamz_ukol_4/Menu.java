package com.example.domin.tamz_ukol_4;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.XmlResourceParser;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;


public class Menu extends Activity {


    private Button startGame,about,highScore;
    private ImageView logo;
    MediaPlayer player;
    SharedPreferences sharedPreferences;
    Switch zvuk;
    int maxVolume = 50,currVolume=50;
    float log1=(float)(Math.log(maxVolume-currVolume)/Math.log(maxVolume));

    public String adresa="https://homel.vsb.cz/~hol0391/questions.xml";
    public String[] questions=new String[390];
    public Integer[] answers = new Integer[78];
    public String[] questions_easy=new String[130];
    public Integer[] answers_easy = new Integer[26];
    public String[] questions_medium=new String[130];
    public Integer[] answers_medium = new Integer[26];
    public String[] questions_hard=new String[130];
    public Integer[] answers_hard = new Integer[26];

    private XmlPullParserFactory xmlFactoryObject;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.loadPage();

        startGame = findViewById(R.id.startgamebutton);
        zvuk = findViewById(R.id.zvuk);

        startGame.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), gameCore.class);
                startActivity(intent);
            }
        });

        about = findViewById(R.id.aboutbutton);

        about.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), about.class);
                startActivity(intent);
            }
        });

        highScore=findViewById(R.id.high_score);
        highScore.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), SqlScore.class);
                startActivity(intent);
            }
        });

        RotateNow();

        sharedPreferences = getSharedPreferences("zvuk", Context.MODE_PRIVATE);
        final SharedPreferences.Editor edit = sharedPreferences.edit();

        zvuk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    edit.putString("zvuk", "ok");
                    play();
                    edit.commit();
                } else if (!b) {
                    edit.putString("zvuk", "no");
                    stop();
                    edit.commit();
                }
            }

        });
        if (sharedPreferences.getString("zvuk", "").equals("ok")) {
            play();
            zvuk.setChecked(true);
        } else zvuk.setChecked(false);


    }

    public void RotateNow() {
        logo = (ImageView) findViewById(R.id.logo_main);
        RotateAnimation rotate = new RotateAnimation(0, 359, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(15000);
        rotate.setInterpolator(new LinearInterpolator());
        logo.startAnimation(rotate);
    }
    @Override
    public void onBackPressed(){

    }

    public void play() {
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.main);
            player.setVolume(log1,log1);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();
                }
            });
        }

        player.start();
    }

    public void pause() {
        if (player != null) {
            player.pause();
        }
    }

    public void stop() {
        stopPlayer();
    }

    private void stopPlayer() {
        if (player != null) {
            player.release();
            player = null;

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopPlayer();
    }

    public void loadPage(){
        AsyncDownloader down = new AsyncDownloader();
        down.execute();
    }

    private class AsyncDownloader extends AsyncTask<Object, String, String[]> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Menu.this);
            pDialog.setTitle("Získávám otázky z XML");
            pDialog.setMessage("Loading...");
            pDialog.show();
        }

        @Override
        protected String[] doInBackground(Object... objects) {
            try {
                URL url = new URL(adresa);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(10000 /* milliseconds */);
                connection.setConnectTimeout(15000 /* milliseconds */);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();
                InputStream stream = connection.getInputStream();

                xmlFactoryObject = XmlPullParserFactory.newInstance();
                XmlPullParser myParser = xmlFactoryObject.newPullParser();

                myParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                myParser.setInput(stream, null);
                questions = parseXML(myParser);

                stream.close();
                connection.disconnect();

                return questions;

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("Error","Chyba při načítání dat z webu");
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] result) {
            //call back data to main thread
            pDialog.dismiss();

            questions_easy=Arrays.copyOfRange(questions,0,130);
            answers_easy=Arrays.copyOfRange(answers,0,26);

            otazky.otazky.setOtazky_easy(questions_easy);
            otazky.otazky.setOdpovedi_easy(answers_easy);

            questions_medium=Arrays.copyOfRange(questions,130,260);
            answers_medium=Arrays.copyOfRange(answers,26,52);

            otazky.otazky.setOtazky_medium(questions_medium);
            otazky.otazky.setOdpovedi_medium(answers_medium);

            questions_hard=Arrays.copyOfRange(questions,260,390);
            answers_hard=Arrays.copyOfRange(answers,52,78);

            otazky.otazky.setOtazky_hard(questions_hard);
            otazky.otazky.setOdpovedi_hard(answers_hard);


            if(questions[0]==null){
                pDialog = new ProgressDialog(Menu.this);
                pDialog.setTitle("Chyba při načítání dat z webu, zkuste aplikaci zapnout znovu");
                pDialog.setMessage("Error");
                pDialog.show();
            }
        }


    }

    public String[] parseXML(XmlPullParser xmlData) throws XmlPullParserException, IOException {

        Arrays.fill(questions, null);
        Arrays.fill(answers, null);
        String text = "";
        int i=0,j=0;
        int eventType = xmlData.getEventType();

        while (eventType != XmlResourceParser.END_DOCUMENT) {
            String tagName = xmlData.getName();

            switch (eventType) {
                case XmlResourceParser.START_TAG:
                    // Start of a record, so pull values encoded as attributes.
                    if (tagName.equals("otazky")) {

                    }
                    break;

                // Grab data text (very simple processing)
                // NOTE: This could be full XML data to process.
                case XmlResourceParser.TEXT:
                    text = xmlData.getText();
                    break;

                case XmlPullParser.END_TAG:
                    if (tagName.equalsIgnoreCase("otazky")) {

                    } else if (tagName.equalsIgnoreCase("otazka")) {
                        questions[i]=text;
                        i++;
                    } else if (tagName.equalsIgnoreCase("a")) {
                        questions[i]=text;
                        i++;
                    } else if (tagName.equalsIgnoreCase("b")) {
                        questions[i]=text;
                        i++;
                    } else if (tagName.equalsIgnoreCase("c")) {
                        questions[i]=text;
                        i++;
                    } else if (tagName.equalsIgnoreCase("d")) {
                        questions[i]=text;
                        i++;
                    } else if (tagName.equalsIgnoreCase("odpoved")) {
                        answers[j]=Integer.parseInt(text);
                        j++;
                    }

                    break;

            }
            eventType = xmlData.next();
        }

        return questions;
    }




}
