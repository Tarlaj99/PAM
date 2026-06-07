package com.example.listviewratingbar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);

        // Przygotowanie danych (obrazek i początkowa ocena)
        List<Item> items = new ArrayList<>();
        items.add(new Item(android.R.drawable.ic_menu_gallery, 0));
        items.add(new Item(android.R.drawable.ic_menu_camera, 0));
        items.add(new Item(android.R.drawable.ic_menu_slideshow, 0));
        items.add(new Item(android.R.drawable.ic_menu_compass, 0));

        listView.setAdapter(new ItemAdapter(items));
    }

    // Klasa przechowująca stan elementu
    static class Item {
        int imageRes;
        float rating;
        Item(int imageRes, float rating) {
            this.imageRes = imageRes;
            this.rating = rating;
        }
    }

    class ItemAdapter extends BaseAdapter {
        private List<Item> items;

        ItemAdapter(List<Item> items) { this.items = items; }

        @Override
        public int getCount() { return items.size(); }
        @Override
        public Object getItem(int i) { return items.get(i); }
        @Override
        public long getItemId(int i) { return i; }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item, parent, false);
            }

            Item item = items.get(position);
            ImageView img = convertView.findViewById(R.id.imageView);
            RatingBar rb = convertView.findViewById(R.id.ratingBar);

            img.setImageResource(item.imageRes);

            // Zapobieganie nadpisywaniu ocen podczas przewijania (Recycling)
            rb.setOnRatingBarChangeListener(null);
            rb.setRating(item.rating);

            rb.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
                if (fromUser) {
                    item.rating = rating; // Zapamiętanie oceny w obiekcie
                }
            });

            return convertView;
        }
    }
}