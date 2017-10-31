package com.example.gzano.uniboors.utils.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gzano.uniboors.Model.Lesson;
import com.example.gzano.uniboors.Model.LessonSchedule;
import com.example.gzano.uniboors.Model.LessonTime;
import com.example.gzano.uniboors.Presenter.PresenterInterface.Presenter;
import com.example.gzano.uniboors.R;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by gzano on 04/10/2017.
 */

public class RecyclerAdapterLessons extends RecyclerView.Adapter<RecyclerAdapterLessons.LessonsHolder> {
    private ArrayList<Lesson> mCampuseLessons;
    private ArrayList<String> mUserLessons;
    private Presenter.LessonsPresenter lessonsPresenter;

    public RecyclerAdapterLessons(ArrayList<Lesson> mCampuseLessons, Presenter.LessonsPresenter lessonsPresenter, ArrayList<String> mUserLessons) {
        this.mCampuseLessons = mCampuseLessons;
        this.lessonsPresenter = lessonsPresenter;
        this.mUserLessons = mUserLessons;
    }

    public ArrayList<Lesson> getCampusLessons() {
        return mCampuseLessons;
    }

    public ArrayList<String> getUserLessons() {
        return mUserLessons;
    }

    @Override
    public LessonsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lessons_and_places_row_layout, parent, false);

        return new LessonsHolder(inflatedView);
    }


    @Override
    public void onBindViewHolder(LessonsHolder holder, int position) {
        holder.bind(mCampuseLessons.get(position));
    }

    @Override
    public int getItemCount() {
        return mCampuseLessons.size();
    }

    private boolean isFavorite(Lesson lesson) {
        for (String type : mUserLessons) {
            Log.d("TAGISFAV", type + " " + lesson.getType().toString());
            if (type.equals(lesson.getType().toString())) {
                return true;
            }
        }
        return false;
    }

    public class LessonsHolder extends RecyclerView.ViewHolder {
        private TextView lessonTitle, description;
        private ImageView addImage, backgroundImage;


        public LessonsHolder(View itemView) {
            super(itemView);

            lessonTitle = itemView.findViewById(R.id.lesson_title);
            description = itemView.findViewById(R.id.description);
            addImage = itemView.findViewById(R.id.add);
            backgroundImage = itemView.findViewById(R.id.image_background);
            addImage.setClickable(true);


        }


        public void bind(final Lesson lesson) {
            render(lesson);
            lessonTitle.setText(lesson.getName());
            description.setText("Luogo " + renderPlace(lesson.getSchedule()) + " giorno  " + renderTime(lesson.getSchedule()));
            if (isFavorite(lesson)) {
                addImage.setImageResource(R.drawable.ic_check_added);
                addImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        lessonsPresenter.removeLesson(lesson, getAdapterPosition());
                        addImage.setImageResource(R.drawable.ic_add);
                    }
                });
            } else {
                addImage.setImageResource(R.drawable.ic_add);
                addImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        lessonsPresenter.addLesson(lesson, getAdapterPosition());
                        addImage.setImageResource(R.drawable.ic_check_added);
                    }
                });

            }

        }

        private void render(Lesson lesson) {
            switch (lesson.getType()) {
                case SVILUPPO_SISTEMI_SOFTWARE:
                    backgroundImage.setImageResource(R.drawable.ic_dev);
                    break;
                case APPLICAZIONI_WEB:
                    backgroundImage.setImageResource(R.drawable.ic_web);

                    break;
            }

        }

        private String renderPlace(LessonSchedule lessonSchedule) {
            int dow = new DateTime().getDayOfWeek();
            HashMap<Integer, LessonTime> map = lessonSchedule.getDaysAndTime();

            if (map.containsKey(dow)) {
                Log.d("TAGDOW", String.valueOf(dow));
                return map.get(dow).getRoomName();
            } else {
                int closestDayOfLesson = LessonSchedule.Utils.getClosestDayOfLesson(map.keySet().toArray(new Integer[map.size()]));
                return map.get(closestDayOfLesson).getRoomName();
            }
        }

        private String renderTime(LessonSchedule lessonSchedule) {
            HashMap<Integer, LessonTime> map = lessonSchedule.getDaysAndTime();
            int size = map.keySet().size();
            if (LessonSchedule.Utils.isToday(map.keySet().toArray(new Integer[size]))) {
                return "Today";
            } else {

                return String.valueOf(LessonSchedule.Utils.getClosestDayOfLesson(map.keySet().toArray(new Integer[size])));
            }
        }


    }


}

