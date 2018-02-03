package com.example.gzano.uniboors.utils.WebAppInterfaces;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;

/**
 * Created by gzano on 03/02/2018.
 */

public class ClassDetailsPicker {

    private Context context;

    public ClassDetailsPicker(Context context) {
        this.context = context;
    }

    @JavascriptInterface
    public ClassDetails getClassDetails() {
        Intent intent = ((Activity) context).getIntent();

        String lessonName = intent.getStringExtra("lessonName");
        String teacherName = intent.getStringExtra("teacherName");
        String day = intent.getStringExtra("day");
        String startTime = intent.getStringExtra("startTime");
        String endTime = intent.getStringExtra("endTime");
        String placeName = intent.getStringExtra("placeName");


        return null;
    }

    public class ClassDetails {
        private String lessonName, teacher, day, startTime, endTime, placeName;

        public ClassDetails(String lessonName, String teacher, String day, String startTime, String endTime, String placeName) {
            this.lessonName = lessonName;
            this.teacher = teacher;
            this.day = day;
            this.startTime = startTime;
            this.endTime = endTime;
            this.placeName = placeName;
        }

        public String getLessonName() {
            return lessonName;
        }

        public String getTeacher() {
            return teacher;
        }

        public String getDay() {
            return day;
        }

        public String getStartTime() {
            return startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public String getPlaceName() {
            return placeName;
        }
    }

}


