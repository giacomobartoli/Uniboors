package com.example.gzano.uniboors.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private AlertDialog alertDialogGoToNavigation;
    private RecyclerAdapter mAdapter;

    private int pageTag;

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
    public void setAdapter(@NotNull ArrayList<Room> fetchedRooms, @NotNull ArrayList<Room> fetchedRoomsUser) {

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

        //  progressBar.setVisibility(View.GONE);


    }

    @Override
    public void suggestUserToLookForPlaces() {
        mRecyclerView.setVisibility(View.GONE);

    }


//    @Override
//    public void showAlertAlreadyInsert() {
//
//    }
//
//    @Override
//    public void showAlertGoToNavigationOrStay(final HashMap<String, String> data, final String roomName) {
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        User u = new User(user.getEmail());
//        AlertDialog.Builder builder =
//                new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
//        builder.setTitle("Hi " + u.toString());
//        builder.setMessage("Would you like to add places or do you want to jump straight to navigation?");
//        String positiveText = "ADD AND GO!";
//        builder.setPositiveButton(positiveText,
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        placesPresenter.add(data, roomName);
//                        alertDialogGoToNavigation.hide();
//                        goToNavigationActivity();
//
//
//                    }
//                });
//
//        String negativeText = "ADD";
//        builder.setNegativeButton(negativeText,
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//
//                        alertDialogGoToNavigation.hide();
//
//
//                    }
//                });
//
//
//        String neutralText = "BACK";
//        builder.setNeutralButton(neutralText, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                alertDialogGoToNavigation.hide();
//            }
//        });
//        alertDialogGoToNavigation = builder.create();
//
//        alertDialogGoToNavigation.show();
//    }


//    @Override
//    public void showPopUp(@NonNull final View viewClicked) {
//      //  PopupMenu popupMenu = new PopupMenu(getActivity(), viewClicked.findViewById(R.id.cardView), Gravity.CENTER_HORIZONTAL);
//        MenuInflater inflater = popupMenu.getMenuInflater();
//        inflater.inflate(R.menu.menu_options, popupMenu.getMenu());
//        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                TextView textView = viewClicked.findViewById(R.id.room_details_text_view);
//                String roomName = textView.getText().toString();
//
//                placesPresenter.removeRoom(roomName);
//                return true;
//            }
//        });
//        popupMenu.show();
    //   }

//    @Override
//    public void showGoAlertOrRemove(@NonNull final String roomName) {
//        AlertDialog.Builder builder =
//                new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
//        builder.setTitle("This place is already added!");
//        builder.setMessage("Do you want to go to navigation or remove from your list?");
//        String positiveText = "GO!";
//        builder.setPositiveButton(positiveText,
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//
//                        alertDialogGoToNavigation.hide();
//                        goToNavigationActivity();
//
//                    }
//                });
//        String negativeText = "REMOVE";
//        builder.setNegativeButton(negativeText,
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        placesPresenter.removeRoom(roomName);
//                        alertDialogGoToNavigation.hide();
//
//
//                    }
//                });
//
//
//        String neutralText = "BACK";
//        builder.setNeutralButton(neutralText, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                alertDialogGoToNavigation.hide();
//            }
//        });
//        alertDialogGoToNavigation = builder.create();
//        alertDialogGoToNavigation.show();
//    }


    @Override
    public void startActivity() {
        goToNavigationActivity();
    }

    public void setPlacesPresenter(DatabaseReference reference) {
        placesPresenter = new PlacesPresenter(this, reference);

    }

    private void goToNavigationActivity() {
        Intent intent = new Intent(getActivity(), NavigationActivity.class);
        startActivity(intent);
    }


    @Override
    public void setFavoriteIcon(int resource, int position) {
        RecyclerAdapter.PlacesHolder placesHolder = (RecyclerAdapter.PlacesHolder) mRecyclerView.findViewHolderForAdapterPosition(position);
        placesHolder.setHeartImageResource(resource);
    }
}
