package roid.berlin.android.onlinemusicplayer.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import roid.berlin.android.onlinemusicplayer.R;

public class FragmentMusicPlayList extends Fragment {


    public FragmentMusicPlayList() {
        // Required empty public constructor
    }

    public static FragmentMusicPlayList newInstance() {
        FragmentMusicPlayList fragment = new FragmentMusicPlayList();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_music_play_list, container, false);
    }
}