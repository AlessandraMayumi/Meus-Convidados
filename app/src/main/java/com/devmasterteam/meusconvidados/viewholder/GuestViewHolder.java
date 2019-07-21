package com.devmasterteam.meusconvidados.viewholder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.devmasterteam.meusconvidados.R;
import com.devmasterteam.meusconvidados.entities.GuestEntity;
import com.devmasterteam.meusconvidados.listener.OnGuestListenerInteractionListener;

public class GuestViewHolder extends RecyclerView.ViewHolder {
    private TextView mTextName;
    private ImageView mImageRemove;
    private Context mContext;

    public GuestViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.mTextName = (TextView) itemView.findViewById(R.id.text_name);
        this.mImageRemove = (ImageView) itemView.findViewById(R.id.image_remove);
        this.mContext = context;
    }

    public void bindData(final GuestEntity guestEntity, final OnGuestListenerInteractionListener listener) {
        this.mTextName.setText(guestEntity.getName());
        this.mTextName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onListClick(guestEntity.getId());
            }
        });

        this.mTextName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(mContext, guestEntity.getId())
                        .setTitle(mContext.getString(R.string.title_remocao))
                        .setMessage(mContext.getString(R.string.message_remove))
                        .setIcon(R.drawable.remove)
                        .setPositiveButton(mContext.getString(R.string.sim), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listener.onDeleteClick(guestEntity.getId());
                            }
                        })
                        .setNeutralButton(mContext.getString(R.string.nao), null)
                        .show();
                return true;
            }
        });

        this.mImageRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mContext, guestEntity.getId())
                        .setTitle(mContext.getString(R.string.title_remocao))
                        .setMessage(mContext.getString(R.string.message_remove))
                        .setIcon(R.drawable.remove)
                        .setPositiveButton(mContext.getString(R.string.sim), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listener.onDeleteClick(guestEntity.getId());
                            }
                        })
                        .setNeutralButton(mContext.getString(R.string.nao), null)
                        .show();
            }
        });
    }
}
