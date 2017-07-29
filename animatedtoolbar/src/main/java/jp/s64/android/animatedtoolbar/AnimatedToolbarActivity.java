package jp.s64.android.animatedtoolbar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import jp.s64.android.animatedtoolbar.util.AnimatedToolbarActivityHelper;

public abstract class AnimatedToolbarActivity extends AppCompatActivity {

    private AnimatedToolbarActivityHelper<AnimatedToolbarActivity> mHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = AnimatedToolbarActivityHelper.onCreate(this);
    }

    @Nullable
    @Override
    public ActionBar getSupportActionBar() {
        return mHelper.getSupportActionBar(super.getSupportActionBar());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        {
            AnimatedToolbarActivityHelper.onDestroy(mHelper);
            mHelper = null;
        }
    }

}
