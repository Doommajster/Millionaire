package com.example.domin.tamz_ukol_4;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;


public class Menu extends Activity {

    private Button startGame,about,highScore;
    private ImageView logo;
    MediaPlayer player;
    SharedPreferences sharedPreferences;
    Switch zvuk;
    int maxVolume = 50,currVolume=50;
    float log1=(float)(Math.log(maxVolume-currVolume)/Math.log(maxVolume));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

}
