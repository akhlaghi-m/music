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

public class FragmentMusicPop extends Fragment {

RecyclerView rec1;
RequestQueue requestQueue;
MyAdapter myAdapter1;
LinearLayoutManager mLayout1;
String url1 = "Pop";
Codes codes = new Codes();

    public FragmentMusicPop() {
        // Required empty public constructor
    }


    public static FragmentMusicPop newInstance() {
        FragmentMusicPop fragment = new FragmentMusicPop();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_music_pop, container, false);
        rec1=root.findViewById(R.id.rec1);
        requestQueue = Volley.newRequestQueue(getActivity());

        rec1.setLayoutManager(new GridLayoutManager(getActivity(),1));
        rec1.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(getActivity(),4),true));
        rec1.setHasFixedSize(true);
        rec1.setAdapter(myAdapter1);

        codes.getData(getActivity(), url1, requestQueue, new OnResponse() {
            @Override
            public void onSuccess(List<Music> musics) {
                myAdapter1 = new MyAdapter(musics, getActivity());
                rec1.setItemAnimator(new DefaultItemAnimator());
                rec1.setAdapter(myAdapter1);
            }
        });



        return root;
    }
}