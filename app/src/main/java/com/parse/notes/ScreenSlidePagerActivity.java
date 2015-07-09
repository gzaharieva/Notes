package com.parse.notes;

import java.io.Serializable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

import com.parse.notes.config.Constants;
import com.parse.notes.fragments.AuthenticationFragment;
import com.parse.notes.fragments.LoginAuthenticationFragment;
import com.parse.notes.fragments.Pager;
import com.parse.notes.fragments.RegisterAuthenticationFragment;
import com.parse.notes.listeners.IPagerCallbacks;

public class ScreenSlidePagerActivity extends ActionBarActivity implements IPagerCallbacks, Serializable
{
  /**
   * The number of pages (wizard steps) to show in this demo.
   */
  private static final int NUM_PAGES = 2;

  /**
   * The pager widget, which handles animation and allows swiping horizontally to access previous
   * and next wizard steps.
   */
  private ViewPager mPager;

  /**
   * The pager adapter, which provides the pages to the view pager widget.
   */
  private PagerAdapter mPagerAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_screen_slide);

    // Instantiate a ViewPager and a PagerAdapter.
    mPager = (ViewPager) findViewById(R.id.pager);
    mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
    mPager.setAdapter(mPagerAdapter);

    // FacebookSdk.sdkInitialize(getApplicationContext());
  }

  @Override
  public void onBackPressed()
  {
    if (mPager.getCurrentItem() == 0)
    {
      // If the user is currently looking at the first step, allow the system to handle the
      // Back button. This calls finish() on this activity and pops the back stack.
      super.onBackPressed();
    }
    else
    {
      // Otherwise, select the previous step.
      mPager.setCurrentItem(mPager.getCurrentItem() - 1, true);
    }
  }

  @Override
  public void setCurrentItem(final Pager position)
  {
    mPager.setCurrentItem(position.ordinal(), true);
  }

  /**
   * A simple pager adapter that represents 3 ScreenSlidePageFragment objects, in sequence.
   */
  private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter
  {
    public ScreenSlidePagerAdapter(FragmentManager fm)
    {
      super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
      Fragment result = null;
      switch (position)
      {
        case 0:
          result = new AuthenticationFragment();
          Bundle bundle = new Bundle();
          bundle.putSerializable(Constants.PAGER_CALLBACKS, ScreenSlidePagerActivity.this);
          result.setArguments(bundle);
          break;
        case 1:
          result = new LoginAuthenticationFragment();
          break;
        case 2:
          result = new RegisterAuthenticationFragment();
          break;
      }

      return result;
    }

    @Override
    public int getCount()
    {
      return NUM_PAGES;
    }
  }
}