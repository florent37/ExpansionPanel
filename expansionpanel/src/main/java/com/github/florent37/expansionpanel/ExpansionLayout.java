package com.github.florent37.expansionpanel;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.List;

public class ExpansionLayout extends NestedScrollView {

    private final List<IndicatorListener> indicatorListeners = new ArrayList<>();
    private final List<Listener> listeners = new ArrayList<>();
    private boolean expanded = false;
    private Animator animator;

    public ExpansionLayout(Context context) {
        super(context);
    }

    public ExpansionLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpansionLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        if (attrs != null) {
            final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ExpansionLayout);
            if (a != null) {
                expanded = a.getBoolean(R.styleable.ExpansionLayout_expansion_expanded, expanded);
                a.recycle();
            }
        }
    }


    public void addListener(Listener listener) {
        if (listener != null && !listeners.contains(listener))
            listeners.add(listener);
    }

    public void removeListener(Listener listener){
        if(listener != null){
            listeners.remove(listener);
        }
    }

    public void addIndicatorListener(IndicatorListener listener) {
        if (listener != null && !indicatorListeners.contains(listener))
            indicatorListeners.add(listener);
    }

    public void removeIndicatorListener(IndicatorListener listener){
        if(listener != null){
            indicatorListeners.remove(listener);
        }
    }

    @Override
    public void addView(View child) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ExpansionLayout can host only one direct child");
        }

        super.addView(child);
    }

    @Override
    public void addView(View child, int index) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ExpansionLayout can host only one direct child");
        }

        super.addView(child, index);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ExpansionLayout can host only one direct child");
        }

        super.addView(child, params);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ExpansionLayout can host only one direct child");
        }

        super.addView(child, index, params);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        requestDisallowInterceptTouchEvent(true);

        if (getChildCount() != 0) {
            final View childView = getChildAt(0);
            childView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    childView.getViewTreeObserver().removeOnPreDrawListener(this);

                    //now we have a size
                    if (expanded) {
                        expand();
                    }

                    childView.addOnLayoutChangeListener(new OnLayoutChangeListener() {
                        @Override
                        public void onLayoutChange(View view, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                            if (expanded && animator == null) {
                                final int height = bottom - top;
                                post(new Runnable() {
                                    @Override
                                    public void run() {
                                        setHeight(height);

                                    }
                                });
                            }
                        }
                    });

                    return false;
                }
            });
        }

        if(!expanded){
            setHeight(0f);
        }
    }

    public void collapse() {
        if(!isEnabled()){
            return;
        }

        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(1f * getHeight(), 0f);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                setHeight((Float) valueAnimator.getAnimatedValue());
            }
        });
        pingIndicatorListeners();
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                expanded = false;
                animator = null;
                pingListeners();
            }
        });
        animator = valueAnimator;
        valueAnimator.start();
    }

    private void pingIndicatorListeners() {
        for (IndicatorListener indicatorListener : indicatorListeners) {
            if (indicatorListener != null) {
                indicatorListener.onStartedExpand(this, !expanded);
            }
        }
    }

    private void pingListeners() {
        for (Listener listener : listeners) {
            if (listener != null) {
                listener.onExpansionChanged(this, expanded);
            }
        }
    }

    public void expand() {
        if(!isEnabled()){
            return;
        }

        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, getChildAt(0).getHeight());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                setHeight((Float) valueAnimator.getAnimatedValue());
            }
        });
        pingIndicatorListeners();
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                expanded = true;
                animator = null;
                pingListeners();
            }
        });
        animator = valueAnimator;
        valueAnimator.start();
    }

    private void setHeight(float height) {
        final ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = (int) height;
        setLayoutParams(layoutParams);
    }

    public void toggle() {
        if (expanded) {
            collapse();
        } else {
            expand();
        }
    }

    public boolean isExpanded() {
        return expanded;
    }

    public interface Listener {
        void onExpansionChanged(ExpansionLayout expansionLayout, boolean expanded);
    }

    public interface IndicatorListener {
        void onStartedExpand(ExpansionLayout expansionLayout, boolean willExpand);
    }
}
