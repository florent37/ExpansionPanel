package com.github.florent37.expansionpanel;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class ExpansionHeader extends FrameLayout {

    int headerIndicatorId = 0;
    int expansionLayoutId = 0;
    boolean toggleOnClick = true;
    @Nullable
    View headerIndicator;
    @Nullable
    ExpansionLayout expansionLayout;
    @Nullable
    Animator indicatorAnimator;
    private int headerRotationExpanded = 90;
    private int headerRotationCollapsed = 0;
    private boolean expansionLayoutInitialised = false;

    public ExpansionHeader(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public ExpansionHeader(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ExpansionHeader(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        if (attrs != null) {
            final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ExpansionHeader);
            if (a != null) {
                headerRotationExpanded = a.getInt(R.styleable.ExpansionHeader_expansion_headerRorationExpanded, headerRotationExpanded);
                headerRotationCollapsed = a.getInt(R.styleable.ExpansionHeader_expansion_headerRorationCollapsed, headerRotationCollapsed);
                headerIndicatorId = a.getResourceId(R.styleable.ExpansionHeader_expansion_headerIndicator, headerIndicatorId);
                expansionLayoutId = a.getResourceId(R.styleable.ExpansionHeader_expansion_layout, expansionLayoutId);
                toggleOnClick = a.getBoolean(R.styleable.ExpansionHeader_expansion_toggleOnClick, toggleOnClick);
                a.recycle();
            }
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (headerIndicatorId != 0) {
            headerIndicator = findViewById(headerIndicatorId);
        }
        if (expansionLayoutId != 0) {
            final ViewParent parent = getParent();
            if (parent instanceof ViewGroup) {
                expansionLayout = ((ViewGroup) parent).findViewById(expansionLayoutId);
            }
        }

        setup();
    }

    private void setup() {
        if (expansionLayout != null && !expansionLayoutInitialised) {
            expansionLayout.addIndicatorListener(new ExpansionLayout.IndicatorListener() {
                @Override
                public void onStartedExpand(ExpansionLayout expansionLayout, boolean willExpand) {
                    onExpansionModifyView(willExpand);
                }
            });

            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (toggleOnClick) {
                        expansionLayout.toggle();
                    }
                }
            });

            initialiseView(expansionLayout.isExpanded());
            expansionLayoutInitialised = true;
        }
    }

    //can be overriden
    protected void initialiseView(boolean isExpanded) {
        if (headerIndicator != null) {
            headerIndicator.setRotation(isExpanded ? headerRotationExpanded : headerRotationCollapsed);
        }
    }

    //can be overriden
    protected void onExpansionModifyView(boolean willExpand) {
        if (headerIndicator != null) {
            if (indicatorAnimator != null) {
                indicatorAnimator.cancel();
            }
            if (willExpand) {
                indicatorAnimator = ObjectAnimator.ofFloat(headerIndicator, View.ROTATION, headerRotationExpanded);
            } else {
                indicatorAnimator = ObjectAnimator.ofFloat(headerIndicator, View.ROTATION, headerRotationCollapsed);
            }

            indicatorAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation, boolean isReverse) {
                    indicatorAnimator = null;
                }
            });

            if (indicatorAnimator != null) {
                indicatorAnimator.start();
            }
        }
    }

    public void setExpansionHeaderIndicator(ImageView headerIndicator) {
        this.headerIndicator = headerIndicator;
        setup();
    }

    public void setExpansionLayout(ExpansionLayout expansionLayout) {
        this.expansionLayout = expansionLayout;
        setup();
    }

}