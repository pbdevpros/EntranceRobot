package com.projects.sjccrr.entryrobot;

/*
 *  Open-source software licensed from stack exchange user Suragch
 *  Permission is taken from user with knowledge of being sourced in respect to commercial use.
 *  https://stackoverflow.com/questions/40587168/simple-android-grid-example-using-recyclerview-with-gridlayoutmanager-like-the
 *
 */


import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private String[] mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;


    // data is passed into the constructor
    RecyclerViewAdapter(Context context, String[] data, Point size) {
        mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(mContext)
                .applyDefaultRequestOptions(new RequestOptions()
                        .placeholder(R.drawable.r2d2_mid)) // image being used is: https://avatars3.githubusercontent.com/u/6271?s=400&v=4
                .asBitmap()
                .load(mData[position])
                .into(holder.myImageView);
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mData.length;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView myImageView; //EDIT7: videoView myImageView //EDIT 2: TextView myTextView

        ViewHolder(View itemView) {
            super(itemView);
            myImageView = itemView.findViewById(R.id.info_image); // myTextView = itemView.findViewById(R.id.info_text)
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick( view, getAdapterPosition()); //getAdapterPosition() );
        }

    }

    // allows clicks events to be caught
    void setClickListener( ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}