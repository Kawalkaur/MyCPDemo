package kawal.com.mycpdemo;

import android.app.backup.BackupDataInput;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class StudentProvider extends ContentProvider {

    DBHelper dbHelper;
    SQLiteDatabase sqLiteDatabase1;

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        String tabName = uri.getLastPathSegment();
        int i  = sqLiteDatabase1.delete(tabName,selection,null);
        return i;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String tabName = uri.getLastPathSegment();
        long l = sqLiteDatabase1.insert(tabName, null, values);
        Uri dummy = Uri.parse("kuchbhi/"+l);
        return dummy;
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext(),Util.DB_Name,null, Util.DB_VERSION);
        sqLiteDatabase1 = dbHelper.getWritableDatabase();

        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
    String tabName = uri.getLastPathSegment();
        Cursor cursor = sqLiteDatabase1.query(tabName,projection, null,null, null, null, null);
        return  cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        String tabName = uri.getLastPathSegment();
        int i = sqLiteDatabase1.update(tabName,values,selection,null);
        return i;
    }

    class DBHelper extends SQLiteOpenHelper {


        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(Util.CREATE_TAB_QUERY);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabse, int oldVersion, int newVersion) {

        }
    }
}