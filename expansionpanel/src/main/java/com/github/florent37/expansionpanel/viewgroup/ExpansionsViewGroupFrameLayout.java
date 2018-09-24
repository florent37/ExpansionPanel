package com.github.florent37.expansionpanel.viewgroup;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.florent37.expansionpanel.R;


public class ExpansionsViewGroupFrameLayout extends LinearLayout {

    private final ExpansionViewGroupManager expansionViewGroupManager = new ExpansionViewGroupManager(this);

    public ExpansionsViewGroupFrameLayout(Context context) {
        super(context);
        init(context, null);
    }

    public ExpansionsViewGroupFrameLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ExpansionsViewGroupFrameLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        if (attrs != null) {
            final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ExpansionsViewGroupFrameLayout);
            if (a != null) {
                expansionViewGroupManager.setOpenOnlyOne(a.getBoolean(R.styleable.ExpansionsViewGroupFrameLayout_expansion_openOnlyOne, false));
                a.recycle();
            }
        }
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        expansionViewGroupManager.onViewAdded();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        expansionViewGroupManager.onViewAdded();
    }

}
