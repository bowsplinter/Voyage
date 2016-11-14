package istd.team4.travelapp;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.sql.SQLException;

public class SinglishDetailActivity extends AppCompatActivity {

    long id;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlish_detail);

        TextView word = (TextView)findViewById(R.id.word);
        TextView originlabel = (TextView)findViewById(R.id.originlabel);
        TextView origin = (TextView)findViewById(R.id.origin);
        TextView def = (TextView)findViewById(R.id.def);
        TextView examplelabel = (TextView)findViewById(R.id.examplelabel);
        TextView example = (TextView)findViewById(R.id.example);

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
        String wordString = cursor.getString(cursor.getColumnIndex(databaseHelper.KEY_SINGLISH_WORD));
        String defString = cursor.getString(cursor.getColumnIndex(databaseHelper.KEY_SINGLISH_DEFINATION));
        String exampleString = cursor.getString(cursor.getColumnIndex(databaseHelper.KEY_SINGLISH_EXAMPLE));
        String originString = cursor.getString(cursor.getColumnIndex(databaseHelper.KEY_SINGLISH_ORIGIN));
        word.setText(wordString);
        def.setText(defString);
        if (!originString.equals("")) {
            originlabel.setVisibility(View.VISIBLE);
            origin.setText(originString);
            origin.setVisibility(View.VISIBLE);
        }
        if (!exampleString.equals("")) {
            examplelabel.setVisibility(View.VISIBLE);
            example.setText(originString);
            example.setVisibility(View.VISIBLE);
        }
    }
}
