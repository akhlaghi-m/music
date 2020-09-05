package roid.berlin.android.onlinemusicplayer.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;
import roid.berlin.android.onlinemusicplayer.Codes;
import roid.berlin.android.onlinemusicplayer.MusicUtils;
import roid.berlin.android.onlinemusicplayer.R;
import roid.berlin.android.onlinemusicplayer.view.CircularMusicProgressBar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;

public class MusicPageActivity extends AppCompatActivity {

    ImageButton next;
    FloatingActionButton play;
    TextView tv_song_current_duration,tv_song_total_duration,title;
    CircularMusicProgressBar cover;
    AppCompatSeekBar seek_song_progressbar;
    Context context;
    int i,j,z;
    Timer timer;
    Bundle extra;

    Uri myUri;

   TextView musicPage_text;
   ImageView img_background,img_like;

   MusicUtils utils;
   private Handler mHandler = new Handler();
    private View parent_view;
    ScrollView scrollView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_page);
       initComponent();

    }

    private void initComponent(){
        context = this;
        i=0;
        j=0;
        z=0;
        utils = new MusicUtils();

        scrollView=findViewById(R.id.musicPage_scrollView);
        seek_song_progressbar=findViewById(R.id.seek_song_progressbar);
        next =findViewById(R.id.m_next);
        title=findViewById(R.id.musicPage_title);
        tv_song_current_duration =findViewById(R.id.tv_song_current_duration);
        tv_song_total_duration=findViewById(R.id.tv_song_total_duration);
        cover=findViewById(R.id.cover);
        musicPage_text=findViewById(R.id.musicPage_text);
        play=findViewById(R.id.m_play);
        img_background=findViewById(R.id.img_background);
        img_like=findViewById(R.id.img_like);



        // set Progress bar values
        seek_song_progressbar.setProgress(0);
        seek_song_progressbar.setMax(MusicUtils.MAX_PROGRESS);


        extra= getIntent().getExtras();
        musicPage_text.setText(extra.getString("text"));

        final String path=extra.getString("img");
        Picasso.with(context).load(path).into(cover);
        Picasso.with(context).load(path).into(img_background);
        title.setText(extra.getString("title")+" _ "+extra.getString("artist") );

        timer =new Timer();
        myUri = Uri.parse(extra.getString("link"));



        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (i==0){
                    PlayMusic();
                    i=1;
                    play.setImageResource(R.drawable.ic_pause);

                }else if (i==1){

                    i=2;
                    play.setImageResource(R.drawable.ic_play_arrow);
                    Codes.mediaPlayer.pause();


                }
                else {
                    i=1;
                    play.setImageResource(R.drawable.ic_pause);
                    int b = Codes.mediaPlayer.getCurrentPosition();
                    Codes.mediaPlayer.seekTo(b);
                    Codes.mediaPlayer.start();
                }

            }


        });



        seek_song_progressbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                mHandler.removeCallbacks(mUpdateTimeTask);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mHandler.removeCallbacks(mUpdateTimeTask);
                int totalDuration = Codes.mediaPlayer.getDuration();
                int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);

                // forward or backward to certain seconds
                Codes.mediaPlayer.seekTo(currentPosition);

                // update timer progress again
                mHandler.post(mUpdateTimeTask);
            }
        });


    }

    /**
     * Background Runnable thread
     */
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            getTime();

            // Running this thread after 10 milliseconds
            if (Codes.mediaPlayer.isPlaying()) {
                mHandler.postDelayed(this, 100);
            }
        }
    };

    public void PlayMusic(){

       Thread thread = new Thread(new Runnable() {
           @Override
           public void run() {

               try {

                   Codes.mediaPlayer = new MediaPlayer();
                   Codes.mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                   Codes.mediaPlayer.setDataSource(context,myUri);
                   Codes.mediaPlayer.prepare();
                   Codes.mediaPlayer.start();

               }catch (Exception e){

               }
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       try {
                           getTime();
                       }catch (Exception e){

                       }
                   }
               });
           }
       });
thread.start();

    }



    public void getTime(){

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        long current = Codes.mediaPlayer.getCurrentPosition();
                        long totalDuration = Codes.mediaPlayer.getDuration();
                        int i = (Codes.mediaPlayer.getCurrentPosition()*100)/Codes.mediaPlayer.getDuration();
                        tv_song_total_duration.setText(""+utils.milliSecondsToTimer(totalDuration));
                        tv_song_current_duration.setText(""+utils.milliSecondsToTimer(current));

                        int progress = (int) (utils.getProgressSeekBar(current, totalDuration));
                        seek_song_progressbar.setProgress(progress);


                        cover.setValue(i);

                    }
                });

            }
        },0,1000);

    }

    public void controlClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.img_like:{

                if (z==0){
                    z=1;
                   img_like.setImageResource(R.drawable.ic_heart);
                }else if (z==1){

                    z=2;

                    img_like.setImageResource(R.drawable.ic_heart_white);

                } else {
                    z=1;
                     img_like.setImageResource(R.drawable.ic_heart);
                }

                  break;
            }
            case R.id.bt_repeat: {
                toggleButtonColor((ImageButton) v);
                Toast.makeText(context,"Repeat",Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.bt_shuffle: {
                toggleButtonColor((ImageButton) v);
                Toast.makeText(context,"Shuffle",Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.bt_prev: {
                toggleButtonColor((ImageButton) v);
                Toast.makeText(context,"Preview",Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.m_next: {
                toggleButtonColor((ImageButton) v);
                Toast.makeText(context,"Next",Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.img_text_arrow:{

                toggleArrowButtonColor((ImageButton) v);
                if (j==0){
                   j=1;
                   scrollView.setVisibility(View.VISIBLE);
                }else if (j==1){

                    j=2;
                  scrollView.setVisibility(View.GONE);

                } else {
                    j=1;
                   scrollView.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private boolean toggleButtonColor(ImageButton bt) {
        String selected = (String) bt.getTag(bt.getId());
        if (selected != null) { // selected
            bt.setColorFilter(getResources().getColor(R.color.grey_90), PorterDuff.Mode.SRC_ATOP);
            bt.setTag(bt.getId(), null);
            return false;
        } else {
            bt.setTag(bt.getId(), "selected");
            bt.setColorFilter(getResources().getColor(R.color.red_500), PorterDuff.Mode.SRC_ATOP);
            return true;
        }
    }

    private boolean toggleArrowButtonColor(ImageButton bt) {
        String selected = (String) bt.getTag(bt.getId());
        if (selected != null) { // selected
            bt.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
            bt.setTag(bt.getId(), null);
            return false;
        } else {
            bt.setTag(bt.getId(), "selected");
            bt.setColorFilter(getResources().getColor(R.color.red_500), PorterDuff.Mode.SRC_ATOP);
            return true;
        }
    }


    private void rotateImageAlbum() {
        if (!Codes.mediaPlayer.isPlaying()) return;
        cover.animate().setDuration(100).rotation(cover.getRotation() + 2f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                rotateImageAlbum();
                super.onAnimationEnd(animation);
            }
        });
    }



//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//
//        mHandler.removeCallbacks(mUpdateTimeTask);
//        Codes.mediaPlayer.release();
//    }
}