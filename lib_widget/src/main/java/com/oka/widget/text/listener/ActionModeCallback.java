package com.oka.widget.text.listener;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
/**
 * Created by zengyong on 2020/5/13
 */
public class ActionModeCallback implements ActionMode.Callback {

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }

}
