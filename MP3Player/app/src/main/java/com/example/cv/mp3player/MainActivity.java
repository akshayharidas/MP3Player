package com.example.cv.mp3player;

import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Handler;
import android.speech.tts.Voice;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.cv.mp3player.R.raw.animals;

public class MainActivity extends AppCompatActivity {
    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private MediaPlayer player;
    private ImageButton ib1;
    private Button b;
    private ImageButton ib2;
    private ImageButton ib3;
    private long songStartTimer;
    private Handler handler = new Handler();
    private TextView time;
    private TextView time2;

    //MediaMetadataRetriever mmr = new MediaMetadataRetriever();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
        initListener();

    }
    private void initHandler(){handler.postDelayed(updateUI,1000);}
    private Runnable updateUI = new Runnable() {
        @Override
        public void run() {
            long seconds = (System.currentTimeMillis()-songStartTimer)/1000;
            time.setText(String.format("%02d:%02d",seconds/60,seconds%60));
            
            handler.postDelayed(this, 1000);
        }
    };


    private void initListener() {
        ib1.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (player.isPlaying()) {
                            player.pause();
                            ib1.setImageDrawable(getResources().getDrawable(R.drawable.play_button));
                            handler.removeCallbacks(updateUI);

                        } else {
                            player.start();
                            initHandler();
                            ib1.setImageDrawable(getResources().getDrawable(R.drawable.pause_button));
                            songStartTimer = System.currentTimeMillis();
                        }
                    }
                }
        );
    }

    private void initview() {
        img1 = (ImageView) findViewById(R.id.imageView);
        img1.setImageDrawable(getResources().getDrawable(R.drawable.now_playing));
        img2 = (ImageView) findViewById(R.id.imageView1);
        img2.setImageDrawable(getResources().getDrawable(R.drawable.song_tittle));
        img3 = (ImageView) findViewById(R.id.imageView5);
        img3.setImageDrawable(getResources().getDrawable(R.drawable.album));
        player = MediaPlayer.create(this, animals);
        ib1 = (ImageButton) findViewById(R.id.imageButton);
        time = (TextView)findViewById(R.id.textView);
        time.setText(String.format("%02d:%02d", 0, 0));

        //mmr.setDataSource(raw/animals);

       // String albumName = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);



    }
}