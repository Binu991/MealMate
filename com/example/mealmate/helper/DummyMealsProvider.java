package com.example.mealmate.helper;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.mealmate.R;
import com.example.mealmate.model.Dish;

import java.util.ArrayList;
import java.util.List;

public class DummyMealsProvider {

    public static void insertDummyMealsIfNeeded(Context context) {
        RecipeDao dao = new RecipeDao(context);

        if (dao.getAllDishes().isEmpty()) {
            List<Dish> dummyDishes = new ArrayList<>();

            dummyDishes.add(new Dish("Spaghetti Carbonara",
                    List.of("200g Spaghetti", "2 Eggs", "100g Pancetta", "50g Parmesan cheese", "Black pepper"),
                    List.of("Boil pasta", "Cook pancetta", "Mix eggs and cheese", "Combine all and serve"),
                    getDrawableUri(context, R.drawable.spha)));

            dummyDishes.add(new Dish("Chicken Stir Fry",
                    List.of("250g Chicken", "1 cup Mixed vegetables", "2 tbsp Soy sauce", "2 Garlic cloves", "1 tsp Ginger"),
                    List.of("Marinate chicken", "Stir-fry chicken", "Add vegetables and sauce", "Serve hot"),
                    getDrawableUri(context, R.drawable.chicken)));

            dummyDishes.add(new Dish("Paneer Butter Masala",
                    List.of("200g Paneer", "1 cup Tomato puree", "2 tbsp Butter", "3 tbsp Cream", "1 tsp Garam masala"),
                    List.of("Fry paneer", "Cook tomato puree with spices", "Add butter and cream", "Mix paneer and simmer"),
                    getDrawableUri(context, R.drawable.paneer)));

            dummyDishes.add(new Dish("Veggie Pizza",
                    List.of("1 Pizza base", "Tomato sauce", "Cheese", "Capsicum", "Onion", "Olives"),
                    List.of("Spread sauce", "Add toppings", "Sprinkle cheese", "Bake in oven until golden"),
                    getDrawableUri(context, R.drawable.pizza)));

            dummyDishes.add(new Dish("Fruit Salad",
                    List.of("1 Apple", "1 Banana", "5 Grapes", "1 Orange", "1 tbsp Honey"),
                    List.of("Chop fruits", "Mix together", "Add honey", "Chill and serve"),
                    getDrawableUri(context, R.drawable.salad)));

            dummyDishes.add(new Dish("Grilled Sandwich",
                    List.of("2 slices Bread", "Butter", "Cheese", "Tomato", "Cucumber", "Salt & Pepper"),
                    List.of("Layer ingredients", "Grill sandwich", "Cut and serve with ketchup"),
                    getDrawableUri(context, R.drawable.sandwhich)));

            dummyDishes.add(new Dish("Egg Fried Rice",
                    List.of("2 Eggs", "1 cup Rice", "1/2 cup Veggies", "1 tbsp Soy sauce", "Oil"),
                    List.of("Scramble eggs", "Add cooked rice", "Add veggies and soy sauce", "Stir-fry and serve"),
                    getDrawableUri(context, R.drawable.rice)));

            dummyDishes.add(new Dish("Tomato Soup",
                    List.of("4 Tomatoes", "1 Onion", "1 tsp Butter", "Salt", "Pepper"),
                    List.of("Boil tomatoes", "Blend and strain", "Cook with onion and butter", "Season and serve hot"),
                    getDrawableUri(context, R.drawable.soup)));

            for (Dish dish : dummyDishes) {
                dao.insertDish(dish);
                Log.d("DummyMealsProvider", "Inserted dummy dish: " + dish.getTitle());
            }

            Toast.makeText(context, "\u2714 Dummy meals inserted!", Toast.LENGTH_SHORT).show();
        }
    }

    private static Uri getDrawableUri(Context context, int resId) {
        return Uri.parse("android.resource://" + context.getPackageName() + "/" + resId);
    }
}
