package com.parse.notes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.parse.ParseUser;

/**
 * An example full-screen activity that shows and hides the system UI (i.e. status bar and
 * navigation/system bar) with user interaction.
 *
 */
public class SplashActivity extends Activity
{
  /**
   * Whether or not the system UI should be auto-hidden after {@link #AUTO_HIDE_DELAY_MILLIS}
   * milliseconds.
   */
  private static final boolean AUTO_HIDE = true;

  /**
   * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after user interaction before
   * hiding the system UI.
   */
  private static final int AUTO_HIDE_DELAY_MILLIS = 1000;

  /**
   * Called when the activity is first created.
   */
  @Override
  public void onCreate(Bundle icicle)
  {
    super.onCreate(icicle);
    setContentView(R.layout.activity_splash);

    /*
     * New Handler to start the Menu-Activity and close this Splash-Screen after some seconds.
     */
    new Handler().postDelayed(new Runnable()
    {
      @Override
      public void run()
      {
        ParseUser currentUser = ParseUser.getCurrentUser();

        if (currentUser != null)
        {
          /* Create an Intent that will start the Menu-Activity. */
          Intent intent = new Intent(SplashActivity.this, NoteListActivity.class);
          startActivity(intent);
          finish();
        }
        else
        {
          Intent intent = new Intent(SplashActivity.this, ScreenSlidePagerActivity.class);
          startActivity(intent);
          finish();
        }
      }
    }, AUTO_HIDE_DELAY_MILLIS);
  }

}
