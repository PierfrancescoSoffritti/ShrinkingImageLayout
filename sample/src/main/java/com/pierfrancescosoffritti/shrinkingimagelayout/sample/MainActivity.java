package com.pierfrancescosoffritti.shrinkingimagelayout.sample;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
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
        header.setText("customHeader");
        header.setTextColor(ContextCompat.getColor(this, android.R.color.black));

        shrinkingImageLayout.addCustomHeader(header);
        shrinkingImageLayout.setupRecyclerView(new RecyclerView(this), new GridLayoutManager(this, 1), new StringAdapter(getData()));

        Glide
                .with(this)
                .load("https://avatars1.githubusercontent.com/u/7457011?v=3&s=460")
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
