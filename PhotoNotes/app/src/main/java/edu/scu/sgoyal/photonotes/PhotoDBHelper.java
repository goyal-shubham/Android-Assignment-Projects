package edu.scu.sgoyal.photonotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by shubhamgoyal on 5/18/16.
 */
public class PhotoDBHelper extends SQLiteOpenHelper
{
    Context context;
    static private final int VERSION=3;
    static private final String DB_NAME="shubham";

    static private final String SQL_CREATE_TABLE =
            "CREATE TABLE PhotoNotes (" +
                    "  _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "  caption TEXT," +
                    "  imagePath TEXT);";

    static private final String SQL_DROP_TABLE = "DROP TABLE PhotoNotes";

    public PhotoDBHelper(Context context)
    {
        super(context, DB_NAME, null, VERSION);     // we use default cursor factory (null, 3rd arg)
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // a simple crude implementation that does not preserve data on upgrade
        db.execSQL(SQL_DROP_TABLE);
        db.execSQL(SQL_CREATE_TABLE);
        displayMsg.toast(context, "Upgrading DB and dropping data!!!");
    }

    public int getMaxRecID()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(_id) FROM PhotoNotes;", null);

        if (cursor.getCount() == 0) {
            return 0;
        } else {
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
    }

    public Cursor fetchAll()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM PhotoNotes;", null);
    }

//    public void add(viewPhotoDetails ci)
//    {
//        SQLiteDatabase db = this.getReadableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("caption", ci.caption);
//        contentValues.put("imagePath", ci.imagePath);
//
//
//        db.insert("photo", null, contentValues);
//
//        /*
//        String SQL_ADD =
//                "INSERT INTO contact (name, surname, email, phone) VALUES ('"
//                + ci.name + "', '"
//                + ci.surname + "', '"
//                + ci.email + "', '"
//                + ci.phone +"');";
//        db.execSQL(SQL_ADD);
//        */
//    }
//
//    public void delete(int id)
//    {
//        SQLiteDatabase db = this.getReadableDatabase();
//        db.delete("photo", "_id=?", new String[]{String.valueOf(id)});
//
//        /*
//        String SQL_DELETE="DELETE FROM contact WHERE _id=" + id + ";";
//        db.execSQL(SQL_DELETE);
//         */
//    }
}
