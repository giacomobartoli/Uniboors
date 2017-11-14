package com.example.gzano.uniboors.utils.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gzano.uniboors.Fragments.LessonsFragment;
import com.example.gzano.uniboors.Model.Lesson;
import com.example.gzano.uniboors.Model.LessonSchedule;
import com.example.gzano.uniboors.Model.LessonTime;
import com.example.gzano.uniboors.R;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by gzano on 04/10/2017.
 */

public class RecyclerAdapterLessons extends RecyclerView.Adapter<RecyclerAdapterLessons.LessonsHolder> {
    private ArrayList<Lesson> mCampusLessons;
    private LessonsFragment lessonsFragment;
    private boolean isClicked = false;

    public RecyclerAdapterLessons(ArrayList<Lesson> mCampuseLessons, LessonsFragment lessonsFragment) {
        this.mCampusLessons = mCampuseLessons;
        this.lessonsFragment = lessonsFragment;


    }


    @Override
    public LessonsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lessons_and_places_row_layout, parent, false);

        return new LessonsHolder(inflatedView);
    }


    @Override
    public void onBindViewHolder(LessonsHolder holder, int position) {
        holder.bind(mCampusLessons.get(position));
    }

    @Override
    public int getItemCount() {
        return mCampusLessons.size();
    }

    public void setFABClicked(boolean b) {
        isClicked = b;
    }


    public class LessonsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView lessonTitle, description, dayAndTime, teacher;
        private ImageView backgroundImage;
        private String day, timeStart, timeEnd, place;


        public LessonsHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setBackgroundResource(R.drawable.recycler_view_row_selector);
            lessonTitle = itemView.findViewById(R.id.lesson_title);
            backgroundImage = itemView.findViewById(R.id.image_background);
            description = itemView.findViewById(R.id.description);
            dayAndTime = itemView.findViewById(R.id.day_and_time);
            teacher = itemView.findViewById(R.id.teacher_text);


        }


        public void bind(final Lesson lesson) {
            renderBackground(lesson);
            int dayInt = renderDay(lesson.getSchedule());
            place = renderPlace(lesson.getSchedule());
            lessonTitle.setText(lesson.getName());
            description.setText(String.format("%s ", place));
            teacher.setText(lesson.getTeacher());
            day = getDayString(dayInt);
            timeStart = String.valueOf(LessonSchedule.Utils.getLessonTime(dayInt, lesson).getTimeStart()) + ":00";
            timeEnd = String.valueOf(LessonSchedule.Utils.getLessonTime(dayInt, lesson).getTimeEnd()) + ":00";
            dayAndTime.setText(String.format("%s, %s-%s", day, timeStart, timeEnd));
        }

        private void renderBackground(Lesson lesson) {
            switch (lesson.getType()) {
                case SVILUPPO_SISTEMI_SOFTWARE:
                    backgroundImage.setImageResource(R.drawable.ic_dev);
                    break;
                case APPLICAZIONI_WEB:
                    backgroundImage.setImageResource(R.drawable.ic_web_development);
                    break;
                case MACHINE_LEARNING:
                    backgroundImage.setImageResource(R.drawable.ic_machine_learning);
                    break;
                case DATA_MINING:
                    backgroundImage.setImageResource(R.drawable.ic_data_mining);
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

        private int renderDay(LessonSchedule lessonSchedule) {
            HashMap<Integer, LessonTime> map = lessonSchedule.getDaysAndTime();
            int size = map.keySet().size();

            return LessonSchedule.Utils.getClosestDayOfLesson(map.keySet().toArray(new Integer[size]));
        }


        @Override
        public void onClick(View view) {

            if (isClicked) {
                lessonsFragment.goToNavigationActivity(lessonTitle.getText().toString(), teacher.getText().toString(), day, timeStart, timeEnd, place);

            }
        }


        private String getDayString(int dayOfWeek) {
            switch (dayOfWeek) {
                case 1:
                    return "Monday";
                case 2:
                    return "Tuesday";
                case 3:
                    return "Wednesday";
                case 4:
                    return "Thursday";
                case 5:
                    return "Friday";
                case 0:
                    return "Today";


            }
            return null;
        }


    }


}

