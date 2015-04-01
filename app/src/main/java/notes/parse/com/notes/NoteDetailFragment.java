package notes.parse.com.notes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseQuery;

import notes.parse.com.notes.data.Note;


/**
 * A fragment representing a single Note detail screen.
 * This fragment is either contained in a {@link NoteListActivity}
 * in two-pane mode (on tablets) or a {@link NoteDetailActivity}
 * on handsets.
 */
public class NoteDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Note mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NoteDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = (Note) getArguments().getSerializable(ARG_ITEM_ID);
           // ParseQuery.
//           mItem = new Note(noteId);
//            mItem.setContent(noteId);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_note_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.note_detail)).setText(mItem.getContent());
        }

        return rootView;
    }
}
