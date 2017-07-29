package jp.s64.android.animatedtoolbar.util;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;

import jp.s64.android.animatedtoolbar.IAnimatedToolbar;

public class AnimatedToolbarHelper<V extends View>
        implements
        IAnimatedToolbar {

    @IntegerRes
    private static final int DEFAULT_DURATION_RES_ID = android.R.integer.config_mediumAnimTime;

    private static final Interpolator DEFAULT_INTERPOLATOR = new LinearOutSlowInInterpolator();

    private static final int VIEW_STATE_VISIBLE = View.VISIBLE;
    private static final int VIEW_STATE_HIDE = View.INVISIBLE;

    public static <V extends View> AnimatedToolbarHelper<V> instantiate(V self) {
        return new AnimatedToolbarHelper(self);
    }

    private final V self;

    @Nullable
    private Integer mRestoredHeight = null;

    private ValueAnimator mVisibilityAnimator;
    private int mAnimationDuration;
    private Interpolator mInterpolator;

    protected AnimatedToolbarHelper(final V self) {
        {
            this.self = self;
        }
        {
            setVisibilityDurationResId(null);
            setVisibilityInterpolator(null);
        }
    }

    @Override
    public void show(boolean animate) {
        switchVisibility(true, animate);
    }

    @Override
    public void hide(boolean animate) {
        switchVisibility(false, animate);
    }

    @Override
    public boolean isShowing() {
        return self.getVisibility() == VIEW_STATE_VISIBLE;
    }

    @Override
    public void setVisibilityDurationResId(@Nullable @IntegerRes Integer resId) {
        int id = resId != null ? resId : DEFAULT_DURATION_RES_ID;
        setVisibilityDuration(self.getResources().getInteger(id));
    }

    @Override
    public void setVisibilityDuration(@Nullable Integer duration) {
        if (duration == null) {
            setVisibilityDurationResId(null);
        } else {
            mAnimationDuration = duration;
        }
    }

    @Override
    public void setVisibilityInterpolator(@Nullable Interpolator interpolator) {
        mInterpolator = interpolator != null ? interpolator : DEFAULT_INTERPOLATOR;
    }

    protected void switchVisibility(boolean show, boolean animate) {
        if (mVisibilityAnimator != null) {
            mVisibilityAnimator.cancel();
            mVisibilityAnimator = null;
        }
        final int oldVisibility = self.getVisibility(),
                newVisibility = show ? VIEW_STATE_VISIBLE : VIEW_STATE_HIDE;

        if (oldVisibility != newVisibility) {
            if (animate) {
                switchVisibilityWithAnimation(newVisibility);
            } else {
                self.setVisibility(newVisibility);
            }
        }
    }

    protected void switchVisibilityWithAnimation(final int newVisibility) {
        final int oldMargin, height, newMargin;
        {
            oldMargin = getTopMargin();
            height = self.getHeight();
        }
        {
            int m = newVisibility == VIEW_STATE_HIDE ? oldMargin - height : oldMargin + height;
            if (newVisibility == VIEW_STATE_VISIBLE && mRestoredHeight != null) {
                newMargin = m + (mRestoredHeight - height);
            } else {
                newMargin = m;
            }
            mRestoredHeight = null;
        }
        final ValueAnimator anim = ValueAnimator.ofInt(
                oldMargin,
                newMargin
        );
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator anim) {
                setTopMargin((int) anim.getAnimatedValue());
            }
        });
        anim.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                if (newVisibility == VIEW_STATE_VISIBLE) {
                    self.setVisibility(newVisibility);
                }
                setTopMargin(oldMargin);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (newVisibility == VIEW_STATE_HIDE) {
                    self.setVisibility(newVisibility);
                }
                setTopMargin(newMargin);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                self.setVisibility(newVisibility);
                setTopMargin(newMargin);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

        });
        anim.setDuration(mAnimationDuration);
        anim.setInterpolator(mInterpolator);
        {
            mVisibilityAnimator = anim;
            mVisibilityAnimator.start();
        }
    }

    public Parcelable onSaveInstanceState(SavedState state) {
        {
            state.setVisibility(self.getVisibility());
            state.setTopMargin(getTopMargin());
            state.setHeight(self.getHeight());
        }
        return state;
    }

    public void onRestoreInstanceState(SavedState state) {
        {
            self.setVisibility(state.getVisibility());
            setTopMargin(state.getTopMargin());
        }
        {
            mRestoredHeight = state.getHeight();
        }
    }

    protected void setTopMargin(int topMargin) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) self.getLayoutParams();
        params.topMargin = topMargin;
        self.setLayoutParams(params);
        self.requestLayout();
    }

    protected int getTopMargin() {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) self.getLayoutParams();
        return params.topMargin;
    }

    public static class SavedState extends AbsSavedState {

        public static final Creator CREATOR = new Creator() {

            @Override
            public Object createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            @Override
            public Object[] newArray(int size) {
                return new SavedState[size];
            }

        };

        private int visibility;
        private int topMargin;
        private int height;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            {
                out.writeInt(getVisibility());
                out.writeInt(getTopMargin());
                out.writeInt(getHeight());
            }
        }

        public SavedState(Parcel source) {
            super(source, SavedState.class.getClassLoader());
            {
                setVisibility(source.readInt());
                setTopMargin(source.readInt());
                setHeight(source.readInt());
            }
        }

        public int getVisibility() {
            return visibility;
        }

        public void setVisibility(int visibility) {
            this.visibility = visibility;
        }

        public int getTopMargin() {
            return topMargin;
        }

        public void setTopMargin(int topMargin) {
            this.topMargin = topMargin;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

    }

}
