package com.example.mealmate.model;

import java.util.ArrayList;

public class WeeklyPlanEntry {
    private String dayName;
    private String date;
    private ArrayList<Meal> mealsForDay;

    public WeeklyPlanEntry(String dayName, String date) {
        this.dayName = dayName;
        this.date = date;
        this.mealsForDay = new ArrayList<>();
    }

    public String getDayName() {
        return dayName;
    }

    public String getDate() {
        return date;
    }

    public ArrayList<Meal> getMealsForDay() {
        return mealsForDay;
    }

    public void addMeal(Meal meal) {
        mealsForDay.add(meal);
    }

    public void removeMeal(Meal meal) {
        mealsForDay.remove(meal);
    }
}
