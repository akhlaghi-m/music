package roid.berlin.android.onlinemusicplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import roid.berlin.android.onlinemusicplayer.R;
import roid.berlin.android.onlinemusicplayer.model.Music;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViwholder> {

   List<Music> musicList;
   Context context;

    public MyAdapter(List<Music> musicList, Context context) {
        this.musicList = musicList;
        this.context = context;
    }

    public class MyViwholder extends RecyclerView.ViewHolder{

       TextView title;
       ImageView img;



        public MyViwholder(@NonNull View itemView) {

            super(itemView);

            title = itemView.findViewById(R.id.item_title);
            img = itemView.findViewById(R.id.item_image);
        }
    }
    @NonNull
    @Override
    public MyAdapter.MyViwholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new MyViwholder(view );
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViwholder holder, int position) {

       final Music music = musicList.get(position);

       holder.title.setText(music.getTitle());
        Picasso.with(context).load(music.getImg()).placeholder(R.mipmap.ic_launcher).into(holder.img);

    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }
}
