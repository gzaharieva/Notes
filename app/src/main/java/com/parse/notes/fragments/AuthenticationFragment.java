package com.parse.notes.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.parse.notes.R;
import com.parse.notes.config.Constants;
import com.parse.notes.listeners.IPagerCallbacks;

/**
 * Authentication screen view.
 */
public class AuthenticationFragment extends BaseAppFragment
{

  private Button signUpButton;
  private Button createAccountButton;
  private IPagerCallbacks pagerCallbacks;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
  {
    ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_screen_slide_page, container, false);
    signUpButton = (Button) rootView.findViewById(R.id.action_sign_in);
    createAccountButton = (Button) rootView.findViewById(R.id.action_register);

    return rootView;

  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState)
  {
    super.onActivityCreated(savedInstanceState);
    init();
  }

  private void init()
  {
    pagerCallbacks = (IPagerCallbacks) getArguments().getSerializable(Constants.PAGER_CALLBACKS);
    signUpButton.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View view)
      {
        pagerCallbacks.setCurrentItem(Pager.SIGN_UP);
      }
    });

    createAccountButton.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View view)
      {
        pagerCallbacks.setCurrentItem(Pager.REGISTER);
      }
    });
  }
}