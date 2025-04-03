package com.example.mealmate.model;

import com.example.mealmate.model.Meal;
import java.util.ArrayList;

public class WeeklyDay {
    private String dayName;
    private String date;
    private ArrayList<Meal> meals;
    public WeeklyDay(String dayName, String date) {
        this.dayName = dayName;
        this.date = date;
        this.meals = new ArrayList<>();
    }

    public String getDayName() {
        return dayName;
    }

    public String getDate() {
        return date;
    }

    public ArrayList<Meal> getMeals() {
        return meals;
    }

    public void addMeal(Meal meal) {
        meals.add(meal);
    }

    public void removeMeal(Meal meal) {
        meals.remove(meal);
    }

    public void setMeals(ArrayList<Meal> meals) {
        this.meals = meals;
    }
}
