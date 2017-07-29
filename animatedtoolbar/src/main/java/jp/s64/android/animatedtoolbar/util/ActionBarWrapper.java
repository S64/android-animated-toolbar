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

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.view.ActionMode;
import android.view.KeyEvent;
import android.view.View;
import android.widget.SpinnerAdapter;

public class ActionBarWrapper extends ActionBar {

    public static ActionBarWrapper wrap(ActionBar self) {
        return new ActionBarWrapper(self);
    }

    private final ActionBar self;

    protected ActionBarWrapper(ActionBar self) {
        this.self = self;
    }

    @Override
    public void setCustomView(View view) {
        self.setCustomView(view);
    }

    @Override
    public void setCustomView(View view, LayoutParams layoutParams) {
        self.setCustomView(view, layoutParams);
    }

    @Override
    public void setCustomView(int resId) {
        self.setCustomView(resId);
    }

    @Override
    public void setIcon(int resId) {
        self.setIcon(resId);
    }

    @Override
    public void setIcon(Drawable icon) {
        self.setIcon(icon);
    }

    @Override
    public void setLogo(int resId) {
        self.setLogo(resId);
    }

    @Override
    public void setLogo(Drawable logo) {
        self.setLogo(logo);
    }

    @Override
    public void setListNavigationCallbacks(SpinnerAdapter adapter, OnNavigationListener callback) {
        self.setListNavigationCallbacks(adapter, callback);
    }

    @Override
    public void setSelectedNavigationItem(int position) {
        self.setSelectedNavigationItem(position);
    }

    @Override
    public int getSelectedNavigationIndex() {
        return self.getSelectedNavigationIndex();
    }

    @Override
    public int getNavigationItemCount() {
        return self.getNavigationItemCount();
    }

    @Override
    public void setTitle(CharSequence title) {
        self.setTitle(title);
    }

    @Override
    public void setTitle(int resId) {
        self.setTitle(resId);
    }

    @Override
    public void setSubtitle(CharSequence subtitle) {
        self.setSubtitle(subtitle);
    }

    @Override
    public void setSubtitle(int resId) {
        self.setSubtitle(resId);
    }

    @Override
    public void setDisplayOptions(int options) {
        self.setDisplayOptions(options);
    }

    @Override
    public void setDisplayOptions(int options, int mask) {
        self.setDisplayOptions(options, mask);
    }

    @Override
    public void setDisplayUseLogoEnabled(boolean useLogo) {
        self.setDisplayUseLogoEnabled(useLogo);
    }

    @Override
    public void setDisplayShowHomeEnabled(boolean showHome) {
        self.setDisplayShowHomeEnabled(showHome);
    }

    @Override
    public void setDisplayHomeAsUpEnabled(boolean showHomeAsUp) {
        self.setDisplayHomeAsUpEnabled(showHomeAsUp);
    }

    @Override
    public void setDisplayShowTitleEnabled(boolean showTitle) {
        self.setDisplayShowTitleEnabled(showTitle);
    }

    @Override
    public void setDisplayShowCustomEnabled(boolean showCustom) {
        self.setDisplayShowCustomEnabled(showCustom);
    }

    @Override
    public void setBackgroundDrawable(Drawable d) {
        self.setBackgroundDrawable(d);
    }

    @Override
    public void setStackedBackgroundDrawable(Drawable d) {
        self.setStackedBackgroundDrawable(d);
    }

    @Override
    public void setSplitBackgroundDrawable(Drawable d) {
        self.setSplitBackgroundDrawable(d);
    }

    @Override
    public View getCustomView() {
        return self.getCustomView();
    }

    @Override
    public CharSequence getTitle() {
        return self.getTitle();
    }

    @Override
    public CharSequence getSubtitle() {
        return self.getSubtitle();
    }

    @Override
    public int getNavigationMode() {
        return self.getNavigationMode();
    }

    @Override
    public void setNavigationMode(int mode) {
        self.setNavigationMode(mode);
    }

    @Override
    public int getDisplayOptions() {
        return self.getDisplayOptions();
    }

    @Override
    public Tab newTab() {
        return self.newTab();
    }

    @Override
    public void addTab(Tab tab) {
        self.addTab(tab);
    }

    @Override
    public void addTab(Tab tab, boolean setSelected) {
        self.addTab(tab, setSelected);
    }

    @Override
    public void addTab(Tab tab, int position) {
        self.addTab(tab, position);
    }

    @Override
    public void addTab(Tab tab, int position, boolean setSelected) {
        self.addTab(tab, position, setSelected);
    }

    @Override
    public void removeTab(Tab tab) {
        self.removeTab(tab);
    }

    @Override
    public void removeTabAt(int position) {
        self.removeTabAt(position);
    }

    @Override
    public void removeAllTabs() {
        self.removeAllTabs();
    }

    @Override
    public void selectTab(Tab tab) {
        self.selectTab(tab);
    }

    @Override
    public Tab getSelectedTab() {
        return self.getSelectedTab();
    }

    @Override
    public Tab getTabAt(int index) {
        return self.getTabAt(index);
    }

    @Override
    public int getTabCount() {
        return self.getTabCount();
    }

    @Override
    public int getHeight() {
        return self.getHeight();
    }

    @Override
    public void show() {
        self.show();
    }

    @Override
    public void hide() {
        self.hide();
    }

    @Override
    public boolean isShowing() {
        return self.isShowing();
    }

    @Override
    public void addOnMenuVisibilityListener(OnMenuVisibilityListener listener) {
        self.addOnMenuVisibilityListener(listener);
    }

    @Override
    public void removeOnMenuVisibilityListener(OnMenuVisibilityListener listener) {
        self.removeOnMenuVisibilityListener(listener);
    }

    @Override
    public void setHomeButtonEnabled(boolean enabled) {
        self.setHomeButtonEnabled(enabled);
    }

    @Override
    public Context getThemedContext() {
        return self.getThemedContext();
    }

    @Override
    public boolean isTitleTruncated() {
        //noinspection RestrictedApi
        return self.isTitleTruncated();
    }

    @Override
    public void setHomeAsUpIndicator(Drawable indicator) {
        self.setHomeAsUpIndicator(indicator);
    }

    @Override
    public void setHomeAsUpIndicator(int resId) {
        self.setHomeAsUpIndicator(resId);
    }

    @Override
    public void setHomeActionContentDescription(CharSequence description) {
        self.setHomeActionContentDescription(description);
    }

    @Override
    public void setHomeActionContentDescription(int resId) {
        self.setHomeActionContentDescription(resId);
    }

    @Override
    public void setHideOnContentScrollEnabled(boolean hideOnContentScroll) {
        self.setHideOnContentScrollEnabled(hideOnContentScroll);
    }

    @Override
    public boolean isHideOnContentScrollEnabled() {
        return self.isHideOnContentScrollEnabled();
    }

    @Override
    public int getHideOffset() {
        return self.getHideOffset();
    }

    @Override
    public void setHideOffset(int offset) {
        self.setHideOffset(offset);
    }

    @Override
    public void setElevation(float elevation) {
        self.setElevation(elevation);
    }

    @Override
    public float getElevation() {
        return self.getElevation();
    }

    @Override
    public void setDefaultDisplayHomeAsUpEnabled(boolean enabled) {
        //noinspection RestrictedApi
        self.setDefaultDisplayHomeAsUpEnabled(enabled);
    }

    @Override
    public void setShowHideAnimationEnabled(boolean enabled) {
        //noinspection RestrictedApi
        self.setShowHideAnimationEnabled(enabled);
    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        //noinspection RestrictedApi
        self.onConfigurationChanged(config);
    }

    @Override
    public void dispatchMenuVisibilityChanged(boolean visible) {
        //noinspection RestrictedApi
        self.dispatchMenuVisibilityChanged(visible);
    }

    @Override
    public ActionMode startActionMode(ActionMode.Callback callback) {
        //noinspection RestrictedApi
        return self.startActionMode(callback);
    }

    @Override
    public boolean openOptionsMenu() {
        //noinspection RestrictedApi
        return self.openOptionsMenu();
    }

    @Override
    public boolean invalidateOptionsMenu() {
        //noinspection RestrictedApi
        return self.invalidateOptionsMenu();
    }

    @Override
    public boolean onMenuKeyEvent(KeyEvent event) {
        //noinspection RestrictedApi
        return self.onMenuKeyEvent(event);
    }

    @Override
    public boolean onKeyShortcut(int keyCode, KeyEvent ev) {
        //noinspection RestrictedApi
        return self.onKeyShortcut(keyCode, ev);
    }

    @Override
    public boolean collapseActionView() {
        //noinspection RestrictedApi
        return self.collapseActionView();
    }

    @Override
    public void setWindowTitle(CharSequence title) {
        //noinspection RestrictedApi
        self.setWindowTitle(title);
    }

}
