package com.pierfrancescosoffritti.shrinkingimagelayout.sample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pierfrancescosoffritti.shrinkingimagelayout.HeaderRecyclerViewAdapter;

import java.util.List;

/**
 * Created by  Pierfrancesco on 23/07/2016.
 */
public class StringAdapter extends HeaderRecyclerViewAdapter<StringAdapter.StringViewHolder> {

    private final List<String> data;

    public StringAdapter(List<String> data) {
        this.data = data;
    }

    @Override
    public StringViewHolder onCreateViewHolder_(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_item, parent, false);
        return new StringViewHolder(view);
    }

    @Override
    public void onBindViewHolder_(StringViewHolder viewHolder, int position) {
        viewHolder.text.setText(data.get(position));
    }

    @Override
    public int getItemCount_() {
        return data.size();
    }

    static class StringViewHolder extends RecyclerView.ViewHolder {
        final TextView text;
        public StringViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text_view);

        }
    }
}
