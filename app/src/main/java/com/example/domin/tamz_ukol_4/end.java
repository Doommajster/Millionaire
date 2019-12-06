package com.example.domin.tamz_ukol_4;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class end extends Activity {

    TextView win;
    Button menu;
    int score=0;
    MediaPlayer player;
    private Twitter twitter;
    private volatile boolean isConfigured = false;
    private Object lock = new Object();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.endwindow);
        final SharedPreferences sharedPreferences=getSharedPreferences("zvuk", Context.MODE_PRIVATE);

        new ConfigureTwitterTask().execute();

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
    public void configureTwitter() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("oEJEAxEPjNgWb0O35QNVaA")
                .setOAuthConsumerSecret("2TyiPmQMpnYHPE3S8ITkIQWld5fjk6jQ5eGfTsG8kg")
                .setOAuthAccessToken("927024486-4X07W3nTicx2SG0dTccqsNzraAyT1G8Ffc4VvNqN")
                .setOAuthAccessTokenSecret("neehbYt9lBY6o29UdcMLsZ1Zs9vVLPPncOpivLoyXtA");

        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
    }


    public void sendTweet(String message) {
        try {
            Status status = twitter.updateStatus(message);
        } catch (TwitterException e) {
            // TODO Auto-generated catch block
            // oops
            Toast.makeText(this, "Error s nahráním na twitter", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public void sendMessage(View view) {

        String message = "";
        if(score==-1)
            message="Hrál jsem milionáře a všechno jsem prohrál :(";
        else if(score != 0 && score!=1000000)
            message="Hrál jsem milionáře a vyhrál jsem "+score+" Kč";
        else if(score==1000000)
            message="Hrál jsem milionáře a stávám se novým milionářem!";
        else
            message="Ve výpočtu skóre nastala chyba :(";

        //TODO uncomment
        new SendTweetTask().execute(message);

    }


    private class ConfigureTwitterTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            configureTwitter();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            isConfigured = true;
            synchronized (lock) {
                lock.notifyAll();
            }
        }
    }

    private class SendTweetTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... message) {

            synchronized (lock) {
                while (!isConfigured) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            sendTweet(message[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getBaseContext(), "\"Send tweet done\"",
                    Toast.LENGTH_LONG).show();
        }
    }
}
