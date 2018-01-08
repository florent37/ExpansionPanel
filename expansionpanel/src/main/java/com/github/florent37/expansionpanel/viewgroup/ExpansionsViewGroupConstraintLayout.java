package com.github.florent37.expansionpanel.viewgroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.florent37.expansionpanel.R;


public class ExpansionsViewGroupConstraintLayout extends ConstraintLayout {

    private final ExpansionViewGroupManager expansionViewGroupManager = new ExpansionViewGroupManager(this);

    public ExpansionsViewGroupConstraintLayout(Context context) {
        super(context);
        init(context, null);
    }

    public ExpansionsViewGroupConstraintLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ExpansionsViewGroupConstraintLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        if (attrs != null) {
            final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ExpansionsViewGroupConstraintLayout);
            if (a != null) {
                expansionViewGroupManager.setOpenOnlyOne(a.getBoolean(R.styleable.ExpansionsViewGroupConstraintLayout_expansion_openOnlyOne, false));
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
