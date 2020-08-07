package roid.berlin.android.onlinemusicplayer.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import roid.berlin.android.onlinemusicplayer.Codes;
import roid.berlin.android.onlinemusicplayer.R;
import roid.berlin.android.onlinemusicplayer.adapter.MyAdapter;
import roid.berlin.android.onlinemusicplayer.model.Music;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rec1,rec2;
    RequestQueue requestQueue;
    Context context;
    MyAdapter myAdapter1,myAdapter2;
    LinearLayoutManager mlayout1,mlayout2;
    List<Music> list1 = new ArrayList<>();
    List<Music> list2 = new ArrayList<>();
    String url1="https://berlinroid.ir/music.php?key=Pop";
    String url2="https://berlinroid.ir/music.php?key=HipHop";

    Codes cod1,cod2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        rec1 = findViewById(R.id.rec1);
        rec2 = findViewById(R.id.rec2);
        requestQueue = Volley.newRequestQueue(context);
        mlayout1 = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
        mlayout2 = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
        myAdapter1 = new MyAdapter(list1,context);
        myAdapter2 = new MyAdapter(list2,context);
        cod1 = new Codes();
        cod2 = new Codes();
        rec1.setAdapter(myAdapter1);
        rec2.setAdapter(myAdapter2);
        rec1.setLayoutManager(mlayout1);
        rec2.setLayoutManager(mlayout2);

        cod1.getData(context,list1,rec1,url1,requestQueue);
        cod2.getData(context,list2,rec2,url2,requestQueue);

    }
}