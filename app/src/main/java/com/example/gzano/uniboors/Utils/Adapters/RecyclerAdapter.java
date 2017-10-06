package com.example.gzano.uniboors.Utils.Adapters;

import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gzano.uniboors.Model.Room;
import com.example.gzano.uniboors.Model.RoomType;
import com.example.gzano.uniboors.Presenter.PlacesPresenter;
import com.example.gzano.uniboors.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by gzano on 04/10/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private ArrayList<Room> mRooms;
    private PlacesPresenter placesPresenter;
    private HashMap<RoomType, File> files;

    public RecyclerAdapter(ArrayList<Room> mRooms, PlacesPresenter placesPresenter, HashMap<RoomType, File> files) {
        this.mRooms = mRooms;
        this.placesPresenter = placesPresenter;
        this.files = files;
        Log.d("TESTFILE ", " map " + files.values().size());

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {           //create the view to inflate in the rows, and pass it to the holder
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item_row, parent, false);
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mItemImage;
        private TextView mItemDate, description;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mItemDate = itemView.findViewById(R.id.room_details_text_view);
            mItemImage = itemView.findViewById(R.id.classroomImageView);
            description = itemView.findViewById(R.id.description);

        }

        @Override
        public void onClick(View view) {

            String roomName = mItemDate.getText().toString();
            RoomType roomType = (RoomType) mItemDate.getTag();
            if (roomType.equals(RoomType.CLASSROOM)) {
                placesPresenter.addToFavPlaces(new Room.ClassRoom(roomType, roomName, false));
            }
            if (roomType.equals(RoomType.COMPUTER_LAB)) {
                placesPresenter.addToFavPlaces(new Room.ComputerLab(roomType, roomName, false));
            }

        }

        public void bind(final Room room) {

            switch (room.getRoomType()) {
                case CLASSROOM:
                    mItemDate.setTag(RoomType.CLASSROOM);
                    mItemImage.setImageBitmap(BitmapFactory.decodeFile(files.get(RoomType.CLASSROOM).getAbsolutePath()));
                    break;

                case COMPUTER_LAB:
                    mItemDate.setTag(RoomType.COMPUTER_LAB);
                    mItemImage.setImageBitmap(BitmapFactory.decodeFile(files.get(RoomType.COMPUTER_LAB).getAbsolutePath()));

                    break;
            }
            mItemDate.setText(room.getRoomName());
            description.setText(R.string.wow_cool_lesson);


        }


    }
}
