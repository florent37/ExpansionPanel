# ExpansionPanel
Expansion panels contain creation flows and allow lightweight editing of an element.

*AndroidX Ready*

Based on `Expansion Panels` described on Material Design Components 
https://material.io/archive/guidelines/components/expansion-panels.html#

[![screen](https://raw.githubusercontent.com/florent37/ExpansionPanel/master/medias/material-components-expansion-panels.png)](https://www.github.com/florent37/ExpansionPanel)

<a href="https://goo.gl/WXW8Dc">
  <img alt="Android app on Google Play" src="https://developer.android.com/images/brand/en_app_rgb_wo_45.png" />
</a>

[![screen](https://raw.githubusercontent.com/florent37/ExpansionPanel/master/medias/video1.gif)](https://www.github.com/florent37/ExpansionPanel)

# Download

<a href='https://ko-fi.com/A160LCC' target='_blank'><img height='36' style='border:0px;height:36px;' src='https://az743702.vo.msecnd.net/cdn/kofi1.png?v=0' border='0' alt='Buy Me a Coffee at ko-fi.com' /></a>

[ ![Download](https://api.bintray.com/packages/florent37/maven/expansionpanel/images/download.svg) ](https://bintray.com/florent37/maven/expansionpanel/_latestVersion)
```java
dependencies {
    implementation 'com.github.florent37:expansionpanel:1.2.4'
}
```

# Usage

```xml
<LinearLayout
    android:layout_height="wrap_content"
    android:layout_width="match_parent"
    android:orientation="vertical"
    >

    <com.github.florent37.expansionpanel.ExpansionHeader
        android:layout_height="wrap_content"
        android:layout_width="match_parent"
        app:expansion_headerIndicator="@id/headerIndicator"
        app:expansion_layout="@id/expansionLayout"
        app:expansion_toggleOnClick="true">

        <!-- HEADER -->

        ...
        <!-- HEADER INDICATOR -->
        <ImageView
               android:adjustViewBounds="true"
               android:id="@+id/headerIndicator"
               android:layout_gravity="center_vertical|right"
               android:layout_height="wrap_content"
               android:layout_marginLeft="16dp"
               android:layout_width="wrap_content"
               app:srcCompat="@drawable/ic_expansion_header_indicator_grey_24dp" />


    </com.github.florent37.expansionpanel.ExpansionHeader>

    <com.github.florent37.expansionpanel.ExpansionLayout
        android:id="@+id/expansionLayout"
        android:layout_height="wrap_content"
        android:layout_width="match_parent">

        <!-- CONTENT -->

    </com.github.florent37.expansionpanel.ExpansionLayout>
</LinearLayout>

```

# Header

1. Connect with his Expansion Layout : `expansion_layout` (they must have the same parent)
2. Define the indicator view with `expansion_headerIndicator` (the id of a view inside the header)
3. If you want to expand/close when the header is clicked, setup `expansion_toggleOnClick`
4. You can modify the indicator rotation with `expansion_headerIndicatorRotationExpanded` and `expansion_headerIndicatorRotationCollapsed`

# Layout

You can modify the default expansion of the label with `app:expansion_expanded="false"

Layout can be toggled programmatically with `.toggle()`

Use `.setEnable(true/false)` to enable/disable the expansion

# Listener

Just add a listener into ExpansionLayout (**not the header !**) to follow the expansion layout state

```java
ExpansionLayout expansionLayout = findViewById(...);
expansionLayout.addListener(new ExpansionLayout.Listener() {
    @Override
    public void onExpansionChanged(ExpansionLayout expansionLayout, boolean expanded) {

    }
});
```

# Open only one

[![screen](https://raw.githubusercontent.com/florent37/ExpansionPanel/master/medias/onlyone.gif)](https://www.github.com/florent37/ExpansionPanel)

You can setup multiple expansions layout to enable only 1 opened at time

```
final ExpansionLayoutCollection expansionLayoutCollection = new ExpansionLayoutCollection();
expansionLayoutCollection.add(ex1);
expansionLayoutCollection.add(ex2);

expansionLayoutCollection.openOnlyOne(true);
```

or direcly in xml with
- ExpansionsViewGroupLinearLayout
- ExpansionsViewGroupFrameLayout
- ExpansionsViewGroupRelativeLayout
- ExpansionsViewGroupConstraintLayout

```
<com.github.florent37.expansionpanel.viewgroup.ExpansionsViewGroupLinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                app:expansion_openOnlyOne="true"
                android:orientation="vertical">

                <!-- Expansions Header & Layouts -->

</com.github.florent37.expansionpanel.viewgroup.ExpansionsViewGroupLinearLayout>
```

# Horizontal

Simply use `HorizontalExpansionLayout` instead of `ExpansionLayout`

```
<com.github.florent37.expansionpanel.HorizontalExpansionLayout
        android:id="@+id/expansionLayout"
        android:layout_height="wrap_content"
        android:layout_width="match_parent">

        <!-- CONTENT -->

</com.github.florent37.expansionpanel.HorizontalExpansionLayout>
```

# RecyclerView

Sample: https://github.com/florent37/ExpansionPanel/blob/master/app/src/main/java/florent37/github/com/expansionpanel/ExpansionPanelSampleActivityRecycler.java

```java
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerHolder> {

    ...

    //add an ExpansionLayoutCollection to your recycler adapter
    private final ExpansionLayoutCollection expansionsCollection = new ExpansionLayoutCollection();


    @Override
    public void onBindViewHolder(MyRecyclerHolder holder, int position) {
        //bind your elements

        //just add the ExpansionLayout (with findViewById) to the expansionsCollection
        expansionsCollection.add(holder.getExpansionLayout());
    }
}
```

# Single Listener

Update gradle, add ability to have only one listener at time

For what? If you tried to debug ViewHolders with this layout, 
then you might notice that over time addListener continues to add a listener, 
which causes a lot of unnecessary listeners and, moreover, 
there can be many listeners to one layouts in different ViewHolders due to which it may cause logic breakdown

```kotlin
var isExpanded = false

expandableLayout.run {
    //expandableLayout.singleListener = true
    singleListener = true

    expandableLayout.addListener { expansionLayout, expanded ->
        isExpanded = expanded
    }

    //do not toggle (expand/collapse, etc) before add listener

    if (isExpanded)
        expand(false)
    else
        collapse(false)
}
```

# Credits

Author: Florent Champigny 

Blog : [http://www.tutos-android-france.com/](http://www.tutos-android-france.com/)

Fiches Plateau Moto : [https://www.fiches-plateau-moto.fr/](https://www.fiches-plateau-moto.fr/)

<a href="https://goo.gl/WXW8Dc">
  <img alt="Android app on Google Play" src="https://developer.android.com/images/brand/en_app_rgb_wo_45.png" />
</a>

<a href="https://plus.google.com/+florentchampigny">
  <img alt="Follow me on Google+"
       src="https://raw.githubusercontent.com/florent37/DaVinci/master/mobile/src/main/res/drawable-hdpi/gplus.png" />
</a>
<a href="https://twitter.com/florent_champ">
  <img alt="Follow me on Twitter"
       src="https://raw.githubusercontent.com/florent37/DaVinci/master/mobile/src/main/res/drawable-hdpi/twitter.png" />
</a>
<a href="https://www.linkedin.com/in/florentchampigny">
  <img alt="Follow me on LinkedIn"
       src="https://raw.githubusercontent.com/florent37/DaVinci/master/mobile/src/main/res/drawable-hdpi/linkedin.png" />
</a>


License
--------

    Copyright 2017 Florent37, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
