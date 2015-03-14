package notes.parse.com.notes;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseCrashReporting;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Crash Reporting.
        ParseCrashReporting.enable(this);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
//    Parse.initialize(this);

        Parse.initialize(this, "D9JTpVlEupZSqym0e0ZPAwItCBj6EPzZjWflsQhg", "tHeJ6EJ1k9oMZnf7tGwd3QAEyAUo7BSPymVo33XL");


        //ParseUser.enableAutomaticUser();


      
       
    }
}
