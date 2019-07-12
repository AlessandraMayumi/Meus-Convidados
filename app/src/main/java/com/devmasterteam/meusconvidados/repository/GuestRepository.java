package com.devmasterteam.meusconvidados.repository;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.devmasterteam.meusconvidados.constants.DataBaseConstants;
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
            contentValues.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, guestEntity.getConfirmed());

            sqLiteDatabase.insert(DataBaseConstants.GUEST.TABLE_NAME, null, contentValues);

            return true;
        }
         catch (Exception e) {
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
                    DataBaseConstants.GUEST.COLUMNS.PRESENCE};
            String seletion = DataBaseConstants.GUEST.COLUMNS.ID + " = ?";
            String[] selectionArgs = {String.valueOf(id)};

            Cursor cursor = sqLiteDatabase.query(DataBaseConstants.GUEST.TABLE_NAME, projection, seletion, selectionArgs, null, null, null);
//            sqLiteDatabase.rawQuery("SELECT * FROM guest WHERE id=" + String.valueOf(id), null);
            if(cursor != null && cursor.getCount()>0){
                cursor.moveToFirst();
                guestEntity.setId(cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID)));
                guestEntity.setName(cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME)));
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
}
