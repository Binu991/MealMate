package com.example.mealmate;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import com.example.mealmate.adapter.MenuAdapter;
import com.example.mealmate.menumanager.Menu;
import com.example.mealmate.menumanager.MenuDatabase;
import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    private ListView lvMenus;
    private MenuDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        lvMenus = findViewById(R.id.lvMenus);

        db = Room.databaseBuilder(
                        getApplicationContext(),
                        MenuDatabase.class,
                        "menu_database"
                ).allowMainThreadQueries()
                .build();

        List<Menu> allMenus = db.menuDao().getAllMenus();

        MenuAdapter menuAdapter = new MenuAdapter(this, allMenus);
        lvMenus.setAdapter(menuAdapter);

        lvMenus.setOnItemClickListener((parent, view, position, id) -> {
            Menu selectedMenu = allMenus.get(position);

            Intent intent = new Intent(Home.this, MenuDetails.class);
            intent.putExtra("menuName", selectedMenu.getMenuName());
            intent.putStringArrayListExtra("menuItems", new ArrayList<>(selectedMenu.getMenuItems()));
            intent.putExtra("imagePath", selectedMenu.getImagePath());
            startActivity(intent);
        });
    }
}
