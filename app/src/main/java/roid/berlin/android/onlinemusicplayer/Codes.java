package roid.berlin.android.onlinemusicplayer;

import android.content.Context;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import roid.berlin.android.onlinemusicplayer.adapter.MyAdapter;
import roid.berlin.android.onlinemusicplayer.model.Music;

public class Codes extends AppCompatActivity{

    RequestQueue req;
    Context context;
    MyAdapter myAdapter;
    LinearLayoutManager mLayout1,mLayout2;
    List<Music> list1 =  new ArrayList<>();
    String url = "https://berlinroid.ir/music.php?key=";

    public void getData (final Context con, final List<Music> list1, final RecyclerView recyclerView,String cat,RequestQueue req){

        this.req = req;
        req = Volley.newRequestQueue(con);
        this.list1 = list1;
        String link = url+cat;
        JsonObjectRequest job = new JsonObjectRequest(Request.Method.GET, link, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray jsonArray = response.getJSONArray("music");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject job = jsonArray.getJSONObject(i);
                        String title = job.getString("title");
                        String img = job.getString("img");
                        String genre = job.getString("genre");
                        String link = job.getString("link");
                        String artist = job.getString("artist");

                        Music m = new Music();
                        m.setTitle(title);
                        m.setArtist(artist);
                        m.setImg(img);
                        m.setLink(link);
                        m.setGenre(genre);
                        list1.add(m);
                    }

                    myAdapter = new MyAdapter(list1, con);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(myAdapter);
                } catch (Exception e) {

                    Toast.makeText(con, "try error", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(con, "not response", Toast.LENGTH_SHORT).show();
            }
        });

        req.add(job);

    }
}