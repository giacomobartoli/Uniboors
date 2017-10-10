package com.example.gzano.uniboors.utils.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gzano.uniboors.Model.Room;
import com.example.gzano.uniboors.Model.RoomType;
import com.example.gzano.uniboors.Presenter.PlacesPresenter;
import com.example.gzano.uniboors.R;

import java.util.ArrayList;

/**
 * Created by gzano on 04/10/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private ArrayList<Room> mRooms;
    private PlacesPresenter placesPresenter;
    private int tag;

    public RecyclerAdapter(ArrayList<Room> mRooms, PlacesPresenter placesPresenter, int tag) {
        this.mRooms = mRooms;
        this.placesPresenter = placesPresenter;

        this.tag = tag;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item_row_cesena_places, parent, false);
        return new ViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.bind(mRooms.get(position));
    }

    @Override
    public int getItemCount() {
        return mRooms.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView roomTitle, description;
        private ImageView heartImage;


        public ViewHolder(View itemView) {
            super(itemView);

            roomTitle = itemView.findViewById(R.id.room_details_text_view);
            description = itemView.findViewById(R.id.description);
            heartImage = itemView.findViewById(R.id.favourite);


        }



        public void bind(final Room room) {

            switch (room.getRoomType()) {
                case CLASSROOM:
                    roomTitle.setTag(RoomType.CLASSROOM);
                    break;

                case COMPUTER_LAB:
                    roomTitle.setTag(RoomType.COMPUTER_LAB);

                    break;
            }

            roomTitle.setText(room.getRoomName());
            description.setText(R.string.wow_cool_lesson);
            heartImage.setFocusableInTouchMode(true);
            heartImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    placesPresenter.addToFavPlaces(room);

                }
            });

        }


    }
}
