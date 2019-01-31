package maksim.stuffchecker.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbInitializer extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "checker_db";
    private static final int DATABASE_VERSION = 1;

    public static final String VALIDATION_TABLE = "for_validation";
    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String REQUEST = "request";
    public static final String RESPONSE = "response_contains";
    public static final String TIME = "time";
    public static final String IS_OK = "is_ok";

    public DbInitializer(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + VALIDATION_TABLE
                + "(" + ID + " integer PRIMARY KEY,"
                + NAME + " text,"
                + REQUEST + " text,"
                + RESPONSE + " text,"
                + TIME + " text,"
                + IS_OK + " boolean check ("+IS_OK+" in (0,1))"
                + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + VALIDATION_TABLE);
        onCreate(db);
    }

/*    private void init(SQLiteDatabase db) {
        initProgressTable(db);
        initStatsTable(db);
    }

    private void initProgressTable(SQLiteDatabase db) {
        ContentValues contentValues;
        int ID = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++, ID++) {
                contentValues = new ContentValues();
                contentValues.put(DbInitializer.ID, ID);
                contentValues.put(NAME, 0);
                contentValues.put(REQUEST, 0);
                db.insert(VALIDATION_TABLE, null, contentValues);
            }
        }
    }

    private void initStatsTable(SQLiteDatabase db) {
        ContentValues contentValues;
        for (int difficulty : difficulties) {
            contentValues = new ContentValues();
            contentValues.put(ID, difficulty);
            contentValues.put(DATE, 0);
            db.insert(MISMATCHES, null, contentValues);
        }
    }
*/

}
