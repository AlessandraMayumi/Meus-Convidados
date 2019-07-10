package com.devmasterteam.meusconvidados.business;

import android.content.Context;

import com.devmasterteam.meusconvidados.constants.DataBaseConstants;
import com.devmasterteam.meusconvidados.entities.GuestEntity;
import com.devmasterteam.meusconvidados.repository.GuestRepository;

import java.util.List;

public class GuestBusiness {
    private GuestRepository mGuestRepository;

    public GuestBusiness(Context context) {
        this.mGuestRepository = GuestRepository.getInstance(context);
    }

    public Boolean insert(GuestEntity guestEntity) {
        return this.mGuestRepository.insert(guestEntity);
    }

    public List<GuestEntity> getInvited() {
        return this.mGuestRepository.getGuestByQuery("SELECT * FROM " + DataBaseConstants.GUEST.TABLE_NAME);
    }
}
