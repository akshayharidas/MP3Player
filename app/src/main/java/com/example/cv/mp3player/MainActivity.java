package com.example.cv.mp3player;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.speech.tts.Voice;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
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

    MediaMetadataRetriever mmr = new MediaMetadataRetriever();

    //MediaMetadataRetriever metaRetriver;
    //byte[] art;
    private TextView album;
    private SeekBar seekbar;


    //MediaMetadataRetriever mmr = new MediaMetadataRetriever();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
        initListener();


        //metadata
//        metaRetriver = new MediaMetadataRetriever(); metaRetriver.setDataSource("/raw/animals.mp3");
//        try { art = metaRetriver.getEmbeddedPicture();
//            Bitmap songImage = BitmapFactory.decodeByteArray(art, 0, art.length);
//            img3.setImageBitmap(songImage);
//            album.setText(metaRetriver .extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));
//
//        } catch (Exception e) { img3.setBackgroundColor(getColor(R.color.picColor));
//            album.setText("Unknown Album");
//
//
//        }
//
//
//
//
  }
    private void initHandler(){handler.postDelayed(updateUI,1000);}
    private Runnable updateUI = new Runnable() {
        @Override
        public void run() {
            //double seekpercentage = 100 * player.getCurrentPosition() / player.getDuration();
            //player.setOnSeekCompleteListener((long)seekpercentage);
            long seconds = (System.currentTimeMillis()-songStartTimer)/1000;
            time.setText(String.format("%02d:%02d",seconds/60,seconds%60));

           ib2.setImageDrawable(getResources().getDrawable(R.drawable.fast_forward_button));

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
        ib2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (player.isPlaying()) {
                            player.seekTo(player.getCurrentPosition() + 5000);
                        }
                    }
                }
        );
        ib3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (player.isPlaying()) {
                            player.seekTo(player.getCurrentPosition() - 5000);
                        }
                    }
                }
        );
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                player.seekTo(progress * player.getDuration()/100);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
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
        time2 =(TextView)findViewById(R.id.textView3);
        ib2 = (ImageButton)findViewById(R.id.imageButton2);
        ib3 = (ImageButton)findViewById(R.id.imageButton3);
        long totalTime = player.getDuration();
        totalTime /=1000;
        time2.setText(String.format("%02d:%02d",totalTime/60,totalTime%60));
        seekbar= (SeekBar) findViewById(R.id.seekBar3);


        Uri mediaPath = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.animals);
        mmr.setDataSource(this, mediaPath);
        String albumName = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        album = (TextView)findViewById(R.id.textView2);
        album.setText(albumName);

        //img3 = (ImageView) findViewById(R.id.imageView5);
        //album = (TextView) findViewById(R.id.textView2);



        //mmr.setDataSource(raw/animals);

       // String albumName = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);



    }
}