package com.example.gzano.uniboors.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.gzano.uniboors.Model.Room;
import com.example.gzano.uniboors.Model.RoomType;
import com.example.gzano.uniboors.Model.User;
import com.example.gzano.uniboors.NavigationActivity;
import com.example.gzano.uniboors.Presenter.PlacesPresenter;
import com.example.gzano.uniboors.R;
import com.example.gzano.uniboors.ViewInterfaces.FragmentView;
import com.example.gzano.uniboors.utils.Adapters.RecyclerAdapter;
import com.example.gzano.uniboors.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;


public class RecyclerFragment extends Fragment implements FragmentView.PlacesFragmentView {
    private RecyclerView mRecyclerView;
    private PlacesPresenter placesPresenter;
    private ProgressBar progressBar, progressBarActivity;
    private AlertDialog alertDialogGoToNavigation;
    private int pageTag;
    private NetworkInfo activeNetwork;
    private View rootView;

    public RecyclerFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.places_recycler_view_layout, container, false);
        mRecyclerView = rootView.findViewById(R.id.placesRecyclerView);
        progressBar = rootView.findViewById(R.id.progressBarPlacesFragment);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        progressBarActivity = getActivity().findViewById(R.id.progressBarPlacesActivity);
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetwork = cm.getActiveNetworkInfo();

        placesPresenter.onCreate();


        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        placesPresenter.onCreate();
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
    public void setAdapter(@NonNull ArrayList<Room> fetchedRooms, @NonNull HashMap<RoomType, File> files) {

        RecyclerAdapter mAdapter = new RecyclerAdapter(fetchedRooms, placesPresenter, files);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showProgressBar(int pageTag) {

        if (pageTag == Constants.PAGE_TAG_CESENA_PLACE) {
            progressBarActivity.setVisibility(View.VISIBLE);
        }
        if (pageTag == Constants.PAGE_TAG_YOUR_PLACE) {
            progressBar.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void hideProgressBar(int pageTag) {

        if (pageTag == Constants.PAGE_TAG_CESENA_PLACE) {
            progressBarActivity.setVisibility(View.GONE);
        }
        if (pageTag == Constants.PAGE_TAG_YOUR_PLACE) {
            progressBar.setVisibility(View.GONE);
        }


    }

    @Override
    public void suggestUserToLookForPlaces() {
        mRecyclerView.setVisibility(View.GONE);

    }

    public void setPlacesPresenter(DatabaseReference reference) {
        placesPresenter = new PlacesPresenter(this, reference);

    }

    @Override
    public void showAlertAlreadyInsert() {

    }

    @Override
    public void showAlertGoToNavigationOrStay(final String value) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        User u = new User(user.getEmail());
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
        builder.setTitle("Hi " + u.toString());
        builder.setMessage("Would you like to add places or do you want to jump straight to navigation?");
        String positiveText = "ADD AND GO!";
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        alertDialogGoToNavigation.hide();
                        goToNavigationActivity();


                    }
                });

        String negativeText = "ADD";
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        alertDialogGoToNavigation.hide();


                    }
                });


        String neutralText = "BACK";
        builder.setNeutralButton(neutralText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialogGoToNavigation.hide();
            }
        });
        alertDialogGoToNavigation = builder.create();

        alertDialogGoToNavigation.show();
    }


    @Override
    public void showPopUp(final View viewClicked) {
        PopupMenu popupMenu = new PopupMenu(getActivity(), viewClicked.findViewById(R.id.cardView), Gravity.CENTER_HORIZONTAL);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_options, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                TextView textView = viewClicked.findViewById(R.id.room_details_text_view);
                String roomName = textView.getText().toString();

                placesPresenter.removeRoom(roomName);
                return true;
            }
        });
        popupMenu.show();
    }

    @Override
    public void showGoAlertOrRemove(String value, final String roomName) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
        builder.setTitle("This place is already added!");
        builder.setMessage("Do you want to go to navigation or remove from your list?");
        String positiveText = "GO!";
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        alertDialogGoToNavigation.hide();
                        goToNavigationActivity();

                    }
                });
        String negativeText = "REMOVE";
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        placesPresenter.removeRoom(roomName);
                        alertDialogGoToNavigation.hide();


                    }
                });


        String neutralText = "BACK";
        builder.setNeutralButton(neutralText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialogGoToNavigation.hide();
            }
        });
        alertDialogGoToNavigation = builder.create();
        alertDialogGoToNavigation.show();
    }

    @Override
    public int getPageTag() {
        return pageTag;
    }

    public void setPageTag(int pageNumber) {
        this.pageTag = pageNumber;
    }

    @Override
    public void startActivity() {
        goToNavigationActivity();
    }

    private void goToNavigationActivity() {
        Intent intent = new Intent(getActivity(), NavigationActivity.class);
        startActivity(intent);
    }


}
