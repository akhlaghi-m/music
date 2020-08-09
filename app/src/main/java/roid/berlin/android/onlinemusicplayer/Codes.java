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

public class Codes {

    RequestQueue req;
    String url = "http://berlinroid.ir/music.php?key=";

    public void getData(final Context con, String cat, RequestQueue req, final OnResponse onResponse) {

        final List<Music> list1 =  new ArrayList<>();
        this.req = req;
        req = Volley.newRequestQueue(con);
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

                    onResponse.onSuccess(list1);
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