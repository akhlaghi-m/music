package roid.berlin.android.onlinemusicplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import roid.berlin.android.onlinemusicplayer.adapter.relatedadadapter;
import roid.berlin.android.onlinemusicplayer.interfaces.OnResponse;
import roid.berlin.android.onlinemusicplayer.model.Music;

public class Codes {

    RequestQueue req;
    String url = "http://berlinroid.ir/music.php?key=";
    public static MediaPlayer mediaPlayer;
    relatedadadapter myadapter2;

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
                        String text = job.getString("text");

                        Music m = new Music();
                        m.setTitle(title);
                        m.setArtist(artist);
                        m.setImg(img);
                        m.setLink(link);
                        m.setGenre(genre);
                        m.setText(text);
                        list1.add(m);
                    }

                    onResponse.onSuccess(list1);
                } catch (Exception e) {

                    Toast.makeText(con, "try error", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                String message = null;

                 if (volleyError instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                    Toast.makeText(con,message,Toast.LENGTH_SHORT).show();
                } else  if (volleyError instanceof NetworkError) {
                     message = "Cannot connect to Internet...Please check your connection!";
                     Toast.makeText(con,message,Toast.LENGTH_SHORT).show();
                 }
                 else if (volleyError instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                    Toast.makeText(con,message,Toast.LENGTH_SHORT).show();
                } else if (volleyError instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                    Toast.makeText(con,message,Toast.LENGTH_SHORT).show();
                } else if (volleyError instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                    Toast.makeText(con,message,Toast.LENGTH_SHORT).show();
                } else if (volleyError instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                    Toast.makeText(con,message,Toast.LENGTH_SHORT).show();
                }
            }
        });

        req.add(job);

    }

    /************************************* related recyclerView cods
     *
     */



    public void getrelated(final Context con, String cat, RequestQueue req, final OnResponse onResponse) {

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
                        String text = job.getString("text");

                        Music m = new Music();
                        m.setTitle(title);
                        m.setArtist(artist);
                        m.setImg(img);
                        m.setLink(link);
                        m.setGenre(genre);
                        m.setText(text);
                        list1.add(m);
                    }

                    onResponse.onSuccess(list1);
                } catch (Exception e) {

                    Toast.makeText(con, "try error", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                String message = null;

                if (volleyError instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                    Toast.makeText(con,message,Toast.LENGTH_SHORT).show();
                } else  if (volleyError instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                    Toast.makeText(con,message,Toast.LENGTH_SHORT).show();
                }
                else if (volleyError instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                    Toast.makeText(con,message,Toast.LENGTH_SHORT).show();
                } else if (volleyError instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                    Toast.makeText(con,message,Toast.LENGTH_SHORT).show();
                } else if (volleyError instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                    Toast.makeText(con,message,Toast.LENGTH_SHORT).show();
                } else if (volleyError instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                    Toast.makeText(con,message,Toast.LENGTH_SHORT).show();
                }
            }
        });

        req.add(job);

    }
}