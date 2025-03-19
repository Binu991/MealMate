package com.example.mealmate.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mealmate.R;
import com.example.mealmate.menumanager.Menu;

import java.util.List;

public class MenuAdapter extends BaseAdapter {

    private final Context context;
    private final List<Menu> menuList;

    public MenuAdapter(Context context, List<Menu> menuList) {
        this.context = context;
        this.menuList = menuList;
    }

    @Override
    public int getCount() {
        return menuList.size();
    }

    @Override
    public Menu getItem(int position) {
        return menuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return menuList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.activity_item_menu, parent, false);
        }

        ImageView ivMenuImage = convertView.findViewById(R.id.ivMenuImage);
        TextView tvMenuName = convertView.findViewById(R.id.tvMenuName);
        Menu menu = getItem(position);
        tvMenuName.setText(menu.getMenuName());
        String imagePath = menu.getImagePath();
        if (imagePath != null && !imagePath.isEmpty()) {
            Uri imageUri = Uri.parse(imagePath);
            ivMenuImage.setImageURI(imageUri);


            ivMenuImage.setImageResource(R.mipmap.ic_launcher);
        }

        return convertView;
    }
}
