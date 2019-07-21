
package com.devmasterteam.meusconvidados.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.devmasterteam.meusconvidados.R;
import com.devmasterteam.meusconvidados.business.GuestBusiness;
import com.devmasterteam.meusconvidados.constants.GuestConstants;
import com.devmasterteam.meusconvidados.entities.GuestEntity;

public class GuestFormActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewHolder mViewHolder = new ViewHolder();
    private GuestBusiness mGuestBusiness;
    private int mGuestId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_form);

        this.mViewHolder.mEditName = (EditText) this.findViewById(R.id.edit_name);
        this.mViewHolder.mEditDocument = (EditText) this.findViewById(R.id.edit_document);
        this.mViewHolder.mRadioNotConfirmed = (RadioButton) this.findViewById(R.id.radio_not_confirmed);
        this.mViewHolder.mRadioPresent = (RadioButton) this.findViewById(R.id.radio_present);
        this.mViewHolder.mRadioAbsent = (RadioButton) this.findViewById(R.id.radio_absent);
        this.mViewHolder.mButtonSave = (Button) this.findViewById(R.id.button_save);
        this.mViewHolder.mButtonCancel = (Button) this.findViewById(R.id.button_cancel);

        this.mGuestBusiness = new GuestBusiness(this);

        this.setListeners();

        this.loadDataFromActivity();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button_save){
            this.handleSave();
        }
        else if(v.getId()==R.id.button_cancel){
            this.finish();
        }
    }

    private void setListeners() {
        this.mViewHolder.mButtonSave.setOnClickListener(this);
        this.mViewHolder.mButtonCancel.setOnClickListener(this);
    }

    private void handleSave() {

        if(!this.validateSave()){
            return;
        }

        GuestEntity guestEntity = new GuestEntity();
        guestEntity.setName(this.mViewHolder.mEditName.getText().toString());
        guestEntity.setDocument(this.mViewHolder.mEditDocument.getText().toString());

        if (this.mViewHolder.mRadioNotConfirmed.isChecked()){
            guestEntity.setConfirmed(GuestConstants.CONFIRMATION.NOT_CONFIRMED);
        }
        else if (this.mViewHolder.mRadioPresent.isChecked()){
            guestEntity.setConfirmed(GuestConstants.CONFIRMATION.PRESENT);
        }
        else if (this.mViewHolder.mRadioAbsent.isChecked()){
            guestEntity.setConfirmed(GuestConstants.CONFIRMATION.ABSENT);
        }

        if (this.mGuestId == 0){

            if(this.mGuestBusiness.insert(guestEntity)){
                Toast.makeText(this, getString(R.string.convidado_salvo_com_sucesso), Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, getString(R.string.erro_ao_salvar), Toast.LENGTH_LONG).show();
            }
        }else{
            guestEntity.setId(this.mGuestId);

            if(this.mGuestBusiness.update(guestEntity)){
                Toast.makeText(this, getString(R.string.convidado_salvo_com_sucesso), Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, getString(R.string.erro_ao_salvar), Toast.LENGTH_LONG).show();
            }
        }

        finish();
    }


    private void loadDataFromActivity() {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            this.mGuestId = bundle.getInt(GuestConstants.BundleConstants.GUEST_ID);
            GuestEntity guestEntity = this.mGuestBusiness.load(this.mGuestId);

            this.mViewHolder.mEditName.setText(guestEntity.getName());
            this.mViewHolder.mEditDocument.setText(guestEntity.getDocument());
            if(guestEntity.getConfirmed() == GuestConstants.CONFIRMATION.PRESENT){
                this.mViewHolder.mRadioPresent.setChecked(true);
            }
            else if(guestEntity.getConfirmed() == GuestConstants.CONFIRMATION.ABSENT){
                this.mViewHolder.mRadioAbsent.setChecked(true);
            }
            else{
                this.mViewHolder.mRadioNotConfirmed.setChecked(true);
            }
        }
    }

    private boolean validateSave() {
        if(this.mViewHolder.mEditName.getText().toString().equals("")){
            this.mViewHolder.mEditName.setError(getString(R.string.nome_obrigatorio));
            return false;
        }
        return true;
    }

    private static class ViewHolder {
        EditText mEditName;
        EditText mEditDocument;
        RadioButton mRadioNotConfirmed;
        RadioButton mRadioPresent;
        RadioButton mRadioAbsent;
        Button mButtonSave;
        Button mButtonCancel;
    }
}
