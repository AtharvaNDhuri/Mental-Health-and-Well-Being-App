package com.example.mentalhealthandwellbeingapp;

import android.graphics.drawable.ColorDrawable;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.CalendarDay;

public class MoodDecorator implements DayViewDecorator {
    private final int color;
    private final CalendarDay day;

    public MoodDecorator(int color, CalendarDay day) {
        this.color = color;
        this.day = day;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return day.equals(this.day); // Decorate only specific day
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setBackgroundDrawable(new ColorDrawable(color)); // Set the color for the day
    }
}