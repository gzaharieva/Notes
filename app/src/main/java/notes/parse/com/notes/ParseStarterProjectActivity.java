package notes.parse.com.notes;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;

public class ParseStarterProjectActivity extends Activity {
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.main);

		ParseAnalytics.trackAppOpenedInBackground(getIntent());

        ParseUser parseUser = new ParseUser();
        parseUser.setUsername("gabi");
        parseUser.setPassword("12345");
        parseUser.setEmail("gabi@gmail.com");
        parseUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {

                Log.d("LOG", "Logged in:"+ParseUser.getCurrentUser().getObjectId());

                ParseACL defaultACL = new ParseACL();
                // Optionally enable public read access.
                defaultACL.setReadAccess(ParseUser.getCurrentUser(), true);
                defaultACL.setWriteAccess(ParseUser.getCurrentUser(), true);
               
                ParseACL.setDefaultACL(defaultACL, true);

                Log.d("LOG", ParseUser.getCurrentUser().getACL()+"");
                
                final ParseObject testObject = new ParseObject("TestObject");
                testObject.put("foo", "bar");

               
//                testObject.setACL(ParseUser.getCurrentUser().getACL());
                testObject.saveEventually(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        Log.d("ParseStarterProject", testObject.getObjectId()+ "");
                    }
                });

                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("TestObject");

                parseQuery.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> parseObjects, ParseException e) {
                        for(ParseObject parseObject : parseObjects){
                            Log.d("LOG", "object:"+parseObject.getObjectId());
                        }
                    }

                });

            }
        });
        
       
	}
}
