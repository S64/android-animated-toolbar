package jp.s64.android.animatedtoolbar;

import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.view.animation.Interpolator;

public interface IAnimatedToolbar {

    void show(boolean animate);

    void hide(boolean animate);

    boolean isShowing();

    void setVisibilityDurationResId(@Nullable @IntegerRes Integer resId);

    void setVisibilityDuration(@Nullable Integer duration);

    void setVisibilityInterpolator(@Nullable Interpolator interpolator);

}
