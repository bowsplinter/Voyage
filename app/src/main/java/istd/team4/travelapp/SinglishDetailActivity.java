package istd.team4.travelapp;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.sql.SQLException;

public class SinglishDetailActivity extends AppCompatActivity {

    long id;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlish_detail);
        Bundle extras = getIntent().getExtras();
        id = extras.getLong("singlish");
        Log.v("detail", "id " + id);
        SinglishDatabaseHelper databaseHelper = SinglishDatabaseHelper.getInstance(this);
        try {
            cursor = databaseHelper.fetchByID(id);
        } catch (SQLException e) {
            Log.e("SQL","" + e);
        }
        Log.v("detail", "cursor " + cursor.toString());
        cursor.moveToFirst();
        String word = cursor.getString(cursor.getColumnIndex(databaseHelper.KEY_SINGLISH_WORD));
        String def = cursor.getString(cursor.getColumnIndex(databaseHelper.KEY_SINGLISH_DEFINATION));
        String example = cursor.getString(cursor.getColumnIndex(databaseHelper.KEY_SINGLISH_EXAMPLE));
        String origin = cursor.getString(cursor.getColumnIndex(databaseHelper.KEY_SINGLISH_ORIGIN));
        Log.v("PLS", word + def + example + origin);
    }
}
