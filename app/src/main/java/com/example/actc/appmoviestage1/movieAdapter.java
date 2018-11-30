package com.example.actc.appmoviestage1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class movieAdapter extends  RecyclerView.Adapter <movieAdapter.template_view_holder>  {

        private int numItem;
        List<Movie> list_movie = new ArrayList<Movie>();


    final private ListMovieClickListener mOnClickListener;


    public interface ListMovieClickListener {
        public void onclickListener(int pos);

    }




    public movieAdapter(int num, List<Movie> L,ListMovieClickListener lsn) {
        numItem = num;
        list_movie = L;
        mOnClickListener=lsn;
    }



    @Override
    public template_view_holder onCreateViewHolder(ViewGroup viewGroup, int viewType) {


        Context context = viewGroup.getContext();

        int layoutIdForListItem = R.layout.template_cell_recycleview;

        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        template_view_holder viewHolder = new template_view_holder(view);

        return viewHolder;

    }

    ///==================================================

    @Override
    public void onBindViewHolder(template_view_holder holder, int position) {

        holder.bind(list_movie.get(position));

    }

    /////==========================================

    public int getItemCount() {
        return numItem;
    }


    ////////////////==============================






    class template_view_holder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        ImageView img_poster;


        public template_view_holder(View view) {
            super(view);
            img_poster = (ImageView) view.findViewById(R.id.img_poster);
            view.setOnClickListener(this);
            // view.setOnClickListener(this);

        }


        public void bind(Movie temp_movie) {


            Picasso.get().load(temp_movie.getPoster_path()).into(img_poster);


        }


        @Override
        public void onClick(View v) {

            int pos = getAdapterPosition();
            mOnClickListener.onclickListener(pos);

        }
    } // viewHolder


}//// Adapter