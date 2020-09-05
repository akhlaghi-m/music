package roid.berlin.android.onlinemusicplayer.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import roid.berlin.android.onlinemusicplayer.Codes;
import roid.berlin.android.onlinemusicplayer.fragments.FragmentMusicHipHop;
import roid.berlin.android.onlinemusicplayer.fragments.FragmentMusicPlayList;
import roid.berlin.android.onlinemusicplayer.fragments.FragmentMusicPop;
import roid.berlin.android.onlinemusicplayer.MusicUtils;
import roid.berlin.android.onlinemusicplayer.R;
import roid.berlin.android.onlinemusicplayer.adapter.MyAdapter;
import roid.berlin.android.onlinemusicplayer.fragments.FragmentMusicNew;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rec1, rec2;
    RequestQueue requestQueue;
    MyAdapter myAdapter1, myAdapter2;
    LinearLayoutManager mLayout1, mLayout2;
    String url1 = "Pop";
    String url2 = "HipHop";
    Codes cod = new Codes();

    //****************play Music Tabs Widget *******************

    public View parent_view;

    private ViewPager view_pager;
    private TabLayout tab_layout;

    private ImageButton bt_play;
    private ProgressBar song_progressbar;
    //private AdapterListMusicSong mAdapter;

    // Media Player
    private MediaPlayer mp;
    // Handler to update UI timer, progress bar etc,.
    private Handler mHandler = new Handler();

    //private SongsManager songManager;
    private MusicUtils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

//        initViews();
//
//
//        cod.getData(this, url1, requestQueue, new OnResponse() {
//            @Override
//            public void onSuccess(List<Music> musics) {
//                myAdapter1 = new MyAdapter(musics, MainActivity.this);
//                rec1.setItemAnimator(new DefaultItemAnimator());
//                rec1.setAdapter(myAdapter1);
//            }
//        });
//
//        cod.getData(this, url2, requestQueue, new OnResponse() {
//            @Override
//            public void onSuccess(List<Music> musics) {
//                myAdapter2 = new MyAdapter(musics, MainActivity.this);
//                rec2.setItemAnimator(new DefaultItemAnimator());
//                rec2.setAdapter(myAdapter2);
//            }
//        });

        //*********************************************************************
        initToolbar();
        initPlayMusicTabs();

    }

    public void initViews() {


//        rec1 = findViewById(R.id.rec1);
//        rec2 = findViewById(R.id.rec2);
//        requestQueue = Volley.newRequestQueue(this);
//
//        mLayout1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        mLayout2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//
//        rec1.setLayoutManager(mLayout1);
//        rec2.setLayoutManager(mLayout2);
//
//        rec1.setAdapter(myAdapter1);
//        rec2.setAdapter(myAdapter2);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Music Player");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setSystemBarColor(this);
    }

    public static void setSystemBarColor(Activity act) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = act.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //window.setStatusBarColor(act.getResources().getColor(R.color.primaryDarkColorRed));
        }
    }

    public void initPlayMusicTabs() {

        view_pager = findViewById(R.id.view_pager);
        setupViewPager(view_pager);

        tab_layout = findViewById(R.id.tab_layout);
        tab_layout.setupWithViewPager(view_pager);

        bt_play = findViewById(R.id.bt_play);
        song_progressbar = findViewById(R.id.song_progressbar);

        //set Progressbar Value
        song_progressbar.setProgress(0);
        song_progressbar.setMax(MusicUtils.MAX_PROGRESS);

        // Media Player
        mp = new MediaPlayer();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                // Changing button image to play button
                bt_play.setImageResource(R.drawable.ic_play_arrow);

            }
        });

        try {
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            AssetFileDescriptor afd = getAssets().openFd("short_music.mp3");
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mp.prepare();
        } catch (Exception e) {
            Snackbar.make(parent_view, "Cannot load audio file", Snackbar.LENGTH_SHORT).show();
        }

        utils = new MusicUtils();

        buttonPlayerAction();

    }


    /**
     * Play button click event plays a song and changes button to pause image
     * pauses a song and changes button to play image
     */
    private void buttonPlayerAction() {
        bt_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // check for already playing
                if (mp.isPlaying()) {
                    mp.pause();
                    // Changing button image to play button
                    bt_play.setImageResource(R.drawable.ic_play_arrow);
                } else {
                    // Resume song
                    mp.start();
                    // Changing button image to pause button
                    bt_play.setImageResource(R.drawable.ic_pause);
                    mHandler.post(mUpdateTimeTask);
                }

            }
        });
    }


    /**
     * Background Runnable thread
     */
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            updateTimerAndSeekbar();
            // Running this thread after 10 milliseconds
            if (mp.isPlaying()) {
                mHandler.postDelayed(this, 100);
            }
        }
    };

    private void updateTimerAndSeekbar() {
        long totalDuration = mp.getDuration();
        long currentDuration = mp.getCurrentPosition();

        // Updating progress bar
        int progress = (int) (utils.getProgressSeekBar(currentDuration, totalDuration));
        song_progressbar.setProgress(progress);
    }

    // stop player when destroy
    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mUpdateTimeTask);
        mp.release();
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(FragmentMusicNew.newInstance(),"NewSongs");
        adapter.addFragment(FragmentMusicHipHop.newInstance(), "HipHop");
        adapter.addFragment(FragmentMusicPop.newInstance(),"Pop");
        adapter.addFragment(FragmentMusicPlayList.newInstance(), "PlayList");
        viewPager.setAdapter(adapter);
    }

    public void controlClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.bt_expand: {
                Snackbar.make(parent_view, "Expand", Snackbar.LENGTH_SHORT).show();
                break;
            }
        }
    }


    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
