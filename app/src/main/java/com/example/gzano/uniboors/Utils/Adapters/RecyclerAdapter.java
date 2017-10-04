package com.example.gzano.uniboors.Utils.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gzano.uniboors.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by gzano on 04/10/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private ArrayList<Integer> mPhotos;

    public RecyclerAdapter(ArrayList<Integer> mPhotos) {
        this.mPhotos = mPhotos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {           //create the view to inflate in the rows, and pass it to the holder
        View inflatedView= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item_row,parent,false);
        return new ViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d("TAAAAAG"," number: " + mPhotos.get(position));

        holder.bind(mPhotos.get(position));
    }

    @Override
    public int getItemCount() {
        return mPhotos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mItemImage;
        private TextView mItemDate;
        private TextView mItemDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mItemDate=itemView.findViewById(R.id.item_date);

        }

        @Override
        public void onClick(View view) {
            Log.d("RecyclerView", "CLICK!");

        }

        public void bind(int number){
            mItemDate.setText(String.valueOf(number));
        }

    }
}
