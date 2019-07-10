package com.devmasterteam.meusconvidados.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.devmasterteam.meusconvidados.R;
import com.devmasterteam.meusconvidados.entities.GuestEntity;

public class GuestViewHolder extends RecyclerView.ViewHolder {
    private TextView mTextName;

    public GuestViewHolder(@NonNull View itemView) {
        super(itemView);
        this.mTextName = (TextView) itemView.findViewById(R.id.text_name);
    }

    public void bindData(GuestEntity guestEntity) {
        this.mTextName.setText(guestEntity.getName());
    }
}
