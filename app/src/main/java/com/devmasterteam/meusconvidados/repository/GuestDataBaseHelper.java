package com.devmasterteam.meusconvidados.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.devmasterteam.meusconvidados.constants.DataBaseConstants;

public class GuestDataBaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MeusConvidados.db";

    private static final String SQL_CREATE_TABLE_GUEST =
            "create table " + DataBaseConstants.GUEST.TABLE_NAME + " ("
            + DataBaseConstants.GUEST.COLUMNS.ID + " integer primary key autoincrement "
            + DataBaseConstants.GUEST.COLUMNS.NAME + " text, "
            + DataBaseConstants.GUEST.COLUMNS.PRESENCE + " integer);";

    private static final String DROP_TABLE_GUEST =
            "DROP TABLE IF EXISTS " + DataBaseConstants.GUEST.TABLE_NAME;

    public GuestDataBaseHelper(Context context) {
        super(context, this.DATABASE_NAME, null, this.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_GUEST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_GUEST);
        db.execSQL(SQL_CREATE_TABLE_GUEST);
    }
}