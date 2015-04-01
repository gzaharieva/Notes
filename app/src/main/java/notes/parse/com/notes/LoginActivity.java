package notes.parse.com.notes;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.parse.LogInCallback;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * A login screen that offers login via email/password and via Google+ sign in.
 * <p/>
 * ************ IMPORTANT SETUP NOTES: ************
 * In order for Google+ sign in to work with your app, you must first go to:
 * https://developers.google.com/+/mobile/android/getting-started#step_1_enable_the_google_api
 * and follow the steps in "Step 1" to create an OAuth 2.0 client for your package.
 */
public class LoginActivity extends PlusBaseActivity implements LoaderCallbacks<Cursor> {

    private final String TAG = LoginActivity.class.getSimpleName();

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mEmailLoginFormView;
    private SignInButton mPlusSignInButton;
    private View mLoginFormView;
    private TextView switchAutheticationContext;
    private boolean isLoginContext = true;
    private Button mEmailSignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ParseAnalytics.trackAppOpenedInBackground(getIntent());
        // Find the Google+ sign in button.
        mPlusSignInButton = (SignInButton) findViewById(R.id.plus_sign_in_button);


        if (supportsGooglePlayServices()) {
            // Set a listener to connect the user when the G+ button is clicked.
            mPlusSignInButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    signIn();
                }
            });
        } else {
            // Don't offer G+ sign in if the app's version is too low to support Google Play
            // Services.
            mPlusSignInButton.setVisibility(View.GONE);
            return;
        }

        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.email);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
//        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
//                if (id == R.id.login || id == EditorInfo.IME_NULL) {
//                    attemptLogin();
//                    return true;
//                }
//                return false;
//            }
//        })

        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                    boolean isValid = attemptLogin();
                    if(isValid) {
                        // Show a progress spinner, and kick off a background task to
                        // perform the user login attempt.
                        if (isLoginContext) {
                            showProgress(true);
                            login(mEmailView.getText().toString(), mPasswordView.getText().toString());
                        }else{
                            register(mEmailView.getText().toString(), mPasswordView.getText().toString());
                        }

                    }
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        mEmailLoginFormView = findViewById(R.id.email_login_form);

        switchAutheticationContext = (TextView) findViewById(R.id.switch_authentication_context);
        switchAutheticationContext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isLoginContext){
                    switchToRegister();
                }else{
                    switchToLogin();
                }
                isLoginContext = !isLoginContext;
            }
        });
    }

    private void register(String email, String password) {
        ParseUser user = new ParseUser();
        user.setUsername(email);
        user.setPassword(password);
        user.setEmail(email);


        user.signUpInBackground(new SignUpCallback()
        {
            public void done(ParseException e)
            {
                if (e == null)
                {
                    showHomeScreen();
                }
                else
                {
                    showProgress(false);
                    mPasswordView.setError(getString(R.string.error_incorrect_password));
                    mPasswordView.requestFocus();
                    Log.d(TAG, "Ex:", e);
                }
            }
        });

    }


    private void switchToLogin() {
        switchAutheticationContext.setText(getString(R.string.label_switch_to_register));
        mEmailSignInButton.setText(getString(R.string.action_login));

    }

    private void switchToRegister() {
        switchAutheticationContext.setText(getString(R.string.label_switch_to_login));
        mEmailSignInButton.setText(getString(R.string.action_sign_up));
    }

    private void populateAutoComplete() {
        getLoaderManager().initLoader(0, null, this);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public boolean attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean isValid = true;        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            mEmailView.requestFocus();
            isValid = false;
        } else if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            mPasswordView.requestFocus();
            isValid = false;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            mEmailView.requestFocus();
            isValid = false;
        }

       return isValid;
    }

    private void login(String email, String password) {
        ParseUser.logInInBackground(email, password, new LogInCallback() {

            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if (e == null) {
                    showHomeScreen();
                    finish();
                } else {
                    showProgress(false);
                    mPasswordView.setError(getString(R.string.error_incorrect_password));
                    mPasswordView.requestFocus();
                    Log.d(TAG, "Ex:", e);
                }
            }
        });
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            Log.d(TAG, "Show:"+ show);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    protected void onPlusClientSignIn(String email) {
            Log.d(TAG, "onPlusClientSignIn");
        if(isLoginContext){
            login(email, "");
        }else{
            register(email, "");
        }
    }

    @Override
    protected void onPlusClientBlockingUI(boolean show) {
        showProgress(show);
    }

    @Override
    protected void updateConnectButtonState() {
        //TODO: Update this logic to also handle the user logged in by email.
        boolean connected = getPlusClient().isConnected();
    Log.d(TAG, "updateConnectButtonState:"+ connected);
        mPlusSignInButton.setVisibility(connected ? View.GONE : View.VISIBLE);
        mEmailLoginFormView.setVisibility(connected ? View.GONE : View.VISIBLE);
    }

    @Override
    protected void onPlusClientRevokeAccess() {
        // TODO: Access to the user's G+ account has been revoked.  Per the developer terms, delete
        // any stored user data here.
    }

    @Override
    protected void onPlusClientSignOut() {

    }

    /**
     * Check if the device supports Google Play Services.  It's best
     * practice to check first rather than handling this as an error case.
     *
     * @return whether the device supports Google Play Services
     */
    private boolean supportsGooglePlayServices() {
        return GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) ==
                ConnectionResult.SUCCESS;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    @Override
    public void onConnectionSuspended(int i) {
        getPlusClient().connect();
    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }


    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

       // mEmailView.setAdapter(adapter);
    }



    private void showHomeScreen() {
        ParseACL defaultACL = new ParseACL();
        // Optionally enable public read access.
        defaultACL.setReadAccess(ParseUser.getCurrentUser(), true);
        defaultACL.setWriteAccess(ParseUser.getCurrentUser(), true);


        ParseACL.setDefaultACL(defaultACL, true);
        Intent intent = new Intent(this, NoteListActivity.class);
        startActivity(intent);

        finish();
    }
}



