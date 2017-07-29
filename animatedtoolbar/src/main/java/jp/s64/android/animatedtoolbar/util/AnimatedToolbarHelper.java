/*
 * Copyright (C) 2017 Shuma Yoshioka
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.s64.android.animatedtoolbar.util;

import android.animation.Animator;
import android.animation.LayoutTransition;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.AnimRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.TextView;

import java.lang.reflect.Field;

import jp.s64.android.animatedtoolbar.IAnimatedToolbar;
import jp.s64.android.animatedtoolbar.R;

public class AnimatedToolbarHelper<V extends Toolbar & IAnimatedToolbar> {

    @IntegerRes
    private static final int DEFAULT_DURATION_RES_ID = android.R.integer.config_mediumAnimTime;

    @AnimRes
    private static final int DEFAULT_TITLE_SHOW_ANIM = R.anim.show_title;

    @AnimRes
    private static final int DEFAULT_TITLE_HIDE_ANIM = R.anim.hide_title;

    private static final Interpolator DEFAULT_INTERPOLATOR = new LinearOutSlowInInterpolator();

    private static final int VIEW_STATE_VISIBLE = View.VISIBLE;
    private static final int VIEW_STATE_HIDE = View.INVISIBLE;

    private static final String TOOLBAR_TITLE_TEXT_VIEW_FIELD = "mTitleTextView";
    private static final String TOOLBAR_MENU_VIEW_FIELD = "mMenuView";

    public static <V extends Toolbar & IAnimatedToolbar> AnimatedToolbarHelper<V> instantiate(V self) {
        return new AnimatedToolbarHelper(self);
    }

    private final V self;

    @Nullable
    private Integer mRestoredHeight = null;

    private ValueAnimator mVisibilityAnimator;
    private int mAnimationDuration;
    private Interpolator mInterpolator;

    private AnimationFactory mTitleShowAnimationFactory;
    private AnimationFactory mTitleHideAnimationFactory;

    protected AnimatedToolbarHelper(final V self) {
        {
            this.self = self;
        }
        {
            setVisibilityDurationResId(null);
            setVisibilityInterpolator(null);
        }
        {
            setTitleShowAnimationFactory(null);
            setTitleHideAnimationFactory(null);
        }
        {
            self.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    ActionMenuView mv = getMenuView();
                    if (mv != null) {
                        {
                            self.removeOnLayoutChangeListener(this);
                        }
                        LayoutTransition lt = new LayoutTransition();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                            lt.setAnimateParentHierarchy(true);
                        }
                        mv.setLayoutTransition(lt);
                    }
                }
            });
        }
    }

    public void show(boolean animate) {
        switchVisibility(true, animate);
    }

    public void hide(boolean animate) {
        switchVisibility(false, animate);
    }

    public boolean isShowing() {
        return self.getVisibility() == VIEW_STATE_VISIBLE;
    }

    public void setVisibilityDurationResId(@Nullable @IntegerRes Integer resId) {
        int id = resId != null ? resId : DEFAULT_DURATION_RES_ID;
        setVisibilityDuration(self.getResources().getInteger(id));
    }

    public void setVisibilityDuration(@Nullable Integer duration) {
        if (duration == null) {
            setVisibilityDurationResId(null);
        } else {
            mAnimationDuration = duration;
        }
    }

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

    public void setTitle(final CharSequence title) {
        final TextView view = getTitleTextView();
        if (view != null && view.getParent() != null) {
            final Animation show, hide;
            {
                hide = mTitleHideAnimationFactory.createNew(self.getContext());
                show = title.length() > 0 ? mTitleShowAnimationFactory.createNew(self.getContext()) : null;
            }
            if (show == null) {
                hide.setFillAfter(false);
            }
            hide.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    self.post(new Runnable() {
                        @Override
                        public void run() {
                            self.setTitleWithoutAnimation(title);
                        }
                    });
                    if (show != null) {
                        view.startAnimation(show);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            view.startAnimation(hide);
        } else {
            self.setTitleWithoutAnimation(title);
            if (view != null) {
                view.startAnimation(mTitleShowAnimationFactory.createNew(self.getContext()));
            }
        }
    }

    public void setTitle(@StringRes int resId) {
        setTitle(self.getContext().getString(resId));
    }

    public void setTitleShowAnimationFactory(@Nullable AnimationFactory factory) {
        mTitleShowAnimationFactory = factory != null ? factory : new AnimationFactory() {

            @Override
            public Animation createNew(Context context) {
                Animation ret = AnimationUtils.loadAnimation(context, DEFAULT_TITLE_SHOW_ANIM);
                ret.setInterpolator(DEFAULT_INTERPOLATOR);
                return ret;
            }

        };
    }

    public void setTitleHideAnimationFactory(@Nullable AnimationFactory factory) {
        mTitleHideAnimationFactory = factory != null ? factory : new AnimationFactory() {

            @Override
            public Animation createNew(Context context) {
                Animation ret = AnimationUtils.loadAnimation(context, DEFAULT_TITLE_HIDE_ANIM);
                ret.setInterpolator(DEFAULT_INTERPOLATOR);
                return ret;
            }

        };
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

    @Nullable
    protected TextView getTitleTextView() {
        Field f;
        try {
            f = Toolbar.class.getDeclaredField(TOOLBAR_TITLE_TEXT_VIEW_FIELD);
        } catch (NoSuchFieldException e) {
            throw new AnimatedToolbarHelperException(e);
        }
        try {
            f.setAccessible(true);
            Object ret = f.get(self);
            return ret != null ? (TextView) ret : null;
        } catch (IllegalAccessException e) {
            throw new AnimatedToolbarHelperException(e);
        } finally {
            f.setAccessible(false);
        }
    }

    @Nullable
    protected ActionMenuView getMenuView() {
        Field f;
        try {
            f = Toolbar.class.getDeclaredField(TOOLBAR_MENU_VIEW_FIELD);
        } catch (NoSuchFieldException e) {
            throw new AnimatedToolbarHelperException(e);
        }
        try {
            f.setAccessible(true);
            Object ret = f.get(self);
            return ret != null ? (ActionMenuView) ret : null;
        } catch (IllegalAccessException e) {
            throw new AnimatedToolbarHelperException(e);
        } finally {
            f.setAccessible(false);
        }
    }

    protected static class AnimatedToolbarHelperException extends RuntimeException {
        public AnimatedToolbarHelperException(Throwable throwable) {
            super(throwable);
        }
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

    public interface AnimationFactory {

        Animation createNew(Context context);

    }

}
