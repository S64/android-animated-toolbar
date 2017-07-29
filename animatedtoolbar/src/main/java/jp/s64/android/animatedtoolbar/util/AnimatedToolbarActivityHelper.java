package jp.s64.android.animatedtoolbar.util;

import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ToolbarWidgetWrapper;

import java.lang.reflect.Field;

import jp.s64.android.animatedtoolbar.IAnimatedToolbar;

public class AnimatedToolbarActivityHelper<A extends AppCompatActivity> {

    private static final String TBAB_CLASS_NAME = "android.support.v7.app.ToolbarActionBar";
    private static final String TBAB_FIELD_TBWWR = "mDecorToolbar";

    private static final String TBWWR_FIELD_TOOLBAR = "mToolbar";

    public static <A extends AppCompatActivity> AnimatedToolbarActivityHelper<A> onCreate(A self) {
        return new AnimatedToolbarActivityHelper<>(self);
    }

    private final Class TBAB_CLASS;
    private final A self;

    protected AnimatedToolbarActivityHelper(A self) {
        try {
            TBAB_CLASS = self.getClassLoader().loadClass(TBAB_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            throw new AnimatedToolbarActivityHelperException(e);
        }
        this.self = self;
    }

    @Nullable
    public ActionBar getSupportActionBar(@Nullable ActionBar returned) {
        if (returned != null && TBAB_CLASS.isInstance(returned)) {
            return TbabWrapper.wrap(TBAB_CLASS, returned);
        }
        return returned;
    }

    public static <A extends AppCompatActivity> void onDestroy(AnimatedToolbarActivityHelper<A> helper) {
        helper.onDestroy();
    }

    public void onDestroy() {
        // no-op
    }

    protected static class TbabWrapper<T extends ActionBar> extends ActionBarWrapper {

        public static <T extends ActionBar> TbabWrapper<T> wrap(Class clazz, T tbab) {
            return new TbabWrapper<>(clazz, tbab);
        }

        private final Class clazz;
        private final T tbab;

        protected TbabWrapper(Class clazz, T tbab) {
            super(tbab);
            this.clazz = clazz;
            this.tbab = tbab;
        }

        @Override
        public void show() {
            Toolbar toolbar = getToolbar(getTbwwr());
            if (toolbar instanceof IAnimatedToolbar) {
                ((IAnimatedToolbar) toolbar).show(true);
            } else {
                super.show();
            }
        }

        @Override
        public void hide() {
            Toolbar toolbar = getToolbar(getTbwwr());
            if (toolbar instanceof IAnimatedToolbar) {
                ((IAnimatedToolbar) toolbar).hide(true);
            } else {
                super.hide();
            }
        }

        @Override
        public boolean isShowing() {
            Toolbar toolbar = getToolbar(getTbwwr());
            if (toolbar instanceof IAnimatedToolbar) {
                return ((IAnimatedToolbar) toolbar).isShowing();
            } else {
                return super.isShowing();
            }
        }

        protected ToolbarWidgetWrapper getTbwwr() {
            Field f;
            try {
                f = clazz.getDeclaredField(TBAB_FIELD_TBWWR);
            } catch (NoSuchFieldException e) {
                throw new AnimatedToolbarActivityHelperException(e);
            }
            try {
                f.setAccessible(true);
                return (ToolbarWidgetWrapper) f.get(tbab);
            } catch (IllegalAccessException e) {
                throw new AnimatedToolbarActivityHelperException(e);
            } finally {
                f.setAccessible(false);
            }
        }

        protected static Toolbar getToolbar(ToolbarWidgetWrapper tbwwr) {
            Field f;
            try {
                f = ToolbarWidgetWrapper.class.getDeclaredField(TBWWR_FIELD_TOOLBAR);
            } catch (NoSuchFieldException e) {
                throw new AnimatedToolbarActivityHelperException(e);
            }
            try {
                f.setAccessible(true);
                return (Toolbar) f.get(tbwwr);
            } catch (IllegalAccessException e) {
                throw new AnimatedToolbarActivityHelperException(e);
            } finally {
                f.setAccessible(false);
            }
        }

    }

    public static class AnimatedToolbarActivityHelperException extends RuntimeException {

        public AnimatedToolbarActivityHelperException(Throwable throwable) {
            super(throwable);
        }

    }

}
