package roid.berlin.android.onlinemusicplayer.adapter;

import android.content.Context;
import android.content.Intent;
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
import roid.berlin.android.onlinemusicplayer.activitys.MusicPageActivity;
import roid.berlin.android.onlinemusicplayer.interfaces.InterfaceClick;
import roid.berlin.android.onlinemusicplayer.model.Music;

public class relatedadadapter extends RecyclerView.Adapter<relatedadadapter.MyViwholder> {

    List<Music> musicList;
    Context context;

    public relatedadadapter(List<Music> musicList, Context context) {
        this.musicList = musicList;
        this.context = context;
    }

    public class MyViwholder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{

        TextView title,artist;
        ImageView img;
        private InterfaceClick interfaceClick;



        public MyViwholder(@NonNull View itemView) {

            super(itemView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            title = itemView.findViewById(R.id.related_title);
            artist = itemView.findViewById(R.id.artist);
            img = itemView.findViewById(R.id.related_pic);
        }

        public void setClick(InterfaceClick interfaceClick){
            this.interfaceClick=interfaceClick;

        }
        @Override
        public void onClick(View view) {
            interfaceClick.onclick(view,getPosition(),false);

        }

        @Override
        public boolean onLongClick(View view) {

            interfaceClick.onclick(view,getPosition(),true);
            return false;
        }
    }
    @NonNull
    @Override
    public relatedadadapter.MyViwholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.related,parent,false);
        return new relatedadadapter.MyViwholder(view );
    }

    @Override
    public void onBindViewHolder(@NonNull relatedadadapter.MyViwholder holder, int position) {

        final Music music = musicList.get(position);

        holder.title.setText(music.getTitle());
        Picasso.with(context).load(music.getImg()).placeholder(R.mipmap.ic_launcher).into(holder.img);

        holder.setClick(new InterfaceClick() {
            @Override
            public void onclick(View view, int position, boolean longClick) {

                Intent i =new Intent(context, MusicPageActivity.class);
                i.putExtra("title",music.getTitle());
                i.putExtra("artist",music.getArtist());
                i.putExtra("img",music.getImg());
                i.putExtra("link",music.getLink());
                i.putExtra("genre",music.getGenre());
                i.putExtra("text",music.getText());


                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }
}
