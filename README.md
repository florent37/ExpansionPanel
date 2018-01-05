# ExpansionPanel
Expansion panels contain creation flows and allow lightweight editing of an element.

Based on `Expansion Panels` described on Material Design Components 
https://material.io/guidelines/components/expansion-panels.html#

[![screen](https://raw.githubusercontent.com/florent37/EspansionPanels/master/medias/components-expansion-panels.png)](https://www.github.com/florent37/EspansionPanels)

<a href="https://goo.gl/WXW8Dc">
  <img alt="Android app on Google Play" src="https://developer.android.com/images/brand/en_app_rgb_wo_45.png" />
</a>

INSERT GIF HERE

# Download

<a href='https://ko-fi.com/A160LCC' target='_blank'><img height='36' style='border:0px;height:36px;' src='https://az743702.vo.msecnd.net/cdn/kofi1.png?v=0' border='0' alt='Buy Me a Coffee at ko-fi.com' /></a>

[ ![Download](https://api.bintray.com/packages/florent37/maven/viewtooltip/images/download.svg) ](https://bintray.com/florent37/maven/viewtooltip/_latestVersion)
```java
dependencies {
    compile 'com.github.florent37:expansionpanel:1.0.0'
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
        <android.support.v7.widget.AppCompatImageView
               android:adjustViewBounds="true"
               android:id="@+id/headerIndicator"
               android:layout_alignParentRight="true"
               android:layout_centerVertical="true"
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

# Credits

Author: Florent Champigny [http://www.florentchampigny.com/](http://www.florentchampigny.com/)

Blog : [http://www.tutos-android-france.com/](http://www.www.tutos-android-france.com/)


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
