package com.grandilo.stanthonyapp.db;

/**
 * Created by calistus on 10/5/2015.
 */
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DBAdapter {

    static final String DATABASE_NAME = "prayers";
    static final String TABLE_DEVOTION = "devotion_prayer";
    static final String TABLE_SPECIAL_PRAYERS = "special_prayers";
    static final int DATABASE_VERSION = 1;

    final Context context;
    DatabaseHelper DBHelper;
    SQLiteDatabase db;
    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            //Empty since DB has been pre_created
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {

        }
    }
    //---opens the database---
    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }
    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }
    public Cursor getDevotionPrayer(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, TABLE_DEVOTION, new String[] {"id",
                                "title", "body"}, "id" + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    public Cursor getSpecialPrayer(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, TABLE_SPECIAL_PRAYERS, new String[] {"id",
                                "title", "body"}, "id" + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

}