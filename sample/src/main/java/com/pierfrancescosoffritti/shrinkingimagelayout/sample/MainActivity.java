package com.pierfrancescosoffritti.shrinkingimagelayout.sample;

import android.graphics.Typeface;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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

        RequestOptions options = new RequestOptions().centerCrop();

        Glide
                .with(this)
                .load("http://www.notizie.it/wp-content/uploads/2016/07/android.jpg")
                .apply(options)
                .into(shrinkingImageLayout.getImageView());
    }

    private List<String> getData() {
        List<String> data = new ArrayList<>();

        for(int i=0; i<10; i++)
            data.add("data " +i);

        return data;
    }
}
