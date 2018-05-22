package mx.uabcs.idecasso.myapplicationtm.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import mx.uabcs.idecasso.myapplicationtm.R;
import mx.uabcs.idecasso.myapplicationtm.database.ContactDBH;
import mx.uabcs.idecasso.myapplicationtm.databinding.ActivityMainBinding;
import mx.uabcs.idecasso.myapplicationtm.models.Contact;
import mx.uabcs.idecasso.myapplicationtm.utils.Utils;
import mx.uabcs.idecasso.myapplicationtm.views.adapters.ContactAdapter;
import mx.uabcs.idecasso.myapplicationtm.views.adapters.PhoneAdapter;

import static mx.uabcs.idecasso.myapplicationtm.utils.Constants.CONTACT_DATA;
import static mx.uabcs.idecasso.myapplicationtm.utils.Constants.EDIT_CONTACT;
import static mx.uabcs.idecasso.myapplicationtm.utils.Constants.NEW_CONTACT;

public class MainActivity extends AppCompatActivity {
    //region Variables
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ActivityMainBinding binding;
    private List<Contact> contacts;
    private Contact mContact;
    private ContactAdapter adapter;

    //endregion

    //region Create and Inits
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

    }

    private void init() {
        initBinding();
        initOnClicks();
        initRecyclerView();
        initContact();
    }

    private void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        contacts = ContactDBH.getContacts(this);
        binding.setX(10);
        for(Contact contact: contacts){
            Log.i("contact", contact.getName());
        }
    }

    private void initContact() {
        Contact contact = Parcels.unwrap(getIntent().getParcelableExtra(CONTACT_DATA));
        if(contact != null) {
            mContact = contact;
        }else{
            mContact = new Contact();
        }
    }
    private void initRecyclerView() {
        adapter = new ContactAdapter(contacts);
        binding.contactsRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.contactsRecycler.setItemAnimator(new DefaultItemAnimator());
        binding.contactsRecycler.setAdapter(adapter);
    }

    private void initOnClicks() {

        binding.fab.setOnClickListener((view) ->{
            Utils.openActivityForResult(this, ContactActivity.class, NEW_CONTACT);
        });
    }


    //endregion

    //region Menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_about){
            Utils.openActivity(this, AboutActivity.class);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //endregion

    //region Process Activity Result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == NEW_CONTACT||requestCode == EDIT_CONTACT){
                Contact contact = Parcels.unwrap(data.getParcelableExtra(CONTACT_DATA));
                Snackbar.make(binding.fab, "Contact: " + contact.getName(), Snackbar.LENGTH_LONG)
                        .show();
                ContactDBH.addOrUpdate(this,contact);
                contacts.clear();
                contacts.addAll(ContactDBH.getContacts(this));
                adapter.notifyDataSetChanged();
            }
        }else if(resultCode == Activity.RESULT_CANCELED){
            Snackbar.make(binding.fab, "Action canceled by user", Snackbar.LENGTH_LONG)
                    .show();
        }else{
            Snackbar.make(binding.fab, "Action unkown", Snackbar.LENGTH_LONG)
                    .show();
        }
    }
    //endregion
}
