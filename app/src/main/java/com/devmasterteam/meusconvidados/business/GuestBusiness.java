package com.devmasterteam.meusconvidados.business;

import android.content.Context;

import com.devmasterteam.meusconvidados.entities.GuestEntity;
import com.devmasterteam.meusconvidados.repository.GuestRepository;

public class GuestBusiness {
    private GuestRepository mGuestRepository;

    public GuestBusiness(Context context) {
        this.mGuestRepository = GuestRepository.getInstance(context);
    }

    public Boolean insert(GuestEntity guestEntity) {
        return this.mGuestRepository.insert(guestEntity);
    }
}
