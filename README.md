# ShrinkingImageLayout
[![](https://jitpack.io/v/PierfrancescoSoffritti/ShrinkingImageLayout.svg)](https://jitpack.io/#PierfrancescoSoffritti/ShrinkingImageLayout)

Android layout with an header image sensible to scroll and touch events. Original code taken from [Plaid](https://github.com/nickbutcher/plaid) and made into a backward compatible layout, sensible to RecyclerView scrolls.

<img height="450" src="https://github.com/PierfrancescoSoffritti/ShrinkingImageLayout/blob/master/pics/Animation.gif" />

## Download
Add this to you project-level `build.gradle`:
```
allprojects {
  repositories {
    ...
    maven { url "https://jitpack.io" }
  }
}
```
Add this to your module-level `build.gradle`:
```
dependencies {
  compile 'com.github.PierfrancescoSoffritti:ShrinkingImageLayout:0.3'
}
```

## Usage
`ShrinkingImageLayout` is a simple `FrameLayout` with some extra functionalities.

Add the ViewGroup to your layout file:
```
<com.pierfrancescosoffritti.shrinkingimagelayout.ShrinkingImageLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/shrinking_image_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```
Then set it up by calling this method:
```
shrinkingImageLayout.setupRecyclerView(new RecyclerView(this), new LinearLayoutManager(this), new Adapter(getData()));
```
the adapter <b>must implement</b> `HeaderRecyclerViewAdapter`

### Extra
To add an header to the RecyclerView call this method:
```
shrinkingImageLayout.addCustomHeader(header);
```
Where `header` can be any `View` object.

<br/>

Call this method to get a reference to the `ImageView`
```
shrinkingImageLayout.getImageView();
```

On API 21+ the `ImageView` automatically handles its Z animation when scrolled and touched.
