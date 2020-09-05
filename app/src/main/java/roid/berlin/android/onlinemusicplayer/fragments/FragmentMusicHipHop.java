package roid.berlin.android.onlinemusicplayer.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import roid.berlin.android.onlinemusicplayer.Codes;
import roid.berlin.android.onlinemusicplayer.R;
import roid.berlin.android.onlinemusicplayer.SpacingItemDecoration;
import roid.berlin.android.onlinemusicplayer.Tools;
import roid.berlin.android.onlinemusicplayer.adapter.MyAdapter;
import roid.berlin.android.onlinemusicplayer.interfaces.OnResponse;
import roid.berlin.android.onlinemusicplayer.model.Music;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.List;

public class FragmentMusicHipHop extends Fragment {


    RecyclerView rec2;
    RequestQueue requestQueue;
    MyAdapter  myAdapter2;
    LinearLayoutManager  mLayout2;
    String url2 = "HipHop";
    Codes codes = new Codes();


    public FragmentMusicHipHop() {
        // Required empty public constructor
    }


    public static FragmentMusicHipHop newInstance() {
        FragmentMusicHipHop fragment = new FragmentMusicHipHop();
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_music_hiphop, container, false);
        rec2=root.findViewById(R.id.rec2);
        requestQueue = Volley.newRequestQueue(getActivity());
        //mLayout2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        rec2.setLayoutManager(new GridLayoutManager(getActivity(),1));
        rec2.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(getActivity(),4),true));
        rec2.setHasFixedSize(true);
       // rec2.setLayoutManager(mLayout2);
        rec2.setAdapter(myAdapter2);

        codes.getData(getActivity(), url2, requestQueue, new OnResponse() {
            @Override
            public void onSuccess(List<Music> musics) {

                myAdapter2 = new MyAdapter(musics, getActivity());
                rec2.setItemAnimator(new DefaultItemAnimator());
                rec2.setAdapter(myAdapter2);
            }
        });





        return root;
    }
}