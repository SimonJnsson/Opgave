package com.example.simon.opgave;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.util.Log;
import android.widget.Toast;

import com.example.simon.opgave.User;

import java.sql.ResultSet;

public class DBHandler extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "users";
    private static final String TABLE_USER = "user";
    private static final String COLOUMN_ID = "_id";
    private static final String COLOUMN_NAME = "username";
    private static final String COLOUMN_PASSWORD = "password";
    private static final String COLOUMN_SCORE = "score";

    private static final int DATABASE_VERSION = 5;

    public DBHandler(Context context, SQLiteDatabase.CursorFactory factory)
    {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String query = "CREATE TABLE " + TABLE_USER + "(" + COLOUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLOUMN_NAME + " TEXT," + COLOUMN_PASSWORD + " TEXT," + COLOUMN_SCORE + " INTEGER)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER + ";");
        onCreate(db);
    }

    public void adduser(User user)
    {
        ContentValues values = new ContentValues();
        values.put(COLOUMN_NAME, user.getUsername());
        values.put(COLOUMN_PASSWORD, user.getPassword());
        values.put(COLOUMN_SCORE, user.getScore());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public void deleteuser(String name)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_USER + " WHERE " + COLOUMN_NAME + "=\"" + name + "\";");
    }

    public void clearTable()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_USER);
        Log.i("LOG", "Log: Table cleared");
        Log.i("LOG", "Log: \n" + this.ToString(false));
    }

    public void setScore(String name, int score)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_USER + " SET " + COLOUMN_SCORE + " = " + (score + getScore(name)) + " WHERE " + COLOUMN_NAME + "=\"" + name + "\";");
        db.close();
        ToString(false);
    }

    public int getScore(String name)
    {
        SQLiteDatabase db = getWritableDatabase();
        int score = 0;
        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + COLOUMN_NAME + "=\"" + name + "\";";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        if (c.getString(c.getColumnIndex(COLOUMN_NAME)) != null)
        {
            score = c.getInt(c.getColumnIndex(COLOUMN_SCORE));
        }

        return score;
    }

    public String ToString(boolean formatTheString)
    {
        Log.i("LOG", "Log - Content of database: " + TABLE_USER + "\n");
        SQLiteDatabase db = getWritableDatabase();
        String result = "";
        String query = "SELECT * FROM " + TABLE_USER + " WHERE 1 ORDER BY " + COLOUMN_SCORE + " DESC LIMIT 10;";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();


        while (!c.isAfterLast())
        {
            if (c.getString(c.getColumnIndex(COLOUMN_NAME)) != null)
            {
                if(!formatTheString)
                {
                    result += "Username: " + c.getString(c.getColumnIndex(COLOUMN_NAME)) + "\t\tPassword: " + c.getString(c.getColumnIndex(COLOUMN_PASSWORD)) + "\t\tScore: " + c.getString(c.getColumnIndex(COLOUMN_SCORE)) + "\n";
                }
                else
                {
                    if(c.getInt(c.getColumnIndex(COLOUMN_SCORE)) > 0)
                    result += c.getString(c.getColumnIndex(COLOUMN_NAME)) + " - " + c.getString(c.getColumnIndex(COLOUMN_SCORE)) + "\n";
                }
            }

            c.moveToNext();
        }

        db.close();

        return result;
    }

    // Checks availability of passed username in database
    public boolean usernameAvailable(String username)
    {
        SQLiteDatabase db = getWritableDatabase();
        ResultSet result = null;
        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + COLOUMN_NAME + " = \"" + username + "\" LIMIT 1;"; // Gets the first (only) element in the table with given username

        Cursor c = db.rawQuery(query, null);
        try
        {
            c.moveToFirst();

            if (c.getString(c.getColumnIndex(COLOUMN_NAME)) == null)
            {
                db.close();
                return false;
            }
        } catch (Exception e) // An exception is thrown because no column with the value was found
        {
            // The username is available
            db.close();
            return true;
        }

        return false;
    }

    public boolean confirmLogIn(String username, String password)
    {
        SQLiteDatabase db = getWritableDatabase();
        ResultSet result = null;
        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + COLOUMN_NAME + " = \"" + username + "\" AND " + COLOUMN_PASSWORD + " = \"" + password + "\";";
        Log.v("LOG", query);

        Cursor c = db.rawQuery(query, null);

        try
        {
            c.moveToFirst();

            if (c.getString(c.getColumnIndex(COLOUMN_NAME)) != null)
            {
                db.close();
                return true;
            }
            else
            {
                db.close();
                return false;
            }
        } catch (Exception e)
        {
            db.close();
            return false;
        }
    }
}
