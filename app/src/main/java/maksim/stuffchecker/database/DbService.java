package maksim.stuffchecker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import maksim.stuffchecker.R;
import maksim.stuffchecker.entity.ObjForValidation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static maksim.stuffchecker.database.DbInitializer.*;

public class DbService {

    public static void insert(List<ObjForValidation> objects, Context context) {
        DbInitializer initializer = new DbInitializer(context);
        SQLiteDatabase database = initializer.getWritableDatabase();
        for (ObjForValidation obj : objects) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(ID, obj.getId());
            contentValues.put(NAME, obj.getName());
            contentValues.put(REQUEST, obj.getRequestGET());

            StringBuilder sb = new StringBuilder();
            String[] responseValidators = obj.getValidatedPartOfResponse();
            for (int i = 0; i < responseValidators.length; i++) {
                sb.append(responseValidators[i]);
                if (i != responseValidators.length - 1)
                    sb.append(context.getString(R.string.db_regex));
            }
            String responseArrayToDb = sb.toString();
            contentValues.put(RESPONSE, responseArrayToDb);

            contentValues.put(TIME, obj.getTime());
            contentValues.put(IS_OK, (obj.getIsOk()) ? 1 : 0);
            database.insert(VALIDATION_TABLE, null, contentValues);
        }
        database.close();
        initializer.close();
    }

    public static List<ObjForValidation> read(Context context) {
        List<ObjForValidation> objects = new ArrayList<>();
        DbInitializer initializer = new DbInitializer(context);
        SQLiteDatabase database = initializer.getReadableDatabase();
        Cursor cursor = database.query(VALIDATION_TABLE, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(ID);
            int nameIndex = cursor.getColumnIndex(NAME);
            int requestIndex = cursor.getColumnIndex(REQUEST);
            int responseIndex = cursor.getColumnIndex(RESPONSE);
            int timeIndex = cursor.getColumnIndex(TIME);
            int isOkIndex = cursor.getColumnIndex(IS_OK);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(idIndex);
                String name = cursor.getString(nameIndex);
                String request = cursor.getString(requestIndex);
                String[] response = cursor.getString(responseIndex)
                        .split(context.getString(R.string.db_regex));
                long time = (cursor.isNull(timeIndex)) ? 0 : cursor.getLong(timeIndex);
                boolean isOk = (!cursor.isNull(isOkIndex)) && (cursor.getInt(isOkIndex) == 1);
                objects.add(new ObjForValidation(id, name, request, response, time, isOk));
            }
        }
        cursor.close();
        database.close();
        initializer.close();
        Collections.sort(objects, (o1, o2) -> {
            if (o1.getIsOk() && o2.getIsOk()) {
                return Integer.compare(o1.getId(), o2.getId());
            }
            if (!o1.getIsOk() && !o2.getIsOk())
                return Integer.compare(o1.getId(), o2.getId());
            if (o1.getIsOk()) return 1;
            if (o2.getIsOk()) return -1;
            return 0;
        });
        return objects;
    }

    public static void update(List<ObjForValidation> objects) {

    }

    public static void delete(List<ObjForValidation> objects) {

    }

}
