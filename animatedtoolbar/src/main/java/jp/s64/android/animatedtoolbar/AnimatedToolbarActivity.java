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
