package mx.uabcs.idecasso.myapplicationtm.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.InputType;
import android.view.MenuItem;


import com.afollestad.materialdialogs.MaterialDialog;

import org.parceler.Parcels;

import mx.uabcs.idecasso.myapplicationtm.R;
import mx.uabcs.idecasso.myapplicationtm.database.ContactDBH;
import mx.uabcs.idecasso.myapplicationtm.databinding.ActivityContactBinding;
import mx.uabcs.idecasso.myapplicationtm.models.Contact;
import mx.uabcs.idecasso.myapplicationtm.models.Phone;
import mx.uabcs.idecasso.myapplicationtm.views.adapters.PhoneAdapter;
import mx.uabcs.idecasso.myapplicationtm.views.adapters.ContactAdapter;

import static mx.uabcs.idecasso.myapplicationtm.utils.Constants.CONTACT_DATA;
import static mx.uabcs.idecasso.myapplicationtm.utils.Constants.EMPTY_STRING;

public class ContactActivity extends AppCompatActivity {
    //region Variables
    private ActivityContactBinding binding;
    private Contact mContact;
    private PhoneAdapter adapter;

    //endregion

    //region Create and Inits
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        initBinding();
        initContact();
        initRecyclerView();
        initOnClicks();
        enableBackArrow();
    }

    private void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact);
    }

    private void initContact() {
        Contact contact = Parcels.unwrap(getIntent().getParcelableExtra(CONTACT_DATA));
        if(contact != null) {
            mContact = contact;
            binding.contactName.setText(mContact.getName());
        }else{
            mContact = new Contact();
        }
    }

    private void initOnClicks() {
        binding.fab.setOnClickListener(view -> {
            fillContact();
            Intent data = new Intent();
            data.putExtra(CONTACT_DATA, Parcels.wrap(mContact));
            setResult(Activity.RESULT_OK, data);
            finish();
        });
        binding.phoneAdd.setOnClickListener(view -> createPhone());
    }

    private void initRecyclerView() {
        adapter = new PhoneAdapter(mContact.getPhones());
        binding.phonesRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.phonesRecycler.setItemAnimator(new DefaultItemAnimator());
        binding.phonesRecycler.setAdapter(adapter);
    }

    private void enableBackArrow() {
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
    }
    //endregion

    //region Fill data
    private void fillContact() {
        String name = binding.contactName.getText().toString();
        if (!name.equals(EMPTY_STRING)) {
            mContact.setName(name);
        }
    }

    private void createPhone() {
        Phone phone = new Phone();
        phone.setCategory(Phone.MOBILE);
        new MaterialDialog.Builder(this)
                .title("Número de teléfono")
                .content("Introduzca el número de teléfono")
                .inputRange(5,10)
                .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_PHONE)
                .input("Número de telefono", "",
                        (dialog, input) -> {

                            if (!input.toString().equals(EMPTY_STRING)) {
                                phone.setNumber(input.toString());
                                ContactDBH.addOrUpdate(this, phone);
                                adapter.add(phone);
                            }
                        })
                .show();
    }
    //end region
}
