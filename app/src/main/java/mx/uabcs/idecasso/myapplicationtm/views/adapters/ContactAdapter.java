package mx.uabcs.idecasso.myapplicationtm.views.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.List;

import mx.uabcs.idecasso.myapplicationtm.R;
import mx.uabcs.idecasso.myapplicationtm.database.ContactDBH;
import mx.uabcs.idecasso.myapplicationtm.models.Contact;
import mx.uabcs.idecasso.myapplicationtm.views.activities.ContactActivity;

import static mx.uabcs.idecasso.myapplicationtm.utils.Constants.CONTACT_DATA;
import static mx.uabcs.idecasso.myapplicationtm.utils.Constants.EDIT_CONTACT;


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactVH> {
    //region Variables & Contructors
    private List<Contact> contacts;

    public ContactAdapter(List<Contact> contacts) {
        this.contacts = contacts;
    }
    //endregion

    //region Adapter methods
    @NonNull
    @Override
    public ContactVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext())
                                   .inflate(R.layout.item_contact, parent, false);
        ContactVH holder = new ContactVH(mView);
        mView.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactVH holder, int position) {
        Contact mContact = contacts.get(position);
        holder.contact = mContact;
        String name = "";
        String lastName = "";
        if(mContact.getName()!=null) {
            name = mContact.getName();
        }
        if(mContact.getLastname()!=null) {
            lastName = mContact.getLastname();
        }
        holder.name.setText(name + lastName);
        if (!mContact.getPhones().isEmpty()) {
            holder.number.setText(mContact.getPhones().get(0).getNumber());
        }
        holder.cardView.setOnClickListener(view -> {
            Intent contact = new Intent(view.getContext(), ContactActivity.class);
            contact.putExtra(CONTACT_DATA, Parcels.wrap(mContact));
            ((Activity) view.getContext()).startActivityForResult(contact, EDIT_CONTACT);
        });
        holder.deleteIcon.setOnClickListener(view -> {
            delete(holder.contact);
            ContactDBH.delete(view.getContext(), holder.contact);
            Snackbar.make(view, "Contact deleted", Snackbar.LENGTH_LONG)
                    .setAction("UNDO", viewUndo -> {
                        add(holder.contact, position);
                        ContactDBH.addOrUpdate(view.getContext(), holder.contact);
                    })
                    .show();
        });
    }

    //region CRUD Operations
    public void add(Contact contact) {
        add(contact, contacts.size());
    }

    public void add(Contact contact, int position) {
        if (!contacts.contains(contact)) {
            contacts.add(position, contact);
            notifyItemInserted(position);
        }
    }

    public void delete(Contact contact) {
        if (contacts.contains(contact))
            delete(contacts.indexOf(contact));
    }

    public void delete(int position) {
        if (position >= 0 && position < contacts.size()) {
            contacts.remove(position);
            notifyItemRemoved(position);
        }
    }
    //endregion

    @Override
    public int getItemCount() {
        return contacts.size();
    }
    //endregion

    //region ViewHolder
    public static class ContactVH extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView img;
        ImageView deleteIcon;
        TextView name;
        TextView number;
        Contact contact;

        public ContactVH(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.contact_cardview);
            deleteIcon = itemView.findViewById(R.id.contact_delete);
            img = itemView.findViewById(R.id.contact_img);
            name = itemView.findViewById(R.id.contact_name);
            number = itemView.findViewById(R.id.contact_number);
        }
    }
    //endregion
}
