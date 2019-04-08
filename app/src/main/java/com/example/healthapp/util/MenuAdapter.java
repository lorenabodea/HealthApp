package com.example.healthapp.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.healthapp.R;
import com.example.healthapp.classes.Menu;


import java.util.List;

public class MenuAdapter  extends ArrayAdapter<Menu> {

    private Context context;
    private List<Menu> menus;
    private int resource;
    private LayoutInflater inflater;

    public MenuAdapter(@NonNull Context context, int resource, @NonNull List<Menu> objects, LayoutInflater inflater) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        this.menus = objects;
        this.inflater = inflater;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.lv_menus_row, null);



        TextView tvFood = itemView.findViewById(R.id.tv_lv_menu_food);
        TextView tvQuantity = itemView.findViewById(R.id.tv_lv_menu_quantity);

        Menu menu = menus.get(position);

        tvFood.setText(menu.getFood());
        //tvQuantity.setText(menu.getQuantity());

        return itemView;
    }
}
