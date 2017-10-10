package com.example.gzano.uniboors.utils.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gzano.uniboors.Model.Room;
import com.example.gzano.uniboors.Presenter.PlacesPresenter;
import com.example.gzano.uniboors.R;

import java.util.ArrayList;

/**
 * Created by gzano on 04/10/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.PlacesHolder> {
    private ArrayList<Room> mRoomsCampus, mRoomsUser;
    private PlacesPresenter placesPresenter;
    private PlacesHolder placesHolder;

    public RecyclerAdapter(ArrayList<Room> mRooms, PlacesPresenter placesPresenter, ArrayList<Room> mRoomsUser) {
        this.mRoomsCampus = mRooms;
        this.placesPresenter = placesPresenter;
        this.mRoomsUser = mRoomsUser;

    }

    @Override
    public PlacesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item_row_cesena_places, parent, false);
        this.placesHolder = new PlacesHolder(inflatedView);
        return placesHolder;
    }

    public PlacesHolder getPlacesHolder() {
        return placesHolder;
    }

    @Override
    public void onBindViewHolder(PlacesHolder holder, int position) {

        holder.bind(mRoomsCampus.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mRoomsCampus.size();
    }


    public class PlacesHolder extends RecyclerView.ViewHolder {
        private TextView roomTitle, description;
        private ImageView heartImage;


        public PlacesHolder(View itemView) {
            super(itemView);

            roomTitle = itemView.findViewById(R.id.room_details_text_view);
            description = itemView.findViewById(R.id.description);
            heartImage = itemView.findViewById(R.id.favourite);
            heartImage.setClickable(true);


        }


        public void bind(final Room room, final int position) {


            roomTitle.setText(room.getRoomName());
            description.setText(R.string.wow_cool_lesson);
            if (isFavorite(room)) {
                heartImage.setImageResource(R.drawable.favorite_icon);
                heartImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        placesPresenter.removeRoom(room, position);

                    }


                });
            } else

            {

                heartImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("TAG", "CLICKED!");
                        placesPresenter.addRoom(room, position);

                    }
                });
            }
        }

        public void setHeartImageResource(int resource) {
            heartImage.setImageResource(resource);
        }


        private boolean isFavorite(Room room) {
            for (Room r : mRoomsUser) {
                if (r.getRoomName().equals(room.getRoomName())) {
                    return true;
                }
            }
            return false;
        }
    }
}
