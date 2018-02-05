package com.example.gzano.uniboors.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.gzano.uniboors.Model.Lesson;
import com.example.gzano.uniboors.NavigationActivity;
import com.example.gzano.uniboors.Presenter.ComputerSciencePresenter;
import com.example.gzano.uniboors.Presenter.PresenterInterface.Presenter;
import com.example.gzano.uniboors.R;
import com.example.gzano.uniboors.ViewInterfaces.FragmentView;
import com.example.gzano.uniboors.utils.Adapters.RecyclerAdapterLessons;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class LessonsFragment extends Fragment implements FragmentView.LessonFragmentView {
    private RecyclerView mRecyclerView;
    private Presenter lessonsPresenter;
    private RecyclerAdapterLessons mAdapter;
    private ProgressBar progressBar;


    public LessonsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_lessons, container, false);
        progressBar = getActivity().findViewById(R.id.progress_bar_lessons);
        mRecyclerView = rootView.findViewById(R.id.lessons_recycler_view);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setDrawingCacheEnabled(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);
        lessonsPresenter = new ComputerSciencePresenter(this);
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
    public void setAdapter(@NotNull ArrayList<Lesson> fetchedLessons) {
        mAdapter = new RecyclerAdapterLessons(fetchedLessons, this);
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public void showProgressBar() {
        Log.d("TAGBAR", " progress bar " + progressBar.getId());
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void makeRowClickable(View view) {
        mAdapter.setFABClicked(true);
        view.setVisibility(View.GONE);

    }


    @Override
    public void goToNavigationActivity(@NotNull String className, @NotNull String teacherName, @NotNull String day, @NotNull String startTime, @NotNull String endTime, @NotNull String placeName, @NotNull String lessonName, @NotNull int dayValue) {
        Intent intent = new Intent(getActivity(), NavigationActivity.class);
        intent.putExtra("className", className);
        intent.putExtra("teacherName", teacherName);
        intent.putExtra("day", day);
        intent.putExtra("dayValue", String.valueOf(dayValue));
        intent.putExtra("timeStart", startTime);
        intent.putExtra("timeEnd", endTime);
        intent.putExtra("placeName", placeName);
        intent.putExtra("lessonName", lessonName);
        Log.d("TAGINTENT", className + " " + teacherName + " " + day + " " + startTime + " " + endTime + " " + placeName);
        startActivity(intent);
    }

}



