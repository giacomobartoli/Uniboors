package com.example.gzano.uniboors.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.gzano.uniboors.Model.Room;
import com.example.gzano.uniboors.NavigationActivity;
import com.example.gzano.uniboors.Presenter.PlacesPresenter;
import com.example.gzano.uniboors.R;
import com.example.gzano.uniboors.ViewInterfaces.FragmentView;
import com.example.gzano.uniboors.utils.Adapters.RecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class CampusPlacesFragment extends Fragment implements FragmentView.PlacesFragmentView {
    private RecyclerView mRecyclerView;
    private PlacesPresenter placesPresenter;
    private ProgressBar progressBar, progressBarActivity;
    private RecyclerAdapter mAdapter;


    public CampusPlacesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.places_recycler_view_layout, container, false);
        mRecyclerView = rootView.findViewById(R.id.placesRecyclerView);
        progressBar = rootView.findViewById(R.id.progressBarPlacesFragment);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        progressBarActivity = getActivity().findViewById(R.id.progressBarPlacesActivity);
        placesPresenter.onCreate();


        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        //   placesPresenter.onCreate();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void setAdapter(@NotNull ArrayList<Room> fetchedRooms, @NotNull ArrayList<Room> fetchedRoomsUser) {
        Log.d("TIMES", "COUNTING");
        mAdapter = new RecyclerAdapter(fetchedRooms, placesPresenter, fetchedRoomsUser);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showProgressBar() {

        progressBarActivity.setVisibility(View.VISIBLE);

        //   progressBar.setVisibility(View.VISIBLE);


    }

    @Override
    public void hideProgressBar() {

        progressBarActivity.setVisibility(View.GONE);


    }

    @Override
    public void suggestUserToLookForPlaces() {
        mRecyclerView.setVisibility(View.GONE);

    }


    @Override
    public void startActivity() {
        goToNavigationActivity();
    }

    public void setPlacesPresenter(DatabaseReference reference) {
        placesPresenter = new PlacesPresenter(this, reference);

    }


    @Override
    public void setFavoriteIcon(int resource, final int position, @NonNull final Room room) {


        ImageView imageView = getImageAtPosition(position);
        imageView.setImageResource(resource);


    }

    @Override
    public void setNewClickListener(int resource, final int position, final Room room) {
        ImageView imageView = getImageAtPosition(position);
        if (resource == R.drawable.ic_action_name) {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    placesPresenter.addRoom(room, position);
                }
            });

        }
        if (resource == R.drawable.favorite_icon) {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    placesPresenter.removeRoom(room, position);
                }
            });

        }
    }

    private void goToNavigationActivity() {
        Intent intent = new Intent(getActivity(), NavigationActivity.class);
        startActivity(intent);
    }

    private ImageView getImageAtPosition(int position) {
        RecyclerAdapter.PlacesHolder viewHolderForAdapterPosition = (RecyclerAdapter.PlacesHolder) mRecyclerView.findViewHolderForAdapterPosition(position);

        ImageView imageView = viewHolderForAdapterPosition.itemView.findViewById(R.id.favourite);
        return imageView;


    }

}



