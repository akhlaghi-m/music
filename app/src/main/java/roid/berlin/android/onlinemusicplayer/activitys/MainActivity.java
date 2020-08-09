package roid.berlin.android.onlinemusicplayer.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import roid.berlin.android.onlinemusicplayer.Codes;
import roid.berlin.android.onlinemusicplayer.OnResponse;
import roid.berlin.android.onlinemusicplayer.R;
import roid.berlin.android.onlinemusicplayer.adapter.MyAdapter;
import roid.berlin.android.onlinemusicplayer.model.Music;

import android.content.Context;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rec1,rec2;
    RequestQueue requestQueue;
    MyAdapter myAdapter1,myAdapter2;
    LinearLayoutManager mlayout1,mlayout2;
    String url1="Pop";
    String url2="HipHop";
    Codes cod = new Codes();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rec1 = findViewById(R.id.rec1);
        rec2 = findViewById(R.id.rec2);
        requestQueue = Volley.newRequestQueue(this);

        mlayout1 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mlayout2 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);

        rec1.setLayoutManager(mlayout1);
        rec2.setLayoutManager(mlayout2);

        rec1.setAdapter(myAdapter1);
        rec2.setAdapter(myAdapter2);

        cod.getData(this, url1, requestQueue, new OnResponse() {
            @Override
            public void onSuccess(List<Music> musics)
            {
                myAdapter1 = new MyAdapter(musics, MainActivity.this);
                rec1.setItemAnimator(new DefaultItemAnimator());
                rec1.setAdapter(myAdapter1);
            }
        });

        cod.getData(this, url2, requestQueue, new OnResponse() {
            @Override
            public void onSuccess(List<Music> musics)
            {
                myAdapter2 = new MyAdapter(musics, MainActivity.this);
                rec2.setItemAnimator(new DefaultItemAnimator());
                rec2.setAdapter(myAdapter2);
            }
        });

    }
}