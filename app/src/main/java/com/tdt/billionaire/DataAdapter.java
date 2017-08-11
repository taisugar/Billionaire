package com.tdt.billionaire;;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataAdapter {
    protected static final String TAG = "DataAdapter";
    private final Context mContext;
    private SQLiteDatabase mDb;
    private DataBaseHelper mDbHelper;

    /*
    Tạo adapter để chuyển đổi dữ liệu
    */
    public DataAdapter(Context context)
    {
        this.mContext = context;
        mDbHelper = new DataBaseHelper(mContext);
    }

    public DataAdapter createDatabase() throws SQLException
    {
        try
        {
            mDbHelper.createDataBase();
        }
        catch (IOException mIOException)
        {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public DataAdapter open() throws SQLException
    {
        try
        {
            mDbHelper.openDataBase();
            mDbHelper.close();
            mDb = mDbHelper.getReadableDatabase();
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "open >>"+ mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    /*
    Chuyển dữ liệu từ database sang list
    */
    public ArrayList<Question> getDataAdapter() throws SQLException {
        String sql = "SELECT * FROM Tb_ailatrieuphu";
        ArrayList<Question> listQ = new ArrayList<Question>();
        Cursor mCur = mDb.rawQuery(sql, null);
        while(mCur.moveToNext()) {
            listQ.add(new Question(
                    mCur.getString(mCur.getColumnIndex("id")),
                    mCur.getString(mCur.getColumnIndex("question")),
                    mCur.getString(mCur.getColumnIndex("optA")),
                    mCur.getString(mCur.getColumnIndex("optB")),
                    mCur.getString(mCur.getColumnIndex("optC")),
                    mCur.getString(mCur.getColumnIndex("optD")),
                    mCur.getString(mCur.getColumnIndex("optR"))));
        }
        return listQ;
    }
}
