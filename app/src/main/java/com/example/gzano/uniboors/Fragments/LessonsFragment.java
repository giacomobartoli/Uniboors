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

import com.example.gzano.uniboors.Model.Lesson;
import com.example.gzano.uniboors.NavigationActivity;
import com.example.gzano.uniboors.Presenter.ComputerSciencePresenter;
import com.example.gzano.uniboors.Presenter.PresenterInterface.Presenter;
import com.example.gzano.uniboors.R;
import com.example.gzano.uniboors.ViewInterfaces.FragmentView;
import com.example.gzano.uniboors.utils.Adapters.RecyclerAdapterLessons;
import com.example.gzano.uniboors.utils.Adapters.RecyclerAdapterPlaces;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class LessonsFragment extends Fragment implements FragmentView.LessonFragmentView {
    private RecyclerView mRecyclerView;
    private Presenter.LessonsPresenter lessonsPresenter;
    private RecyclerAdapterLessons mAdapter;


    public LessonsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_lessons, container, false);
        mRecyclerView = rootView.findViewById(R.id.lessons_recycler_view);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new RecyclerAdapterLessons(new ArrayList<Lesson>(), lessonsPresenter, new ArrayList<Lesson>());
        mRecyclerView.setAdapter(mAdapter);
        lessonsPresenter.onCreate();


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
    public void setAdapter(@NotNull ArrayList<Lesson> fetchedLessons, @NotNull ArrayList<Lesson> fetchedUserlessons) {

    }


    @Override
    public void setPlacesPresenter() {
        lessonsPresenter = new ComputerSciencePresenter(this);
    }

    @Override
    public void addLesson(Lesson lesson) {
        mAdapter.getCampusLessons().add(lesson);
        mAdapter.notifyItemInserted(mAdapter.getCampusLessons().size());

    }

    @Override
    public void removeLesson(Lesson lesson) {
        int position = mAdapter.getCampusLessons().lastIndexOf(lesson);
        mAdapter.getCampusLessons().remove(lesson);
        mAdapter.notifyItemRemoved(position);

    }

    @Override
    public void addUserLesson(Lesson lesson) {
        if (!mAdapter.getCampusLessons().contains(lesson)) {
            mAdapter.getUserLessons().add(lesson);

        }

    }

    @Override
    public void removeUserLesson(Lesson lesson) {
        if (!mAdapter.getCampusLessons().contains(lesson)) {
            mAdapter.getUserLessons().remove(lesson);

        }
    }

    @Override
    public void setNewClickListener(final int resource, final int position, @NonNull final Lesson lesson) {
        ImageView imageView = getImageAtPosition(position);
        if (resource == R.drawable.ic_add) {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("TAGADDROOM", " add room  " + lesson);
                    lessonsPresenter.addLesson(lesson, position);
                }
            });

        }
        if (resource == R.drawable.ic_added_check) {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("TAGremoveROOM", " remove room " + lesson);

                    lessonsPresenter.removeLesson(lesson, position);
                }
            });

        }
    }

    public void startCheckTime() {

    }

    private void goToNavigationActivity() {
        Intent intent = new Intent(getActivity(), NavigationActivity.class);
        startActivity(intent);
    }

    private ImageView getImageAtPosition(int position) {

        RecyclerAdapterPlaces.PlacesHolder viewHolderForAdapterPosition = (RecyclerAdapterPlaces.PlacesHolder) mRecyclerView.findViewHolderForAdapterPosition(position);


        return viewHolderForAdapterPosition.itemView.findViewById(R.id.favourite);

    }

}



