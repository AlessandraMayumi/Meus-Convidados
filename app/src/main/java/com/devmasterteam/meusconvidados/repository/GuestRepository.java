package com.devmasterteam.meusconvidados.repository;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.devmasterteam.meusconvidados.constants.DataBaseConstants;
import com.devmasterteam.meusconvidados.constants.GuestConstants;
import com.devmasterteam.meusconvidados.entities.GuestCount;
import com.devmasterteam.meusconvidados.entities.GuestEntity;

import java.util.ArrayList;
import java.util.List;


public class GuestRepository {

    private static GuestRepository INSTANCE;
    private GuestDataBaseHelper mGuestDataBaseHelper;

    private GuestRepository(Context context){
        this.mGuestDataBaseHelper = new GuestDataBaseHelper(context);
    }

    public static synchronized GuestRepository getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = new GuestRepository(context);
        }
        return INSTANCE;
    }

    public Boolean insert(GuestEntity guestEntity) {
        try{
            SQLiteDatabase sqLiteDatabase = this.mGuestDataBaseHelper.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(DataBaseConstants.GUEST.COLUMNS.NAME, guestEntity.getName());
            contentValues.put(DataBaseConstants.GUEST.COLUMNS.DOCUMENT, guestEntity.getDocument());
            contentValues.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, guestEntity.getConfirmed());

            sqLiteDatabase.insert(DataBaseConstants.GUEST.TABLE_NAME, null, contentValues);

            return true;
        }
         catch (Exception e) {
            return false;
        }
    }

    public boolean update(GuestEntity guestEntity) {
        try{
            SQLiteDatabase sqLiteDatabase = this.mGuestDataBaseHelper.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(DataBaseConstants.GUEST.COLUMNS.NAME, guestEntity.getName());
            contentValues.put(DataBaseConstants.GUEST.COLUMNS.DOCUMENT, guestEntity.getDocument());
            contentValues.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, guestEntity.getConfirmed());

            String selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?";
            String[] selectionArgs = {String.valueOf(guestEntity.getId())};

            sqLiteDatabase.update(
                    DataBaseConstants.GUEST.TABLE_NAME,
                    contentValues,
                    selection,
                    selectionArgs);
            return true;

        }catch (Exception e){
            return false;
        }
    }


    public Boolean remove(int id) {
        try{
            SQLiteDatabase sqLiteDatabase = this.mGuestDataBaseHelper.getWritableDatabase();

            String whereClause = DataBaseConstants.GUEST.COLUMNS.ID + " = ?";
            String[] whereArgs = {String.valueOf(id)};
            sqLiteDatabase.delete(DataBaseConstants.GUEST.TABLE_NAME, whereClause, whereArgs);

            return true;
        }catch (Exception e){
            return false;
        }
    }

    public List<GuestEntity> getGuestByQuery(String query) {
        List<GuestEntity> list = new ArrayList<>();
        try{
            SQLiteDatabase sqLiteDatabase = this.mGuestDataBaseHelper.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);

            if(cursor != null && cursor.getCount() > 0){
                while(cursor.moveToNext()){
                    GuestEntity guestEntity = new GuestEntity();
                    guestEntity.setId(cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID)));
                    guestEntity.setName(cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME)));
                    guestEntity.setDocument(cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.DOCUMENT)));
                    guestEntity.setConfirmed(cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE)));

                    list.add(guestEntity);
                }
            }
            if(cursor != null){
                cursor.close();
            }

        }catch (Exception e){
            return list;
        }
        return list;
    }

    public GuestEntity load(int id) {
        GuestEntity guestEntity = new GuestEntity();
        try{
            SQLiteDatabase sqLiteDatabase = this.mGuestDataBaseHelper.getReadableDatabase();

            String[] projection = {
                    DataBaseConstants.GUEST.COLUMNS.ID,
                    DataBaseConstants.GUEST.COLUMNS.NAME,
                    DataBaseConstants.GUEST.COLUMNS.DOCUMENT,
                    DataBaseConstants.GUEST.COLUMNS.PRESENCE};
            String seletion = DataBaseConstants.GUEST.COLUMNS.ID + " = ?";
            String[] selectionArgs = {String.valueOf(id)};

            Cursor cursor = sqLiteDatabase.query(DataBaseConstants.GUEST.TABLE_NAME, projection, seletion, selectionArgs, null, null, null);
//            sqLiteDatabase.rawQuery("SELECT * FROM guest WHERE id=" + String.valueOf(id), null);
            if(cursor != null && cursor.getCount()>0){
                cursor.moveToFirst();
                guestEntity.setId(cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID)));
                guestEntity.setName(cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME)));
                guestEntity.setDocument(cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.DOCUMENT)));
                guestEntity.setConfirmed(cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE)));
            }
            if (cursor != null){
                cursor.close();
            }
            return guestEntity;
        }catch (Exception e){
            return guestEntity;
        }
    }

    public GuestCount loadDashboard() {
        GuestCount guestCount = new GuestCount(0,0,0);
        Cursor cursor;
        try{
            SQLiteDatabase sqLiteDatabase = this.mGuestDataBaseHelper.getReadableDatabase();

//            SELECT count(*) FROM Guest WHERE presence='1'
            String queryPresence = "SELECT count(*) FROM "
                    + DataBaseConstants.GUEST.TABLE_NAME
                    + " WHERE "
                    + DataBaseConstants.GUEST.COLUMNS.PRESENCE
                    + "="
                    + GuestConstants.CONFIRMATION.PRESENT;
            cursor = sqLiteDatabase.rawQuery(queryPresence,null);
            if(cursor != null && cursor.getCount() > 0){
                cursor.moveToFirst();
                guestCount.setPresentCount(cursor.getInt(0));
            }

            String queryAbsent = "SELECT count(*) FROM "
                    + DataBaseConstants.GUEST.TABLE_NAME
                    + " WHERE "
                    + DataBaseConstants.GUEST.COLUMNS.PRESENCE
                    + "="
                    + GuestConstants.CONFIRMATION.ABSENT;
            cursor = sqLiteDatabase.rawQuery(queryAbsent,null);
            if(cursor != null && cursor.getCount() > 0){
                cursor.moveToFirst();
                guestCount.setAbsentCount(cursor.getInt(0));
            }

            String queryAllInvited = "SELECT count(*) FROM " + DataBaseConstants.GUEST.TABLE_NAME;
            cursor = sqLiteDatabase.rawQuery(queryAllInvited,null);
            if(cursor != null && cursor.getCount() > 0){
                cursor.moveToFirst();
                guestCount.setAllInvitedCount(cursor.getInt(0));
            }

            if(cursor != null){
                cursor.close();
            }
            return guestCount;
        }catch(Exception e){
            return guestCount;
        }
    }
}
