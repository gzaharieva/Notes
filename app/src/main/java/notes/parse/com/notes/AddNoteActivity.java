package notes.parse.com.notes;

import android.app.AlertDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import notes.parse.com.notes.data.LocalObjects;

public class AddNoteActivity extends ActionBarActivity {

    private final String TAG = AddNoteActivity.class.getSimpleName();
    private EditText noteEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        noteEditText = (EditText) findViewById(R.id.edittext_note);
    }

    private void createNote() {

        final ParseObject note = new ParseObject(LocalObjects.OBJECT_NOTE);
        note.put(LocalObjects.FIELD_CONTENT, noteEditText.getText().toString());
        note.put(LocalObjects.FIELD_IS_IMPORTANT, false);

        note.saveEventually(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null) {
                    Log.d(TAG, note.getObjectId() + "");
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            if(!noteEditText.getText().toString().isEmpty()) {
                createNote();

                finish();
            }else{
                new AlertDialog.Builder(this).setMessage(getString(R.string.message_note_not_empty)).setTitle(getString(R.string.warning));
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
