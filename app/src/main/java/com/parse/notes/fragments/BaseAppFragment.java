/*
 * BaseAppFragment.java
 *
 * Copyright (c) 2014-2015 LECTURION GMBH,
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * LECTURIO GMBH ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you
 * entered into with LECTURIO GMBH.
 */
package com.parse.notes.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;


/**
 * Base application fragment.
 * 
 * @author Gabriela Zaharieva
 */
public class BaseAppFragment extends Fragment
{

  @Override
  public void onCreate(final Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
  }

  public ActionBar getActionBar()
  {
    return ((ActionBarActivity) getActivity()).getSupportActionBar();
  }


  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
  }

  @Override
  public void setRetainInstance(boolean retain)
  {
    super.setRetainInstance(retain);
  }

  @Override
  public void onViewStateRestored(Bundle paramBundle)
  {
    super.onViewStateRestored(paramBundle);
  }


}
