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

package jp.s64.android.animatedtoolbar;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.animation.Interpolator;

import jp.s64.android.animatedtoolbar.util.AnimatedToolbarHelper;

public class AnimatedToolbar extends Toolbar
        implements
        IAnimatedToolbar {

    private final AnimatedToolbarHelper mHelper;

    public AnimatedToolbar(Context context) {
        this(context, null);
    }

    public AnimatedToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimatedToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHelper = AnimatedToolbarHelper.instantiate(this);
    }

    @Override
    public void show(boolean animate) {
        mHelper.show(animate);
    }

    @Override
    public void hide(boolean animate) {
        mHelper.hide(animate);
    }

    @Override
    public void setTitle(@StringRes int resId) {
        mHelper.setTitle(resId);
    }

    @Override
    public void setTitle(CharSequence title) {
        mHelper.setTitle(title);
    }

    @Override
    public void setTitleWithoutAnimation(CharSequence title) {
        super.setTitle(title);
    }

    @Override
    public void setTitleWithoutAnimation(@StringRes int resId) {
        super.setTitle(resId);
    }

    @Override
    public boolean isShowing() {
        return mHelper.isShowing();
    }

    @Override
    public void setVisibilityDurationResId(@Nullable @IntegerRes Integer resId) {
        mHelper.setVisibilityDurationResId(resId);
    }

    @Override
    public void setVisibilityDuration(@Nullable Integer duration) {
        mHelper.setVisibilityDuration(duration);
    }

    @Override
    public void setVisibilityInterpolator(@Nullable Interpolator interpolator) {
        mHelper.setVisibilityInterpolator(interpolator);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        AnimatedToolbarHelper.SavedState state = new AnimatedToolbarHelper.SavedState(superState);
        return mHelper.onSaveInstanceState(state);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable raw) {
        if (raw instanceof AnimatedToolbarHelper.SavedState) {
            AnimatedToolbarHelper.SavedState state = (AnimatedToolbarHelper.SavedState) raw;
            {
                super.onRestoreInstanceState(state.getSuperState());
            }
            {
                mHelper.onRestoreInstanceState(state);
            }
        }
    }

}
