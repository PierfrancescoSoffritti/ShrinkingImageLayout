package com.pierfrancescosoffritti.shrinkingimagelayout;

import android.animation.AnimatorInflater;
import android.animation.StateListAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pierfrancescosoffritti.shrinkingimagelayout.parallaxScrimageView.ParallaxScrimageView;

public class ShrinkingImageLayout extends FrameLayout {

    private ParallaxScrimageView parallaxScrimageView;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private HeaderRecyclerViewAdapter adapter;
    private LinearLayout header;

    private int imageMaximumHeight;
    private int imageMinimumHeight;

    private RecyclerView.OnScrollListener onScollListener;

    public ShrinkingImageLayout(Context context) {
        this(context, null, 0);
    }

    public ShrinkingImageLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShrinkingImageLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        imageMaximumHeight = (int) (getResources().getDimensionPixelSize(R.dimen._168dp)*1.5);
        imageMinimumHeight = getResources().getDimensionPixelSize(R.dimen._168dp);

        initParallaxScrimView();
        initHeader();
    }

    public void setImageMaximumHeight(int maximumHeight) {
        imageMaximumHeight = maximumHeight;

        View space = header.findViewById(R.id.space);
        ViewGroup.LayoutParams spaceParams = space.getLayoutParams();
        spaceParams.height = maximumHeight;
        space.setLayoutParams(spaceParams);

        ViewGroup.LayoutParams parallaxParams = parallaxScrimageView.getLayoutParams();
        parallaxParams.height = maximumHeight;
        parallaxScrimageView.setLayoutParams(parallaxParams);
    }

    public void setImageMinimumHeight(int minimumHeight) {
        this.imageMinimumHeight = minimumHeight;
        parallaxScrimageView.setMinimumHeight(minimumHeight);
    }

    private void initHeader() {
        header = new LinearLayout(getContext());
        header.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        header.setOrientation(LinearLayout.VERTICAL);

        View space = new View(getContext());
        space.setId(R.id.space);
        space.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, imageMaximumHeight));
        header.addView(space);
    }

    private void initParallaxScrimView() {
        parallaxScrimageView = new ParallaxScrimageView(getContext());

        parallaxScrimageView.setId(R.id.parallaxImageView);
        parallaxScrimageView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, imageMaximumHeight));
        parallaxScrimageView.setMinimumHeight(imageMinimumHeight);

        int[] attrs = new int[] { R.attr.selectableItemBackground };
        TypedArray ta = getContext().obtainStyledAttributes(attrs);
        Drawable selectableItemBackground = ta.getDrawable(0);
        ta.recycle();

        parallaxScrimageView.setClickable(true);
        parallaxScrimageView.setForeground(selectableItemBackground);

        parallaxScrimageView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.light_grey));
        parallaxScrimageView.setScrimColor(ContextCompat.getColor(getContext(), R.color.scrim));

        parallaxScrimageView.setScrimAlpha(0f);
        parallaxScrimageView.setMaxSrimAlpha(0.4f);
        parallaxScrimageView.setParallaxFactor(-0.5f);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StateListAnimator stateListAnimator = AnimatorInflater.loadStateListAnimator(getContext(), R.animator.app_bar_pin);
            parallaxScrimageView.setStateListAnimator(stateListAnimator);
        }
    }

    public void addCustomHeader(@NonNull View header) {
        this.header.addView(header, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void setupRecyclerView(@NonNull RecyclerView recyclerView, @NonNull GridLayoutManager layoutManager, @NonNull HeaderRecyclerViewAdapter adapter) {
        this.recyclerView = recyclerView;
        this.adapter = adapter;
        this.layoutManager = layoutManager;
        adapter.setHeader(this.header);

        initScrollListener();
        initRecyclerView();
    }

    private void initScrollListener() {
        onScollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                if (firstVisibleItemPosition == 0 && recyclerView.getChildAt(0) != null) {
                    final int listScroll = recyclerView.getChildAt(0).getTop();
                    parallaxScrimageView.setOffset(listScroll);
                }
            }
        };
    }

    private void initRecyclerView() {
        if(recyclerView.getParent() != null && recyclerView.getParent() != this)
            throw new IllegalArgumentException("recyclerView parent must be ShrinkingImageLayout or none");

        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setAdapter(adapter);

        this.recyclerView.addOnScrollListener(onScollListener);

        if(recyclerView.getParent() == null)
            this.addView(recyclerView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        if(parallaxScrimageView.getParent() == null)
            this.addView(parallaxScrimageView);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if(this.recyclerView == null)
            throw new IllegalStateException("recyclerView == null. You must call 'addRecyclerView'.");

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setImageResource(@DrawableRes int id) {
        parallaxScrimageView.setImageResource(id);
    }

    public void setImageDrawable(Drawable drawable) {
        parallaxScrimageView.setImageDrawable(drawable);
    }

    public void setImageBitmap(Bitmap image) {
        parallaxScrimageView.setImageBitmap(image);
    }

    public ImageView getImageView() {
        return parallaxScrimageView;
    }
}
