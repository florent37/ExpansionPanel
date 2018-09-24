package com.github.florent37.expansionpanel;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class HorizontalExpansionLayout extends HorizontalScrollView {

    private final List<IndicatorListener> indicatorListeners = new ArrayList<>();
    private final List<Listener> listeners = new ArrayList<>();
    private boolean expanded = false;
    private Animator animator;

    public HorizontalExpansionLayout(Context context) {
        super(context);
        init(context, null);
    }

    public HorizontalExpansionLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public HorizontalExpansionLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        requestDisallowInterceptTouchEvent(true);

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

    public void removeListener(Listener listener) {
        if (listener != null) {
            listeners.remove(listener);
        }
    }

    public void addIndicatorListener(IndicatorListener listener) {
        if (listener != null && !indicatorListeners.contains(listener))
            indicatorListeners.add(listener);
    }

    public void removeIndicatorListener(IndicatorListener listener) {
        if (listener != null) {
            indicatorListeners.remove(listener);
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!expanded) {
            setWidth(0f);
        }
    }

    @Override
    public void addView(View child) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ExpansionLayout can host only one direct child");
        }

        super.addView(child);
        onViewAdded();
    }

    @Override
    public void addView(View child, int index) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ExpansionLayout can host only one direct child");
        }

        super.addView(child, index);
        onViewAdded();
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ExpansionLayout can host only one direct child");
        }

        super.addView(child, params);
        onViewAdded();
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ExpansionLayout can host only one direct child");
        }

        super.addView(child, index, params);
        onViewAdded();
    }

    private void onViewAdded(){
        if (getChildCount() != 0) {
            final View childView = getChildAt(0);
            childView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    childView.getViewTreeObserver().removeOnPreDrawListener(this);

                    //now we have a size
                    if (expanded) {
                        expand(false);
                    }

                    childView.addOnLayoutChangeListener(new OnLayoutChangeListener() {
                        @Override
                        public void onLayoutChange(View view, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                            if (expanded && animator == null) {
                                final int width = right - left;
                                post(new Runnable() {
                                    @Override
                                    public void run() {
                                        setWidth(width);

                                    }
                                });
                            }
                        }
                    });

                    return false;
                }
            });
        }
    }

    public void collapse(boolean animated) {
        if (!isEnabled() || !expanded) {
            return;
        }

        pingIndicatorListeners(false);
        if (animated) {
            final ValueAnimator valueAnimator = ValueAnimator.ofFloat(1f * getWidth(), 0f);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    setWidth((Float) valueAnimator.getAnimatedValue());
                }
            });
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    animator = null;
                    pingListeners();
                }
            });
            expanded = false;
            animator = valueAnimator;
            valueAnimator.start();
        } else {
            setWidth(0f);
            expanded = false;
            pingListeners();
        }
    }

    private void pingIndicatorListeners(boolean willBeExpanded) {
        for (IndicatorListener indicatorListener : indicatorListeners) {
            if (indicatorListener != null) {
                indicatorListener.onStartedExpand(this, willBeExpanded);
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

    public void expand(boolean animated) {
        if (!isEnabled() || expanded) {
            return;
        }

        pingIndicatorListeners(true);
        if (animated) {
            final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, getChildAt(0).getWidth());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    setWidth((Float) valueAnimator.getAnimatedValue());
                }
            });
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    animator = null;
                    pingListeners();
                }
            });
            expanded = true;
            animator = valueAnimator;
            valueAnimator.start();
        } else {
            setWidth(getChildAt(0).getWidth());
            expanded = true;
            pingListeners();
        }
    }

    private void setWidth(float width) {
        final ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (layoutParams != null) {
            layoutParams.width = (int) width;
            setLayoutParams(layoutParams);
        }
    }

    public void toggle(boolean animated) {
        if (expanded) {
            collapse(animated);
        } else {
            expand(animated);
        }
    }


    public boolean isExpanded() {
        return expanded;
    }

    public interface Listener {
        void onExpansionChanged(HorizontalExpansionLayout expansionLayout, boolean expanded);
    }

    public interface IndicatorListener {
        void onStartedExpand(HorizontalExpansionLayout expansionLayout, boolean willExpand);
    }
}
