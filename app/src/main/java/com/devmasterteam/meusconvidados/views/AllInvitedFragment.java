package com.devmasterteam.meusconvidados.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.devmasterteam.meusconvidados.R;
import com.devmasterteam.meusconvidados.adapter.GuestListAdapter;
import com.devmasterteam.meusconvidados.business.GuestBusiness;
import com.devmasterteam.meusconvidados.constants.GuestConstants;
import com.devmasterteam.meusconvidados.entities.GuestCount;
import com.devmasterteam.meusconvidados.entities.GuestEntity;
import com.devmasterteam.meusconvidados.listener.OnGuestListenerInteractionListener;

import java.util.List;


public class AllInvitedFragment extends Fragment {
    private ViewHolder mViewHolder = new ViewHolder();
    private GuestBusiness mGuestBusiness;
    private OnGuestListenerInteractionListener mOnGuestListenerInteractionListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_inveted, container, false);

        this.mViewHolder.mTextPresentCount = (TextView) view.findViewById(R.id.text_present_count);
        this.mViewHolder.mTextAbsentCount = (TextView) view.findViewById(R.id.text_absent_count);
        this.mViewHolder.mTextAllInvitedCount = (TextView) view.findViewById(R.id.text_all_invited_count);

        Context context = view.getContext();

        this.mGuestBusiness = new GuestBusiness(context);

        this.mOnGuestListenerInteractionListener = new OnGuestListenerInteractionListener() {
            @Override
            public void onListClick(int id) {
                Bundle bundle = new Bundle();
                bundle.putInt(GuestConstants.BundleConstants.GUEST_ID, id);

                Intent intent = new Intent(getContext(), GuestFormActivity.class);
                intent.putExtras(bundle);

                startActivity(intent);
            }

            @Override
            public void onDeleteClick(int id) {
                mGuestBusiness.remove(id);

                Toast.makeText(getContext(), getString(R.string.convidado_removido), Toast.LENGTH_LONG).show();

                loadDashboard();
                loadGuest();
            }
        };

        // Definir Recycler View
        this.mViewHolder.mRecyclerAllInvited = (RecyclerView) view.findViewById(R.id.recycler_all_invited);
        // Definir um layout
        this.mViewHolder.mRecyclerAllInvited.setLayoutManager(new LinearLayoutManager(context));

        this.loadGuest();
        this.loadDashboard();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        this.loadDashboard();
        this.loadGuest();
    }

    private void loadGuest() {
        // Buscar a lista de convidados
        List<GuestEntity> guestEntityList = this.mGuestBusiness.getInvited();
        // Definir um adapter
        GuestListAdapter guestListAdapter = new GuestListAdapter(guestEntityList, mOnGuestListenerInteractionListener);
        this.mViewHolder.mRecyclerAllInvited.setAdapter(guestListAdapter);
        guestListAdapter.notifyDataSetChanged();
    }

    private void loadDashboard() {
        GuestCount guestCount = this.mGuestBusiness.loadDashboard();

        this.mViewHolder.mTextPresentCount.setText(String.valueOf(guestCount.getPresentCount()));
        this.mViewHolder.mTextAbsentCount.setText(String.valueOf(guestCount.getAbsentCount()));
        this.mViewHolder.mTextAllInvitedCount.setText(String.valueOf(guestCount.getAllInvitedCount()));
    }

    private static class ViewHolder{
        TextView mTextPresentCount;
        TextView mTextAbsentCount;
        TextView mTextAllInvitedCount;

        RecyclerView mRecyclerAllInvited;
    }
}
