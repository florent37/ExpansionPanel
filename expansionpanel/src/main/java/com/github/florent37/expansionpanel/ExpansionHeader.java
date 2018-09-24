package com.github.florent37.expansionpanel;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
    private int headerRotationExpanded = 270;
    private int headerRotationCollapsed = 90;
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
                setHeaderRotationExpanded(a.getInt(R.styleable.ExpansionHeader_expansion_headerIndicatorRotationExpanded, headerRotationExpanded));
                setHeaderRotationCollapsed(a.getInt(R.styleable.ExpansionHeader_expansion_headerIndicatorRotationCollapsed, headerRotationCollapsed));
                setHeaderIndicatorId(a.getResourceId(R.styleable.ExpansionHeader_expansion_headerIndicator, headerIndicatorId));
                setExpansionLayoutId(a.getResourceId(R.styleable.ExpansionHeader_expansion_layout, expansionLayoutId));
                setToggleOnClick(a.getBoolean(R.styleable.ExpansionHeader_expansion_toggleOnClick, toggleOnClick));
                a.recycle();
            }
        }
    }

    public void setHeaderRotationExpanded(int headerRotationExpanded) {
        this.headerRotationExpanded = headerRotationExpanded;
    }

    public void setHeaderRotationCollapsed(int headerRotationCollapsed) {
        this.headerRotationCollapsed = headerRotationCollapsed;
    }

    public boolean isToggleOnClick() {
        return toggleOnClick;
    }

    public void setToggleOnClick(boolean toggleOnClick) {
        this.toggleOnClick = toggleOnClick;
    }

    public void setHeaderIndicatorId(int headerIndicatorId) {
        this.headerIndicatorId = headerIndicatorId;
        if (headerIndicatorId != 0) {
            headerIndicator = findViewById(headerIndicatorId);
            setExpansionHeaderIndicator(headerIndicator);
        }
    }

    public void setExpansionHeaderIndicator(@Nullable View headerIndicator) {
        this.headerIndicator = headerIndicator;

        //if not, the view will clip when rotate
        if (headerIndicator != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            headerIndicator.setLayerType(LAYER_TYPE_SOFTWARE, null);
        }

        setup();
    }

    public void setExpansionLayout(@Nullable ExpansionLayout expansionLayout) {
        this.expansionLayout = expansionLayout;
        setup();
    }

    public void setExpansionLayoutId(int expansionLayoutId) {
        this.expansionLayoutId = expansionLayoutId;

        if (expansionLayoutId != 0) {
            final ViewParent parent = getParent();
            if (parent instanceof ViewGroup) {
                final View view = ((ViewGroup) parent).findViewById(expansionLayoutId);
                if (view instanceof ExpansionLayout) {
                    setExpansionLayout(((ExpansionLayout) view));
                }
            }
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        setHeaderIndicatorId(this.headerIndicatorId); //setup or update
        setExpansionLayoutId(this.expansionLayoutId); //setup or update
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
                        expansionLayout.toggle(true);
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
        setSelected(willExpand);
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

    public boolean isExpanded() {
        return expansionLayout != null && expansionLayout.isExpanded();
    }

    public void addListener(ExpansionLayout.Listener listener) {
        if (expansionLayout != null) {
            expansionLayout.addListener(listener);
        }
    }

    public void removeListener(ExpansionLayout.Listener listener) {
        if (expansionLayout != null) {
            expansionLayout.removeListener(listener);
        }
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        final Bundle savedInstance = new Bundle();
        savedInstance.putParcelable("super", super.onSaveInstanceState());

        savedInstance.putInt("headerIndicatorId", headerIndicatorId);
        savedInstance.putInt("expansionLayoutId", expansionLayoutId);
        savedInstance.putBoolean("toggleOnClick", toggleOnClick);
        savedInstance.putInt("headerRotationExpanded", headerRotationExpanded);
        savedInstance.putInt("headerRotationCollapsed", headerRotationCollapsed);

        return savedInstance;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            final Bundle savedInstance = (Bundle) state;

            headerIndicatorId = savedInstance.getInt("headerIndicatorId");
            expansionLayoutId = savedInstance.getInt("expansionLayoutId");
            setToggleOnClick(savedInstance.getBoolean("toggleOnClick"));
            setHeaderRotationExpanded(savedInstance.getInt("headerRotationExpanded"));
            setHeaderRotationCollapsed(savedInstance.getInt("headerRotationCollapsed"));
            //setup(); will wait to onAttachToWindow

            expansionLayoutInitialised = false;

            super.onRestoreInstanceState(savedInstance.getParcelable("super"));
        } else {
            super.onRestoreInstanceState(state);
        }
    }

    @Nullable
    public View getHeaderIndicator() {
        return headerIndicator;
    }
}
