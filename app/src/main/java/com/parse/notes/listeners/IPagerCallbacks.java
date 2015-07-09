package com.parse.notes.listeners;

import com.parse.notes.fragments.Pager;

/**
 * Created by gzaharieva on 09.07.15.
 */
public interface IPagerCallbacks {

    /**
     *
     * @param position
     */
    void setCurrentItem(final Pager position);
}
