package istd.team4.travelapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;

import java.sql.SQLException;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThreeFragment extends Fragment {
    private SinglishDatabaseHelper databaseHelper;

    public ThreeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_three, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        final SearchView search = (SearchView)view.findViewById(R.id.search);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Fetch the data remotely
                // Reset SearchView
                search.clearFocus();
                search.setQuery("", false);
                search.setIconified(true);
                showResults(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                showResults(s);
                return false;
            }
        });
    }

//    public boolean onQueryTextChange(String newText) {
//        Log.v("textchange", newText);
//        showResults(newText + "*");
//        return false;
//    }
//
//    public boolean onQueryTextSubmit(String query) {
//        Log.v("query", query);
//        showResults(query + "*");
//        return false;
//    }
//
//    public boolean onClose() {
//        showResults("");
//        return false;
//    }

    private void showResults(String query) {
        Cursor cursor = null;
        databaseHelper = new SinglishDatabaseHelper(getActivity());
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        try {
            cursor = databaseHelper.fetchByWord(query);
        } catch (SQLException e) {
            Log.e("SQL", "" + e);
        }
//        Cursor cursor1 = databaseHelper.fetchByWord(query);
        ListView wordList = (ListView)getView().findViewById(R.id.words);
        SinglishCursorAdapter singAdapter = new SinglishCursorAdapter(getActivity(),cursor);
        wordList.setAdapter(singAdapter);

//        try {
//            cursor = databaseHelper.fetchByWord(query != null ? query.toString() : "*");
//        } catch (SQLException e) {
//            Log.e("SQL", "" + e);
//        }
//
//        if (cursor == null) {
//            //
//        } else {
//            ListView wordList = (ListView)getView().findViewById(R.id.words);
//            SinglishCursorAdapter singAdapter = new SinglishCursorAdapter(getActivity(), cursor);
//            wordList.setAdapter(singAdapter);
//        }
    }



}