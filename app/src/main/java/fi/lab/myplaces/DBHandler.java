package fi.lab.myplaces;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.LinkedList;
import java.util.List;

// TODO: SQL DATABASE HANDLING
public class DBHandler extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "myplaces.db";
    private static final String TABLE_NAME = "myplaces";
    private static final String FIELD_ROW_ID = "_id"; // PRIMARY KEY
    private static final String FIELD_LAT = "lat"; // LATITUDE
    private static final String FIELD_LNG = "lng"; // LONGITUDE

    private static final String FIELD_TITLE = "tle"; // TITLE
    private static final String FIELD_SNIPPET = "snp"; // SNIPPET
    private static final String CREATE_MYPLACES_TABLE =
                    "CREATE TABLE "+TABLE_NAME+"("+
                    FIELD_ROW_ID+" TEXT PRIMARY KEY,"+
                    FIELD_LAT+" DOUBLE,"+
                    FIELD_LNG+" DOUBLE,"+
                    FIELD_TITLE+" TEXT,"+
                    FIELD_SNIPPET+" TEXT"+
                                        ")";

    private static final String DROP_CONTACTS_TABLE =
            "DROP TABLE IF EXISTS "+TABLE_NAME;
    // When DBHandler obj created --> super will create the db if needed
    public DBHandler(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    // first time event
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_MYPLACES_TABLE);
    }
    // if db version changed, onUpgrade() is called
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_CONTACTS_TABLE);
        onCreate(sqLiteDatabase);
    }


}
