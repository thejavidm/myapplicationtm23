package mx.uabcs.idecasso.myapplicationtm.views.adapters;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import mx.uabcs.idecasso.myapplicationtm.R;
import mx.uabcs.idecasso.myapplicationtm.models.Phone;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.PhoneVH>{
    //region Variables & Constructor
    private List<Phone> phones;

    public PhoneAdapter(List<Phone> phones) {
        this.phones = phones;
    }
    //endregion

    //region Adapter Methods
    @NonNull
    @Override
    public PhoneVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_phone, parent, false);
        PhoneVH holder = new PhoneVH(mView);
        mView.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneVH holder, int position) {
        Phone mPhone = phones.get(position);
        holder.phone = mPhone;
        holder.number.setText(mPhone.getNumber());
        switch (mPhone.getCategory()){
            case Phone.HOME:
                holder.iconType.setImageResource(R.drawable.ic_home_black_24dp);
                break;
            case Phone.MOBILE:
                holder.iconType.setImageResource(R.drawable.ic_phone_android_black_24dp);
                break;
            case Phone.OFFICE: case Phone.WORK:
                holder.iconType.setImageResource(R.drawable.ic_work_black_24dp);
                break;
            case Phone.OTHER:
                holder.iconType.setImageResource(R.drawable.ic_phone_black_24dp);
                break;
        }
        holder.deleteIcon.setOnClickListener(view -> {
            delete(holder.phone);
            Snackbar.make(view, "Phone deleted", Snackbar.LENGTH_LONG)
                    .setAction("UNDO", viewUndo -> {
                        add(holder.phone, position);
                    })
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return phones.size();
    }
    //endregion

    //region CRUD Operations
    public void add(Phone phone){
        add(phone, phones.size());
    }

    public void add(Phone phone, int position){
        if(!phones.contains(phone)){
            position = position <= phones.size() ? position : phones.size();
            phones.add(position, phone);
            notifyItemInserted(position);
        }
    }

    public void delete(Phone phone){
        if(phones.contains(phone))
            delete(phones.indexOf(phone));
    }

    public void delete(int position){
        if(position >= 0 && position < phones.size()){
            phones.remove(position);
            notifyItemRemoved(position);
        }
    }
    //endregion

    //region ViewHolder
    public static class PhoneVH extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView iconType;
        ImageView deleteIcon;
        TextView number;
        Phone phone;

        public PhoneVH(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.phone_cardview);
            iconType = itemView.findViewById(R.id.phone_image_type);
            deleteIcon = itemView.findViewById(R.id.phone_delete);
            number = itemView.findViewById(R.id.phone_number);
        }
    }
    //endregion
}
