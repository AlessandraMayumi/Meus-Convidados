package com.devmasterteam.meusconvidados.repository;

import android.content.Context;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.devmasterteam.meusconvidados.constants.DataBaseConstants;
import com.devmasterteam.meusconvidados.entities.GuestEntity;


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
}
