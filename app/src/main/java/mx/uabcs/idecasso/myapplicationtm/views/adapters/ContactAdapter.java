package mx.uabcs.idecasso.myapplicationtm.views.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import mx.uabcs.idecasso.myapplicationtm.R;
import mx.uabcs.idecasso.myapplicationtm.models.Contact;


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
        holder.name.setText(mContact.getName() + " " + mContact.getLastname());
        if(!mContact.getPhones().isEmpty())
            holder.number.setText(mContact.getPhones().get(0).getNumber());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
    //endregion

    //region ViewHolder
    public static class ContactVH extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView img;
        TextView name;
        TextView number;
        Contact contact;

        public ContactVH(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.contact_cardview);
            img = itemView.findViewById(R.id.contact_img);
            name = itemView.findViewById(R.id.contact_name);
            number = itemView.findViewById(R.id.contact_number);
        }
    }
    //endregion
}
