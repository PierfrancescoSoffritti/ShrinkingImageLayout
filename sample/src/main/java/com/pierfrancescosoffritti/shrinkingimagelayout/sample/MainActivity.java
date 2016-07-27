package com.pierfrancescosoffritti.shrinkingimagelayout.sample;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pierfrancescosoffritti.shrinkingimagelayout.ShrinkingImageLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ShrinkingImageLayout shrinkingImageLayout = (ShrinkingImageLayout) findViewById(R.id.shrinking_image_layout);

        TextView header = new TextView(this);
        header.setText("Custom Header");
        header.setTypeface(Typeface.DEFAULT_BOLD);
        int padding = (int) getResources().getDimension(R.dimen.activity_horizontal_margin);
        header.setPadding(padding, padding, padding, padding);
        header.setTextColor(ContextCompat.getColor(this, android.R.color.black));

        shrinkingImageLayout.addCustomHeader(header);
        shrinkingImageLayout.setupRecyclerView(new RecyclerView(this), new LinearLayoutManager(this), new StringAdapter(getData()));

        Glide
                .with(this)
                .load("http://www.notizie.it/wp-content/uploads/2016/07/android.jpg")
                .centerCrop()
                .into(shrinkingImageLayout.getImageView());
    }

    private List<String> getData() {
        List<String> data = new ArrayList<>();

        for(int i=0; i<10; i++)
            data.add("data " +i);

        return data;
    }
}
