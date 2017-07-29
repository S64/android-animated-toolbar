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

package jp.s64.android.animatedtoolbar.example.activity;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

import jp.s64.android.animatedtoolbar.AnimatedToolbar;
import jp.s64.android.animatedtoolbar.AnimatedToolbarActivity;
import jp.s64.android.animatedtoolbar.example.R;

public class MainActivity extends AnimatedToolbarActivity
        implements
        CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private static final String STATE_MENU_ITEM_COUNT = "jp.s64.android.animatedtoolbar.example.activity.MainActivity.STATE_MENU_ITEM_COUNT";

    private Toolbar mToolbar;
    private ToggleButton mToggleButton;

    private EditText mTitleEditText;
    private Button mTitleUpdateButton;

    private Button mMenuAdd, mMenuRemove;

    private int mMenuItemCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        {
            mToolbar = (AnimatedToolbar) findViewById(R.id.toolbar);
            setSupportActionBar(mToolbar);
        }
        {
            mToggleButton = (ToggleButton) findViewById(R.id.toggle);
            mToggleButton.setOnCheckedChangeListener(this);
        }
        {
            mTitleEditText = (EditText) findViewById(R.id.title);
            mTitleUpdateButton = (Button) findViewById(R.id.update_title);
            mTitleUpdateButton.setOnClickListener(this);
        }
        {
            mMenuAdd = (Button) findViewById(R.id.menu_add);
            mMenuRemove = (Button) findViewById(R.id.menu_remove);
            mMenuAdd.setOnClickListener(this);
            mMenuRemove.setOnClickListener(this);
        }
        {
            mMenuItemCount = savedInstanceState != null ? savedInstanceState.getInt(STATE_MENU_ITEM_COUNT, 0) : 0;
            onMenuItemCountChanged();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            //mToolbar.show(true);
            getSupportActionBar().show();
        } else {
            //mToolbar.hide(true);
            getSupportActionBar().hide();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == mTitleUpdateButton) {
            setTitle(mTitleEditText.getText().toString());
        } else if (v == mMenuAdd) {
            mMenuItemCount++;
            onMenuItemCountChanged();
        } else if (v == mMenuRemove) {
            mMenuItemCount--;
            onMenuItemCountChanged();
        }
    }

    private void onMenuItemCountChanged() {
        boolean canAdd, canRemove;
        {
            canAdd = true;
            canRemove = mMenuItemCount > 0;
        }
        {
            mMenuAdd.setEnabled(canAdd);
            mMenuRemove.setEnabled(canRemove);
        }
        supportInvalidateOptionsMenu();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        {
            outState.putInt(STATE_MENU_ITEM_COUNT, mMenuItemCount);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // super.onCreateOptionsMenu(menu);

        for (int i = 0; i < mMenuItemCount; i++) {
            MenuItem itm = menu.add(Menu.NONE, i, Menu.NONE, String.valueOf(i));
            MenuItemCompat.setShowAsAction(itm, MenuItem.SHOW_AS_ACTION_ALWAYS);
        }

        return true;
    }

}
