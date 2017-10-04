package com.example.gzano.uniboors.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.gzano.uniboors.Model.Room;
import com.example.gzano.uniboors.Presenter.PlacesPresenter;
import com.example.gzano.uniboors.R;
import com.example.gzano.uniboors.Utils.Adapters.RecyclerAdapter;
import com.example.gzano.uniboors.ViewInterfaces.FragmentView;

import java.util.ArrayList;


public class RecyclerViewFragment extends Fragment implements FragmentView.PlacesFragmentView {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerAdapter mAdapter;
    private PlacesPresenter placesPresenter;
    private View rootView;
    private ProgressBar progressBar;

    public RecyclerViewFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.places_recycler_view_layout, container, false);
        mRecyclerView = rootView.findViewById(R.id.placesRecyclerView);
        progressBar = getActivity().findViewById(R.id.progressBarPlacesActivity);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        placesPresenter = new PlacesPresenter(this);
        placesPresenter.onCreate();

        return rootView;
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
    public void setAdapter(@NonNull ArrayList<Room> fetchedRooms) {

        mAdapter = new RecyclerAdapter(fetchedRooms);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);


    }

    @Override
    public void hideProgressBar() {

        progressBar.setVisibility(View.GONE);

    }
}
