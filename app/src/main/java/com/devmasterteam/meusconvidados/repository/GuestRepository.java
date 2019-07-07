package com.devmasterteam.meusconvidados.repository;

import android.content.Context;

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
}
