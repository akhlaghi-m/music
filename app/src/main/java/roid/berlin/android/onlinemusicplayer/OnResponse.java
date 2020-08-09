package roid.berlin.android.onlinemusicplayer;

import java.util.List;

import roid.berlin.android.onlinemusicplayer.model.Music;

public interface OnResponse {
    void onSuccess(List<Music> musics);
}
