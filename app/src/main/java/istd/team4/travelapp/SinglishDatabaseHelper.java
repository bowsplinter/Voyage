package istd.team4.travelapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;

/**
 * Created by Vivek on 11/11/2016.
 */
class SinglishDatabaseHelper extends SQLiteOpenHelper {
    // DATABASE
    private static final String DATABASE_NAME = "singlishDatabase";
    private static final int DATABASE_VERSION = 4;

    // TABLE
    private static final String TABLE_SINGLISH = "singlish";

    // Singlish Table Columns
    private static final String KEY_SINGLISH_ID = "_id";
    public static final String KEY_SINGLISH_WORD = "word";
    public static final String KEY_SINGLISH_ORIGIN = "origin";
    public static final String KEY_SINGLISH_DEFINATION = "def";
    public static final String KEY_SINGLISH_EXAMPLE = "example";

    private static SinglishDatabaseHelper sInstance;

    public static synchronized SinglishDatabaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new SinglishDatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public SinglishDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SINGLISH_TABLE = "CREATE TABLE " + TABLE_SINGLISH +
                "(" +
                KEY_SINGLISH_ID + " INTEGER PRIMARY KEY," + // Define a primary key
                KEY_SINGLISH_WORD + " TEXT," +
                KEY_SINGLISH_ORIGIN + " TEXT," +
                KEY_SINGLISH_DEFINATION + " TEXT," +
                KEY_SINGLISH_EXAMPLE + " TEXT" +
                ")";
        db.execSQL(CREATE_SINGLISH_TABLE);
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion){
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_SINGLISH);
            onCreate(db);
        }
    }

    public void addSinglish(String cell0, String cell1, String cell2, String cell3) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_SINGLISH_WORD, cell0);
            values.put(KEY_SINGLISH_ORIGIN, cell1);
            values.put(KEY_SINGLISH_DEFINATION, cell2);
            values.put(KEY_SINGLISH_EXAMPLE, cell3);

            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            db.insertOrThrow(TABLE_SINGLISH, null, values);
            db.setTransactionSuccessful();
            Log.d("DB","success");
        } catch (Exception e) {
            Log.d("DB", "Error "+ e);
        } finally {
            db.endTransaction();
        }
    }

    public Cursor fetchByWord(String inputText) throws SQLException {
        Log.w("input", inputText);
        Cursor mCursor = null;
        SQLiteDatabase db = getWritableDatabase();
        if (inputText == null  ||  inputText.length () == 0)  {
            mCursor = db.query(TABLE_SINGLISH, new String[] {KEY_SINGLISH_ID,
                            KEY_SINGLISH_WORD, KEY_SINGLISH_ORIGIN, KEY_SINGLISH_DEFINATION, KEY_SINGLISH_EXAMPLE},
                    null, null, null, null, null);

        }
        else {
            mCursor = db.query(true, TABLE_SINGLISH, new String[] {KEY_SINGLISH_ID,
                            KEY_SINGLISH_WORD, KEY_SINGLISH_ORIGIN, KEY_SINGLISH_DEFINATION, KEY_SINGLISH_EXAMPLE},
                    KEY_SINGLISH_WORD + " like '%" + inputText + "%'", null,
                    null, null, null, null);
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        Log.v("cursor", "" + mCursor.toString());
        return mCursor;

    }

}
