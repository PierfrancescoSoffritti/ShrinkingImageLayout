package com.pierfrancescosoffritti.shrinkingimagelayout;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public abstract class HeaderRecyclerViewAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int HEADER = 0;

    private View header;

    public void setHeader(@NonNull View header) {
        this.header = header;
    }

    @Override
    public final int getItemViewType(int position) {
        if(position == 0)
            return HEADER;

        return getItemViewType_(position-1);
    }

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(header == null)
            throw new IllegalStateException("header == null");

        if(viewType == HEADER)
            return new HeaderViewHolder(header);

        return onCreateViewHolder_(parent, viewType);
    }

    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(viewHolder instanceof HeaderViewHolder)
            return;

        onBindViewHolder_((VH) viewHolder, position-1);
    }

    /**
     * use this method instead of {@link android.support.v7.widget.RecyclerView.Adapter#getItemViewType(int)}
     */
    public @IntRange(from = 1, to = Long.MAX_VALUE) int getItemViewType_(int position) {
        return 1;
    }

    public abstract VH onCreateViewHolder_(ViewGroup parent, int viewType);
    public abstract void onBindViewHolder_(VH viewHolder, int position);

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
}
