package roid.berlin.android.onlinemusicplayer.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.List;

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

public class FragmentMusicNew extends Fragment {
    RecyclerView rec3;
    RequestQueue requestQueue;
    MyAdapter myAdapter3;
    LinearLayoutManager mLayout2;
    String url3 = "New";
    Codes codes = new Codes();
    public FragmentMusicNew() {
        // Required empty public constructor
    }

    public static FragmentMusicNew newInstance() {
        FragmentMusicNew fragment = new FragmentMusicNew();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_music_new, container, false);

        rec3=root.findViewById(R.id.rec2);
        requestQueue = Volley.newRequestQueue(getActivity());
        //mLayout2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        rec3.setLayoutManager(new GridLayoutManager(getActivity(),1));
        rec3.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(getActivity(),4),true));
        rec3.setHasFixedSize(true);
        // rec2.setLayoutManager(mLayout2);
        rec3.setAdapter(myAdapter3);

        codes.getData(getActivity(), url3, requestQueue, new OnResponse() {
            @Override
            public void onSuccess(List<Music> musics) {

                myAdapter3 = new MyAdapter(musics, getActivity());
                rec3.setItemAnimator(new DefaultItemAnimator());
                rec3.setAdapter(myAdapter3);
            }
        });

        return root;
    }
}